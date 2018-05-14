package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class EnergyProducedBar implements Json.Serializable {

    public static final EnergyProducedBar instance = new EnergyProducedBar();

    public float energyProduced = 800;

    private EnergyProducedBar() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("energyProduced", energyProduced);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        energyProduced = jsonData.get("EnergyProducedBar").getFloat("energyProduced");
    }
}
