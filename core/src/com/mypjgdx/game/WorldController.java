package com.mypjgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldController extends InputAdapter {

    private static final float CAMERA_SPEED = 200.0f;
    private static final float CAMERA_MOVE_EDGE = 1/10f;

    private final Viewport viewport;
    private final Vector3 touch;

    public WorldController(Viewport viewport) {
        this.viewport = viewport;
        touch = new Vector3();

        Gdx.input.setInputProcessor(this);
    }

    public void update(int width, int height) {
        viewport.update(width, height);
    }

    public void handleInput(float deltaTime) {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float halfWidth = worldWidth * 0.5f;
        float halfHeight = worldHeight * 0.5f;
        Camera camera = viewport.getCamera();

        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
            viewport.unproject(touch);
            touch.add(halfWidth - camera.position.x, halfHeight - camera.position.y, 0.0f);

            if (touch.x > worldWidth * (1.0f - CAMERA_MOVE_EDGE)) {
                camera.position.x += CAMERA_SPEED * deltaTime;
            }
            else if (touch.x < worldWidth * CAMERA_MOVE_EDGE) {
                camera.position.x -= CAMERA_SPEED * deltaTime;
            }

            if (touch.y > worldHeight * (1.0f - CAMERA_MOVE_EDGE)) {
                camera.position.y += CAMERA_SPEED * deltaTime;
            }
            else if (touch.y < worldHeight * CAMERA_MOVE_EDGE) {
                camera.position.y -= CAMERA_SPEED * deltaTime;
            }
        }

        if (Gdx.input.isKeyPressed(Keys.UP)) camera.position.y += CAMERA_SPEED * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.DOWN)) camera.position.y -= CAMERA_SPEED * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) camera.position.x -= CAMERA_SPEED * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) camera.position.x += CAMERA_SPEED * deltaTime;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.R) {
            Camera camera = viewport.getCamera();
            camera.position.x = 0;
            camera.position.y = 0;
        }
        return false;
    }

}
