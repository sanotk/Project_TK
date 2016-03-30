package com.mypjgdx.esg.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.mypjgdx.esg.game.objects.AnimatedObject.ViewDirection;
import com.mypjgdx.esg.game.objects.Enemy;
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
        cameraHelper.setTarget(this.level.player);
        Gdx.input.setInputProcessor(this);
    }

    public void update (float deltaTime) {
        handleInput(deltaTime);
        level.update(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleInput (float deltaTime) {
        handleCameraInput(deltaTime);
        handleplayerInput();
    }

    private void handleCameraInput (float deltaTime) {
        if (cameraHelper.hasTarget()) return; // มุมกล้องติดตาม player อยู่จะใช้ Input เลื่อนมุมกล้องเองไม่ได้

        if (Gdx.input.isKeyPressed(Keys.UP)) cameraHelper.addPostion(0, CAMERA_SPEED * deltaTime); //กดลูกศรขึ้น
        if (Gdx.input.isKeyPressed(Keys.DOWN)) cameraHelper.addPostion(0, -CAMERA_SPEED * deltaTime);//กดลูกศรลง
        if (Gdx.input.isKeyPressed(Keys.LEFT)) cameraHelper.addPostion(-CAMERA_SPEED * deltaTime, 0);//กดลูกศรซ้าย
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) cameraHelper.addPostion(CAMERA_SPEED * deltaTime, 0);//กดลูกศรขวา
        if (Gdx.input.isKeyPressed(Keys.Z)) cameraHelper.addZoom(CAMERA_ZOOM_SPEED * deltaTime); //กด z
        if (Gdx.input.isKeyPressed(Keys.X)) cameraHelper.addZoom(-CAMERA_ZOOM_SPEED * deltaTime); // กด x
    }

    private void handleplayerInput()  { // ควบคุม player
        if (!cameraHelper.hasTarget()) return; // มุมกล้องติดตาม player อยู่ถึงจะควมคุม player ได้

        final int screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();
        final float SCREEN_MOVE_EGDE = 0.4f;

        if (Gdx.app.getType() == ApplicationType.Android && Gdx.input.isTouched())  {
            float x = Gdx.input.getX();
            float filppedY = screenHeight- Gdx.input.getY();
            if (x > screenWidth * (1.0f - SCREEN_MOVE_EGDE)) level.player.move(ViewDirection.RIGHT);
            else if (x < screenWidth * SCREEN_MOVE_EGDE ) level.player.move(ViewDirection.LEFT);

            if (filppedY > screenHeight * (1.0f - SCREEN_MOVE_EGDE)) level.player.move(ViewDirection.UP);
            else if (filppedY < screenHeight * SCREEN_MOVE_EGDE) level.player.move(ViewDirection.DOWN);        }

        else {
            if (Gdx.input.isKeyPressed(Keys.UP)) level.player.move(ViewDirection.UP);
            if (Gdx.input.isKeyPressed(Keys.DOWN)) level.player.move(ViewDirection.DOWN);
            if (Gdx.input.isKeyPressed(Keys.LEFT)) level.player.move(ViewDirection.LEFT);
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) level.player.move(ViewDirection.RIGHT);
            if (Gdx.input.isKeyPressed(Keys.X)) level.player.trapAttack(level.traps, level.map.getMapLayer()) ;
            if (Gdx.input.isKeyPressed(Keys.Z)) level.player.rangeAttack(level.bullets, level.map.getMapLayer());
            if (Gdx.input.isKeyPressed(Keys.B)) level.player.beamAttack(level.beams, level.map.getMapLayer());

        }
     }

    @Override
    public boolean keyDown (int keycode) {
        switch(keycode) {
        case Keys.R:  // กด R เพื่อ Reset มุมกล้อง ยกเลิกการมุมกล้องติดตาม player
            cameraHelper.setPosition(0, 0);
            cameraHelper.setZoom(1.0f);
            for (Enemy e: level.enemies) e.init(level.map.getMapLayer());
            break;
        case Keys.SPACE: // กด Spacebar เพื่อให้มุมกล้องติดตาม/เลิกติดตาม player
            if (!cameraHelper.hasTarget()) cameraHelper.setTarget(level.player);
            else cameraHelper.setTarget(null);
            break;
        case Keys.ENTER: // กด Enter เพื่อเปลี่ยน Map
            init(new Level(level.map.next()));
            break;
        }
        return true;
    }

}
