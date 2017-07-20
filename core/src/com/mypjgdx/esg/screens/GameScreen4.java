package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.game.levels.Level1;
import com.mypjgdx.esg.game.levels.Level4;

public class GameScreen4 extends AbstractGameScreen {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    SpriteBatch batch;
    public Texture bg;

    private Stage stage;
    private Skin skin;

    public static final int SCENE_WIDTH = 480; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 800; //เซตค่าความสูงของจอ

    private Label textBullet;
    private Label textBeam;
    private Label textTrap;
    private Label textTime;
    private Label energyLevel;


    public GameScreen4(Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");



        textBullet = new Label("Punch Max : " ,skin);
        textBullet.setColor(1, 1, 1, 1);
        textBullet.setFontScale(1f,1f);
        textBullet.setPosition(50, 700);

        textBeam = new Label("Z-Punch Max : " ,skin);
        textBeam.setColor(1, 1, 1, 1);
        textBeam.setFontScale(1.f,1.f);
        textBeam.setPosition(200, 700);

        textTrap = new Label("Trap Max : " ,skin);
        textTrap.setColor(1, 1, 1, 1);
        textTrap.setFontScale(1f,1f);
        textTrap.setPosition(350, 700);

        textTime = new Label("Time : " ,skin);
        textTime.setColor(1, 1, 1, 1);
        textTime.setFontScale(1f,1f);
        textTime.setPosition(150, 750);

        energyLevel = new Label("Energy : ", skin);
        energyLevel.setColor(1, 1, 1, 1);
        energyLevel.setFontScale(1,1f);
        energyLevel.setPosition(300, 750);

        stage.addActor(textBullet);
        stage.addActor(textBeam);
        stage.addActor(textTrap);
        stage.addActor(textTime);
        stage.addActor(energyLevel);

        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        textBullet.setText(String.format("Punch Max : %d", worldController.level.player.bulletCount));
        textBeam.setText(String.format("Z-Punch Max : %d", worldController.level.player.beamCount));
        textTrap.setText(String.format("Trap Max : %d", worldController.level.player.trapCount));
        textTime.setText(String.format("Time : %d", worldController.level.player.timeCount));
        energyLevel.setText(String.format("Energy : %d", (int)worldController.level.energyTube.energy));


        if (Gdx.input.isKeyJustPressed(Keys.M)) {
            game.setScreen(new MenuScreen(game));
            return;
        }

        if (!worldController.level.player.isAlive()) {
            game.setScreen(new GameOverScreen(game));
            return;
        }

        if (worldController.level.player.timeCount <= 0) {
            game.setScreen(new GameOverScreen(game));
            return;
        }

        worldController.update(Gdx.graphics.getDeltaTime()); //อัพเดท Game World
        worldRenderer.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        worldController = new WorldController(new Level(new Level4()));
        worldRenderer = new WorldRenderer(worldController);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
    }

    @Override
    public void pause() {}

}
