package com.mypjgdx.esg.ashleytest.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mypjgdx.esg.ashleytest.components.PhysicsComponent;
import com.mypjgdx.esg.ashleytest.components.TransformComponent;

/**
 * ระบบเช็กชน
 * Created by Bill on 8/8/2560.
 */
public class CollisionDetectionSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, PhysicsComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

    }
}
