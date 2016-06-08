package com.mypjgdx.esg.game.objects;

import com.mypjgdx.esg.game.Assets;

public class EnergyTube extends Item {

    private static final float SCALE = 1.0f;

    public float energy;
    private boolean drainEnergy;

    public EnergyTube(float startEnergy) {
        super(Assets.instance.solarCellAltas, SCALE, SCALE);  // แก้ตรง altas เป็นภาพหลอดพลังงาน
        energy = startEnergy;
        startDrainEnergy();
    }

    public void startDrainEnergy() {
        drainEnergy = true;
    }

    public void stopDrainEnergy() {
        drainEnergy = false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (drainEnergy) {
            energy -= deltaTime;
            if (energy < 0) energy = 0;
        }
    }

    public boolean isEnergyDraining() {
        return drainEnergy;
    }

}
