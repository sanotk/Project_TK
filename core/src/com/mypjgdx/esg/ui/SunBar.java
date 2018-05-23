package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SunBar implements Json.Serializable {

    public static final SunBar instance = new SunBar();
    
    public float accelerateTime = 1;
    public float timeCount = 0;
    public float sunTime = 11;

    private SunBar() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("sunTime", sunTime);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        sunTime = jsonData.get("SunBar").getFloat("sunTime");
    }
}
