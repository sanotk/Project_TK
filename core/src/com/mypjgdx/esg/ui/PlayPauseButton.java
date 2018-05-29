package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;

public class PlayPauseButton extends Button {

    private final ButtonStyle style = new ButtonStyle();
    private Drawable pauseUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_pause"));
    private Drawable pauseDown = ((TextureRegionDrawable) pauseUp).tint(Color.LIGHT_GRAY);
    private Drawable playUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_play"));
    private Drawable playDown = ((TextureRegionDrawable) playUp).tint(Color.LIGHT_GRAY);

    public PlayPauseButton(final WorldController worldController, float x, float y) {
        super();
        setStyle(style);
        setPosition(x, y);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldController.level.player.timeStop = !worldController.level.player.timeStop;
                setPlayIcon(worldController.level.player.timeStop);
            }
        });
        setPlayIcon(false);
    }

    private void setPlayIcon(boolean playIcon) {
        if (playIcon) {
            style.up = playUp;
            style.down = playDown;
        } else {
            style.up = pauseUp;
            style.down = pauseDown;
        }
        pack();
    }
}
