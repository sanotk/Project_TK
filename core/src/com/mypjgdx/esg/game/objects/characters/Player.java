package com.mypjgdx.esg.game.objects.characters;

import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player.PlayerAnimation;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.Beam;
import com.mypjgdx.esg.game.objects.weapons.Bullet;
import com.mypjgdx.esg.game.objects.weapons.Trap;
import com.mypjgdx.esg.game.objects.weapons.Weapon;
import com.mypjgdx.esg.utils.Direction;

public class Player extends AnimatedObject<PlayerAnimation> implements Damageable {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ player
    private static final float SCALE = 0.7f;

    private static final float INITIAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
    private static final float INITIAL_MOVING_SPEED = 120f;

    private static final int INTITAL_HEALTH = 10;
    private static final int INTITAL_TRAP = 3;
    private static final int INTITAL_BULLET = 25;
    private static final int INTITAL_BEAM = 1;

    public enum PlayerAnimation {
        ATK_LEFT,
        ATK_RIGHT,
        ATK_DOWN,
        ATK_UP,
        WALK_LEFT,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_UP,
    }

    public enum PlayerState {
    	WALK, ATTACK
    }

    public Item item;

    private PlayerState state;
    private int health;
    public int trapCount;
    public int bulletCount;
    public int beamCount;
    private boolean dead;
    private boolean invulnerable;
    private boolean knockback;

    private long lastInvulnerableTime;
    private long invulnerableTime;
    private float movingSpeed;

    private TiledMapTileLayer mapLayer;
    private Direction viewDirection;

    public Player(TiledMapTileLayer mapLayer, float positionX, float positionY) {
        super(Assets.instance.playerAltas);

        addNormalAnimation(PlayerAnimation.ATK_LEFT, FRAME_DURATION, 30, 3);
        addNormalAnimation(PlayerAnimation.ATK_RIGHT, FRAME_DURATION, 21, 3);
        addNormalAnimation(PlayerAnimation.ATK_UP, FRAME_DURATION, 3, 3);
        addNormalAnimation(PlayerAnimation.ATK_DOWN, FRAME_DURATION, 12, 3);
        addLoopAnimation(PlayerAnimation.WALK_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(PlayerAnimation.WALK_DOWN, FRAME_DURATION, 9, 3);
        addLoopAnimation(PlayerAnimation.WALK_LEFT, FRAME_DURATION, 27, 3);
        addLoopAnimation(PlayerAnimation.WALK_RIGHT, FRAME_DURATION, 18, 3);

        scale.set(SCALE, SCALE);
        movingSpeed = INITIAL_MOVING_SPEED;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        init(mapLayer, positionX, positionY);
    }

    public void init(TiledMapTileLayer mapLayer, float positionX, float positionY) {
        this.mapLayer = mapLayer;
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);

        state = PlayerState.WALK;
        setCurrentAnimation(PlayerAnimation.WALK_LEFT);
        viewDirection = Direction.LEFT;

        health = INTITAL_HEALTH;
        trapCount = INTITAL_TRAP;
        bulletCount = INTITAL_BULLET;
        beamCount = INTITAL_BEAM;

        dead = false;
        invulnerable = false;
        lastInvulnerableTime = 0;
        invulnerableTime = 0;
        setPosition(positionX, positionY);
    }

    @Override
	public void update(float deltaTime) {
        super.update(deltaTime);
        statusUpdate();
    }

    public void move(Direction direction) {
        if (knockback) return;
        switch(direction) {
        case LEFT:  velocity.x = -movingSpeed; break;
        case RIGHT: velocity.x = movingSpeed; break;
        case DOWN: velocity.y = -movingSpeed; break;
        case UP: velocity.y = movingSpeed; break;
        default:
            break;
        }
        viewDirection = direction;
        velocity.setLength(movingSpeed);
    }

    @Override
    public Direction getViewDirection() {
        return viewDirection;
    }

    @Override
    protected void setAnimation() {

        if (state == PlayerState.ATTACK) {
            unFreezeAnimation();
            switch (viewDirection) {
            case DOWN: setCurrentAnimation(PlayerAnimation.ATK_DOWN); break;
            case LEFT: setCurrentAnimation(PlayerAnimation.ATK_LEFT); break;
            case RIGHT: setCurrentAnimation(PlayerAnimation.ATK_RIGHT); break;
            case UP:  setCurrentAnimation(PlayerAnimation.ATK_UP); break;
            default:
                break;
            }
            if (isAnimationFinished(PlayerAnimation.ATK_LEFT) || isAnimationFinished(PlayerAnimation.ATK_RIGHT)) {
                state = PlayerState.WALK;
                resetAnimation();
            }
            if (isAnimationFinished(PlayerAnimation.ATK_UP) || isAnimationFinished(PlayerAnimation.ATK_DOWN)) {
                state = PlayerState.WALK;
                resetAnimation();
            }
        }
        else
        {
            unFreezeAnimation();
            switch (viewDirection) {
            case DOWN:setCurrentAnimation(PlayerAnimation.WALK_DOWN); break;
            case LEFT: setCurrentAnimation(PlayerAnimation.WALK_LEFT); break;
            case RIGHT: setCurrentAnimation(PlayerAnimation.WALK_RIGHT); break;
            case UP: setCurrentAnimation(PlayerAnimation.WALK_UP); break;
            default:
                break;
            }
            if (velocity.x == 0 && velocity.y == 0) {
                freezeAnimation();
                resetAnimation();
            }
        }
    }

    public void trapAttack(List<Weapon> weapons){
    	if(state != PlayerState.ATTACK){
    		state = PlayerState.ATTACK;
    		if(trapCount!=0){
    		    weapons.add(new Trap(mapLayer, this));
	            Assets.instance.bulletSound.play();
	            trapCount--;
    		}
            Assets.instance.bulletSound.play();
    		resetAnimation();
    	}
    }

    /*
    public boolean takeDamage(float knockbackSpeed, float knockbackAngle){
    	if (!invulnerable) {
    	    --health;
            if (health <= 0) {
                dead = true;
                return true;
            }
            takeInvulnerable(500);
            takeKnockback(knockbackSpeed, knockbackAngle);
            return true;
    	}
    	return false;
    }
    */

    public void statusUpdate() {
        if (invulnerable && TimeUtils.nanoTime() - lastInvulnerableTime > invulnerableTime)
            invulnerable = false;

        if (knockback && velocity.isZero()) {
            knockback =  false;
        }
    }

    public void takeInvulnerable(long duration) {
        invulnerable = true;
        lastInvulnerableTime = TimeUtils.nanoTime();
        invulnerableTime = TimeUtils.millisToNanos(duration);
    }

    public void takeKnockback(float knockbackSpeed, float knockbackAngle) {
        velocity.set(
                knockbackSpeed *MathUtils.cosDeg(knockbackAngle),
                knockbackSpeed *MathUtils.sinDeg(knockbackAngle));
        knockback = true;
    }

    public void rangeAttack(List<Weapon> weapons){
    	if (state != PlayerState.ATTACK){
    		state = PlayerState.ATTACK;
    		if(bulletCount!=0){
    		    weapons.add(new Bullet(mapLayer, this));
	            Assets.instance.bulletSound.play();
	            bulletCount--;
    		}
            Assets.instance.bulletSound.play();
    		resetAnimation();
    	}
    }

    public void beamAttack(List<Weapon> weapons){
    	if (state != PlayerState.ATTACK){
    		state = PlayerState.ATTACK;
    		if(beamCount!=0){
    		    weapons.add(new Beam(mapLayer, this));
	            Assets.instance.beamSound.play();
	            beamCount--;
    		}
            Assets.instance.beamSound.play();
    		resetAnimation();
    	}
    }

    public boolean isAlive(){
    	return !dead;
    }

    public void showHp(ShapeRenderer shapeRenderer){
    	shapeRenderer.rect(
    	        getPositionX(), getPositionY()-10,
    	        bounds.width * ((float) health / INTITAL_HEALTH), 5);
    }

    @Override
    public boolean takeDamage(float damage, float knockbackSpeed, float knockbackAngle) {
        if (!invulnerable) {
            health -= damage;
            if (health <= 0) {
                dead = true;
                return true;
            }
            takeInvulnerable(500);
            takeKnockback(knockbackSpeed, knockbackAngle);
            return true;
        }
        return false;
    }

    public void findItem(List<Item> items) {
        for(Item i: items) {
        	if (bounds.overlaps(i.bounds)) {
                i.addPlayer();;
        	}
        }
    }


    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}