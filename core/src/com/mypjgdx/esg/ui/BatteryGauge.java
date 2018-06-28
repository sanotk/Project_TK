package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mypjgdx.esg.game.Assets;

public class BatteryGauge extends Table {

    private static final Color red = Color.valueOf("ff3838ff");
    private static final Color blue = Color.valueOf("00aad4ff");

    private static final int GAUGE_WIDTH = 88;
    private static final int GAUGE_HEIGHT = 22;

    private float max = 1800000;
    private float limit = 10000;

    private TextureRegionDrawable redBg = new TextureRegionDrawable(new TextureRegion(Assets.instance.redBatteryGauge));
    private TextureRegionDrawable blueBg = new TextureRegionDrawable(new TextureRegion(Assets.instance.blueBatteryGauge));

    private TextureRegionDrawable whiteDrawable = new TextureRegionDrawable(new TextureRegion(Assets.instance.white));

    private Drawable leftBlueGauge;
    private Drawable leftRedGauge;
    private Drawable rightBlueGauge;
    private Drawable rightRedGauge;

    private Image leftBar;
    private Image limitImage;
    private Image rightBar;

    private float startPercent;
    private float endPercent;
    private float currentPercent;
    private float elapsed;

    public BatteryGauge() {
        leftBlueGauge = whiteDrawable.tint(blue);
        leftRedGauge =  whiteDrawable.tint(red);
        rightBlueGauge = whiteDrawable.tint(blue);
        rightRedGauge =  whiteDrawable.tint(red);

        leftBlueGauge.setMinHeight(GAUGE_HEIGHT);
        leftRedGauge.setMinHeight(GAUGE_HEIGHT);
        rightBlueGauge.setMinHeight(GAUGE_HEIGHT);
        rightRedGauge.setMinHeight(GAUGE_HEIGHT);

        leftBar = new Image(leftBlueGauge);
        rightBar = new Image(rightBlueGauge);
        limitImage = new Image(Assets.instance.limit);

        setBackground(blueBg);
        left().top();
        pad(7, 31, 0, 0);
        add(leftBar);
        add(limitImage);
        add(rightBar);

        setGaugePercent(1);
        pack();
        setGaugePercent(0);
    }

    private void updateGauge(float delta, float energyUse) {
        float gaugePercent = Math.min((energyUse/ max), 1f);
        if (Math.abs(endPercent - gaugePercent) > 0.01f) {
            endPercent = gaugePercent;
            startPercent = currentPercent;
            elapsed = 0;
        }
        elapsed += delta;
        currentPercent = Math.min(1f, Interpolation.elasticOut.apply(startPercent, endPercent, Math.min(1f, elapsed/3f)));
        setGaugePercent(currentPercent);
    }

    private void setGaugePercent(float gaugePercent) {
        float limitPercent = Math.min((limit / max), 1f);
        float leftPercent = Math.min(gaugePercent, limitPercent);
        float rightPercent = Math.max(gaugePercent - limitPercent, 0f);

        float leftWidth = GAUGE_WIDTH * leftPercent;
        float rightWidth = GAUGE_WIDTH * rightPercent;

        leftBlueGauge.setMinWidth(leftWidth);
        rightBlueGauge.setMinWidth(rightWidth);

        leftRedGauge.setMinWidth(leftWidth);
        rightRedGauge.setMinWidth(rightWidth);

        leftBar.pack();
        limitImage.pack();
        rightBar.pack();


        if (currentPercent > limitPercent) {
            setBackground(redBg);
            leftBar.setDrawable(leftRedGauge);
            rightBar.setDrawable(rightRedGauge);
        } else {
            setBackground(blueBg);
            leftBar.setDrawable(leftBlueGauge);
            rightBar.setDrawable(rightBlueGauge);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateGauge(delta, EnergyUsedBar.instance.energyUse);
    }
}
