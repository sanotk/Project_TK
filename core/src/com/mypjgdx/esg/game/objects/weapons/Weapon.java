package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

public abstract class Weapon extends AbstractGameObject implements Json.Serializable {

    private TextureRegion weaponTexture;
    private boolean destroyed;

    protected Player player;
    public Direction direction;

    public Weapon(TextureRegion weaponTexture, float scaleX, float scaleY, float frictionX, float frictionY) {
        this.weaponTexture = weaponTexture;

        scale.set(scaleX, scaleY);
        setDimension(
                weaponTexture.getRegionWidth(),
                weaponTexture.getRegionHeight());

        destroyed = false;
        friction.set(frictionX, frictionY);
    }

    public void init(TiledMapTileLayer mapLayer, Player player) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        this.player = player;
    }

    protected abstract void spawn(Direction direction);

    @Override
    public void render(SpriteBatch batch) {
        render(batch, weaponTexture);
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void destroy(){
        destroyed = true;
    }

    public abstract void attack(Damageable damageable);

    @Override
    public void write(Json json) {
        json.writeValue("direction", direction);
        json.writeValue("position", position);
        json.writeValue("velocity", velocity);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        spawn(Direction.valueOf(jsonData.getString("direction")));

        JsonValue positionJson = jsonData.get("position");
        setPosition(positionJson.getFloat("x", 0), positionJson.getFloat("y", 0));

        JsonValue velocityJson = jsonData.get("velocity");
        velocity.set(velocityJson.getFloat("x", 0), velocityJson.getFloat("y", 0));
    }
}
