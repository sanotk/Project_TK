package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.utils.MusicManager;

public class GameScreen extends AbstractGameScreen {

    private static final int SCENE_WIDTH = 1024;
    private static final int SCENE_HEIGHT = 576;

    private Stage stage;

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    public GameScreen(Game game, Level level) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        worldController = new WorldController(this);
        worldRenderer = new WorldRenderer(worldController);

        worldController.init(level);

        MusicManager.instance.play(MusicManager.Musics.MUSIC_2, true);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Keys.M)) {
            game.setScreen(new MenuScreen(game));
            return;
        }

        worldController.update(Gdx.graphics.getDeltaTime());
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
    public void hide() {
        worldRenderer.dispose();
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void changeLevel(Level level) {
        worldController.level.onExit();
        worldController.init(level);
    }
}
