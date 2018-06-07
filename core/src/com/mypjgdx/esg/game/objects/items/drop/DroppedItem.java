package com.mypjgdx.esg.game.objects.items.drop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.game.objects.AbstractGameObject;

public class DroppedItem extends AbstractGameObject implements Json.Serializable {
    private DroppedItemType type;
    private TextureRegion iconRegion;

    public DroppedItem(DroppedItemType type, TextureRegion iconRegion) {
        this.type = type;
        this.iconRegion = iconRegion;
        friction.set(500, 500);
        setDimension(iconRegion.getRegionWidth(), iconRegion.getRegionHeight());
    }

    @Override
    public void render(SpriteBatch batch) {
        render(batch, iconRegion);
    }

    @Override
    public void write(Json json) {
        json.writeValue("position", position);
        json.writeValue("velocity", velocity);
        json.writeValue("type", type);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue positionJson = jsonData.get("position");
        setPosition(positionJson.getFloat("x", 0), positionJson.getFloat("y", 0));

        JsonValue velocityJson = jsonData.get("velocity");
        velocity.set(velocityJson.getFloat("x", 0), velocityJson.getFloat("y", 0));
    }
}
