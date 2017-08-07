package com.mypjgdx.esg.ashleytest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.ashleytest.CollisionResponse;
import com.mypjgdx.esg.ashleytest.NullCollisionResponse;
import com.mypjgdx.esg.collision.CollisionCheck;
import com.mypjgdx.esg.collision.NullCollsionCheck;

/**
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
