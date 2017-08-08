package com.mypjgdx.esg.ashleytest.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.collision.respone.CollisionResponse;
import com.mypjgdx.esg.collision.respone.NullCollisionResponse;
import com.mypjgdx.esg.collision.check.CollisionCheck;
import com.mypjgdx.esg.collision.check.NullCollsionCheck;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class PhysicsComponent implements Component {

    public Vector2 velocity = new Vector2();
    public Vector2 friction = new Vector2();
    public Vector2 acceleration = new Vector2();

    public Rectangle bounds = new Rectangle();
    public CollisionCheck collisionCheck = new NullCollsionCheck();
    public CollisionResponse collisionResponse = new NullCollisionResponse();
}
