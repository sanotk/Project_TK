package com.mypjgdx.esg.ashleytest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mypjgdx.esg.ashleytest.components.ColliderComponent;
import com.mypjgdx.esg.ashleytest.components.TiledColliderComponent;


/**
 * Created by Bill on 8/8/2560.
 */
public class TiledCollisionCheckSystem extends IteratingSystem {


    private ComponentMapper<ColliderComponent> colliderMapper = ComponentMapper.getFor(ColliderComponent.class);
    private ComponentMapper<TiledColliderComponent> tiledColliderMapper = ComponentMapper.getFor(TiledColliderComponent.class);

    public TiledCollisionCheckSystem() {
        super(Family.all(ColliderComponent.class, TiledColliderComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ColliderComponent collider = colliderMapper.get(entity);
        TiledColliderComponent tiledCollider = tiledColliderMapper.get(entity);

        TiledMapTileLayer tileLayer = tiledCollider.tileLayer;
        String property = tiledCollider.tiledColliderProperty;
        Rectangle colliderBox = collider.colliderBox;

        //TODO
        if (isCollidesRight(tileLayer, property, colliderBox)) {

        }
        if (isCollidesLeft(tileLayer, property, colliderBox)) {

        }
        if (isCollidesRight(tileLayer, property, colliderBox)) {

        }
        if (isCollidesTop(tileLayer, property, colliderBox)) {

        }
        if (isCollidesBottom(tileLayer, property, colliderBox)) {

        }
    }

    private boolean isCollidesRight(TiledMapTileLayer tileLayer, String property, Rectangle rectObject) {
        for (float step = 0; step < rectObject.height; step += tileLayer.getTileHeight())
            if (isCellHasProperty (tileLayer, property, rectObject.x + rectObject.width, rectObject.y + step))
                return true;
        return isCellHasProperty (tileLayer, property, rectObject.x + rectObject.width, rectObject.y + rectObject.height);
    }

    private boolean isCollidesLeft(TiledMapTileLayer tileLayer, String property, Rectangle rectObject) {
        for (float step = 0; step < rectObject.height; step += tileLayer.getTileHeight())
            if (isCellHasProperty (tileLayer, property, rectObject.x, rectObject.y + step))
                return true;
        return isCellHasProperty (tileLayer, property, rectObject.x, rectObject.y + rectObject.height);
    }

    private boolean isCollidesTop(TiledMapTileLayer tileLayer, String property, Rectangle rectObject) {
        for(float step = 0; step < rectObject.width; step += tileLayer.getTileWidth())
            if (isCellHasProperty(tileLayer, property, rectObject.x + step, rectObject.y + rectObject.height))
                return true;
        return isCellHasProperty(tileLayer, property, rectObject.x + rectObject.width, rectObject.y + rectObject.height);
    }

    private boolean isCollidesBottom(TiledMapTileLayer tileLayer, String property, Rectangle rectObject) {
        for(float step = 0; step < rectObject.width; step += tileLayer.getTileWidth())
            if (isCellHasProperty(tileLayer, property, rectObject.x + step, rectObject.y))
                return true;
        return isCellHasProperty(tileLayer, property, rectObject.x + rectObject.width, rectObject.y);
    }

    private boolean isCellHasProperty(TiledMapTileLayer tileLayer, String property, float tilePositionX, float tilePositionY) {
        int cellX = (int) (tilePositionX / tileLayer.getTileWidth());
        int cellY = (int) (tilePositionY / tileLayer.getTileHeight());
        boolean correctCell = cellX < tileLayer.getWidth() && cellX >= 0 && cellY < tileLayer.getHeight() && cellY >= 0;
        return correctCell && tileLayer.getCell(cellX, cellY).getTile().getProperties().containsKey(property);
    }
}
