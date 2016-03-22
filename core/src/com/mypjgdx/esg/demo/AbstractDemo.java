package com.mypjgdx.esg.demo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** คลาสต้นฉบับสำหรับการสาธิตเกี่ยวกับเรื่องต่างๆ
 *
 * @author S-Kyousuke
 */

public abstract class AbstractDemo extends InputAdapter implements ApplicationListener {

    protected static final float SCENE_WIDTH = 1280f;
    protected static final float SCENE_HEIGHT = 720f;

    private OrthographicCamera camera;
    private Viewport viewport;
    protected ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        camera.position.set(0.0f, 0.0f, 0.0f);

        Gdx.input.setInputProcessor(this);
        init();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        events();
        logic();
        draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() { }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    abstract protected void init();
    abstract protected void events();
    abstract protected void logic() ;
    abstract protected void draw();

}
