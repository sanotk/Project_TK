package com.mypjgdx.esg.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mypjgdx.esg.game.Assets;

public class Hud extends Table {

    private Label arrowLabel;
    private Label swordWaveLabel;
    private Label trapLabel;
    private Label timeLabel;
    private Label energyLabel;

    public Hud() {
        arrowLabel = new Label("", Assets.instance.skin);
        swordWaveLabel = new Label("", Assets.instance.skin);
        trapLabel = new Label("", Assets.instance.skin);
        timeLabel = new Label("", Assets.instance.skin);
        energyLabel = new Label("", Assets.instance.skin);

        add(arrowLabel).spaceRight(20);
        add(swordWaveLabel).spaceRight(20);
        add(trapLabel).spaceRight(20);
        add(timeLabel).spaceRight(20);
        add(energyLabel);
        pack();
    }

    public void setArrow(int bullet) {
        arrowLabel.setText("Arrow: " + bullet);
    }

    public void setSwordWave(int beam) {
        swordWaveLabel.setText("Sword Wave: " + beam);
    }

    public void setTrap(int trap) {
        trapLabel.setText("Trap: " + trap);
    }

    public void setTime(float time) {
        timeLabel.setText("Time: " + (int) time);
    }

    public void setEnergy(float energy) {
        energyLabel.setText("Energy: " + (int) energy);
    }

    public void update(int bullet, int beam, int trap, float time, float energy) {
        setArrow(bullet);
        setSwordWave(beam);
        setTrap(trap);
        setTime(time);
        setEnergy(energy);
    }
}
