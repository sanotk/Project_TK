package com.mypjgdx.esg.ashleytest.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.components.PhysicsComponent;
import com.mypjgdx.esg.ashleytest.components.TransformComponent;

/**
 *
 * Created by Bill on 7/8/2560.
 */
public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(Family.all(TransformComponent.class, PhysicsComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mappers.transform.get(entity);
        PhysicsComponent physics = Mappers.physics.get(entity);

        if (physics.velocity.x > 0) {
            physics.velocity.x = Math.max(physics.velocity.x - physics.friction.x * deltaTime, 0);
        } else if (physics.velocity.x < 0) {
            physics.velocity.x = Math.min(physics.velocity.x + physics.friction.x * deltaTime, 0);
        }
        if (physics.velocity.y > 0) {
            physics.velocity.y = Math.max(physics.velocity.y - physics.friction.y * deltaTime, 0);
        } else if (physics.velocity.y < 0) {
            physics.velocity.y = Math.min(physics.velocity.y + physics.friction.y * deltaTime, 0);
        }

        physics.velocity.x += physics.acceleration.x * deltaTime;
        physics.velocity.y += physics.acceleration.y * deltaTime;

        float oldPositionX = transform.position.x;
        float oldPositionY = transform.position.y;

        transform.position.x += physics.velocity.x * deltaTime;
        physics.bounds.x = transform.position.x;
        if (physics.collisionCheck.isCollidesLeft() || physics.collisionCheck.isCollidesRight()) {
            physics.collisionResponse.responseCollisionX(entity, oldPositionX);
        }

        transform.position.y += physics.velocity.y * deltaTime;
        physics.bounds.y = transform.position.y;
        if (physics.collisionCheck.isCollidesTop() || physics.collisionCheck.isCollidesBottom()) {
            physics.collisionResponse.responseCollisionY(entity, oldPositionY);
        }
    }
}