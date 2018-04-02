package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.MusicManager;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.SoundManager;

public class MenuScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ
    private Stage stage; //
    private Skin skin; //
    private Label text_mainmenu;

    private TextButton buttonStart;
    private TextButton buttonLoad;
    private TextButton buttonOption;
    private TextButton buttonExit;
    private BitmapFont font;

    protected Window window;

    public MenuScreen(final Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT)); //สร้างจุดโฟกัสของหน้าจอ
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("uiskin.json")); //โหลดฟ้อน

        int btn_w = 200;
        int btn_h = 50;

        text_mainmenu = new Label("Main Menu", skin);
        text_mainmenu.setColor(1, 1, 1, 1);
        text_mainmenu.setFontScale(1.2f, 1.2f);
        text_mainmenu.setPosition(SCENE_WIDTH / 2 - btn_w / 2 + 50, 450);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;
        buttonStart = new TextButton("Start", buttonStyle);
        buttonStart.setWidth(btn_w);
        buttonStart.setHeight(btn_h);
        buttonStart.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 350);

        buttonLoad = new TextButton("Load", buttonStyle);
        buttonLoad.setWidth(btn_w);
        buttonLoad.setHeight(btn_h);
        buttonLoad.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 280);

        buttonOption = new TextButton("Option", buttonStyle);
        buttonOption.setWidth(btn_w);
        buttonOption.setHeight(btn_h);
        buttonOption.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 210);

        buttonExit = new TextButton("Exit", buttonStyle);
        buttonExit.setWidth(btn_w);
        buttonExit.setHeight(btn_h);
        buttonExit.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        window = createOptionsWindow();
        window.setVisible(false);
//        window.debug();

        stage.addActor(text_mainmenu);
        stage.addActor(buttonStart);
        stage.addActor(buttonLoad);
        stage.addActor(buttonOption);
        stage.addActor(buttonExit);
        stage.addActor(window);

        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, window));
            }
        });

        buttonLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new MapScreen(game));
            }
        });

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.setPosition(
                        Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - window.getHeight() / 2);
                window.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

    }

    protected Window createOptionsWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("slider_back_hor"));
        TextureRegionDrawable knobRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("knob_03"));
        sliderStyle.knob = knobRegion;
        sliderStyle.knobDown = knobRegion.tint(Color.LIGHT_GRAY);

        final Slider musicSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        musicSlider.setValue(0.5f);

        final Slider soundSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        soundSlider.setValue(0.5f);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonStyle.up = buttonRegion;
        buttonStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonStyle);

        final Window window = new Window("Options", style);
        window.setModal(true);
        window.padTop(40);
        window.padLeft(40);
        window.padRight(40);
        window.padBottom(20);
        window.getTitleLabel().setAlignment(Align.center);
        window.row().padBottom(10).padTop(10);
        window.add(new Label("Volume", labelStyle)).colspan(3);
        window.row();
        window.add(new Image(Assets.instance.uiBlue.findRegion("icon_music"))).padRight(10);
        window.add(new Label("Music", labelStyle)).padRight(10);
        window.add(musicSlider).width(250);
        window.row().padTop(10);
        window.add(new Image(Assets.instance.uiBlue.findRegion("icon_sound_on"))).padRight(10);
        window.add(new Label("Sound Fx", labelStyle)).padRight(10);
        window.add(soundSlider).width(250);
        window.row().padTop(20);
        window.add(closeButton).colspan(3);
        window.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });

        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MusicManager.instance.setVolume(musicSlider.getValue());
            }
        });

        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.instance.setVolume(soundSlider.getValue());
            }
        });

        return window;
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
    public void show() {
        MusicManager.instance.play(MusicManager.Musics.MUSIC_1, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
