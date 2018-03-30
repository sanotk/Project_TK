package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.CollisionCheck;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player.PlayerAnimation;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.Beam;
import com.mypjgdx.esg.game.objects.weapons.Bullet;
import com.mypjgdx.esg.game.objects.weapons.Trap;
import com.mypjgdx.esg.game.objects.weapons.Weapon;
import com.mypjgdx.esg.game.objects.weapons.Weapon.WeaponType;
import com.mypjgdx.esg.utils.Direction;

import java.util.List;

public class Player extends AnimatedObject<PlayerAnimation> implements Damageable {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 16.0f;

    // อัตราการขยายภาพ player
    private static final float SCALE = 0.6f;

    private static final float INITIAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
    private static final float INITIAL_MOVING_SPEED = 120f;

    private static final int INTITAL_HEALTH = 10;
    private static final int INTITAL_TRAP = 3;
    private static final int INTITAL_TIME = 300;
    private static final int INTITAL_BULLET = 99999;
    private static final int INTITAL_BEAM = 3;

    public boolean status_find = false;
    public boolean status_windows_link = false;
    private float Countdown;

    public enum PlayerAnimation {
        ATK_LEFT,
        ATK_RIGHT,
        ATK_DOWN,
        ATK_UP,
        STAND_LEFT,
        STAND_RIGHT,
        STAND_DOWN,
        STAND_UP,
        WALK_LEFT,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_UP,
        ITEM_LEFT,
        ITEM_RIGHT,
        ITEM_DOWN,
        ITEM_UP,
        ITEM_STAND_LEFT,
        ITEM_STAND_RIGHT,
        ITEM_STAND_DOWN,
        ITEM_STAND_UP
    }

    public enum PlayerState {
    	STAND, ATTACK
    }

    private Item item;

    private PlayerState state;
    private int health;
    public int trapCount;
    public int bulletCount;
    public int beamCount;
    public int timeCount;
    private boolean dead;
    private boolean invulnerable;
    private boolean knockback;

    private long lastInvulnerableTime;
    private long invulnerableTime;
    private float movingSpeed;

    private TiledMapTileLayer mapLayer;
    private Direction viewDirection;


    public boolean status_solarcell = false;
    public boolean status_inverter = false;
    public boolean status_ccontroller = false;
    public boolean status_battery = false;
    public boolean status_door = false;

    protected CollisionCheck solarcellCheck;
    protected CollisionCheck inverterCheck;
    protected CollisionCheck ccontrollerCheck;
    protected CollisionCheck batteryCheck;
    protected CollisionCheck doorCheck;

    public Player(TiledMapTileLayer mapLayer, float positionX, float positionY) {
        super(Assets.instance.playerAltas);

        addLoopAnimation(PlayerAnimation.STAND_UP, FRAME_DURATION, 120, 8);
        addLoopAnimation(PlayerAnimation.STAND_DOWN, FRAME_DURATION, 112, 8);
        addLoopAnimation(PlayerAnimation.STAND_LEFT, FRAME_DURATION, 128, 8);
        addLoopAnimation(PlayerAnimation.STAND_RIGHT, FRAME_DURATION, 136, 8);
        addNormalAnimation(PlayerAnimation.ATK_LEFT, FRAME_DURATION, 32, 8);
        addNormalAnimation(PlayerAnimation.ATK_RIGHT, FRAME_DURATION, 40, 8);
        addNormalAnimation(PlayerAnimation.ATK_UP, FRAME_DURATION, 24, 8);
        addNormalAnimation(PlayerAnimation.ATK_DOWN, FRAME_DURATION, 16, 8);
        addLoopAnimation(PlayerAnimation.WALK_UP, FRAME_DURATION, 8, 8);
        addLoopAnimation(PlayerAnimation.WALK_DOWN, FRAME_DURATION, 0, 8);
        addLoopAnimation(PlayerAnimation.WALK_LEFT, FRAME_DURATION, 144, 8);
        addLoopAnimation(PlayerAnimation.WALK_RIGHT, FRAME_DURATION, 152, 8);
        addLoopAnimation(PlayerAnimation.ITEM_UP, FRAME_DURATION, 56, 8);
        addLoopAnimation(PlayerAnimation.ITEM_DOWN, FRAME_DURATION, 48, 8);
        addLoopAnimation(PlayerAnimation.ITEM_LEFT, FRAME_DURATION, 64, 8);
        addLoopAnimation(PlayerAnimation.ITEM_RIGHT, FRAME_DURATION, 72, 8);
        addLoopAnimation(PlayerAnimation.ITEM_STAND_UP, FRAME_DURATION, 88, 8);
        addLoopAnimation(PlayerAnimation.ITEM_STAND_DOWN, FRAME_DURATION, 80, 8);
        addLoopAnimation(PlayerAnimation.ITEM_STAND_LEFT, FRAME_DURATION, 96, 8);
        addLoopAnimation(PlayerAnimation.ITEM_STAND_RIGHT, FRAME_DURATION, 104, 8);

        scale.set(SCALE, SCALE);
        movingSpeed = INITIAL_MOVING_SPEED;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        init(mapLayer, positionX, positionY);
    }


    public void init(TiledMapTileLayer mapLayer, float positionX, float positionY) {
        this.mapLayer = mapLayer;
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        solarcellCheck = new TiledCollisionCheck(bounds, mapLayer, "solarcell");
        batteryCheck = new TiledCollisionCheck(bounds, mapLayer, "battery");
        inverterCheck = new TiledCollisionCheck(bounds, mapLayer, "inverter");
        ccontrollerCheck = new TiledCollisionCheck(bounds, mapLayer, "ccontroller");
        doorCheck = new TiledCollisionCheck(bounds, mapLayer, "door");

        state = PlayerState.STAND;
        setCurrentAnimation(PlayerAnimation.STAND_DOWN);
        viewDirection = Direction.DOWN;

        health = INTITAL_HEALTH;
        trapCount = INTITAL_TRAP;
        bulletCount = INTITAL_BULLET;
        timeCount = INTITAL_TIME;
        beamCount = INTITAL_BEAM;

        dead = false;
        invulnerable = false;
        lastInvulnerableTime = 0;
        invulnerableTime = 0;
        setPosition(positionX, positionY);
    }

    public void update(float deltaTime, List<Weapon> weapons) {
        super.update(deltaTime);
        statusUpdate();
        if (item != null) // ถ้ามีไอเทม
            item.setPosition(
                    getPositionX() + origin.x - item.origin.x,
                    getPositionY() + origin.y - item.origin.y);
        for(Weapon w: weapons) {
        	if (bounds.overlaps(w.bounds) && !w.isDestroyed() && w.type == WeaponType.ENEMYBALL) {
                w.attack(this);
        	}
        }
        Countdown +=deltaTime;
        if(Countdown>=1){
            timeCount--;
            Countdown = 0;
        }
        checkCollide();
    }

    private void checkCollide() {
        if (solarcellCheck.isCollidesBottom() || solarcellCheck.isCollidesLeft() ||
                solarcellCheck.isCollidesRight() || solarcellCheck .isCollidesTop()){
            status_solarcell = true;
        }
        else {
            status_solarcell = false;
        }

        if (batteryCheck.isCollidesBottom() || batteryCheck.isCollidesLeft() ||
                batteryCheck.isCollidesRight() || batteryCheck .isCollidesTop()){
            status_battery = true;
        }
        else {
            status_battery = false;
        }

        if (ccontrollerCheck.isCollidesBottom() || ccontrollerCheck.isCollidesLeft() ||
                ccontrollerCheck.isCollidesRight() || ccontrollerCheck .isCollidesTop()){
            status_ccontroller = true;
        }
        else {
            status_ccontroller = false;
        }

        if (inverterCheck.isCollidesBottom() || inverterCheck.isCollidesLeft() ||
                inverterCheck.isCollidesRight() || inverterCheck .isCollidesTop()){
            status_inverter = true;
        }
        else {
            status_inverter = false;
        }
        if (doorCheck.isCollidesBottom() || doorCheck.isCollidesLeft() ||
                doorCheck.isCollidesRight() || doorCheck .isCollidesTop()){
            status_door = true;
        }
        else {
            status_door = false;
        }
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
        if (state == PlayerState.STAND && velocity.x == 0 && velocity.y == 0) {
            unFreezeAnimation();
            if(item == null) {
                switch (viewDirection) {
                    case DOWN:
                        setCurrentAnimation(PlayerAnimation.STAND_DOWN);
                        break;
                    case LEFT:
                        setCurrentAnimation(PlayerAnimation.STAND_LEFT);
                        break;
                    case RIGHT:
                        setCurrentAnimation(PlayerAnimation.STAND_RIGHT);
                        break;
                    case UP:
                        setCurrentAnimation(PlayerAnimation.STAND_UP);
                        break;
                    default:
                        break;
                }
            }
            else {
                switch (viewDirection) {
                    case DOWN:
                        setCurrentAnimation(PlayerAnimation.ITEM_STAND_DOWN);
                        break;
                    case LEFT:
                        setCurrentAnimation(PlayerAnimation.ITEM_STAND_LEFT);
                        break;
                    case RIGHT:
                        setCurrentAnimation(PlayerAnimation.ITEM_STAND_RIGHT);
                        break;
                    case UP:
                        setCurrentAnimation(PlayerAnimation.ITEM_STAND_UP);
                        break;
                    default:
                        break;
                }
            }
        }
        else if (state == PlayerState.ATTACK && item == null) {
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
                state = PlayerState.STAND;
                resetAnimation();
            }
            if (isAnimationFinished(PlayerAnimation.ATK_UP) || isAnimationFinished(PlayerAnimation.ATK_DOWN)) {
                state = PlayerState.STAND;
                resetAnimation();
            }
        }else if (item != null) {
            unFreezeAnimation();
            switch (viewDirection) {
            case DOWN: setCurrentAnimation(PlayerAnimation.ITEM_DOWN); break;
            case LEFT: setCurrentAnimation(PlayerAnimation.ITEM_LEFT); break;
            case RIGHT: setCurrentAnimation(PlayerAnimation.ITEM_RIGHT); break;
            case UP:  setCurrentAnimation(PlayerAnimation.ITEM_UP); break;
            default:
                break;
            }
            if (velocity.x == 0 && velocity.y == 0) {
                freezeAnimation();
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
                state = PlayerState.STAND;
            }
        }
    }

    public void trapAttack(List<Weapon> weapons){
    	if(state != PlayerState.ATTACK && item == null){
    		state = PlayerState.ATTACK;
    		resetAnimation();
            if(trapCount!=0){
                weapons.add(new Trap(mapLayer, this));
                Assets.instance.bulletSound.play();
                trapCount--;
            }
    	}

    }

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
    	if (state != PlayerState.ATTACK && item == null){
    		state = PlayerState.ATTACK;
    		if(bulletCount!=0){
                weapons.add(new Bullet(mapLayer, this));
                Assets.instance.bulletSound.play();
	            bulletCount--;
    		}
    		resetAnimation();
    	}
    }

    public void beamAttack(List<Weapon> weapons){
    	if (state != PlayerState.ATTACK && item == null){
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
        if(health!=INTITAL_HEALTH) {
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(getPositionX(), getPositionY() - 10, bounds.width, 5);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(
                    getPositionX(), getPositionY() - 10,
                    bounds.width * ((float) health / INTITAL_HEALTH), 5);
        }
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

    public void findItem() {
        if(status_windows_link==true){
            status_windows_link = false;
            status_find = false;
        }
        else { // ถ้าหน้าต่างเมนูเปิดอยู่
            status_windows_link = true; // ให้หน้าต่างเมนูปิดลง
            status_find = true; // ให้สเตตัสค้นหาเป็นเท็จ หรือ ไม่มีการค้นหา
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
