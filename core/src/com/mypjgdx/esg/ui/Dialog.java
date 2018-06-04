package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Dialog extends Actor {
    private static final float DEFAULT_TEXT_SPEED = 50f;
    private static final float MAX_TEXT_SPEED = 100.0f;

    private BitmapFont font;
    private TextureRegion dialogTexture;
    private float textStartX;
    private float textStartY;

    private float textSpeed = DEFAULT_TEXT_SPEED;
    private String text = "";
    private int charCount = 0;
    private float stringCompleteness = 0;

    private Action typingAction = new Action() {
        @Override
        public boolean act(float delta) {
            stringCompleteness += textSpeed * delta;
            charCount = (int) stringCompleteness;
            if (charCount >= text.length()) {
                charCount = text.length();
                return true;
            }
            return false;
        }
    };


    public Dialog(BitmapFont font, Texture dialogTexture, float textStartX, float textStartY) {
        this(font, new TextureRegion(dialogTexture), textStartX, textStartY);
    }

    public Dialog(BitmapFont font, TextureRegion dialogTexture, float textStartX, float textStartY) {
        this.font = font;
        this.dialogTexture = dialogTexture;

        this.textStartX = textStartX;
        this.textStartY = textStartY;

        setWidth(dialogTexture.getRegionWidth());
        setHeight(dialogTexture.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(dialogTexture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        Color fontColor = font.getColor();
        font.setColor(fontColor.r, fontColor.g, fontColor.b, color.a*parentAlpha);
        font.draw(batch, text,
                getX() + textStartX,
                getY() + textStartY,
                0, charCount,
                getWidth() - textStartX * 2, Align.left, true);
    }

    public void hide() {
        setVisible(false);
    }

    public void show() {
        setVisible(true);
    }

    public void setText(final String text) {
        removeAction(typingAction);
        typingAction.reset();
        textSpeed = DEFAULT_TEXT_SPEED;
        stringCompleteness = 0;
        charCount = 0;

        this.text = text;
        addAction(typingAction);
    }

    public void speedUp() {
        textSpeed = MAX_TEXT_SPEED;
    }

}
