package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.Enemy.EnemyAnimation;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.Pathfinding;
import com.mypjgdx.esg.utils.Pathfinding.Node;

public abstract class Enemy extends AnimatedObject<EnemyAnimation> {

	// กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    private static final float INITIAL_FRICTION = 600f;
    private static final float INITIAL_FINDING_RANGE = 400f;

    protected Player player;
    protected List<Bullet> bullets;
    protected List<Beam> beams;
    protected List<Trap> traps;

    public enum EnemyAnimation {
        WALK_LEFT,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_UP,
    }

    private Direction viewDirection;

    private boolean dead;
    private boolean knockback;
    private boolean stun;

    private long stunTime;
    private long lastStunTime;

    private int health;
    protected int maxHealth;
    protected float movingSpeed;
    private float findingRange;
    private Pathfinding pathFinding;

    public Enemy(TextureAtlas atlas, float scaleX, float scaleY, TiledMapTileLayer mapLayer) {
        super(atlas);


        addLoopAnimation(EnemyAnimation.WALK_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(EnemyAnimation.WALK_DOWN, FRAME_DURATION, 3, 3);
        addLoopAnimation(EnemyAnimation.WALK_LEFT, FRAME_DURATION, 6, 3);
        addLoopAnimation(EnemyAnimation.WALK_RIGHT, FRAME_DURATION, 9, 3);

        findingRange = INITIAL_FINDING_RANGE;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        scale.set(scaleX, scaleY);
    }

	public void init(TiledMapTileLayer mapLayer) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        pathFinding = new Pathfinding(mapLayer);

        setCurrentAnimation(EnemyAnimation.WALK_DOWN);
        viewDirection = Direction.DOWN;

        health =  maxHealth;
        dead = false;
    	knockback= false;
    	stun = false;


        randomPosition(mapLayer);
    }

    @Override
    protected void setAnimation() {
        unFreezeAnimation();
        switch (viewDirection) {
        case DOWN:setCurrentAnimation(EnemyAnimation.WALK_DOWN); break;
        case LEFT: setCurrentAnimation(EnemyAnimation.WALK_LEFT); break;
        case RIGHT: setCurrentAnimation(EnemyAnimation.WALK_RIGHT); break;
        case UP: setCurrentAnimation(EnemyAnimation.WALK_UP); break;
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
            float angle = MathUtils.atan2(ydiff, xdiff) * MathUtils.radiansToDegrees ;
            float knockbackSpeed = 130 + movingSpeed * 1.2f;

            if (player.takeDamage(knockbackSpeed, angle)) {
                takeKnockback(100,  angle + 180);
            }
        }

        for(Bullet s: bullets) {
        	if (bounds.overlaps(s.bounds) && !s.isDespawned()) {
                float knockbackSpeed = 100f;
                switch(s.getDirection()) {
                case DOWN: takeDamage(knockbackSpeed, 270); break;
                case LEFT: takeDamage(knockbackSpeed, 180); break;
                case RIGHT: takeDamage(knockbackSpeed, 0); break;
                case UP: takeDamage(knockbackSpeed, 90); break;
                default: break;
                }
                s.despawn();
        	}
        }

        for(Beam b: beams) {
        	if (bounds.overlaps(b.bounds) && !b.isDespawned()) {
                float knockbackSpeed = 100f;
                switch(b.getDirection()) {
                case DOWN: takeDamage(knockbackSpeed, 270); break;
                case LEFT: takeDamage(knockbackSpeed, 180); break;
                case RIGHT: takeDamage(knockbackSpeed, 0); break;
                case UP: takeDamage(knockbackSpeed, 90); break;
                default: break;
                }
                b.despawn();
        	}
        }

        for(Trap t: traps) {
        	if (bounds.overlaps(t.bounds) && !t.isDespawned()) {
                float knockbackSpeed = 800f;
                switch(viewDirection) {
                case DOWN: takeDamage(knockbackSpeed, 90); break;
                case LEFT: takeDamage(knockbackSpeed, 0); break;
                case RIGHT: takeDamage(knockbackSpeed, 180); break;
                case UP: takeDamage(knockbackSpeed, 270); break;
                default: break;
                }
                //t.despawn();
        	}
        }
        runToPlayer(deltaTime);
    }

    public boolean isAlive(){
    	return !dead;
    }

    public void move(Direction direction) {
        if (knockback || stun) return;
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

    public void takeDamage(float knockbackSpeed, float knockbackAngle){
        --health;
        if (health <= 0) {
            dead = true;
            return;
        }
        takeKnockback(knockbackSpeed, knockbackAngle);
    }

    public void takeKnockback(float knockbackSpeed, float knockbackAngle) {
        velocity.set(
                knockbackSpeed * MathUtils.cosDeg(knockbackAngle),
                knockbackSpeed * MathUtils.sinDeg(knockbackAngle));

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

        final float startX = bounds.x + bounds.width/2;
        final float startY =  bounds.y + bounds.height/2;
        final float goalX =  player.bounds.x + player.bounds.width/2;
        final float goalY =  player.bounds.y + player.bounds.height/2;
        final float xDiff = startX-goalX;
        final float yDiff = startY-goalY;
        final double distance = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
        if (distance > findingRange) return;

        Array<Node> list = pathFinding.findPath(startX, startY, goalX, goalY);

        if (list.size > 0) {
            Node n = list.get(0);

            float xdiff = n.getPositionX() - bounds.x - bounds.width/2;
            float ydiff = n.getPositionY()- bounds.y - bounds.height/2;

            final float minMovingDistance = movingSpeed/8;

            if (ydiff >  minMovingDistance) {
                move(Direction.UP);
            }
            else if (ydiff < -minMovingDistance) {
                move(Direction.DOWN);
            }

            if (xdiff > minMovingDistance)  {
                move(Direction.RIGHT);
            }
            else if (xdiff < -minMovingDistance) {
                move(Direction.LEFT);
            }
        }
    }

    public void showHp(ShapeRenderer shapeRenderer){
    	if (health < maxHealth)
    	    shapeRenderer.rect(
    	            getPositionX(), getPositionY() - 10,
    	            bounds.width * ((float) health / maxHealth), 5);
    }

    private void randomPosition(TiledMapTileLayer mapLayer) {
        updateBounds();

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
