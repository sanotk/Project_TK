package com.mypjgdx.esg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.utils.CameraHelper;

public class WorldController extends InputAdapter {

    private static final float CAMERA_SPEED = 200.0f; //กำหนดความเร็วในการเคลื่อนที่ของกล้อง
    private static final float CAMERA_ZOOM_SPEED = 1.0f; //ความเร็วของการซูม

    public CameraHelper cameraHelper;
    public Level level;

    public WorldController (Level level) {
        init(level);
    }

    public void init(Level level) {
        this.level = level;

        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(this.level.sano);

        Gdx.input.setInputProcessor(this);
    }

    public void update (float deltaTime) {
        handleInput(deltaTime);
        level.update(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleInput (float deltaTime) {
        handleCameraInput(deltaTime);
        handleSanoInput();
    }

    private void handleCameraInput (float deltaTime) {
        if (cameraHelper.hasTarget()) return; // มุมกล้องติดตาม Sano อยู่จะใช้ Input เลื่อนมุมกล้องเองไม่ได้

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
        final Vector2 sanoVelocity = level.sano.velocity;

        if (Gdx.input.isKeyPressed(Keys.UP)) sanoVelocity.y = SANO_SPEED ;       //กดลูกศรขึ้น
        if (Gdx.input.isKeyPressed(Keys.DOWN)) sanoVelocity.y = -SANO_SPEED ;      //กดลูกศรลง
        if (Gdx.input.isKeyPressed(Keys.LEFT)) sanoVelocity.x = -SANO_SPEED ;      //กดลูกศรซ้าย
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) sanoVelocity.x = SANO_SPEED ;     //กดลูกศรขวา
    }

    @Override
    public boolean keyDown (int keycode) {
        switch(keycode) {
        case Keys.R:  // กด R เพื่อ Reset มุมกล้อง ยกเลิกการมุมกล้องติดตาม Sano
            cameraHelper.setPosition(0, 0);
            cameraHelper.setZoom(1.0f);
            level.sano.init();
            break;
        case Keys.SPACE: // กด Spacebar เพื่อให้มุมกล้องติดตาม/เลิกติดตาม Sano
            if (!cameraHelper.hasTarget()) cameraHelper.setTarget(level.sano);
            else cameraHelper.setTarget(null);
            break;
        case Keys.ENTER: // กด Enter เพื่อเปลี่ยน Map
            init(new Level(level.map.next()));
            break;
        }

        return true;
    }

}
