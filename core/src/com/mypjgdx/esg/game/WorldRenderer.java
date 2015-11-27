package com.mypjgdx.esg.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WorldRenderer implements Disposable {

    public static final int SCENE_WIDTH = 1280; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 720; //เซตค่าความสูงของจอ

    private OrthographicCamera camera;//สร้างตัวแปรกล้อง
    private FitViewport viewport; //พื้นที่การมอง
    private SpriteBatch batch; //ตัวแปรการวาด
    private WorldController worldController; //ส่วนควบคุมเกม

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init () {
        camera = new OrthographicCamera(); //สร้างออปเจ็คกล้องเก็บไว้ในตัวแปร camera
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera); //สร้างออปเจ็คการมองของกล้องเก็บไว้ในตัวแปร
        batch = new SpriteBatch();//สร้างออปเจ็คไว้วาดสิ่งต่างๆ
    }

    public void render () {
        renderWorld();
        renderGui();
    }

    public void renderWorld() {
        worldController.cameraHelper.applyTo(camera); //อัพเดทมุมกล้อง
        batch.setProjectionMatrix(camera.combined); //เรนเดอร์ภาพให้สอดคล้องกับมุมกล้อง
        batch.begin(); //เริ่มวาด
        worldController.level.render(batch); // วาด Game World
        batch.end(); //สิ้นสุดการวาด
    }

    public void renderGui() {

    }

    public void resize(int width, int height) { //ปรับขนาด viewport ให้เหมาะสมกับขนาดจอที่เปลี่ยนไป
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose(); //คืนหน่วยความจำ
    }

}
