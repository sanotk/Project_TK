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

    private static final int SCENE_WIDTH = 1024;
    private static final int SCENE_HEIGHT = 576;

    private WorldController worldController;

    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private SpriteBatch batch;
    private OrthogonalTiledMapRenderer tiledRenderer;
    private ShapeRenderer shapeRenderer;

    private FrameBuffer lightFbo;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);

        batch = new SpriteBatch();//สร้างออปเจ็คไว้วาดสิ่งต่างๆ
        tiledRenderer = new OrthogonalTiledMapRenderer(null,batch);

        lightFbo = new FrameBuffer(Pixmap.Format.RGB888, (int)viewport.getMinWorldWidth(), (int)viewport.getMinWorldHeight(), false);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public void render() {
        worldController.level.createFbo(batch, lightFbo);
        viewport.apply();

        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        tiledRenderer.setView(camera);
        shapeRenderer.setProjectionMatrix(camera.combined);
        worldController.level.render(batch, tiledRenderer, shapeRenderer);
        worldController.level.renderFbo(batch, camera, lightFbo);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);

        lightFbo.dispose();
        lightFbo = new FrameBuffer(Pixmap.Format.RGB888, (int)viewport.getWorldWidth(), (int)viewport.getWorldHeight(), false);
    }

    @Override
    public void dispose() {
        tiledRenderer.dispose();
        batch.dispose();
        lightFbo.dispose();
    }



}
