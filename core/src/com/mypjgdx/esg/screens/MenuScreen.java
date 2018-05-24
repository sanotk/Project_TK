package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
    private BitmapFont font2;

    private Window optionsWindow;
    private Window loadWindow;

    public MenuScreen(final Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT)); //สร้างจุดโฟกัสของหน้าจอ
        Gdx.input.setInputProcessor(stage);

        font = Assets.instance.newFontBig;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.WHITE);

        font2 = Assets.instance.newFont;
        font2.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font2.setColor(Color.WHITE);

        skin = new Skin(Gdx.files.internal("uiskin.json")); //โหลดฟ้อน

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.GREEN;

        int btn_w = 220;
        int btn_h = 60;

        text_mainmenu = new Label("เกมประหยัดพลังงาน", skin);
        text_mainmenu.setColor(1, 1, 1, 1);
        text_mainmenu.setFontScale(1f, 1f);
        text_mainmenu.setPosition(SCENE_WIDTH / 3+43, 450);

        text_mainmenu.setStyle(labelStyle);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        buttonStart = new TextButton("เริ่มเกม", buttonStyle);
        buttonStart.setWidth(btn_w);
        buttonStart.setHeight(btn_h);
        buttonStart.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 350);

        buttonLoad = new TextButton("โหลดเกม", buttonStyle);
        buttonLoad.setWidth(btn_w);
        buttonLoad.setHeight(btn_h);
        buttonLoad.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 280);

        buttonOption = new TextButton("ปรับแต่ง", buttonStyle);
        buttonOption.setWidth(btn_w);
        buttonOption.setHeight(btn_h);
        buttonOption.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 210);

        buttonExit = new TextButton("ปิดเกม", buttonStyle);
        buttonExit.setWidth(btn_w);
        buttonExit.setHeight(btn_h);
        buttonExit.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        buttonStart.setStyle(buttonStyle);
        buttonLoad.setStyle(buttonStyle);
        buttonOption.setStyle(buttonStyle);
        buttonExit.setStyle(buttonStyle);

        optionsWindow = createOptionsWindow();
        optionsWindow.setVisible(false);
//        window.debug();
        loadWindow = createLoadWindow();
        loadWindow.setVisible(false);

        stage.addActor(text_mainmenu);
        stage.addActor(buttonStart);
        stage.addActor(buttonLoad);
        stage.addActor(buttonOption);
        stage.addActor(buttonExit);
        stage.addActor(optionsWindow);
        stage.addActor(loadWindow);

        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, optionsWindow));
            }
        });

        buttonLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 - loadWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - loadWindow.getHeight() / 2);
                loadWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 - optionsWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - optionsWindow.getHeight() / 2);
                optionsWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

    }

    private Window createLoadWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Button.ButtonStyle buttonCrossStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonCrossStyle.up = buttonRegion;
        buttonCrossStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        Button closeButton = new Button(buttonCrossStyle);
        TextButton stage1Button = new TextButton("ด่านที่ 1", buttonStyle);
        TextButton stage2Button = new TextButton("ด่านที่ 2", buttonStyle);
        TextButton stage3Button = new TextButton("ด่านที่ 3", buttonStyle);
        TextButton stage4Button = new TextButton("ด่านที่ 4", buttonStyle);

        stage1Button.setStyle(buttonStyle);
        stage2Button.setStyle(buttonStyle);
        stage3Button.setStyle(buttonStyle);
        stage4Button.setStyle(buttonStyle);

        final Window loadWindow = new Window("โหลดเกม", style);
        loadWindow.setModal(true);
        loadWindow.padTop(40);
        loadWindow.padLeft(40);
        loadWindow.padRight(40);
        loadWindow.padBottom(20);
        loadWindow.getTitleLabel().setAlignment(Align.center);
        loadWindow.row().padBottom(10).padTop(10);
        loadWindow.add(stage1Button).colspan(3);
        loadWindow.row();
        loadWindow.add(stage2Button).colspan(3);
        loadWindow.row().padTop(10);
        loadWindow.add(stage3Button).colspan(3);
        loadWindow.row().padTop(10);
        loadWindow.add(stage4Button).colspan(3);
        loadWindow.row().padTop(20);
        loadWindow.add(closeButton).colspan(3);
        loadWindow.pack();

        stage1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, optionsWindow));
            }
        });

        stage2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen2(game, optionsWindow));
            }
        });

        stage3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen3(game, optionsWindow));
            }
        });

        stage4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen4(game, optionsWindow));
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });


        return loadWindow;
    }

    private Window createOptionsWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font2;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font2;
        labelStyle.fontColor = Color.WHITE;

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("slider_back_hor"));
        TextureRegionDrawable knobRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("knob_03"));
        sliderStyle.knob = knobRegion;
        sliderStyle.knobDown = knobRegion.tint(Color.LIGHT_GRAY);

        final Slider musicSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        musicSlider.setValue(0.5f);

        final Slider soundSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        soundSlider.setValue(0.5f);

        Button.ButtonStyle buttonCrossStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonCrossStyle.up = buttonRegion;
        buttonCrossStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonCrossStyle);

        final Window optionsWindow = new Window("ปรับแต่ง", style);
        optionsWindow.setModal(true);
        optionsWindow.padTop(40);
        optionsWindow.padLeft(40);
        optionsWindow.padRight(40);
        optionsWindow.padBottom(20);
        optionsWindow.getTitleLabel().setAlignment(Align.center);
        optionsWindow.row().padBottom(10).padTop(10);
        optionsWindow.add(new Label("เสียง", labelStyle)).colspan(3);
        optionsWindow.row();
        optionsWindow.add(new Image(Assets.instance.uiBlue.findRegion("icon_music"))).padRight(10);
        optionsWindow.add(new Label("เพลงประกอบฉาก", labelStyle)).padRight(10);
        optionsWindow.add(musicSlider).width(250);
        optionsWindow.row().padTop(10);
        optionsWindow.add(new Image(Assets.instance.uiBlue.findRegion("icon_sound_on"))).padRight(10);
        optionsWindow.add(new Label("เอฟเฟค", labelStyle)).padRight(10);
        optionsWindow.add(soundSlider).width(250);
        optionsWindow.row().padTop(20);
        optionsWindow.add(closeButton).colspan(3);
        optionsWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
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

        return optionsWindow;
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
