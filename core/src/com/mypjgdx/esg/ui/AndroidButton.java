package com.mypjgdx.esg.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public abstract class AndroidButton extends Button {

    private static final float PRESSED_SCALE = 0.9f;
    private static final float EFFECT_TIME = 0.03f;

    private boolean justPressed;

    AndroidButton(ButtonStyle style) {
        super(style);
        setTransform(true);
        setOrigin(Align.center);
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                addAction(Actions.scaleTo(PRESSED_SCALE, PRESSED_SCALE, EFFECT_TIME));
                justPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                clearActions();
                addAction(Actions.scaleTo(1, 1, EFFECT_TIME));
                justPressed = false;
            }
        });
    }

    public boolean isJustPressed() {
        boolean currentJustPressed = justPressed;
        justPressed = false;
        return currentJustPressed;
    }
}