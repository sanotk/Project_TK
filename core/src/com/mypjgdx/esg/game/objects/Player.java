package com.mypjgdx.esg.game.objects;


import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;

public class Player extends AnimatedObject {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ player
    private static final float SCALE = 0.2f;

    private static final float INITIAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
    private static final float INITIAL_X_POSITION = 100f;         // ตำแหน่งเริ่มต้นแกน X
    private static final float INITIAL_Y_POSITION = 100f;      // ตำแหน่งเริ่มต้นแกน Y
    private static final float INITIAL_MOVING_SPEED = 120f;

    private static final int INTITAL_HEALTH = 20;

    public enum PlayerState {
    	WALK, ATTACK
    }

    private PlayerState state;
    private int health;
    private boolean alive;
    private boolean invulnerable;
    private boolean knockback;
    private long lastInvulnerableTime;
    private long invulnerableTime;
    private float movingSpeed;

    public Player(TiledMapTileLayer mapLayer) {
        this(INITIAL_X_POSITION, INITIAL_Y_POSITION);
        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);
    }

    public Player(float xPosition, float yPosition) {
        super(Assets.instance.playerAltas);
        // กำหนดค่าเริ่มต้น เวลาสร้างตัวละครใหม่
        init();
        setPosition(xPosition, yPosition);
    }

    public void init() {
        addNormalAnimation(AnimationName.ATK_LEFT, FRAME_DURATION, 0, 3);
        addNormalAnimation(AnimationName.ATK_RIGHT, FRAME_DURATION, 3, 3);
        addLoopAnimation(AnimationName.WALK_UP, FRAME_DURATION, 6, 3);
        addLoopAnimation(AnimationName.WALK_DOWN, FRAME_DURATION, 9, 3);
        addLoopAnimation(AnimationName.WALK_LEFT, FRAME_DURATION, 12, 3);
        addLoopAnimation(AnimationName.WALK_RIGHT, FRAME_DURATION, 15, 3);

        state = PlayerState.WALK; //สถานะของตัวละคร
        health = INTITAL_HEALTH;
        alive = true;
        invulnerable = false;
        lastInvulnerableTime = 0;
        invulnerableTime = 0;
        movingSpeed = INITIAL_MOVING_SPEED;

        // กำหนดค่าทางฟิสิกส์
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);
        acceleration.set(0.0f, 0.0f);

        // กำหนดขนาดสเกลของ player
        scale.set(SCALE, SCALE);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        statusUpdate();
    }

    public void move(ViewDirection direction) {
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
    protected void setAnimation() {

        if (state == PlayerState.ATTACK) {
            unFreezeAnimation();
            switch (viewDirection) {
            case DOWN: setCurrentAnimation(AnimationName.WALK_DOWN); break;
            case LEFT: setCurrentAnimation(AnimationName.ATK_LEFT); break;
            case RIGHT: setCurrentAnimation(AnimationName.ATK_RIGHT); break;
            case UP:  setCurrentAnimation(AnimationName.WALK_UP); break;
            default:
                break;
            }
            if (isAnimationFinished(AnimationName.ATK_LEFT) || isAnimationFinished(AnimationName.ATK_RIGHT)) {
                state = PlayerState.WALK;
                resetAnimation();
            }
        }
        else
        {
            unFreezeAnimation();
            switch (viewDirection) {
            case DOWN:setCurrentAnimation(AnimationName.WALK_DOWN); break;
            case LEFT: setCurrentAnimation(AnimationName.WALK_LEFT); break;
            case RIGHT: setCurrentAnimation(AnimationName.WALK_RIGHT); break;
            case UP: setCurrentAnimation(AnimationName.WALK_UP); break;
            default:
                break;
            }
            if (velocity.x == 0 && velocity.y == 0) {
                freezeAnimation();
                resetAnimation();
            }
        }
    }

    public void attack(){
    	if(state != PlayerState.ATTACK){
    		state = PlayerState.ATTACK;
    		resetAnimation();
    	}
    }

    public boolean takeDamage(float knockbackSpeed, float knockbackAngle){
    	if (!invulnerable) {
    	    --health;
            if (health <= 0) {
                alive = false;
                return true;
            }
            takeInvulnerable(500);
            takeKnockback(knockbackSpeed, knockbackAngle);
            return true;
    	}
    	return false;
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

    public void rangeAttack(List<Sword>swords,TiledMapTileLayer mapLayer){
    	if (state != PlayerState.ATTACK){
    		state = PlayerState.ATTACK;

            swords.add(new Sword(mapLayer, this));
            Assets.instance.bullet.play();

    		resetAnimation();
    	}
    }

    public boolean isAlive(){
    	return alive;
    }

    public void showHp(ShapeRenderer shapeRenderer){
    	shapeRenderer.rect(
    	        getPositionX(), getPositionY()-10,
    	        dimension.x * ((float) health / INTITAL_HEALTH), 5);
    }
}
