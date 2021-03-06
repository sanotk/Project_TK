package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.utils.MusicManager;

public class GameOverScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ
    private Stage stage; //
    private Skin skin; //
    private Label text_gameover;
	public AbstractGameObject player;
    private BitmapFont font;
    private TextButton buttonBack;

    public GameOverScreen(final Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        int btn_w = 150;
        int btn_h = 50;

        font = Assets.instance.newFontBig;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.WHITE);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.RED;

        text_gameover = new Label("ภารกิจล้มเหลว!" ,labelStyle);
        text_gameover.setColor(Color.RED);
        text_gameover.setFontScale(1f,1f);
        text_gameover.setPosition(SCENE_WIDTH / 2 - btn_w / 1.6f - 20, SCENE_HEIGHT - 175);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle.font = font;
        buttonBack = new TextButton("กลับ", buttonStyle);
        buttonBack.setWidth(btn_w);
        buttonBack.setHeight(btn_h);
        buttonBack.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT - 300);

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
        dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }

}