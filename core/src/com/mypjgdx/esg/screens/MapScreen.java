package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.Assets;

public class MapScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ
    private Stage stage; //
    private Skin skin; //
    private Label text_mapmenu;
    private BitmapFont font;

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

        font = new BitmapFont();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_04"));
        buttonStyle.down = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_03"));
        buttonStyle.font = font;

        text_mapmenu = new Label("Map Selection" ,skin);
        text_mapmenu.setColor(1, 1, 1, 1);
        text_mapmenu.setFontScale(1.2f,1.2f);
        text_mapmenu.setPosition(SCENE_WIDTH / 2 - btn_w / 2 + 50, 520);

        TextButton buttonStage1 = new TextButton("Stage 1", buttonStyle);
        buttonStage1.setWidth(btn_w);
        buttonStage1.setHeight(btn_h);
        buttonStage1.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 450);

        TextButton buttonStage2 = new TextButton("Stage 2", buttonStyle);
        buttonStage2.setWidth(btn_w);
        buttonStage2.setHeight(btn_h);
        buttonStage2.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 380);

        TextButton buttonStage3 = new TextButton("Stage 3", buttonStyle);
        buttonStage3.setWidth(btn_w);
        buttonStage3.setHeight(btn_h);
        buttonStage3.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 310);

        TextButton buttonStage4 = new TextButton("Stage 4", buttonStyle);
        buttonStage4.setWidth(btn_w);
        buttonStage4.setHeight(btn_h);
        buttonStage4.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 240);

        stage.addActor(buttonStage1);
        stage.addActor(buttonStage2);
        stage.addActor(buttonStage3);
        stage.addActor(buttonStage4);
        stage.addActor(text_mapmenu);

        if(Integer.parseInt(choice)==1) {
            buttonStage1.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });
        }else if(Integer.parseInt(choice)==2){
            buttonStage2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen2(game));
                }
            });
        }else if(Integer.parseInt(choice)==3) {
            buttonStage3.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen3(game));
                }
            });
        }else if(Integer.parseInt(choice)==4) {
            buttonStage4.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen4(game));
                }
            });
        }else {
            game.setScreen(new GameScreen(game));
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
