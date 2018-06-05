package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dialog extends Actor {
    private static final float DEFAULT_TEXT_SPEED = 50f;
    private static final float MAX_TEXT_SPEED = 100f;

    private static final DialogCommand nullCommand = new DialogCommand(null, 0, 0, 0);

    private BitmapFont font;
    private TextureRegion dialogTexture;
    private float textStartX;
    private float textStartY;

    private float textSpeed = DEFAULT_TEXT_SPEED;
    private float charAccumulator = 0;
    private int charIndex = -1;

    private String text = "";
    private StringBuilder showingText = new StringBuilder();
    private String cachedShowingText = "";

    private IntMap<DialogCommand> commands = new IntMap<DialogCommand>();
    private DialogListener listener;

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
    public void act(float delta) {
        super.act(delta);
        if (charIndex < text.length() - 1) {
            while (charAccumulator >= 1 && charIndex < text.length() - 1) {
                charAccumulator--;
                charIndex++;
                updateShowingText();
                cachedShowingText = showingText.toString();
            }
            charAccumulator += textSpeed * delta;
        }
    }

    private void updateShowingText() {
        DialogCommand command = commands.get(charIndex, nullCommand);
        if (command != nullCommand) {
            // jumpIndex to last character of command and insert space
            charIndex += (command.endIndex - command.beginIndex);
            for (int i = 0; i < command.space; i++) {
                showingText.append(' ');
            }
            if (listener != null) {
                listener.onCommandExecuted(command.id);
            }
        } else{
            showingText.append(text.charAt(charIndex));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(dialogTexture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        Color fontColor = font.getColor();
        font.setColor(fontColor.r, fontColor.g, fontColor.b, color.a*parentAlpha);
        font.draw(batch, cachedShowingText,
                getX() + textStartX,
                getY() + textStartY,
                getWidth() - textStartX * 2, Align.left, true);
    }

    public void hide() {
        setVisible(false);
    }

    public void show() {
        setVisible(true);
    }

    public void setText(final String text) {
        textSpeed = DEFAULT_TEXT_SPEED;
        charAccumulator = 0;
        charIndex = -1;
        showingText.setLength(0);
        cachedShowingText = "";
        this.text = text;
        processText(text);
    }

    public void speedUp() {
        textSpeed = MAX_TEXT_SPEED;
    }

    private void processText(String text) {
        String regex = "@icon\\( *id=(\\w+) *, *space=(\\d+) *\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        commands.clear();
        while (matcher.find()) {
            String word = matcher.group(0);
            int beginIndex = text.indexOf(word);
            int endIndex = beginIndex + word.length() - 1;
            String id = matcher.group(1);
            int space = Integer.parseInt(matcher.group(2));
            commands.put(beginIndex, new DialogCommand(id, space, beginIndex, endIndex));
        }
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    private static class DialogCommand {
        final String id;
        final int space;
        final int beginIndex;
        final int endIndex;

        DialogCommand(String id, int space, int beginIndex, int endIndex) {
            this.id = id;
            this.space = space;
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
        }
    }
}
