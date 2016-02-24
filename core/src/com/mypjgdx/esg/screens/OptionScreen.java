package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mypjgdx.esg.game.Assets;

public class OptionScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1280; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 720; //เซตค่าความสูงของจอ
    private Stage stage; //
    private Stage stat_music; //
    private Stage stat_sound; //
    private Skin skin; //
    private boolean music;
    private boolean sound;
    private Label text_option;
    private Label text_music;
    private Label text_sound;
    private Label status_music;
    private Label status_sound;

    public OptionScreen(final Game game) {
        super(game);

        music = true;
        sound = true;

        stage = new Stage();
        stat_music = new Stage();
        stat_sound = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        int btn_w = 150;
        int btn_h = 50;

        text_option = new Label("Option" ,skin);
        text_option.setFontScale(1.2f,1.2f);
        text_option.setPosition(SCENE_WIDTH / 2 - btn_w / 2 + 40, 550);

        text_music = new Label("Music" ,skin);
        text_music.setFontScale(1.1f,1.1f);
        text_music.setPosition(SCENE_WIDTH / 2 - btn_w / 2 - 120, 460);

        text_sound = new Label("Sound Effect" ,skin);
        text_sound.setFontScale(1.1f,1.1f);
        text_sound.setPosition(SCENE_WIDTH / 2 - btn_w / 2 - 120, 390);

        TextButton buttonStart = new TextButton("Open/Close", skin);
        buttonStart.setWidth(btn_w);
        buttonStart.setHeight(btn_h);
        buttonStart.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 450);

        TextButton buttonLoad = new TextButton("Open/Close", skin);
        buttonLoad.setWidth(btn_w);
        buttonLoad.setHeight(btn_h);
        buttonLoad.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 380);

        stage.addActor(buttonStart);
        stage.addActor(buttonLoad);
        stage.addActor(text_option);
        stage.addActor(text_music);
        stage.addActor(text_sound);

        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(music==true){
                	Assets.instance.music.pause();
                	music = false;
                	stat_music.clear();
                	status_music = null;
                    status_music = new Label("Music : CLOSE" ,skin);
                    status_music.setFontScale(1.1f,1.1f);
                    status_music.setPosition(SCENE_WIDTH / 2 - 150 / 2 + 180, 460);
                    stat_music.addActor(status_music);
                }
                else {
                	Assets.instance.music.play();
                	Assets.instance.music.setPan(0.0f, 0.2f);
                    Assets.instance.music.setLooping(true);
                    music = true;
                    stat_music.clear();
                	status_music = null;
                    status_music = new Label("Music : OPEN" ,skin);
                    status_music.setFontScale(1.1f,1.1f);
                    status_music.setPosition(SCENE_WIDTH / 2 - 150 / 2 + 180, 460);
                    stat_music.addActor(status_music);
                }
            }
        });

        buttonLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(sound==true){
                	Assets.instance.bullet.dispose();
                	sound = false;
                	stat_sound.clear();
                	status_sound = null;
                	status_sound = new Label("Sound Effect : CLOSE" ,skin);
                	status_sound.setFontScale(1.1f,1.1f);
                	status_sound.setPosition(SCENE_WIDTH / 2 - 150 / 2 + 180, 390);
                    stat_sound.addActor(status_sound);
                }
                else {
                	Assets.instance.bullet.play();
                    sound = true;
                    stat_sound.clear();
                    status_sound = null;
                    status_sound = new Label("Sound Effect : OPEN" ,skin);
                    status_sound.setFontScale(1.1f,1.1f);
                    status_sound.setPosition(SCENE_WIDTH / 2 - 150 / 2 + 180, 390);
                    stat_sound.addActor(status_sound);
                }
            }
        });
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        stat_music.act(Gdx.graphics.getDeltaTime());
        stat_music.draw();
        stat_sound.act(Gdx.graphics.getDeltaTime());
        stat_sound.draw();
        if(Gdx.input.isKeyJustPressed(Keys.SPACE))
            game.setScreen(new MenuScreen(game));
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