package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.utils.MusicManager;
import com.mypjgdx.esg.utils.SoundManager;

public class OptionWindow extends CustomWindow {

    public static final OptionWindow instance = new OptionWindow();

    private OptionWindow() {
        super("Option", style());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.font;
        labelStyle.fontColor = Color.BLACK;

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("slider_back_hor"));
        TextureRegionDrawable knobRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("knob_03"));
        sliderStyle.knob = knobRegion;
        sliderStyle.knobDown = knobRegion.tint(Color.LIGHT_GRAY);

        final Slider musicSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        musicSlider.setValue(0.5f);
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MusicManager.instance.setVolume(musicSlider.getValue());
            }
        });

        final Slider soundSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        soundSlider.setValue(0.5f);
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.instance.setVolume(soundSlider.getValue());
            }
        });

        Button.ButtonStyle buttonCrossStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonCrossStyle.up = buttonRegion;
        buttonCrossStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonCrossStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        setModal(true);
        padTop(40);
        padLeft(40);
        padRight(40);
        padBottom(20);
        getTitleLabel().setAlignment(Align.center);
        row().padBottom(10).padTop(10);
        add(new Label("Volume", labelStyle)).colspan(3);
        row();
        add(new Image(Assets.instance.uiBlue.findRegion("icon_music"))).padRight(10);
        add(new Label("Music", labelStyle)).padRight(10);
        add(musicSlider).width(250);
        row().padTop(10);
        add(new Image(Assets.instance.uiBlue.findRegion("icon_sound_on"))).padRight(10);
        add(new Label("Sound Fx", labelStyle)).padRight(10);
        add(soundSlider).width(250);
        row().padTop(20);
        add(closeButton).colspan(3);
        pack();
    }

    private static WindowStyle style() {
        WindowStyle style = new WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
        style.titleFont = Assets.instance.font;

        return style;
    }
}
