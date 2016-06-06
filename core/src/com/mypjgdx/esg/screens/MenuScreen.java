package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1280; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 720; //เซตค่าความสูงของจอ
    private Stage stage; //
    private Skin skin; //
    private Label text_mainmenu;

    public MenuScreen(final Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        int btn_w = 200;
        int btn_h = 50;

        text_mainmenu = new Label("Main Menu" ,skin);
        text_mainmenu.setColor(1, 1, 1, 1);
        text_mainmenu.setFontScale(1.2f,1.2f);
        text_mainmenu.setPosition(SCENE_WIDTH / 2 - btn_w / 2 + 50, 550);

        TextButton buttonStart = new TextButton("START", skin);
        buttonStart.setWidth(btn_w);
        buttonStart.setHeight(btn_h);
        buttonStart.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 450);

        TextButton buttonLoad = new TextButton("LOAD", skin);
        buttonLoad.setWidth(btn_w);
        buttonLoad.setHeight(btn_h);
        buttonLoad.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 380);

        TextButton buttonOption = new TextButton("OPTION", skin);
        buttonOption.setWidth(btn_w);
        buttonOption.setHeight(btn_h);
        buttonOption.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 310);

        TextButton buttonExit = new TextButton("EXIT", skin);
        buttonExit.setWidth(btn_w);
        buttonExit.setHeight(btn_h);
        buttonExit.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 240);

        stage.addActor(buttonStart);
        stage.addActor(buttonLoad);
        stage.addActor(buttonOption);
        stage.addActor(buttonExit);
        stage.addActor(text_mainmenu);

        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new IntroScreen(game));
            }
        });

        buttonLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoadScreen(game));
            }
        });

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionScreen(game));
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
