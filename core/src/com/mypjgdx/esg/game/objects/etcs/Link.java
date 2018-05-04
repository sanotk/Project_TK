package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.SolarState;

public class Link extends AbstractGameObject implements Json.Serializable{

    public SolarState solarState;

    private TextureRegion linkTexture;

    public Direction direction;
    private boolean destroyed;
   //public int x,y,d;

    private static final float SCALE = 1f;


    public Link() {
        this(null, 0, 0, null, null);
    }

    public Link(TiledMapTileLayer mapLayer, float positionX, float positionY, Direction direction , SolarState solarState) {
        this.linkTexture = Assets.instance.link;

        scale.set(SCALE, SCALE);
        setDimension(
                linkTexture.getRegionWidth(),
                linkTexture.getRegionHeight());

        destroyed = false;

        if (mapLayer != null) {
            init(mapLayer, positionX, positionY, direction, solarState);
        }
    }

    public void init(TiledMapTileLayer mapLayer) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
    }

    public void init(TiledMapTileLayer mapLayer, float positionX, float positionY, Direction direction , SolarState solarState) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        this.solarState = solarState;
        this.direction = direction;
        spawn(positionX, positionY);
    }

    protected void spawn(float positionX, float positionY) {
        switch (direction) {
            case UP:
                rotation = 90;
                positionX += 25;
                break;
            case RIGHT:
                rotation = 180;
                positionY += 25;
                break;
            case DOWN:
                rotation = 270;
                positionX -= 25;
                break;
            case LEFT:
                rotation = 360;
                positionY -= 25;
                break;
            default:
                break;
        }
        setPosition(positionX, positionY);
    }

    @Override
    public void render(SpriteBatch batch) {
        render(batch, linkTexture);
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void destroy(){
        destroyed = true;
    }

    @Override
    public void write(Json json) {
        json.writeValue("position", position);
        json.writeValue("direction", direction);
        json.writeValue("solarState", solarState);
        json.writeValue("rotation", rotation);

    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue positionJson = jsonData.get("position");
        setPosition(positionJson.getFloat("x"), positionJson.getFloat("y"));

        direction = Direction.valueOf(jsonData.getString("direction"));
        solarState = solarState.valueOf(jsonData.getString("solarState"));
        rotation = jsonData.getFloat("rotation");
    }

    @Override
    public String getName() {
        return null;
    }
}
