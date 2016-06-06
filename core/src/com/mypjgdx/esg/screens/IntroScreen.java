package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class IntroScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1280; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 720; //เซตค่าความสูงของจอ

    private OrthographicCamera camera;
    private SpriteBatch batch;
	private Viewport viewport;

	private BitmapFont font;
	private Texture dialogBackground;
	private Sprite dialog;

	private String text =
		        "\"ในปี พ.ศ.2600 ได้เกิดสงครามโลกครั้งที่ 3\" \n\"โดยมีการใช้อาวุธนิวเคลียร์ ทำให้ทั้งโลกเกิดสภาวะอากาศเป็นพิษ\" \n\"ทำให้ผู้คนไม่สามารถอาศัยอยู่บนพื้นโลก ผู้คนจึงต้องหลบไปอยู่ในสถานที่หลบภัย\""
		        + "\n\"คุณคือผู้กล้าที่ถูกคัดเลือกโดยต้องไปสำรวจสถานที่หลบภัยแห่งหนึ่ง\"\n\"และพาคนในพื้นที่เข้าไปหลบภัยในสถานที่หลบภัยแห่งนั้นให้มากที่สุด \"\n\"เอาล่ะ รีบเร่งมือกันเถอะ (กด Enter เพื่อเริ่มเกม)\"";

	private int charCount = 0;
	private float stringCompleteness = 0;


    public IntroScreen(final Game game) {
        super(game);
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
		batch = new SpriteBatch();

		font = new BitmapFont(Gdx.files.internal("thai24.fnt"));
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font.setColor(0.4f,0,0,1);

		dialogBackground = new Texture("dialog.png");
		dialogBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		dialog = new Sprite(dialogBackground);
		dialog.setPosition(-dialogBackground.getWidth()*0.5f, -dialogBackground.getHeight()*0.5f);

    }

    @Override
    public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateText();
		if(Gdx.input.isKeyJustPressed(Keys.R)) stringCompleteness = 0; //กดปุ่ม R เพื่อ Reset ให้ข้อความวิ่งใหม่
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) game.setScreen(new GameScreen(game)); //กดปุ่ม R เพื่อ Reset ให้ข้อความวิ่งใหม่

		camera.update();
	    batch.setProjectionMatrix(camera.combined);

		batch.begin();
		dialog.draw(batch);

		font.draw(batch, text,
		        -170f, 60f,           // ตำแหน่ง  x, y
		        0, charCount,      // start, end ของข้อความที่จะวาด
		        500f,                 // ความกว้างของข้อความก่อนที่จะ Warp (ตัดขึ้นบรรทัดใหม่)
		        Align.left,          // การจัดวางข้อความ
		        true);              // ต้องการให้ Warp ไหม

		batch.end();
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
    }

	private void updateText() {
	    stringCompleteness += 30 * Gdx.graphics.getDeltaTime();  // 30 คือ speed ของอักษร
	    charCount = (int) stringCompleteness;
	    if (charCount > text.length())
	    {
	        charCount = text.length();
	    }
	}

}