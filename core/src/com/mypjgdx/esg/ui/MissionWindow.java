package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;

public class MissionWindow extends Window {

    private Label[] labels = new Label[9];

    public MissionWindow(final WorldController worldController) {
        super("รายชื่อภารกิจ", new WindowStyle(Assets.instance.newFont, Color.WHITE, new NinePatchDrawable(Assets.instance.window)));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.newFont;

        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label("", labelStyle);
        }

        Button.ButtonStyle closeButtonStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        closeButtonStyle.up = buttonRegion;
        closeButtonStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(closeButtonStyle);

        setModal(true);
        padTop(45);
        padLeft(40);
        padRight(40);
        padBottom(20);
        getTitleLabel().setAlignment(Align.center);
        for (Label label : labels) {
            row().padTop(10);
            add(label);
        }
        row().padTop(20);
        add(closeButton).colspan(3).center().bottom();
        pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.timeStop = false;
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            setPosition(
                    getStage().getWidth() / 2 - getWidth() / 2,
                    getStage().getHeight() / 2 - getHeight() / 2);
        }
    }

    public void setCompleted(boolean completed, int index) {
        if (completed)
            labels[index].setColor(Color.LIME);
        else
            labels[index].setColor(Color.WHITE);
    }

    public void setText(String text, int index) {
        labels[index].setText(text);
        pack();
    }
}
