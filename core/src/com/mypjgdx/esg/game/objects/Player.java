package com.mypjgdx.esg.game.objects;


import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;

public class Player extends AnimatedObject {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ player
    private static final float SCALE = 0.2f;

    private static final float INTITAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
    private static final float INTITAL_X_POSITION = 100;         // ตำแหน่งเริ่มต้นแกน X
    private static final float INTITAL_Y_POSITION = 100;      // ตำแหน่งเริ่มต้นแกน Y

    long lastAttackTime;

    public enum PlayerState {
    	WALK, ATTACK
    }

    public enum ViewDirection {
        LEFT, RIGHT, UP, DOWN
    }

    private PlayerState state = PlayerState.WALK; //สถานะของตัวละคร
    private ViewDirection viewDirection;

    public int count=0;
    boolean despawned = false;
    boolean finish = false;

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
        addLoopAnimation(AnimationName.ATK_LEFT, FRAME_DURATION, 0, 3);
        addLoopAnimation(AnimationName.ATK_RIGHT, FRAME_DURATION, 3, 3);
        addLoopAnimation(AnimationName.WALK_UP, FRAME_DURATION, 6, 3);
        addLoopAnimation(AnimationName.WALK_DOWN, FRAME_DURATION, 9, 3);
        addLoopAnimation(AnimationName.WALK_LEFT, FRAME_DURATION, 12, 3);
        addLoopAnimation(AnimationName.WALK_RIGHT, FRAME_DURATION, 15, 3);

        // กำหนดค่าทางฟิสิกส์
        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
        acceleration.set(0.0f, 0.0f);

        // กำหนดขนาดสเกลของ player
        scale.set(SCALE, SCALE);
        viewDirection = ViewDirection.DOWN;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateViewDirection();

    	if (count==20) { despawned = true;}
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

    private void updateViewDirection() { // update ทิศที่ player มองอยู่  โดยยึดการมองด้านแกน X  เป็นหลักหากมีการเดินเฉียง
        if (velocity.x != 0) {
            viewDirection = velocity.x < 0 ?  ViewDirection.LEFT : ViewDirection.RIGHT;
        }
        else if (velocity.y != 0) {
            viewDirection = velocity.y < 0 ?  ViewDirection.DOWN : ViewDirection.UP;
        }
    }

    public ViewDirection getViewDirection(){
        return viewDirection;
    }

    public void attack(){
    	if(state != PlayerState.ATTACK){
    		state = PlayerState.ATTACK;
    		resetAnimation();
    	}
    }

    public void hit_player(){
    	if(TimeUtils.nanoTime() - lastAttackTime > 1000000000) {
    		count++; lastAttackTime = TimeUtils.nanoTime();
    	}
    }

    public void rangeAttack(List<Sword>swords,TiledMapTileLayer mapLayer){
    	if(state != PlayerState.ATTACK){
    		state = PlayerState.ATTACK;
    		resetAnimation();
    		swords.add(new Sword(mapLayer, this));
            Assets.instance.bullet.play();
    	}
    }

    public boolean isDespawned(){
    	return despawned;
    }

    public boolean isFinish(){
    	return finish;
    }

    public void showHp(ShapeRenderer shapeRenderer){
    	if(count>=20){
    		count = 20;
    		despawned = true;
    	}
    	shapeRenderer.rect(getPositionX(), getPositionY()-10, dimension.x*(1-count/20f), 5);
    }

}
