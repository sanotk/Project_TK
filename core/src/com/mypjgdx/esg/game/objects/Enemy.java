package com.mypjgdx.esg.game.objects;

import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.utils.Pathfinding;

public class Enemy extends AbstractGameObject {

    private static final int FRAME_PER_DIRECTION = 3;

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ enemy
    private static final float SCALE = 0.2f;

    private static final float INTITAL_FRICTION = 600f;           // ค่าแรงเสียดทานเริ่มต้น
    //private static final float INTITAL_X_POSITION = 200f;         // ตำแหน่งเริ่มต้นแกน X
    //private static final float INTITAL_Y_POSITION = 200f;      // ตำแหน่งเริ่มต้นแกน Y

    // ทิศที่ตัวละครมอง
    public enum ViewDirection { // ตัวแปร enum เก็บค่า
        LEFT, RIGHT, UP, DOWN
    }

    private ViewDirection viewDirection;  // ทิศที่ตัวละครกำลังมองอยู่
    private TextureAtlas enemyAtlas;       // Texture ทั้งหมดของตัวละคร
    private TextureRegion enemyRegion;   // ส่วน Texture ของตัวละครที่จะใช้แสดง

    private Animation walkLeft;
    private Animation walkRight;
    private Animation walkUp;
    private Animation walkDown;

    private TiledMapTileLayer mapLayer;
    private Player player;

    private List<Sword> swords;
    private int count=0;
    private boolean despawned;
    private boolean pause = false;
    long lastDetectTime;

    // เวลา Animation ที่ใช้หา KeyFrame
    private float animationTime;
    private Vector2 oldPosition;

    boolean attack;
    int i;

    private Pathfinding pathFinding;

    public Enemy(TiledMapTileLayer mapLayer ,Player player, List<Sword> swords) {

        oldPosition = new Vector2();
        this.mapLayer = mapLayer;
        this.player = player;
        this.swords = swords;
        init();
    }

    public void init() {
    	despawned = false;
        // Load Texture ทั้งหมดของตัวละคร
        enemyAtlas = Assets.instance.enemyAltas;

        // สร้างกลุ่มของ Region ของ enemy พร้อมทั้ง เรียงชื่อ Region ตามลำดับตัวอักษร
        Array<AtlasRegion> enemyRegions = new Array<AtlasRegion>(enemyAtlas.getRegions());
        enemyRegions.sort(new enemyRegionComparator());

        // สร้างกลุ่มของ Region ของ enemy แต่ละทิศการเดิน
        Array<AtlasRegion> enemyWalkLeftRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> enemyWalkRightRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> enemyWalkDownRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> enemyWalkUpRegions = new Array<AtlasRegion>();

        // เซ็ตค่าอนิเมชั่นของตัวละคร
        enemyWalkUpRegions.addAll(enemyRegions, 0 * FRAME_PER_DIRECTION,  FRAME_PER_DIRECTION);
        enemyWalkDownRegions.addAll(enemyRegions, 1 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);
        enemyWalkLeftRegions.addAll(enemyRegions, 2 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);
        enemyWalkRightRegions.addAll(enemyRegions, 3 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);

        // สร้าง Animation ทิศการเดินต่างๆ
        walkLeft = new Animation(FRAME_DURATION, enemyWalkLeftRegions, PlayMode.LOOP);
        walkRight = new Animation(FRAME_DURATION, enemyWalkRightRegions, PlayMode.LOOP);
        walkDown = new Animation(FRAME_DURATION, enemyWalkDownRegions, PlayMode.LOOP);
        walkUp = new Animation(FRAME_DURATION, enemyWalkUpRegions, PlayMode.LOOP);

        // กำหนดค่าทางฟิสิกส์
        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
        acceleration.set(0.0f, 0.0f);

        // กำหนดค่าเริ่มต้นให้  enemy หันไปหน้าไปทิศใต้
        viewDirection = ViewDirection.DOWN;

        // กำหนดเวลา Animation เริ่มต้นเท่ากับ 0
        animationTime = 0.0f;

        // กำหนดขนาดสเกลของ enemy
        scale.set(SCALE, SCALE);

        updateKeyFrame(0);
        updateBounds();

        float mapWidth = mapLayer.getTileWidth()*mapLayer.getWidth();
        float mapHeight = mapLayer.getTileHeight()*mapLayer.getHeight();
        double distance;
        final double MIN_DISTANCE = 200;
       	do{
    		position.x = MathUtils.random(200,mapWidth-bounds.width);
    		position.y = MathUtils.random(200,mapHeight-bounds.height);
    		updateBounds();
    		distance = Math.sqrt((position.x-player.position.x)*(position.x-player.position.x)+(position.y-player.position.y)*(position.y-player.position.y));

    	}while((distance <MIN_DISTANCE || collidesTop() || collidesBottom() || collidesRight() || collidesLeft()));

       	pathFinding = new Pathfinding(mapLayer);
    }

    @Override
    public void update(float deltaTime) {
        oldPosition.set(position);
        updateMotionX(deltaTime);
        updateMotionY(deltaTime);

        position.x += velocity.x * deltaTime;
        updateBounds();
        if (collidesLeft() || collidesRight()) {
            position.x = oldPosition.x;
            updateBounds();
        }

        position.y += velocity.y * deltaTime;
        updateBounds();
        if (collidesTop() || collidesBottom()) {
            position.y = oldPosition.y;
            updateBounds();
        }

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

        updateViewDirection();
        if(pause == true){
        	if(TimeUtils.nanoTime() - lastDetectTime > 500000000) {
        		runToPlayer(); lastDetectTime = TimeUtils.nanoTime();
        		pause = false;
        	}
        }
        else {
        	runToPlayer();
        }
        updateKeyFrame(deltaTime);
    }

    public boolean isDespawned(){
    	return despawned;
    }

    private void updateViewDirection() { // update ทิศที่ enemy มองอยู่  โดยยึดการมองด้านแกน X  เป็นหลักหากมีการเดินเฉียง
        if (velocity.x != 0) {
            viewDirection = velocity.x < 0 ?  ViewDirection.LEFT : ViewDirection.RIGHT;
        }
        else if (velocity.y != 0) {
            viewDirection = velocity.y < 0 ?  ViewDirection.DOWN : ViewDirection.UP;
        }
    }

    private void updateKeyFrame(float deltaTime) {

        // ถ้าตัวละครเคลื่อนที่อยู่ ในเพิ่มเวลา Animation ถ้าไม่เคลื่อนที่ให้เวลาเป็น 0 ( Frame ท่ายืน)
        if (velocity.x != 0 || velocity.y != 0) animationTime += deltaTime;
        else  animationTime = 0;

        // อัพเดท TextureRegion ของ enemy
        switch(viewDirection) {
        case DOWN: enemyRegion = walkDown.getKeyFrame(animationTime); break;
        case LEFT: enemyRegion = walkLeft.getKeyFrame(animationTime); break;
        case RIGHT: enemyRegion = walkRight.getKeyFrame(animationTime); break;
        case UP: enemyRegion = walkUp.getKeyFrame(animationTime); break;
        default:
            break;
        }
        // อัพเดทขนาดของตัวละครตาม Region

        setDimension(enemyRegion.getRegionWidth(), enemyRegion.getRegionHeight());
    }

    private void runToPlayer(){
        final float MIN_RANGE = 1f;
        final float MOVE_RANGE = 100f;

        final Vector2 enemyVelocity = velocity;
        final float ENEMY_SPEED = 80.0f;

        if((Math.abs(position.x - player.position.x) < MOVE_RANGE)||(Math.abs(position.y - player.position.y) < MOVE_RANGE)){
        if (Math.abs(position.x - player.position.x) < MIN_RANGE) enemyVelocity.x = 0;
        else if (position.x > player.position.x) enemyVelocity.x = -ENEMY_SPEED;
        else if (position.x < player.position.x)  enemyVelocity.x = ENEMY_SPEED;

        if (Math.abs(position.y - player.position.y) < MIN_RANGE) enemyVelocity.y = 0;
        else if (position.y > player.position.y) enemyVelocity.y = -ENEMY_SPEED;
        else if (position.y < player.position.y) enemyVelocity.y = ENEMY_SPEED;
        }
    }

    public boolean collidesRight() {
        for (float step = 0; step < bounds.height; step += mapLayer.getTileHeight())
            if (isCellBlocked (bounds.x + bounds.width, bounds.y + step))
                return true;
        return isCellBlocked (bounds.x + bounds.width, bounds.y + bounds.height);
    }

    public boolean collidesLeft() {
        for (float step = 0; step < bounds.height; step += mapLayer.getTileHeight())
            if (isCellBlocked (bounds.x, bounds.y + step))
                return true;
        return isCellBlocked (bounds.x, bounds.y + bounds.height);
    }

    public boolean collidesTop() {
        for(float step = 0; step < bounds.width; step += mapLayer.getTileWidth())
            if (isCellBlocked(bounds.x + step, bounds.y + bounds.height))
                return true;
        return isCellBlocked(bounds.x + bounds.width, bounds.y + bounds.height);
    }

    public boolean collidesBottom() {
        for(float step = 0; step < bounds.width; step += mapLayer.getTileWidth())
            if (isCellBlocked(bounds.x + step, bounds.y))
                return true;
        return isCellBlocked(bounds.x + bounds.width, bounds.y);
    }

    private boolean isCellBlocked(float x, float y) {
        int cellX = (int) (x/ mapLayer.getTileWidth());
        int cellY = (int) (y/ mapLayer.getTileHeight());
        if (cellX < mapLayer.getWidth() && cellX >= 0 && cellY < mapLayer.getHeight() && cellY >= 0) {
            return mapLayer.getCell(cellX, cellY).getTile().getProperties().containsKey("blocked");
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        // วาดตัวละคร ตามตำแหน่ง ขนาด และองศาตามที่กำหนด
        render(batch, enemyRegion);
    }

    public void showHp(ShapeRenderer shapeRenderer){
    	if(count > 0)shapeRenderer.rect(position.x, position.y-10, dimension.x*(1-count/5f), 5);
    }

    // คลาสสร้างเองภายใน ที่ใช้เรียงชื่อของออปเจค AtlasRegion ตามลำดับอักษร
    private static class enemyRegionComparator implements Comparator<AtlasRegion> {
        @Override
        public int compare(AtlasRegion region1, AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

}
