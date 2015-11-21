package com.mypjgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sano extends AbstractGameObject {

    // ทิศที่ตัวละครมอง
    public enum ViewDirection {
        LEFT, RIGHT, UP, DOWN
    }

    // TODO *** ยังไม่ใช้ตัวแปรนี้
    //private ViewDirection viewDirection;  // ทิศที่ตัวละครกำลังมองอยู่
    private TextureAtlas sanoAtlas;       // Texture ทั้งหมดของตัวละคร
    private TextureRegion sanoRegion;   // ส่วน Texture ของตัวละครที่จะใช้แสดง

    public Sano() {
        init(); // method กำหนดค่าเริ่มต้น เวลาสร้างตัวละครใหม่
    }

    public void init() {
        // Load Texture ทั้งหมดของตัวละคร
        sanoAtlas = new TextureAtlas(Gdx.files.internal("char_pack.atlas"));

        /*** Texture หน้าตรงแบบ 1 ***/
        sanoRegion = new TextureRegion(sanoAtlas.findRegion("char_front", 1));

        // กำหนดขนาดของตัวละคร 1/5 ของขนาดภาพ
        dimension.set(sanoRegion.getRegionWidth()/5, sanoRegion.getRegionHeight()/5);

        // กำหนดจุดกำเนิด (จุดหมุน) อยู่ตรงกึ่งกลางภาพ
        origin.set(dimension.x / 2, dimension.y / 2);

        // กำหนดกรอบตัวละคร เพื่อใช้เช็คการชน
        bounds.set(0, 0, dimension.x, dimension.y);

        // กำหนดค่าทางฟิสิกส์
        friction.set(400.0f, 400.0f);
        acceleration.set(0.0f, 0.0f);

        // TODO *** ยังไม่ใช้ตัวแปรนี้
        // กำหนดค่าเริ่มต้นให้ตัวละครหันไปทางขวา
        //viewDirection = ViewDirection.RIGHT;

        position.set(450f, 300f);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime); // update ตำแหน่งโดยเรียกใช้ method ของ superclass (AbstractGameObject)
    }

    @Override
    public void render(SpriteBatch batch) {

        // วาดตัวละคร ตามตำแหน่ง ขนาด และองศาตามที่กำหนด
        batch.draw(sanoRegion, position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y,
                scale.x, scale.y, rotation);
    }

}
