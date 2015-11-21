package com.mypjgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldController extends InputAdapter {

    private static final float CAMERA_SPEED = 200.0f; //กำหนดความเร็วในการเคลื่อนที่ของกล้อง
    private static final float CAMERA_MOVE_EDGE = 1/10f; //

    private static final float CAMERA_ZOOM_SPEED = 1.0f; //ความเร็วของการซูม

    private final Vector3 touch;
    private final Viewport viewport;
    private final CameraHelper cameraHelper;

    public WorldController(Viewport viewport) {
        this.viewport = viewport;
        cameraHelper = new CameraHelper();
        touch = new Vector3();

        Gdx.input.setInputProcessor(this);
    }

    public CameraHelper getCameraHelper() { return cameraHelper; }


    public void handleInput(float deltaTime) {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float halfWidth = worldWidth * 0.5f;
        float halfHeight = worldHeight * 0.5f;

        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
            viewport.unproject(touch);
            touch.add(halfWidth - cameraHelper.getPosition().x, halfHeight - cameraHelper.getPosition().y, 0.0f);

            if (touch.x > worldWidth * (1.0f - CAMERA_MOVE_EDGE)) {
                cameraHelper.addPostion(CAMERA_SPEED * deltaTime, 0);
            }
            else if (touch.x < worldWidth * CAMERA_MOVE_EDGE) {
                cameraHelper.addPostion(-CAMERA_SPEED * deltaTime, 0);
            }

            if (touch.y > worldHeight * (1.0f - CAMERA_MOVE_EDGE)) {
                cameraHelper.addPostion(0, CAMERA_SPEED * deltaTime);
            }
            else if (touch.y < worldHeight * CAMERA_MOVE_EDGE) {
                cameraHelper.addPostion(0, -CAMERA_SPEED * deltaTime);
            }
        }

        if (Gdx.input.isKeyPressed(Keys.UP)) cameraHelper.addPostion(0, CAMERA_SPEED * deltaTime); //กดลูกศรขึ้น
        if (Gdx.input.isKeyPressed(Keys.DOWN)) cameraHelper.addPostion(0, -CAMERA_SPEED * deltaTime);//กดลูกศรลง
        if (Gdx.input.isKeyPressed(Keys.LEFT)) cameraHelper.addPostion(-CAMERA_SPEED * deltaTime, 0);//กดลูกศรซ้าย
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) cameraHelper.addPostion(CAMERA_SPEED * deltaTime, 0);//กดลูกศรขวา
        if (Gdx.input.isKeyPressed(Keys.Z)) cameraHelper.addZoom(CAMERA_ZOOM_SPEED * deltaTime); //กด z
        if (Gdx.input.isKeyPressed(Keys.X)) cameraHelper.addZoom(-CAMERA_ZOOM_SPEED * deltaTime); // กด x
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.R) {
            cameraHelper.setPosition(0, 0);
        }
        else if (keycode == Keys.SPACE) {
            cameraHelper.setZoom(1.0f);
        }
        return false;
    }

}
