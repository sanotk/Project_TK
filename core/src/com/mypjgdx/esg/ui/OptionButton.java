package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mypjgdx.esg.game.Assets;

public class OptionButton extends Button {

    private static final ButtonStyle style;
    static {
        style = new ButtonStyle();
        TextureRegionDrawable toolUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_tools"));
        style.up = toolUp;
        style.down = toolUp.tint(Color.LIGHT_GRAY);
    }

    public OptionButton(final Window optionWindow, float x, float y) {
        super(style);
        setPosition(x, y);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionWindow.setPosition(
                        optionWindow.getStage().getWidth() / 2 - optionWindow.getWidth() / 2,
                        optionWindow.getStage().getHeight() / 2 - optionWindow.getHeight() / 2);
                optionWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });
    }

}
