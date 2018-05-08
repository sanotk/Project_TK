package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mypjgdx.esg.collision.TiledCollisionProperty;

import static com.mypjgdx.esg.game.Assets.instance;

public enum ItemBuilder {
    SOLAR_CELL("Solar Cell", instance.solarCellAtlas, TiledCollisionProperty.SOLAR_CELL, true),
    BATTERY("Battery", instance.batAtlas, TiledCollisionProperty.BATTERY),
    INVERTER("Inverter", instance.inverAtlas, TiledCollisionProperty.INVERTER, true),
    CONTROLLER("Charge Controller", instance.ccAtlas, TiledCollisionProperty.CONTROLLER, true),
    DOOR("Door", instance.doorAtlas, TiledCollisionProperty.DOOR),
    SWITCH("Switch", instance.switchAtlas, TiledCollisionProperty.SWITCH),
    TV("Television", instance.tvAtlas, TiledCollisionProperty.TV),
    WATER_FILTER("Water Filter", instance.solarCellAtlas),  //TODO: need to update atlas!
    WATER_HEATER("Water Heater", instance.solarCellAtlas),  //TODO: need to update atlas!
    WATER_PUMP("Water Pump", instance.waterPumpAtlas, 0.5f, TiledCollisionProperty.PUMP),
    RICE_COOKER("Rice Cooker", instance.riceCookerAtlas, TiledCollisionProperty.RICE_COOKER),
    REFRIGERATOR("Refrigerator", instance.refrigeratorAtlas, TiledCollisionProperty.REFRIGERATOR),
    MICROWAVE("Microwave", instance.microwaveAtlas, TiledCollisionProperty.MICROWAVE),
    FAN("Fan", instance.fanAtlas, TiledCollisionProperty.FAN),
    COMPUTER("Computer", instance.comAtlas, TiledCollisionProperty.COMPUTER),
    AIR_CONDITIONER("Air Conditioner", instance.airAtlas, TiledCollisionProperty.AIR_CONDITIONER);

    private final String name;
    private final TextureAtlas atlas;
    private final TiledCollisionProperty collisionProperty;
    private float scale = 1.0f;
    private float frameTime = 1.0f / 2.0f;
    private final boolean hasOnAnimation;

    ItemBuilder(String name, TextureAtlas atlas) {
        this(name, atlas, false);
    }

    ItemBuilder(String name, TextureAtlas atlas, float scale, TiledCollisionProperty collisionProperty) {
        this(name, atlas, collisionProperty, false);
        this.scale = scale;
    }

    ItemBuilder(String name, TextureAtlas atlas, boolean hasOnAnimation) {
        this(name, atlas, null, hasOnAnimation);
    }

    ItemBuilder(String name, TextureAtlas atlas, TiledCollisionProperty collisionProperty) {
        this(name, atlas, collisionProperty, false);
    }

    ItemBuilder(String name, TextureAtlas atlas, TiledCollisionProperty collisionProperty, boolean hasOnAnimation) {
        this.name = name;
        this.atlas = atlas;
        this.collisionProperty = collisionProperty;
        this.hasOnAnimation = hasOnAnimation;
    }

    public Item getNew() {
        Item item = new Item(name, atlas, scale, hasOnAnimation, collisionProperty, frameTime);
        switch (this) {
            case SOLAR_CELL:
            case BATTERY:
            case INVERTER:
            case CONTROLLER:
                item.setLinkingWindowOpenable(true);
                break;
            default:
        }
        return item;
    }
}
