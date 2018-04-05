package com.mypjgdx.esg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mypjgdx.esg.game.Assets;

public class ExampleGame extends ApplicationAdapter {

    Stage stage;
    BitmapFont font;

    TextButton textButton;

    TextButton.TextButtonStyle buttonStyle1 = new TextButton.TextButtonStyle();
    TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();

    @Override
    public void create() {
        stage = new Stage();
        font = new BitmapFont();

        Assets.instance.init();

        buttonStyle1 = new TextButton.TextButtonStyle();
        buttonStyle1.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle1.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle1.font = font;

        buttonStyle2 = new TextButton.TextButtonStyle();
        buttonStyle2.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle2.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle2.font = font;

        textButton = new TextButton("Hello", buttonStyle1);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Function 1");
            }
        });

        stage.addActor(textButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            textButton.setText("World!");
            textButton.clearListeners();
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Function 2");
                }
            });
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        Assets.instance.init();
    }
}
