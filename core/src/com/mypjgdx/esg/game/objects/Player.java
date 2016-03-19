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

    private static final float INTITAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
    private static final float INTITAL_X_POSITION = 100f;         // ตำแหน่งเริ่มต้นแกน X
    private static final float INTITAL_Y_POSITION = 100f;      // ตำแหน่งเริ่มต้นแกน Y
    private static final float INTITAL_MOVING_SPEED = 100f;

    private static final int INTITAL_HEALTH = 20;

    public enum PlayerState {
    	WALK, ATTACK
    }

    private PlayerState state;
    private int health;
    private boolean alive;
    private boolean invulnerable;
    private boolean applyingknockback;
    private long lastInvulnerableTime;
    private long invulnerableTime;
    private long lastKnockbackTime;
    private long knockbackTime;
    private float movingSpeed;

    public Player(TiledMapTileLayer mapLayer) {
        this(INTITAL_X_POSITION, INTITAL_Y_POSITION);
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
        movingSpeed = INTITAL_MOVING_SPEED;

        // กำหนดค่าทางฟิสิกส์
        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
        acceleration.set(0.0f, 0.0f);

        // กำหนดขนาดสเกลของ player
        scale.set(SCALE, SCALE);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        statusUpdate();
    }

    @Override
    protected void updateViewDirection() {}

    public void moveLeft() {
        if (applyingknockback) return;
        velocity.x = -movingSpeed;
        viewDirection = ViewDirection.LEFT;
    }

    public void moveRight() {
        if (applyingknockback) return;
        velocity.x = movingSpeed;
        viewDirection = ViewDirection.RIGHT;
    }

    public void moveUp() {
        if (applyingknockback) return;
        velocity.y = movingSpeed;
        viewDirection = ViewDirection.UP;
    }

    public void moveDown() {
        if (applyingknockback) return;
        velocity.y = -movingSpeed;
        viewDirection = ViewDirection.DOWN;
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

    public void takeDamage(float knockbackSpeed, float knockbackAngle){
    	if (!invulnerable) {
    	    --health;
            if (health <= 0) {
                alive = false;
                return;
            }
            invulnerable = true;
            lastInvulnerableTime = TimeUtils.nanoTime();
            invulnerableTime = TimeUtils.millisToNanos(1000);
    	}

    	acceleration.set(
                knockbackSpeed *MathUtils.cos(knockbackAngle),
                knockbackSpeed *MathUtils.sin(knockbackAngle));

        applyingknockback = true;
        lastKnockbackTime = TimeUtils.nanoTime();
        knockbackTime = TimeUtils.millisToNanos(300);
    }

    public void statusUpdate() {
        if (invulnerable && TimeUtils.nanoTime() - lastInvulnerableTime > invulnerableTime)
            invulnerable = false;

        if (applyingknockback && TimeUtils.nanoTime() - lastKnockbackTime > knockbackTime) {
            applyingknockback =  false;
            acceleration.set(0 ,0);
        }
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

    @Override
    protected void updateMotionX(float deltaTime) {
        super.updateMotionX(deltaTime);
        if (velocity.x >= 0)
            velocity.x = Math.min(velocity.x, 150f);
        else
            velocity.x = Math.max(velocity.x, -150f);
    }

    @Override
    protected void updateMotionY(float deltaTime) {
        super.updateMotionY(deltaTime);
        if (velocity.y >= 0)
            velocity.y = Math.min(velocity.y, 150f);
        else
            velocity.y = Math.max(velocity.y, -150f);
    }

}
