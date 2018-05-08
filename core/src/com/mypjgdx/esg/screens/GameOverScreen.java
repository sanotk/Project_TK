package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.utils.MusicManager;

public class GameOverScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1024;
    public static final int SCENE_HEIGHT = 576;

    private Stage stage;
    private Label gameOverLabel;

	public AbstractGameObject player;
    private BitmapFont font;
    private TextButton buttonBack;

    public GameOverScreen(final Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        int buttonWidth = 150;
        int buttonHeight = 50;

        font = new BitmapFont();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.RED;


        gameOverLabel = new Label("Game Over" ,labelStyle);
        gameOverLabel.setColor(1, 1, 1, 0.75f);
        gameOverLabel.setFontScale(2.1f,2.1f);
        gameOverLabel.setPosition(SCENE_WIDTH / 2 - buttonWidth / 2 , SCENE_HEIGHT - 175);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle.font = font;
        buttonBack = new TextButton("Back", buttonStyle);
        buttonBack.setWidth(buttonWidth);
        buttonBack.setHeight(buttonHeight);
        buttonBack.setPosition(SCENE_WIDTH / 2 - buttonWidth / 2, SCENE_HEIGHT - 300);

        stage.addActor(buttonBack);
        stage.addActor(gameOverLabel);

        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MenuScreen(game));
            }
        });
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        MusicManager.instance.stop();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

}