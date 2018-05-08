package com.mypjgdx.esg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.screens.GameScreen;
import com.mypjgdx.esg.ui.HudMenu;
import com.mypjgdx.esg.ui.LinkingWindow;
import com.mypjgdx.esg.ui.RuleWindow;
import com.mypjgdx.esg.utils.CameraHelper;
import com.mypjgdx.esg.utils.Direction;

public class WorldController {

    private static final float CAMERA_SPEED = 200.0f;
    private static final float CAMERA_ZOOM_SPEED = 1.0f;

    public CameraHelper cameraHelper;
    public Level level;

    private LinkingWindow linkingWindow;
    private HudMenu hudMenu;
    private RuleWindow ruleWindow;

    private GameScreen gameScreen;

    public boolean pausing;

    public WorldController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        linkingWindow = new LinkingWindow();

        final Stage stage = gameScreen.getStage();

        ruleWindow = new RuleWindow(this);

        hudMenu = new HudMenu(gameScreen.getStage(), this, ruleWindow);
        hudMenu.setPosition(stage.getWidth() - 20, stage.getHeight() - 15, Align.topRight);

        stage.addActor(hudMenu);
    }

    public final void init(Level level) {
        this.level = level;
        level.setGameScreen(gameScreen);
        level.onEnter();
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(this.level.player);
        cameraHelper.setMap(this.level.mapLayer);

        level.update(0);
        cameraHelper.update();

        ruleWindow.show(gameScreen.getStage());
        pausing = true;
    }

    public void update(float deltaTime) {
        if (pausing) return;
        handleInput(deltaTime);
        level.update(deltaTime);
        cameraHelper.update();
    }

    private void handleInput(float deltaTime) {
        if (pausing) return;
        handleCameraInput(deltaTime);
        handlePlayerInput();
    }

    private void handleCameraInput(float deltaTime) {
        if (cameraHelper.hasTarget()) return;

        if (Gdx.input.isKeyPressed(Keys.UP)) cameraHelper.addPosition(0, CAMERA_SPEED * deltaTime);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) cameraHelper.addPosition(0, -CAMERA_SPEED * deltaTime);
        if (Gdx.input.isKeyPressed(Keys.LEFT)) cameraHelper.addPosition(-CAMERA_SPEED * deltaTime, 0);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) cameraHelper.addPosition(CAMERA_SPEED * deltaTime, 0);
        if (Gdx.input.isKeyPressed(Keys.Z)) cameraHelper.addZoom(CAMERA_ZOOM_SPEED * deltaTime);
        if (Gdx.input.isKeyPressed(Keys.X)) cameraHelper.addZoom(-CAMERA_ZOOM_SPEED * deltaTime);
    }

    private void handlePlayerInput() {
        if (!cameraHelper.hasTarget()) return;

        if (Gdx.input.isKeyPressed(Keys.UP)) level.player.move(Direction.UP);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) level.player.move(Direction.DOWN);
        if (Gdx.input.isKeyPressed(Keys.LEFT)) level.player.move(Direction.LEFT);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) level.player.move(Direction.RIGHT);
        if (Gdx.input.isKeyPressed(Keys.Z)) level.player.trapAttack();
        if (Gdx.input.isKeyPressed(Keys.C)) level.player.swordAttack();
        if (Gdx.input.isKeyPressed(Keys.X)) level.player.bowAttack();
        if (Gdx.input.isKeyJustPressed(Keys.A)) level.player.findItem(gameScreen.getStage(), linkingWindow, level.items);
    }
}
