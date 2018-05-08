package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ArrowBar implements Json.Serializable {

    public static final ArrowBar instance = new ArrowBar();

    public float energyArrow = 200;

    private ArrowBar() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("energyArrow", energyArrow);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        energyArrow = jsonData.get("ArrowBar").getFloat("energyArrow");
    }
}
