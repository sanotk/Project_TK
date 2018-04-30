package com.mypjgdx.esg.ui;

public class EnergyBar {

    public static final EnergyBar instance = new EnergyBar();
    private static final float ENERGY_MAX = 500;

    public float energy;

    private EnergyBar() {
    }

//    public void draw(ShapeRenderer renderer, float x, float y) {
//        renderer.rect(x, y, 300 * (energy / ENERGY_MAX), 30);
//    }


}
