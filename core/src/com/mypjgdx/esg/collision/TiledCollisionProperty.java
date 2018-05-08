package com.mypjgdx.esg.collision;

public enum TiledCollisionProperty {
    BLOCKED("blocked"),
    SOLAR_CELL("solarcell"),
    INVERTER("inverter"),
    CONTROLLER("ccontroller"),
    BATTERY("battery"),
    DOOR("door"),
    REFRIGERATOR("refrigerator"),
    TV("tv"),
    COMPUTER("com"),
    PUMP("pump"),
    FAN("fan"),
    AIR_CONDITIONER("air"),
    RICE_COOKER("cooker"),
    MICROWAVE("microwave"),
    SWITCH("switch");

    public final String propertyName;

    TiledCollisionProperty(String propertyName) {
        this.propertyName = propertyName;
    }
}
