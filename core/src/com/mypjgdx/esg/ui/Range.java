package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Range implements Json.Serializable {

    public static final Range instance = new Range();

    public float rangeToPlayer = 0;

    private Range() {
    }

//    public void draw(ShapeRenderer renderer, float x, float y) {
//        renderer.rect(x, y, 300 * (energyProduced / ENERGY_MAX), 30);
//    }

    @Override
    public void write(Json json) {
        json.writeValue("rangeToPlayer", rangeToPlayer);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        rangeToPlayer = jsonData.get("Range").getFloat("rangeToPlayer");
    }
    
    public float getRangeToPlayer(){
        return rangeToPlayer;
    }

    public void addRangeToPlayer(float rangeToPlayer) {
        rangeToPlayer += 50;
    }
}
