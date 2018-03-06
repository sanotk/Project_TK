package com.mypjgdx.esg.game.objects.items;

import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.weapons.Weapon;

import java.util.List;

public class EnergyTube extends Item {

    private static final float SCALE = 1.0f;

    public float energy;
    private boolean drainEnergy;
    public static final float P_X = 200f;
    public static final float P_Y = 200f;

    public EnergyTube(float startEnergy) {
        super(Assets.instance.solarCellAltas, SCALE, SCALE , P_X , P_Y);
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
    public void update(float deltaTime, List<Weapon> etcs) {
        super.update(deltaTime, etcs);

    }

    public boolean isEnergyDraining() {
        return drainEnergy;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub

    }

}
