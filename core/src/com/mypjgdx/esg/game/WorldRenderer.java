package com.mypjgdx.esg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WorldRenderer implements Disposable {

    public static final int SCENE_WIDTH = 1280; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 720; //เซตค่าความสูงของจอ

    private WorldController worldController; //ส่วนควบคุมเกม

    private OrthographicCamera camera;//สร้างตัวแปรกล้อง
    private FitViewport viewport; //พื้นที่การมอง

    private SpriteBatch batch; //ตัวแปรการวาด
    private OrthogonalTiledMapRenderer tiledRenderer; // ตัววาด Tiled
    private ShapeRenderer shapeRenderer; // วาดเส้นหรือรูปทรงต่างๆ

    private ShaderProgram shader;
    private SpriteBatch batch2;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init () {
        camera = new OrthographicCamera(); //สร้างออปเจ็คกล้องเก็บไว้ในตัวแปร camera
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera); //สร้างออปเจ็คการมองของกล้องเก็บไว้ในตัวแปร

        batch = new SpriteBatch();//สร้างออปเจ็คไว้วาดสิ่งต่างๆ
        tiledRenderer = new OrthogonalTiledMapRenderer(null,batch2);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);

        shader = new ShaderProgram(Gdx.files.internal("myshader.vert"), Gdx.files.internal("myshader.frag"));
        batch.setShader(shader);
    }

    public void render () {
        renderWorld();
        renderGui();
    }

    private void renderWorld() {
        worldController.cameraHelper.applyTo(camera); //อัพเดทมุมกล้อง
        batch.setProjectionMatrix(camera.combined); //เรนเดอร์ภาพให้สอดคล้องกับมุมกล้อง
        tiledRenderer.setView(camera);
        shapeRenderer.setProjectionMatrix(camera.combined);
        worldController.level.render(batch, tiledRenderer, shapeRenderer); // วาด Game World
    }

    private void renderGui() {

    }

    public void resize(int width, int height) { //ปรับขนาด viewport ให้เหมาะสมกับขนาดจอที่เปลี่ยนไป
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        tiledRenderer.dispose();
        batch.dispose();
        shader.dispose();
    }

}
