package com.mypjgdx.esg.ashleytest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.ashleytest.components.SpriteComponent;
import com.mypjgdx.esg.ashleytest.components.TransformComponent;

/**
 *
 * Created by Bill on 8/8/2560.
 */

public class RenderingSystem extends IteratingSystem {

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<SpriteComponent> spriteMapper = ComponentMapper.getFor(SpriteComponent.class);

    private Array<Entity> renderQueue = new Array<Entity>();

    public RenderingSystem(SpriteBatch batch, OrthographicCamera camera) {
        super(Family.all(SpriteComponent.class, TransformComponent.class).get());
        this.batch = batch;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Entity entity : renderQueue) {
            TransformComponent transform = transformMapper.get(entity);
            SpriteComponent sprite = spriteMapper.get(entity);
            batch.draw(sprite.region,
                    transform.position.x, transform.position.y,
                    transform.origin.x, transform.origin.y,
                    transform.dimension.x, transform.dimension.y,
                    1.0f, 1.0f, transform.rotation);
        }
        batch.end();
        renderQueue.clear();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
