package com.mypjgdx.esg.ashleytest.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.ecs.components.TiledMapComponent;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class TiledMapRenderingSystem extends IteratingSystem {

    private OrthogonalTiledMapRenderer tiledRenderer;
    private OrthographicCamera camera;

    public TiledMapRenderingSystem(OrthogonalTiledMapRenderer tiledRenderer, OrthographicCamera camera) {
        super(Family.all(TiledMapComponent.class).get());
        this.tiledRenderer = tiledRenderer;
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TiledMapComponent tiledMap = Mappers.tiledMap.get(entity);

        tiledRenderer.setView(camera);
        tiledRenderer.setMap(tiledMap.map);
        tiledRenderer.render();
    }
}
