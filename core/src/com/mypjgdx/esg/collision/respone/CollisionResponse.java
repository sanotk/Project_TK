package com.mypjgdx.esg.collision.respone;


import com.badlogic.ashley.core.Entity;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public interface CollisionResponse {

    void responseCollisionX(Entity entity, float oldPositionX);

    void responseCollisionY(Entity entity, float oldPositionY);

}
