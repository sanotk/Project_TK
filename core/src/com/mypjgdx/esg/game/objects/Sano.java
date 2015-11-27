package com.mypjgdx.esg.game.objects;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Sano extends AbstractGameObject {

    // จำนวนเฟรมต่อ 1 ทิศ
    private static final int FRAME_PER_DIRECTION = 3;

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ Sano
    private static final float SCALE = 0.2f;

    private static final float INTITAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
    private static final float INTITAL_X_POSITION = 0f;         // ตำแหน่งเริ่มต้นแกน X
    private static final float INTITAL_Y_POSITION = 0f;      // ตำแหน่งเริ่มต้นแกน Y

    // ทิศที่ตัวละครมอง
    public enum ViewDirection {
        LEFT, RIGHT, UP, DOWN
    }

    private ViewDirection viewDirection;  // ทิศที่ตัวละครกำลังมองอยู่
    private TextureAtlas sanoAtlas;       // Texture ทั้งหมดของตัวละคร
    private TextureRegion sanoRegion;   // ส่วน Texture ของตัวละครที่จะใช้แสดง

    private Animation walkLeft;
    private Animation walkRight;
    private Animation walkUp;
    private Animation walkDown;

    // เวลา Animation ที่ใช้หา KeyFrame
    private float animationTime;

    public Sano() {
        init(); // method กำหนดค่าเริ่มต้น เวลาสร้างตัวละครใหม่
    }

    public void init() {
        // Load Texture ทั้งหมดของตัวละคร
        sanoAtlas = new TextureAtlas(Gdx.files.internal("char_pack.atlas"));

        // สร้างกลุ่มของ Region ของ Sano พร้อมทั้ง เรียงชื่อ Region ตามลำดับตัวอักษร
        Array<AtlasRegion> sanoRegions = new Array<AtlasRegion>(sanoAtlas.getRegions());
        sanoRegions.sort(new SanoRegionComparator());

        // สร้างกลุ่มของ Region ของ Sano แต่ละทิศการเดิน
        Array<AtlasRegion> sanoWalkLeftRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> sanoWalkRightRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> sanoWalkDownRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> sanoWalkUpRegions = new Array<AtlasRegion>();

        // เซ็ตค่าอนิเมชั่นของตัวละคร
        sanoWalkUpRegions.addAll(sanoRegions, 0 * FRAME_PER_DIRECTION,  FRAME_PER_DIRECTION);
        sanoWalkDownRegions.addAll(sanoRegions, 1 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);
        sanoWalkLeftRegions.addAll(sanoRegions, 2 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);
        sanoWalkRightRegions.addAll(sanoRegions, 3 * FRAME_PER_DIRECTION, FRAME_PER_DIRECTION);

        // สร้าง Animation ทิศการเดินต่างๆ
        walkLeft = new Animation(FRAME_DURATION, sanoWalkLeftRegions, PlayMode.LOOP);
        walkRight = new Animation(FRAME_DURATION, sanoWalkRightRegions, PlayMode.LOOP);
        walkDown = new Animation(FRAME_DURATION, sanoWalkDownRegions, PlayMode.LOOP);
        walkUp = new Animation(FRAME_DURATION, sanoWalkUpRegions, PlayMode.LOOP);

        // กำหนดค่าทางฟิสิกส์
        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
        acceleration.set(0.0f, 0.0f);

        // กำหนดค่าเริ่มต้นให้  sano หันไปหน้าไปทิศใต้
        viewDirection = ViewDirection.DOWN;

        // กำหนดเวลา Animation เริ่มต้นเท่ากับ 0
        animationTime = 0.0f;

        // กำหนดขนาดสเกลของ Sano
        scale.set(SCALE, SCALE);

        // กำหนดตำแหน่งเริ่มต้น
        position.set(INTITAL_X_POSITION, INTITAL_Y_POSITION);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime); // update ตำแหน่ง Sano
        updateViewDirection(); // update ทิศที่ Sano มองอยู่
        updateKeyFrame(deltaTime); // update Region ของ Sano ตามทิศที่มอง และ Keyframe

    }

    private void updateViewDirection() { // update ทิศที่ Sano มองอยู่  โดยยึดการมองด้านแกน X  เป็นหลักหากมีการเดินเฉียง
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

        // อัพเดท TextureRegion ของ Sano
        switch(viewDirection) {
        case DOWN: sanoRegion = walkDown.getKeyFrame(animationTime); break;
        case LEFT: sanoRegion = walkLeft.getKeyFrame(animationTime); break;
        case RIGHT: sanoRegion = walkRight.getKeyFrame(animationTime); break;
        case UP: sanoRegion = walkUp.getKeyFrame(animationTime); break;
        default:
            break;
        }

        // อัพเดทขนาดของตัวละครตาม Region
        setDimension(sanoRegion.getRegionWidth(), sanoRegion.getRegionHeight());
    }

    @Override
    public void render(SpriteBatch batch) {

        // วาดตัวละคร ตามตำแหน่ง ขนาด และองศาตามที่กำหนด
        render(batch, sanoRegion);
    }

    // คลาสสร้างเองภายใน ที่ใช้เรียงชื่อของออปเจค AtlasRegion ตามลำดับอักษร
    private static class SanoRegionComparator implements Comparator<AtlasRegion> {
        @Override
        public int compare(AtlasRegion region1, AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

}
