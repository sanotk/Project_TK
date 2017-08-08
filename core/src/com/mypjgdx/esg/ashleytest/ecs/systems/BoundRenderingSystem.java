package com.mypjgdx.esg.ashleytest.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.ecs.components.PhysicsComponent;

/**
 *
 * Created by Bill on 8/8/2560.
 */

public class BoundRenderingSystem extends IteratingSystem {

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    public BoundRenderingSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        super(Family.all(PhysicsComponent.class).get());
        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsComponent physics = Mappers.physics.get(entity);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(physics.bounds.x, physics.bounds.y,
                physics.bounds.width, physics.bounds.height);
        shapeRenderer.end();
    }
}
