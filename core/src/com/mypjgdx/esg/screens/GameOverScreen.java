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
import com.mypjgdx.esg.game.objects.AbstractGameObject;

public class GameOverScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1280; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 720; //เซตค่าความสูงของจอ
    private Stage stage; //
    private Skin skin; //
    private Label text_gameover;
	public AbstractGameObject player;

    public GameOverScreen(final Game game) {
        super(game);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        int btn_w = 150;
        int btn_h = 50;

        text_gameover = new Label("Game Over" ,skin);
        text_gameover.setColor(1, 1, 1, 1);
        text_gameover.setFontScale(1.5f,1.5f);
        text_gameover.setPosition(SCENE_WIDTH / 2 - btn_w / 2 , 550);

        TextButton buttonBack= new TextButton("Back", skin);
        buttonBack.setWidth(btn_w);
        buttonBack.setHeight(btn_h);
        buttonBack.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 310);

        stage.addActor(buttonBack);
        stage.addActor(text_gameover);

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
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}