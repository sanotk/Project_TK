package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MapScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ
    private Stage stage; //
    private Skin skin; //
    private Label text_mapmenu;

    public MapScreen(final Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        int btn_w = 200;
        int btn_h = 50;

        FileHandle file = Gdx.files.local("choice.json");
        String choice = file.readString();
        Json json = new Json();

        text_mapmenu = new Label("Map Selection" ,skin);
        text_mapmenu.setColor(1, 1, 1, 1);
        text_mapmenu.setFontScale(1.2f,1.2f);
        text_mapmenu.setPosition(SCENE_WIDTH / 2 - btn_w / 2 + 50, 550);

        TextButton buttonStart = new TextButton("MAP1", skin);
        buttonStart.setWidth(btn_w);
        buttonStart.setHeight(btn_h);
        buttonStart.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 450);

        TextButton buttonLoad = new TextButton("MAP2", skin);
        buttonLoad.setWidth(btn_w);
        buttonLoad.setHeight(btn_h);
        buttonLoad.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 380);

        TextButton buttonOption = new TextButton("MAP3", skin);
        buttonOption.setWidth(btn_w);
        buttonOption.setHeight(btn_h);
        buttonOption.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 310);

        TextButton buttonExit = new TextButton("MAP4", skin);
        buttonExit.setWidth(btn_w);
        buttonExit.setHeight(btn_h);
        buttonExit.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 240);

        stage.addActor(buttonStart);
        stage.addActor(buttonLoad);
        stage.addActor(buttonOption);
        stage.addActor(buttonExit);
        stage.addActor(text_mapmenu);

        if(Integer.parseInt(choice)>=1) {
            buttonStart.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });
        }
        System.out.print(choice);
        System.out.print(1);




        if(Integer.parseInt(choice)>=2){
            buttonLoad.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });
        }

        if(Integer.parseInt(choice)>=3) {
            buttonOption.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });
        }

        if(Integer.parseInt(choice)>=4) {
            buttonExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });
        }

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
