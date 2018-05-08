package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.levels.Level1;
import com.mypjgdx.esg.game.levels.Level2;
import com.mypjgdx.esg.ui.OptionWindow;
import com.mypjgdx.esg.utils.MusicManager;

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

    private Window loadWindow;

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

        loadWindow = createLoadWindow();
        loadWindow.setVisible(false);

        stage.addActor(text_mainmenu);
        stage.addActor(buttonStart);
        stage.addActor(buttonLoad);
        stage.addActor(buttonOption);
        stage.addActor(buttonExit);
        stage.addActor(loadWindow);

        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, new Level1()));
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
                OptionWindow.instance.show(stage);
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
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
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
        TextButton stage1Button = new TextButton("Stage 1", buttonStyle);
        TextButton stage2Button = new TextButton("Stage 2", buttonStyle);
        TextButton stage3Button = new TextButton("Stage 3", buttonStyle);
        TextButton stage4Button = new TextButton("Stage 4", buttonStyle);

        final Window loadWindow = new Window("Load Game", style);
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
                game.setScreen(new GameScreen(game, new Level1()));
            }
        });
        stage2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, new Level2()));
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
