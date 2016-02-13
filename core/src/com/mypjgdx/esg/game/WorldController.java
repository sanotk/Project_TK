package com.mypjgdx.esg.game;

import com.badlogic.gdx.Application.ApplicationType;
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

        final float player_SPEED = 100.0f;
        final float enemy_SPEED = 80.0f;
        final Vector2 playerVelocity = level.player.velocity;
        final Vector2 enemyVelocity = level.enemy.velocity;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        final float SCREEN_MOVE_EGDE = 0.4f;

        if (Gdx.app.getType() == ApplicationType.Android && Gdx.input.isTouched())  {
            float x = Gdx.input.getX();
            float filppedY = screenHeight- Gdx.input.getY();
            if (x > screenWidth * (1.0f - SCREEN_MOVE_EGDE)) playerVelocity.x = player_SPEED;
            else if (x < screenWidth * SCREEN_MOVE_EGDE )  playerVelocity.x = -player_SPEED;

            if (filppedY > screenHeight * (1.0f - SCREEN_MOVE_EGDE)) playerVelocity.y = player_SPEED;
            else if (filppedY < screenHeight * SCREEN_MOVE_EGDE)  playerVelocity.y = -player_SPEED;
        }
        else {
            if (Gdx.input.isKeyPressed(Keys.UP)) playerVelocity.y = player_SPEED ;       //กดลูกศรขึ้น
            if (Gdx.input.isKeyPressed(Keys.DOWN)) playerVelocity.y = -player_SPEED ;      //กดลูกศรลง
            if (Gdx.input.isKeyPressed(Keys.LEFT)) playerVelocity.x = -player_SPEED ;      //กดลูกศรซ้าย
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) playerVelocity.x = player_SPEED ;     //กดลูกศรขวา
        }

        if (level.enemy.position.x > level.player.position.x) enemyVelocity.x = enemy_SPEED;
        else if (level.enemy.position.x < level.player.position.x)  enemyVelocity.x = -enemy_SPEED;

        if (level.enemy.position.y > level.player.position.y) enemyVelocity.y = enemy_SPEED;
        else if (level.enemy.position.y < level.player.position.y) enemyVelocity.y = -enemy_SPEED;

     }

    @Override
    public boolean keyDown (int keycode) {
        switch(keycode) {
        case Keys.R:  // กด R เพื่อ Reset มุมกล้อง ยกเลิกการมุมกล้องติดตาม player
            cameraHelper.setPosition(0, 0);
            cameraHelper.setZoom(1.0f);
            level.player.init();
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
