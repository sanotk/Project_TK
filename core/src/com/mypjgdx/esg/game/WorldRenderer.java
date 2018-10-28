package com.mypjgdx.esg.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class WorldRenderer implements Disposable {

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ

    private WorldController worldController; //ส่วนควบคุมเกม

    private OrthographicCamera camera;//สร้างตัวแปรกล้อง
    public ExtendViewport viewport; //พื้นที่การมอง

    private SpriteBatch batch; //ตัวแปรการวาด
    private OrthogonalTiledMapRenderer tiledRenderer; // ตัววาด Tiled
    private ShapeRenderer shapeRenderer; // วาดเส้นหรือรูปทรงต่างๆ

    private FrameBuffer lightFbo;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        camera = new OrthographicCamera(); //สร้างออปเจ็คกล้องเก็บไว้ในตัวแปร camera
        viewport = new ExtendViewport(SCENE_WIDTH, SCENE_HEIGHT, camera); //สร้างออปเจ็คการมองของกล้องเก็บไว้ในตัวแปร

        batch = new SpriteBatch();//สร้างออปเจ็คไว้วาดสิ่งต่างๆ
        tiledRenderer = new OrthogonalTiledMapRenderer(null, batch);

        lightFbo = new FrameBuffer(Pixmap.Format.RGB888, (int) viewport.getMinWorldWidth(), (int) viewport.getMinWorldHeight(), false);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void render() {
        renderWorld();
        renderGui();
    }

    private void renderWorld() {
        worldController.level.createFbo(batch, lightFbo);
        viewport.apply();
        worldController.cameraHelper.setZoom(1f);
        worldController.cameraHelper.applyTo(camera); //อัพเดทมุมกล้อง
        batch.setProjectionMatrix(viewport.getCamera().combined);
        tiledRenderer.setView(camera);
        shapeRenderer.setProjectionMatrix(camera.combined);
        worldController.level.render(batch, tiledRenderer, shapeRenderer); // วาด Game World
        worldController.level.renderFbo(batch, camera, lightFbo);
    }

    private void renderGui() {

    }

    public void resize(int width, int height) { //ปรับขนาด viewport ให้เหมาะสมกับขนาดจอที่เปลี่ยนไป
        viewport.update(width, height);

        lightFbo.dispose();
        lightFbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight(), false);
    }

    @Override
    public void dispose() {
        tiledRenderer.dispose();
        batch.dispose();
        lightFbo.dispose();
    }

}
