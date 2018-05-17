package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class LikingBar implements Json.Serializable {

    public static final LikingBar instance = new LikingBar();

    public float liking = 0;

    private LikingBar() {

    }

    @Override
    public void write(Json json) {
        json.writeValue("liking", liking);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        liking = jsonData.get("LikingBar").getFloat("liking");
    }
}
