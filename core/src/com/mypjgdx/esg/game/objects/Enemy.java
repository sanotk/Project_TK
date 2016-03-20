package com.mypjgdx.esg.game.objects;

import static com.mypjgdx.esg.game.objects.AnimatedObject.AnimationName.WALK_DOWN;
import static com.mypjgdx.esg.game.objects.AnimatedObject.AnimationName.WALK_LEFT;
import static com.mypjgdx.esg.game.objects.AnimatedObject.AnimationName.WALK_RIGHT;
import static com.mypjgdx.esg.game.objects.AnimatedObject.AnimationName.WALK_UP;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.utils.Pathfinding;
import com.mypjgdx.esg.utils.Pathfinding.Node;

public class Enemy extends AnimatedObject {


    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ enemy
    private static final float SCALE = 0.2f;
    private static final float INTITAL_FRICTION = 600f;           // ค่าแรงเสียดทานเริ่มต้น

    private static final int INTITAL_HEALTH = 5;
    private static final float INTITAL_MOVING_SPEED = 60f;

    private Player player;
    private List<Sword> swords;

    private boolean alive;
    private boolean knockback;
    private boolean stun;
    private long stunTime;
    private long lastStunTime;

    private int health;
    private float movingSpeed;

    private Pathfinding pathFinding;
    private LinkedList<Node> walkQueue;

    public Enemy(TiledMapTileLayer mapLayer,Player player, List<Sword> swords) {
        super(Assets.instance.enemyAltas);

        this.player = player;
        this.swords = swords;
        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);
        init(mapLayer);
    }

    public void init(TiledMapTileLayer mapLayer) {
        addLoopAnimation(WALK_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(WALK_DOWN, FRAME_DURATION, 3, 3);
        addLoopAnimation(WALK_LEFT, FRAME_DURATION, 6, 3);
        addLoopAnimation(WALK_RIGHT, FRAME_DURATION, 9, 3);

    	alive = true;
    	knockback= false;
    	stun = false;
    	health =  INTITAL_HEALTH;
        movingSpeed = INTITAL_MOVING_SPEED;

        // กำหนดค่าทางฟิสิกส์
        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
        acceleration.set(0.0f, 0.0f);

        // กำหนดขนาดสเกลของ enemy
        scale.set(SCALE, SCALE);

        updateKeyFrame(0);
        setPosition(0, 0);

        randomPosition(mapLayer);

       	pathFinding = new Pathfinding(mapLayer);
       	walkQueue = new LinkedList<Node>();
    }

    @Override
    protected void setAnimation() {
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

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateStatus();

        if (bounds.overlaps(player.bounds)) {
            float ydiff = player.bounds.y + player.bounds.height/2 -bounds.y - bounds.height/2 ;
            float xdiff = player.bounds.x + player.bounds.width/2 - bounds.x - bounds.width/2;
            float angle = MathUtils.atan2(ydiff, xdiff);
            float knockbackSpeed = movingSpeed * 4f;

            if (player.takeDamage(knockbackSpeed, angle)) {
                takeKnockback(movingSpeed * 2.5f,  (float) (angle + Math.PI));
            }
        }

        for(Sword s: swords) {
        	if (bounds.overlaps(s.bounds) && !s.isDespawned()) {
                float knockbackSpeed = 100f;
                switch(s.getDirection()) {
                case DOWN: takeDamage(knockbackSpeed, 270 * MathUtils.degreesToRadians); break;
                case LEFT: takeDamage(knockbackSpeed, 180 * MathUtils.degreesToRadians); break;
                case RIGHT: takeDamage(knockbackSpeed, 0 * MathUtils.degreesToRadians); break;
                case UP: takeDamage(knockbackSpeed, 90 * MathUtils.degreesToRadians); break;
                default: break;
                }
                s.despawn();
        	}
        }
        runToPlayer(deltaTime);
    }

    public boolean isAlive(){
    	return alive;
    }

    public void moveLeft() {
        move(ViewDirection.LEFT);
    }

    public void moveRight() {
        move(ViewDirection.RIGHT);
    }

    public void moveUp() {
        move(ViewDirection.UP);
    }

    public void moveDown() {
        move(ViewDirection.DOWN);
    }

    private void move(ViewDirection direction) {
        if (knockback || stun) return;
        switch(direction) {
        case DOWN: velocity.y = -movingSpeed; break;
        case LEFT: velocity.x = -movingSpeed; break;
        case RIGHT: velocity.x = movingSpeed; break;
        case UP: velocity.y = movingSpeed; break;
        default:
            break;
        }
        viewDirection = direction;
    }

    public void takeDamage(float knockbackSpeed, float knockbackAngle){
        --health;
        if (health <= 0) {
            alive = false;
            return;
        }
        takeKnockback(knockbackSpeed, knockbackAngle);
    }

    public void takeKnockback(float knockbackSpeed, float knockbackAngle) {
        velocity.set(
                knockbackSpeed * MathUtils.cos(knockbackAngle),
                knockbackSpeed * MathUtils.sin(knockbackAngle));

        knockback = true;
    }

    public void takeStun(long duration) {
        stun = true;
        lastStunTime = TimeUtils.nanoTime();
        stunTime = TimeUtils.millisToNanos(duration);
    }

    private void updateStatus() {
        if (knockback && velocity.isZero()) {
            knockback =  false;
        }
        if (stun && TimeUtils.nanoTime() - lastStunTime > stunTime)
            stun = false;
    }


    private void runToPlayer(float deltaTime){
        if (walkQueue.isEmpty()) {
            pathFinding.setStart(
                    bounds.x + bounds.width/2,
                    bounds.y + bounds.height/2);
            pathFinding.setGoal(
                    player.bounds.x + player.bounds.width/2,
                    player.bounds.y + player.bounds.height/2);

            List<Node> list = pathFinding.findPath();
            list.remove(0);
            if(!list.isEmpty()) {
                walkQueue.add(list.get(0));
            }
        }
        if (walkQueue.isEmpty()) return;
        Node n = walkQueue.getFirst();

        float xdiff = n.getPositionX() - bounds.x - bounds.width/2;
        float ydiff = n.getPositionY()- bounds.y - bounds.height/2;
        float distance =  (float) Math.sqrt (xdiff*xdiff + ydiff*ydiff);
        if (distance < 1f ) {
            walkQueue.removeFirst();
            return;
        }

        if (xdiff >= 0) moveRight();
        else moveLeft();

        if (ydiff >= 0)  moveUp();
        else moveDown();
    }

    public void showHp(ShapeRenderer shapeRenderer){
    	if (health < INTITAL_HEALTH)
    	    shapeRenderer.rect(
    	            getPositionX(), getPositionY() - 10,
    	            dimension.x * ((float) health / INTITAL_HEALTH), 5);
    }

    private void randomPosition(TiledMapTileLayer mapLayer) {
        float mapWidth = mapLayer.getTileWidth()*mapLayer.getWidth();
        float mapHeight = mapLayer.getTileHeight()*mapLayer.getHeight();

        final float MIN_DISTANCE = 200;
        double distance;
        do{
            setPosition(
                    MathUtils.random(MIN_DISTANCE, mapWidth - bounds.width),
                    MathUtils.random(MIN_DISTANCE, mapHeight - bounds.height));

            float xdiff = getPositionX() - player.getPositionX();
            float ydiff = getPositionY() - player.getPositionY();

            distance =  Math.sqrt(xdiff*xdiff + ydiff*ydiff);

        } while ((distance < MIN_DISTANCE
                || collisionCheck.isCollidesTop()
                || collisionCheck.isCollidesBottom()
                || collisionCheck.isCollidesRight()
                || collisionCheck.isCollidesLeft()));
    }

}
