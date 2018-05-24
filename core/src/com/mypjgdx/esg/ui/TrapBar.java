package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TrapBar implements Json.Serializable {

    public static final TrapBar instance = new TrapBar();

    public float energyTrap = 100;
    public float totalUse = 0;

    private TrapBar() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("energyTrap", energyTrap);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        energyTrap = jsonData.get("TrapBar").getFloat("energyTrap");
    }
}
