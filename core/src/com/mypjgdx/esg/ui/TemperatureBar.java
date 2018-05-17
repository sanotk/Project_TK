package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TemperatureBar implements Json.Serializable {

    public static final TemperatureBar instance = new TemperatureBar();

    public float Temperature = 40;

    private TemperatureBar() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("Temperature", Temperature);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        Temperature = jsonData.get("TemperatureBar").getFloat("Temperature");
    }
}
