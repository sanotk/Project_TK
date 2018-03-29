package com.mypjgdx.esg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class WorldRenderer implements Disposable {

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ

    private WorldController worldController; //ส่วนควบคุมเกม

    private OrthographicCamera camera;//สร้างตัวแปรกล้อง
    private ExtendViewport viewport; //พื้นที่การมอง

    private SpriteBatch batch; //ตัวแปรการวาด
    private OrthogonalTiledMapRenderer tiledRenderer; // ตัววาด Tiled
    private ShapeRenderer shapeRenderer; // วาดเส้นหรือรูปทรงต่างๆ

    private ShaderProgram shader;
    private float[] resolution = new float[2];
    private FrameBuffer lightFbo;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init () {
        camera = new OrthographicCamera(); //สร้างออปเจ็คกล้องเก็บไว้ในตัวแปร camera
        viewport = new ExtendViewport(SCENE_WIDTH, SCENE_HEIGHT, camera); //สร้างออปเจ็คการมองของกล้องเก็บไว้ในตัวแปร

        batch = new SpriteBatch();//สร้างออปเจ็คไว้วาดสิ่งต่างๆ
        tiledRenderer = new OrthogonalTiledMapRenderer(null,batch);

        lightFbo = new FrameBuffer(Pixmap.Format.RGB888, (int)viewport.getMinWorldWidth(), (int)viewport.getMinWorldHeight(), false);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);

        shader = new ShaderProgram(Gdx.files.internal("myshader.vert"), Gdx.files.internal("myshader.frag"));
        //batch.setShader(shader);
    }

    public void render () {
        renderWorld();
        renderGui();
    }

    private void renderWorld() {
        createLightBuffer();
        viewport.apply();

        worldController.cameraHelper.applyTo(camera); //อัพเดทมุมกล้อง
        batch.setProjectionMatrix(viewport.getCamera().combined);
        tiledRenderer.setView(camera);
        shapeRenderer.setProjectionMatrix(camera.combined);
        worldController.level.render(batch, tiledRenderer, shapeRenderer); // วาด Game World

        batch.begin();
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batch.draw(lightFbo.getColorBufferTexture(),
                camera.position.x - camera.viewportWidth * camera.zoom / 2,
                camera.position.y - camera.viewportHeight * camera.zoom /2,
                0, 0,
                lightFbo.getColorBufferTexture().getWidth(), lightFbo.getColorBufferTexture().getHeight(),
                1 * camera.zoom, 1 * camera.zoom,
                0,
                0, 0,
                lightFbo.getColorBufferTexture().getWidth(), lightFbo.getColorBufferTexture().getHeight(),
                false, true);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.end();
    }

    private void createLightBuffer() {
        lightFbo.begin();

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(1, 1, 1, 1);
        batch.draw(Assets.instance.light,
                worldController.level.player.getPositionX() + worldController.level.player.origin.x  -  Assets.instance.light.getWidth()/ 2f,
                worldController.level.player.getPositionY() + worldController.level.player.origin.y -  Assets.instance.light.getHeight() / 2f);
        batch.draw(Assets.instance.light,
                worldController.level.items.get(0).p_x + worldController.level.items.get(0).origin.x - Assets.instance.light.getWidth()/2f,
                worldController.level.items.get(0).p_y + worldController.level.items.get(0).origin.y - Assets.instance.light.getHeight()/2f);
        batch.end();

        FrameBuffer.unbind();
    }

    private void renderGui() {

    }

    public void resize(int width, int height) { //ปรับขนาด viewport ให้เหมาะสมกับขนาดจอที่เปลี่ยนไป
        viewport.update(width, height);
        resolution[0] = width;
        resolution[1] = height;

        shader.begin();
        // setUniform2fv หมายถึง vector 2 มิติ เก็บตัวแปรขนิด float
        //argument ตัวแรก คือชื่อของตัวแปรให้ fragment shader
        //ตัวสองคือ ตัวแปรที่จะส่งค่า
        //ตัวสามคือ ตำแหน่งเริ่มต้นที่จะเกบ
        //ตัวสีคือจำนวนของข้อมูล ก็คือ 2 ตัว
        shader.setUniform2fv("resolution", resolution, 0, 2);
        shader.end();

        lightFbo.dispose();
        lightFbo = new FrameBuffer(Pixmap.Format.RGB888, (int)viewport.getWorldWidth(), (int)viewport.getWorldHeight(), false);
    }

    @Override
    public void dispose() {
        tiledRenderer.dispose();
        batch.dispose();
        shader.dispose();
        lightFbo.dispose();
    }



}
