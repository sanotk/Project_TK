package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mypjgdx.esg.game.Assets;

public class CloseButton extends Button {

    public CloseButton() {
        super(new Button.ButtonStyle());
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        getStyle().up = buttonRegion;
        getStyle().down = buttonRegion.tint(Color.LIGHT_GRAY);
        pack();
    }
}
