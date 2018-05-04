package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class EnergyBar implements Json.Serializable {

    public static final EnergyBar instance = new EnergyBar();
    private static final float ENERGY_MAX = 500;

    public float energy;

    private EnergyBar() {
    }

//    public void draw(ShapeRenderer renderer, float x, float y) {
//        renderer.rect(x, y, 300 * (energy / ENERGY_MAX), 30);
//    }

    @Override
    public void write(Json json) {
        json.writeValue("energy", energy);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        energy = jsonData.get("energyBar").getFloat("energy");
    }
}
