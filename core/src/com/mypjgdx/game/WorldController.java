package com.mypjgdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;


public class WorldController extends InputAdapter implements GestureListener  {

    private static final float CAMERA_SPEED = 200.0f; //กำหนดความเร็วในการเคลื่อนที่ของกล้อง
    private static final float CAMERA_ZOOM_SPEED = 1.0f; //ความเร็วของการซูม

    private final CameraHelper cameraHelper;

    private final Sano sano;

    public WorldController(Sano sano) {
        this.sano = sano;
        cameraHelper = new CameraHelper();
        GestureDetector gestureDetector = new GestureDetector(this);

        if (Gdx.app.getType() == ApplicationType.Android){
            Gdx.input.setInputProcessor(gestureDetector);
        }
        else  Gdx.input.setInputProcessor(this);

        cameraHelper.setTarget(sano);
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

        final float SANO_SPEED = 100.0f;

        if (Gdx.app.getType() == ApplicationType.Android && Gdx.input.isTouched())  {
            int screenWidth = Gdx.graphics.getWidth();
            int screenHeight = Gdx.graphics.getHeight();
            float x = Gdx.input.getX();
            float filppedY = screenHeight- Gdx.input.getY();
            final float SCREEN_MOVE_EGDE = 0.4f;

            if (x > screenWidth * (1.0f - SCREEN_MOVE_EGDE)) sano.velocity.x = SANO_SPEED;
            else if (x < screenWidth * SCREEN_MOVE_EGDE )  sano.velocity.x = -SANO_SPEED;

            if (filppedY > screenHeight * (1.0f - SCREEN_MOVE_EGDE)) sano.velocity.y = SANO_SPEED;
            else if (filppedY < screenHeight * SCREEN_MOVE_EGDE)  sano.velocity.y = -SANO_SPEED;
        }
        else {
            if (Gdx.input.isKeyPressed(Keys.UP)) sano.velocity.y = SANO_SPEED ;       //กดลูกศรขึ้น
            if (Gdx.input.isKeyPressed(Keys.DOWN)) sano.velocity.y = -SANO_SPEED ;      //กดลูกศรลง
            if (Gdx.input.isKeyPressed(Keys.LEFT)) sano.velocity.x = -SANO_SPEED ;      //กดลูกศรซ้าย
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) sano.velocity.x = SANO_SPEED ;     //กดลูกศรขวา
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.R) {    // กด R เพื่อ Reset มุมกล้อง ยกเลิกการมุมกล้องติดตาม Sano
            cameraHelper.setPosition(0, 0);
            cameraHelper.setZoom(1.0f);
            sano.init();
        }
        else if (keycode == Keys.SPACE) { // กด Spacebar เพื่อให้มุมกล้องติดตาม/เลิกติดตาม Sano
            if (!cameraHelper.hasTarget()) cameraHelper.setTarget(sano);
            else cameraHelper.setTarget(null);
        }
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

}
