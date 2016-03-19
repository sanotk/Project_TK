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

    private Player player;

    private List<Sword> swords;
    private int count=0;
    private boolean despawned;
    private boolean pause = false;
    long lastDetectTime;

    boolean attack;
    int i;

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

    	despawned = false;

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

        if (bounds.overlaps(player.bounds)) {
        	player.hit_player();
        	float angle = MathUtils.atan2((player.bounds.y + player.bounds.height/2 - bounds.y - bounds.height/2),
             		(player.bounds.x + player.bounds.width/2 - bounds.x - bounds.width/2));
        	player.velocity.set(250f*MathUtils.cos(angle), 250f*MathUtils.sin(angle));

        }

        for(Sword s: swords) {
        	if (bounds.overlaps(s.bounds)) {
        		count++; pause = true;
        		s.despawn();
        		if(count==5){despawned = true; }
        	}
        };

        if(pause == true){
        	if(TimeUtils.nanoTime() - lastDetectTime > 500000000) {
        		runToPlayer(); lastDetectTime = TimeUtils.nanoTime();
        		pause = false;
        	}
        }
        else {
        	runToPlayer();
        }
    }

    public boolean isDespawned(){
    	return despawned;
    }
    private void runToPlayer(){

        if(walkQueue.isEmpty()) {
            pathFinding.setStart(bounds.x + bounds.width/2, bounds.y + bounds.height/2);
            pathFinding.setGoal(player.bounds.x + player.bounds.width/2, player.bounds.y + player.bounds.height/2);
            List<Node> list = pathFinding.findPath();
            int count = 0;
            list.remove(0);
            while(!list.isEmpty()) {
                if (count == 3) return;
                walkQueue.add(list.remove(0));
                ++count ;
            }
        }

        if (walkQueue.isEmpty()) return;
        Node n = walkQueue.getFirst();

        float xdiff = n.getPositionX() - bounds.x - bounds.width/2;
        float ydiff = n.getPositionY()- bounds.y - bounds.height/2;

        float distance =  (float) Math.sqrt(xdiff*xdiff + ydiff*ydiff);
        if (distance < 5f ) walkQueue.removeFirst();

        float angle = MathUtils.atan2(ydiff, xdiff);
        velocity.set(80f*MathUtils.cos(angle), 80f*MathUtils.sin(angle));
    }

    public void showHp(ShapeRenderer shapeRenderer){
    	if(count > 0)shapeRenderer.rect(getPositionX(), getPositionY()-10, dimension.x*(1-count/5f), 5);
    }

    private void randomPosition(TiledMapTileLayer mapLayer) {
        float mapWidth = mapLayer.getTileWidth()*mapLayer.getWidth();
        float mapHeight = mapLayer.getTileHeight()*mapLayer.getHeight();

        final double MIN_DISTANCE = 200;
        double distance;
        do{
            setPosition(
                    MathUtils.random(200,mapWidth-bounds.width),
                    MathUtils.random(200,mapHeight-bounds.height));

            float xdiff = getPositionX()-player.getPositionX();
            float ydiff = getPositionY()-player.getPositionY();

            distance =  Math.sqrt(xdiff*xdiff + ydiff*ydiff);


        } while ((distance <MIN_DISTANCE || collisionCheck.isCollidesTop() || collisionCheck.isCollidesBottom() || collisionCheck.isCollidesRight() || collisionCheck.isCollidesLeft()));
    }

}
