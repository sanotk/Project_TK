package com.mypjgdx.esg.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.ui.*;
import com.mypjgdx.esg.utils.CameraHelper;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.GameSaveManager;

public class WorldController extends InputAdapter {

    private static final float CAMERA_SPEED = 200.0f; //กำหนดความเร็วในการเคลื่อนที่ของกล้อง
    private static final float CAMERA_ZOOM_SPEED = 1.0f; //ความเร็วของการซูม

    public CameraHelper cameraHelper;
    public Level level;
    public WorldRenderer worldRenderer;
    public PlayerTouchPad touchPad;
    public SwordAttackButton swordAttackButton;
    public SwordWaveAttackButton swordWaveAttackButton;
    public TrapAttackButton trapAttackButton;
    public TalkButton talkButton;

    public WorldController(Level level) {
        init(level);
    }

    public void init(Level level) {
        this.level = level;
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(this.level.player);
        cameraHelper.setMap(this.level.mapLayer);
        Gdx.input.setInputProcessor(this);
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
        level.update(deltaTime);
        cameraHelper.update(worldRenderer.viewport);
    }

    private void handleInput(float deltaTime) {
        handleCameraInput(deltaTime);
        handlePlayerInput();
    }

    private void handleCameraInput(float deltaTime) {
        if (cameraHelper.hasTarget()) return; // มุมกล้องติดตาม player อยู่จะใช้ Input เลื่อนมุมกล้องเองไม่ได้

        if (Gdx.input.isKeyPressed(Keys.UP)) cameraHelper.addPostion(0, CAMERA_SPEED * deltaTime); //กดลูกศรขึ้น
        if (Gdx.input.isKeyPressed(Keys.DOWN)) cameraHelper.addPostion(0, -CAMERA_SPEED * deltaTime);//กดลูกศรลง
        if (Gdx.input.isKeyPressed(Keys.LEFT)) cameraHelper.addPostion(-CAMERA_SPEED * deltaTime, 0);//กดลูกศรซ้าย
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) cameraHelper.addPostion(CAMERA_SPEED * deltaTime, 0);//กดลูกศรขวา
        if (Gdx.input.isKeyPressed(Keys.Z)) cameraHelper.addZoom(CAMERA_ZOOM_SPEED * deltaTime); //กด z
        if (Gdx.input.isKeyPressed(Keys.X)) cameraHelper.addZoom(-CAMERA_ZOOM_SPEED * deltaTime); // กด x
    }

    private void handlePlayerInput() { // ควบคุม player
        if (!cameraHelper.hasTarget()) return; // มุมกล้องติดตาม player อยู่ถึงจะควมคุม player ได้
        if (level.player.timeStop) return;

        final int screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();
        final float SCREEN_MOVE_EDGE = 0.4f;

        final float MIN_KNOB_PERCENT_TO_MOVE = 0.30f;
        if (touchPad.getKnobPercentY() > MIN_KNOB_PERCENT_TO_MOVE) {
            level.player.move(Direction.UP);
        }
        else if (touchPad.getKnobPercentY() < -MIN_KNOB_PERCENT_TO_MOVE) {
            level.player.move(Direction.DOWN);
        }
        if (touchPad.getKnobPercentX() > MIN_KNOB_PERCENT_TO_MOVE) {
            level.player.move(Direction.RIGHT);
        }
        else if (touchPad.getKnobPercentX() < -MIN_KNOB_PERCENT_TO_MOVE) {
            level.player.move(Direction.LEFT);
        }

        if (swordAttackButton.isJustPressed()) {
            level.player.swordAttack(level.weapons, level.swords);
        }
        if (swordWaveAttackButton.isJustPressed()) {
            level.player.swordWaveAttack(level.weapons, level.swords);
        }
        if (trapAttackButton.isJustPressed()) {
            level.player.trapAttack(level.weapons);
        }

        if (talkButton.isJustPressed()) {
            level.player.findItem();
        }

        if (Gdx.app.getType() == ApplicationType.Android && Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float filppedY = screenHeight - Gdx.input.getY();
            if (x > screenWidth * (1.0f - SCREEN_MOVE_EDGE)) level.player.move(Direction.RIGHT);
            else if (x < screenWidth * SCREEN_MOVE_EDGE) level.player.move(Direction.LEFT);

            if (filppedY > screenHeight * (1.0f - SCREEN_MOVE_EDGE)) level.player.move(Direction.UP);
            else if (filppedY < screenHeight * SCREEN_MOVE_EDGE) level.player.move(Direction.DOWN);
        } else {
            if (Gdx.input.isKeyPressed(Keys.UP)) level.player.move(Direction.UP);
            if (Gdx.input.isKeyPressed(Keys.DOWN)) level.player.move(Direction.DOWN);
            if (Gdx.input.isKeyPressed(Keys.LEFT)) level.player.move(Direction.LEFT);
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) level.player.move(Direction.RIGHT);
            if (Gdx.input.isKeyJustPressed(Keys.Z)) level.player.trapAttack(level.weapons);
            if (Gdx.input.isKeyPressed(Keys.C)) level.player.swordAttack(level.weapons, level.swords);
            if (Gdx.input.isKeyJustPressed(Keys.X)) level.player.swordWaveAttack(level.weapons, level.swords);
            if (Gdx.input.isKeyJustPressed(Keys.A)) level.player.findItem();
            if (Gdx.input.isKeyJustPressed(Keys.B)) {
                GameSaveManager.instance.save();
            }
            if (Gdx.input.isKeyJustPressed(Keys.L)) {
                GameSaveManager.instance.load();
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.SPACE: // กด Spacebar เพื่อให้มุมกล้องติดตาม/เลิกติดตาม player
                if (!cameraHelper.hasTarget()) cameraHelper.setTarget(level.player);
                else cameraHelper.setTarget(null);
                break;
        }
        return true;
    }

}
