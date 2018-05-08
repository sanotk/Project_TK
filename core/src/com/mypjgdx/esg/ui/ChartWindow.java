package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mypjgdx.esg.game.Assets;

public class ChartWindow extends CustomWindow {


    public ChartWindow() {
        super("Chart", style());

        Button.ButtonStyle closeButtonStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        closeButtonStyle.up = buttonRegion;
        closeButtonStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(closeButtonStyle);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });

        setModal(true);
        padTop(40);
        padLeft(40);
        padRight(40);
        padBottom(20);
        getTitleLabel().setAlignment(Align.center);
        row().padBottom(10).padTop(10);
        row().padTop(10);
        add(closeButton).colspan(3);
        pack();
    }

    private static WindowStyle style() {
        Window.WindowStyle style = new WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
        style.titleFont = Assets.instance.font;

        return style;
    }
}
