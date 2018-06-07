package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.ui.Dialog;

public class IntroScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ

    private OrthographicCamera camera;
    private SpriteBatch batch;
	private Viewport viewport;
    private Stage stage;

	private BitmapFont font;
	private Texture dialogBackground;
	private Dialog dialog;

    private Window optionsWindow;

    private String text =
		        "\"ในปี พ.ศ.2600 ได้เกิดสงครามโลกครั้งที่ 3\" \n\"โดยมีการใช้อาวุธนิวเคลียร์ ทำให้ทั่วทั้งโลกเกิดสภาวะอากาศเป็นพิษ\" \n\"ทำให้ผู้คนไม่สามารถอาศัยอยู่บนพื้นโลกได้นานนัก \"\n\"สุดท้ายจึงต้องหลบไปอยู่ในสถานที่หลบภัยใต้ดิน\""
		        + "\n\"คุณคือผู้ที่ถูกคัดเลือกโดยต้องไปสำรวจสถานที่หลบภัยแห่งหนึ่ง\"\n\"และพาประชาชนในพื้นที่เข้าไปหลบภัยในสถานที่หลบภัยแห่งนั้น \"\n\"เอาล่ะ รีบเร่งมือกันเถอะ (กด Enter เพื่อเริ่มเกม)\"";

    public IntroScreen(final Game game, final Window optionsWindow) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);

        font = Assets.instance.newFont;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.BLACK);

		dialogBackground = new Texture("dialog.png");
		dialogBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        this.optionsWindow = optionsWindow;

		dialog = new Dialog(font, dialogBackground, 65f, 220f);
		dialog.setPosition(
		        SCENE_WIDTH/2-dialogBackground.getWidth()*0.5f,
		        SCENE_HEIGHT/2-dialogBackground.getHeight()*0.5f);
		dialog.setText(text);

        dialog.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.app.getType() == Application.ApplicationType.Android) {
                    if (dialog.isFinishedTyping()) {
                        closeDialog();
                    } else {
                        dialog.speedUp();
                    }
                }
                return false;
            }
        });
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (dialog.isFinishedTyping() && keycode == Keys.ENTER) {
                    closeDialog();
                } else {
                    dialog.speedUp();
                }
                return false;
            }
        });

		stage.addActor(dialog);
        Gdx.input.setInputProcessor(stage);
        Assets.instance.music.dispose();
        Assets.instance.introGame.play();
        Assets.instance.introGame.setPan(0.0f, 0.2f);
        Assets.instance.introGame.setLooping(true);
    }

    private void closeDialog() {
        game.setScreen(new GameScreen(game, optionsWindow));
    }

    @Override
    public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
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
	    dialogBackground.dispose();
        font.dispose();
	    batch.dispose();
        stage.dispose();
    }
}