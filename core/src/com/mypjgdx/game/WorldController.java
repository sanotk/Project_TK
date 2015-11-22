package com.mypjgdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class WorldController extends InputAdapter {

    private static final float CAMERA_SPEED = 200.0f; //กำหนดความเร็วในการเคลื่อนที่ของกล้อง
    private static final float CAMERA_ZOOM_SPEED = 1.0f; //ความเร็วของการซูม

    private final CameraHelper cameraHelper;

    private final Sano sano;

    public WorldController(Sano sano) {
        this.sano = sano;
        cameraHelper = new CameraHelper();
        Gdx.input.setInputProcessor(this);
    }

    public CameraHelper getCameraHelper() { return cameraHelper; }

    public void update (float deltaTime) {
        handleInput(deltaTime);
        sano.update(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleInput(float deltaTime) {
        handleCameraInput(deltaTime);
        handleSanoInput();
    }

    private void handleCameraInput(float deltaTime) {
        if (cameraHelper.hasTarget()) return; // มุมกล้องติดตาม Sano อยู่จะใช้ Input เลื่อนมุมกล้องเองไม่ได้
        if (Gdx.app.getType() != ApplicationType.Desktop) return; // ปุ่มกดสำหรับแฟลตฟอร์ม Desktop เท่านั้น

        if (Gdx.input.isKeyPressed(Keys.UP)) cameraHelper.addPostion(0, CAMERA_SPEED * deltaTime); //กดลูกศรขึ้น
        if (Gdx.input.isKeyPressed(Keys.DOWN)) cameraHelper.addPostion(0, -CAMERA_SPEED * deltaTime);//กดลูกศรลง
        if (Gdx.input.isKeyPressed(Keys.LEFT)) cameraHelper.addPostion(-CAMERA_SPEED * deltaTime, 0);//กดลูกศรซ้าย
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) cameraHelper.addPostion(CAMERA_SPEED * deltaTime, 0);//กดลูกศรขวา
        if (Gdx.input.isKeyPressed(Keys.Z)) cameraHelper.addZoom(CAMERA_ZOOM_SPEED * deltaTime); //กด z
        if (Gdx.input.isKeyPressed(Keys.X)) cameraHelper.addZoom(-CAMERA_ZOOM_SPEED * deltaTime); // กด x
    }

    private void handleSanoInput()  { // ควบคุม Sano
        if (!cameraHelper.hasTarget()) return; // มุมกล้องติดตาม Sano อยู่ถึงจะควมคุม Sano ได้
        if (Gdx.app.getType() != ApplicationType.Desktop) return; // ปุ่มกดสำหรับแฟลตฟอร์ม Desktop เท่านั้น

        final float SANO_SPEED = 100.0f;
        if (Gdx.input.isKeyPressed(Keys.UP)) sano.velocity.y = SANO_SPEED ;       //กดลูกศรขึ้น
        if (Gdx.input.isKeyPressed(Keys.DOWN)) sano.velocity.y = -SANO_SPEED ;      //กดลูกศรลง
        if (Gdx.input.isKeyPressed(Keys.LEFT)) sano.velocity.x = -SANO_SPEED ;      //กดลูกศรซ้าย
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) sano.velocity.x = SANO_SPEED ;     //กดลูกศรขวา
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.R) {    // กด R เพื่อ Reset มุมกล้อง ยกเลิกการมุมกล้องติดตาม Sano
            cameraHelper.setPosition(0, 0);
            cameraHelper.setZoom(1.0f);
            cameraHelper.setTarget(null);
        }
        else if (keycode == Keys.SPACE) { // กด Spacebar เพื่อให้มุมกล้องติดตาม/เลิกติดตาม Sano
            if (!cameraHelper.hasTarget()) cameraHelper.setTarget(sano);
            else cameraHelper.setTarget(null);
        }
        return false;
    }

}
