package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mypjgdx.esg.game.Assets;

public class PlayerTouchPad extends Touchpad {

    private static final float DEAD_ZONE_RADIUS = 10;

    private static final float BACKGROUND_SIZE = 200;
    private static final float KNOB_SIZE = 80;

    private static final TouchpadStyle style;
    static {
        style = new TouchpadStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(Assets.instance.touchPadBackground));
        style.knob = new TextureRegionDrawable(new TextureRegion(Assets.instance.touchPadKnob));

        style.background.setMinWidth(BACKGROUND_SIZE);
        style.background.setMinHeight(BACKGROUND_SIZE);
        style.knob.setMinWidth(KNOB_SIZE);
        style.knob.setMinHeight(KNOB_SIZE);
    }

    public PlayerTouchPad() {
        super(DEAD_ZONE_RADIUS, style);
    }
}
