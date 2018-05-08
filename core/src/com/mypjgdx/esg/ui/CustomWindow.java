package com.mypjgdx.esg.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

public class CustomWindow extends Window{

    private InputListener ignoreTouchDown = new InputListener() {
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            event.cancel();
            return false;
        }
    };

    public CustomWindow(String title, Skin skin) {
        super(title, skin);
    }

    public CustomWindow(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
    }

    public CustomWindow(String title, WindowStyle style) {
        super(title, style);
    }

    public void show (Stage stage) {
        clearActions();
        removeCaptureListener(ignoreTouchDown);
        addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.4f, Interpolation.fade)));
        stage.addActor(this);
        setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
    }

    public void hide () {
        addCaptureListener(ignoreTouchDown);
        addAction((Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade), Actions.removeActor())));
    }
}
