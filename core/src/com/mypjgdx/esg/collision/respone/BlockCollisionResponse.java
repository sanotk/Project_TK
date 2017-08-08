package com.mypjgdx.esg.collision.respone;

import com.badlogic.ashley.core.Entity;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.ecs.components.PhysicsComponent;
import com.mypjgdx.esg.ashleytest.ecs.components.TransformComponent;



/**
 *
 * Created by Bill on 8/8/2560.
 */
public class BlockCollisionResponse implements CollisionResponse {

    @Override
    public void responseCollisionX(Entity entity, float oldPositionX) {
        TransformComponent transform = Mappers.transform.get(entity);
        PhysicsComponent physics = Mappers.physics.get(entity);
        transform.position.x = oldPositionX;
        physics.bounds.x = oldPositionX;
    }

    @Override
    public void responseCollisionY(Entity entity, float oldPositionY) {
        TransformComponent transform = Mappers.transform.get(entity);
        PhysicsComponent physics = Mappers.physics.get(entity);
        transform.position.y = oldPositionY;
        physics.bounds.y = oldPositionY;
    }
}
