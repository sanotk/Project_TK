package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mypjgdx.esg.game.Assets;

public class TalkButton extends AndroidButton {

    private static final float SIZE = 100f;

    private static final ButtonStyle style;
    static {
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(Assets.instance.talkButtonBackground));
        style.up.setMinWidth(SIZE);
        style.up.setMinHeight(SIZE);
    }

    public TalkButton() {
        super(style);
    }

}
