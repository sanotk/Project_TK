package com.mypjgdx.esg.game.objects;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.game.Assets;

public class Player extends AbstractGameObject {

    // จำนวนเฟรมต่อ 1 ทิศ
    private static final int FRAME_PER_DIRECTION = 3;

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ player
    private static final float SCALE = 0.2f;

    private static final float INTITAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
    private static final float INTITAL_X_POSITION = 0f;         // ตำแหน่งเริ่มต้นแกน X
    private static final float INTITAL_Y_POSITION = 0f;      // ตำแหน่งเริ่มต้นแกน Y

    // ทิศที่ตัวละครมอง
    public enum ViewDirection {
        LEFT, RIGHT, UP, DOWN
    }

    private ViewDirection viewDirection;  // ทิศที่ตัวละครกำลังมองอยู่
    private TextureAtlas playerAtlas;       // Texture ทั้งหมดของตัวละคร
    private TextureRegion playerRegion;   // ส่วน Texture ของตัวละครที่จะใช้แสดง

    private Animation walkLeft;
    private Animation walkRight;
    private Animation walkUp;
    private Animation walkDown;

    private TiledMapTileLayer mapLayer;
    // เวลา Animation ที่ใช้หา KeyFrame
    private float animationTime;
    Vector2 oldPosition;
    public Player(TiledMapTileLayer mapLayer) {

        this(INTITAL_X_POSITION, INTITAL_Y_POSITION);
        oldPosition = new Vector2();
        this.mapLayer = mapLayer;
    }

    public Player(float xPosition, float yPosition) {
        // กำหนดค่าเริ่มต้น เวลาสร้างตัวละครใหม่
        init();
        position.set(xPosition, yPosition);
    }

    public void init() {
        // Load Texture ทั้งหมดของตัวละคร
        playerAtlas = Assets.instance.playerAltas;

        // สร้างกลุ่มของ Region ของ player พร้อมทั้ง เรียงชื่อ Region ตามลำดับตัวอักษร
        Array<AtlasRegion> playerRegions = new Array<AtlasRegion>(playerAtlas.getRegions());
        playerRegions.sort(new playerRegionComparator());

        // สร้างกลุ่มของ Region ของ player แต่ละทิศการเดิน
        Array<AtlasRegion> playerWalkLeftRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> playerWalkRightRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> playerWalkDownRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> playerWalkUpRegions = new Array<AtlasRegion>();

        // เซ็ตค่าอนิเมชั่นของตัวละคร
        playerWalkUpRegions.addAll(playerRegions, 0 * FRAME_PER_DIRECTION,  FRAME_PER_DIRECTION);
        playerWalkDownRegions.addAll(playerRegions, 1 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);
        playerWalkLeftRegions.addAll(playerRegions, 2 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);
        playerWalkRightRegions.addAll(playerRegions, 3 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);

        // สร้าง Animation ทิศการเดินต่างๆ
        walkLeft = new Animation(FRAME_DURATION, playerWalkLeftRegions, PlayMode.LOOP);
        walkRight = new Animation(FRAME_DURATION, playerWalkRightRegions, PlayMode.LOOP);
        walkDown = new Animation(FRAME_DURATION, playerWalkDownRegions, PlayMode.LOOP);
        walkUp = new Animation(FRAME_DURATION, playerWalkUpRegions, PlayMode.LOOP);

        // กำหนดค่าทางฟิสิกส์
        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
        acceleration.set(0.0f, 0.0f);

        // กำหนดค่าเริ่มต้นให้  player หันไปหน้าไปทิศใต้
        viewDirection = ViewDirection.DOWN;

        // กำหนดเวลา Animation เริ่มต้นเท่ากับ 0
        animationTime = 0.0f;

        // กำหนดขนาดสเกลของ player
        scale.set(SCALE, SCALE);
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

        updateViewDirection();
        updateKeyFrame(deltaTime);
    }


    private void updateViewDirection() { // update ทิศที่ player มองอยู่  โดยยึดการมองด้านแกน X  เป็นหลักหากมีการเดินเฉียง
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

        // อัพเดท TextureRegion ของ player
        switch(viewDirection) {
        case DOWN: playerRegion = walkDown.getKeyFrame(animationTime); break;
        case LEFT: playerRegion = walkLeft.getKeyFrame(animationTime); break;
        case RIGHT: playerRegion = walkRight.getKeyFrame(animationTime); break;
        case UP: playerRegion = walkUp.getKeyFrame(animationTime); break;
        default:
            break;
        }
        // อัพเดทขนาดของตัวละครตาม Region

        setDimension(playerRegion.getRegionWidth(), playerRegion.getRegionHeight());
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
        if (cellX < mapLayer.getWidth() && cellX >= 0 && cellY <= mapLayer.getHeight() && cellY >= 0) {
            return mapLayer.getCell(cellX, cellY).getTile().getProperties().containsKey("blocked");
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {

        // วาดตัวละคร ตามตำแหน่ง ขนาด และองศาตามที่กำหนด
        render(batch, playerRegion);
    }

    // คลาสสร้างเองภายใน ที่ใช้เรียงชื่อของออปเจค AtlasRegion ตามลำดับอักษร
    private static class playerRegionComparator implements Comparator<AtlasRegion> {
        @Override
        public int compare(AtlasRegion region1, AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

}
