package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.collision.CollisionCheck;

public abstract class AbstractGameObject {

    // ตำแหน่ง ขนาด จุดกำเนิด ระดับการขยาย องศาการหมุน
    private Vector2 position;
    protected Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    // ความเร็ว แรงเสียดทาน ความเร่ง กรอบวัตถุ
    public Vector2 velocity;
    public Vector2 friction;
    public Vector2 acceleration;
    public Rectangle bounds;

    protected Vector2 oldPosition;
    protected CollisionCheck collisionCheck;

    public AbstractGameObject () {
        position = new Vector2(); //ตำแหน่ง
        dimension = new Vector2(); //
        origin = new Vector2(); //
        scale = new Vector2(1, 1); //
        rotation = 0; //

        velocity = new Vector2(); //
        friction = new Vector2(); //
        acceleration = new Vector2(); //
        bounds = new Rectangle(); //

        oldPosition = new Vector2();
    }

    public void update (float deltaTime) {
        oldPosition.set(position);

        updateMotionX(deltaTime);
        updateMotionY(deltaTime);

        setPosition(position.x + velocity.x * deltaTime,  position.y);

        if (collisionCheck.isCollidesLeft() || collisionCheck.isCollidesRight()) {
            setPosition(oldPosition.x, position.y);
        }

        setPosition(position.x,  position.y + velocity.y * deltaTime);
        if (collisionCheck.isCollidesTop() || collisionCheck.isCollidesBottom()) {
            setPosition(position.x, oldPosition.y);
        }
    }

    public abstract void render (SpriteBatch batch);

    protected void render(SpriteBatch batch, TextureRegion region) {
        batch.draw(region,
                position.x, position.y, origin.x, origin.y,
                dimension.x, dimension.y,
                1.0f, 1.0f, rotation);
    }

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


    public void setPosition(float x, float y) {
        position.set(x, y);
        bounds.set(position.x, position.y, dimension.x, dimension.y);
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }

    protected void setDimension(float width, float height) {
        dimension.x =  width * scale.x;
        dimension.y =  height * scale.y;

        // อัพเดทจุด origin อยู่ตรงกึ่งกลางภาพ
        origin.set(dimension.x / 2, dimension.y / 2);
    }


}
