package com.mypjgdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {

    // ตำแหน่ง ขนาด จุดกำเนิด ระดับการขยาย องศาการหมุน
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    // ความเร็ว แรงเสียดทาน ความเร่ง กรอบวัตถุ
    public Vector2 velocity;
    public Vector2 friction;
    public Vector2 acceleration;
    public Rectangle bounds;

    public AbstractGameObject () {
        position = new Vector2(); //ตำแหน่ง
        dimension = new Vector2(1, 1); //
        origin = new Vector2(); //
        scale = new Vector2(1, 1); //
        rotation = 0; //

        velocity = new Vector2(); //
        friction = new Vector2(); //
        acceleration = new Vector2(); //
        bounds = new Rectangle(); //
    }

    public void update (float deltaTime) {
        updateMotionX(deltaTime);
        updateMotionY(deltaTime);
        // อัพเดทตำแหน่ง
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }

    public abstract void render (SpriteBatch batch);

    protected void updateMotionX (float deltaTime) {
        if (velocity.x != 0) {
            // คิดแรงเสียดทานแกน X
            if (velocity.x > 0) {
                // ป้องกันไม่ให้แรงเสียดทาน ทำให้ความเร็วกล้บทิศ
                velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
            }
            else
            {
                // ป้องกันไม่ให้แรงเสียดทาน ทำให้ความเร็วกล้บทิศ
                velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
            }
        }
        // คิดความเร่งแกน X
        velocity.x += acceleration.x * deltaTime;
    }

    protected void updateMotionY (float deltaTime) {
        if (velocity.y != 0) {
            // คิดแรงเสียดทานแกน Y
            if (velocity.y > 0) {
                // ป้องกันไม่ให้แรงเสียดทาน ทำให้ความเร็วกล้บทิศ
                velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
            }
            else
            {
                // ป้องกันไม่ให้แรงเสียดทาน ทำให้ความเร็วกล้บทิศ
                velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
            }
        }
        // คิดความเร่งแกน Y
        velocity.y += acceleration.y * deltaTime;
    }
}
