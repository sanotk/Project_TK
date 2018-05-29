package com.mypjgdx.esg.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;

public class LoadTestButton extends TextButton {

    private static final TextButtonStyle style;
    static  {
        style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(Assets.instance.buttonBlue1);
        style.down = new TextureRegionDrawable(Assets.instance.buttonBlue2);
        style.font = Assets.instance.newFont;
    }

    private WorldController worldController;

    public LoadTestButton() {
        super("Load", style);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldController.load();
            }
        });
    }

    public void setWorldController(WorldController worldController) {
        this.worldController = worldController;
    }
}
