package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class EnergyUsedBar implements Json.Serializable {

    public static final EnergyUsedBar instance = new EnergyUsedBar();
    private static final float ENERGY_USE_MAX = 500;

    public float energyUse;

    private EnergyUsedBar() {
    }

//    public void draw(ShapeRenderer renderer, float x, float y) {
//        renderer.rect(x, y, 300 * (energyProduced / ENERGY_MAX), 30);
//    }

    @Override
    public void write(Json json) {
        json.writeValue("energyUse", energyUse);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        energyUse = jsonData.get("EnergyUsedBar").getFloat("energyUse");
    }
}
