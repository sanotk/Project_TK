package com.mypjgdx.game;

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

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/10 วินาทีต่อเฟรม หรือ 10 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 10.0f;

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
        sanoAtlas = new TextureAtlas(Gdx.files.internal("char_test.atlas"));

        // สร้างกลุ่มของ Region ของ Sano พร้อมทั้ง เรียงชื่อ Region ตามลำดับตัวอักษร
        Array<AtlasRegion> sanoRegions = new Array<AtlasRegion>(sanoAtlas.getRegions());
        sanoRegions.sort(new SanoRegionComparator());

        // สร้างกลุ่มของ Region ของ Sano แต่ละทิศการเดิน
        Array<AtlasRegion> sanoWalkLeftRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> sanoWalkRightRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> sanoWalkDownRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> sanoWalkUpRegions = new Array<AtlasRegion>();

        sanoWalkLeftRegions.addAll(sanoRegions, 0, 3);
        sanoWalkRightRegions.addAll(sanoRegions, 3, 3);
        sanoWalkDownRegions.addAll(sanoRegions, 6, 3);
        sanoWalkUpRegions.addAll(sanoRegions, 9, 3);

        // สร้าง Animation ทิศการเดินต่างๆ
        walkLeft = new Animation(FRAME_DURATION, sanoWalkLeftRegions, PlayMode.LOOP);
        walkRight = new Animation(FRAME_DURATION, sanoWalkRightRegions, PlayMode.LOOP);
        walkDown = new Animation(FRAME_DURATION, sanoWalkDownRegions, PlayMode.LOOP);
        walkUp = new Animation(FRAME_DURATION, sanoWalkUpRegions, PlayMode.LOOP);

        // กำหนดค่าทางฟิสิกส์
        friction.set(400.0f, 400.0f);
        acceleration.set(0.0f, 0.0f);

        // กำหนดค่าเริ่มต้นให้  sano หันไปหน้าไปทิศใต้
        viewDirection = ViewDirection.DOWN;

        // กำหนดเวลา Animation เริ่มต้นเท่ากับ 0
        animationTime = 0.0f;

        // อัพเดท Region ของ Sano
        updateRegion();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime); // update ตำแหน่ง Sano โดยเรียกใช้ method ของ superclass (AbstractGameObject)
        updateViewDirection(); // update ทิศที่ Sano มองอยู่
        animationTime += deltaTime; // คำนวณเวลา Animation ใหม่
        updateRegion(); // update Region ของ Sano ตามทิศที่มอง และ Keyframe

    }

    private void updateViewDirection() { // update ทิศที่ Sano มองอยู่  โดยยึดการมองด้านแกน X  เป็นหลักหากมีการเดินเฉียง
        if (velocity.x != 0) {
            viewDirection = velocity.x < 0 ?  ViewDirection.LEFT : ViewDirection.RIGHT;
        }
        else if (velocity.y != 0) {
            viewDirection = velocity.y < 0 ?  ViewDirection.DOWN : ViewDirection.UP;
        }
    }

    private void updateRegion() {
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
        dimension.set(sanoRegion.getRegionWidth(), sanoRegion.getRegionHeight());

        // อัพเดทจุดกำเนิด (จุดหมุน) อยู่ตรงกึ่งกลางภาพ
        origin.set(dimension.x / 2, dimension.y / 2);

        // อัพเดทกรอบ  Sano เพื่อใช้เช็คการชน
        bounds.set(0, 0, dimension.x, dimension.y);
    }

    @Override
    public void render(SpriteBatch batch) {

        // วาดตัวละคร ตามตำแหน่ง ขนาด และองศาตามที่กำหนด
        batch.draw(sanoRegion, position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y,
                scale.x, scale.y, rotation);
    }

    /* คลาสสร้างเองภายใน ที่ใช้เรียงชื่อของออปเจค AtlasRegion ตามลำดับอักษร*/

    private static class SanoRegionComparator implements Comparator<AtlasRegion> {
        @Override
        public int compare(AtlasRegion region1, AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

}
