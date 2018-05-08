package com.mypjgdx.esg.ui;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class BatteryBar implements Json.Serializable {

    public static final BatteryBar instance = new BatteryBar();
    public static final float BATTERY_MAX = 1000000;

    public float batteryStorage;

    private BatteryBar() {
    }

//    public void draw(ShapeRenderer renderer, float x, float y) {
//        renderer.rect(x, y, 300 * (energyProduced / ENERGY_MAX), 30);
//    }

    @Override
    public void write(Json json) {
        json.writeValue("batteryStorage", batteryStorage);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        batteryStorage = jsonData.get("BatteryBar").getFloat("batteryStorage");
    }

    public void update(float deltatime){
        batteryStorage += EnergyProducedBar.instance.energyProduced * deltatime;
        batteryStorage -= EnergyUsedBar.instance.energyUse * deltatime;
    }

    public float getBatteryStorage(){
        return batteryStorage;
    }

    public void addEnergy(float energyProduced) {
        batteryStorage += energyProduced;
    }
}
