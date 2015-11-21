package com.mypjgdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sano extends AbstractGameObject {

    // ขนาดของตัวละคร
    private final float SANO_WIDTH = 100f;
    private final float SANO_HEIGHT = 100f;

    // ทิศที่ตัวละครมอง
    public enum ViewDirection {
        LEFT, RIGHT, UP, DOWN
    }

    private ViewDirection viewDirection;  // ทิศที่ตัวละครกำลังมองอยู่
    private TextureRegion sanoRegion;   // ส่วน Texture ของตัวละครที่จะใช้แสดง

    public Sano() {
        init(); // method กำหนดค่าเริ่มต้น เวลาสร้างตัวละครใหม่
    }

    public void init() {
        // กำหนดขนาดของตัวละคร
        dimension.set(SANO_WIDTH, SANO_HEIGHT);

        // กำหนดจุดกำเนิด (จุดหมุน) อยู่ตรงกึ่งกลางภาพ
        origin.set(dimension.x / 2, dimension.y / 2);

        // กำหนดกรอบตัวละคร เพื่อใช้เช็คการชน
        bounds.set(0, 0, dimension.x, dimension.y);

        // กำหนดค่าทางฟิสิกส์
        friction.set(0.0f, 0.0f);
        acceleration.set(0.0f, 0.0f);

        // กำหนดค่าเริ่มต้นให้ตัวละครหันไปทางขวา
        viewDirection = ViewDirection.RIGHT;

        /*** Texture สำหรับการทดสอบ ขนาดกว้างยาว 100px  ***/
        sanoRegion = new TextureRegion(new Texture(createPixmap(100, 100)));

    }

    /*** Method สร้าง Pixmap เพื่อใช้ทำ Texture สำหรับการทดสอบ***/
    private Pixmap createPixmap (int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);

        // สร้าง Pixmap ตามขนาดที่กำหนด และใส่สีเป็นแดง ความโปร่งแสง 50%
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        return pixmap;
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
