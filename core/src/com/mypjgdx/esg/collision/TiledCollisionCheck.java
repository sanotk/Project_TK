package com.mypjgdx.esg.collision;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class TiledCollisionCheck implements CollisionCheck {

    private TiledMapTileLayer tileLayer;
    private Rectangle rectObject;
    private TiledCollisionProperty property;

    private static final Pool<TiledCollisionCheck> pool = new Pool<TiledCollisionCheck>() {
        @Override
        protected TiledCollisionCheck newObject() {
            return new TiledCollisionCheck();
        }
    };

    public TiledCollisionCheck() {
    }

    public TiledCollisionCheck(Rectangle rectObject, TiledMapTileLayer tileLayer) {
        this(rectObject, tileLayer, TiledCollisionProperty.BLOCKED);
    }

    public TiledCollisionCheck(Rectangle rectObject, TiledMapTileLayer tileLayer, TiledCollisionProperty property) {
        init(rectObject, tileLayer, property);
    }

    public static Pool<TiledCollisionCheck> getPool() {
        return pool;
    }

    public final void init(Rectangle rectObject, TiledMapTileLayer tileLayer, TiledCollisionProperty property) {
        this.tileLayer = tileLayer;
        this.rectObject = rectObject;
        this.property = property;
    }

    @Override
    public boolean isCollidesRight() {
        for (float step = 0; step < rectObject.height; step += tileLayer.getTileHeight())
            if (isCellHasProperty (rectObject.x + rectObject.width, rectObject.y + step))
                return true;
        return isCellHasProperty (rectObject.x + rectObject.width, rectObject.y + rectObject.height);
    }

    @Override
    public boolean isCollidesLeft() {
        for (float step = 0; step < rectObject.height; step += tileLayer.getTileHeight())
            if (isCellHasProperty (rectObject.x, rectObject.y + step))
                return true;
        return isCellHasProperty (rectObject.x, rectObject.y + rectObject.height);
    }

    @Override
    public boolean isCollidesTop() {
        for(float step = 0; step < rectObject.width; step += tileLayer.getTileWidth())
            if (isCellHasProperty(rectObject.x + step, rectObject.y + rectObject.height))
            return true;
        return isCellHasProperty(rectObject.x + rectObject.width, rectObject.y + rectObject.height);
    }

    @Override
    public boolean isCollidesBottom() {
        for(float step = 0; step < rectObject.width; step += tileLayer.getTileWidth())
            if (isCellHasProperty(rectObject.x + step, rectObject.y))
                return true;
        return isCellHasProperty(rectObject.x + rectObject.width, rectObject.y);
    }

    private boolean isCellHasProperty(float x, float y) {
        int cellX = (int) (x/ tileLayer.getTileWidth());
        int cellY = (int) (y/ tileLayer.getTileHeight());
        if (cellX < tileLayer.getWidth() && cellX >= 0 && cellY < tileLayer.getHeight() && cellY >= 0) {
            return tileLayer.getCell(cellX, cellY).getTile().getProperties().containsKey(property.propertyName);
        }
        return false;
    }

}
