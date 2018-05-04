package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class EnergyProducedBar implements Json.Serializable {

    public static final EnergyProducedBar instance = new EnergyProducedBar();
    private static final float ENERGY_MAX = 500;

    public float energyProduced;

    private EnergyProducedBar() {
    }

//    public void draw(ShapeRenderer renderer, float x, float y) {
//        renderer.rect(x, y, 300 * (energyProduced / ENERGY_MAX), 30);
//    }

    @Override
    public void write(Json json) {
        json.writeValue("energyProduced", energyProduced);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        energyProduced = jsonData.get("EnergyProducedBar").getFloat("energyProduced");
    }
}
