package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SwordWaveBar implements Json.Serializable {

    public static final SwordWaveBar instance = new SwordWaveBar();

    public float energySwordWave = 9999;

    private SwordWaveBar() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("energySwordWave", energySwordWave);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        energySwordWave = jsonData.get("SwordWaveBar").getFloat("energySwordWave");
    }
}
