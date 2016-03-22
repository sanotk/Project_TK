package com.mypjgdx.esg.utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Triangle {

    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;

    public Triangle(float x1, float y1,  float x2, float y2, float x3, float y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.triangle(x1, y1, x2, y2, x3, y3);
    }

    public boolean overlaps(Triangle t) {
        // TODO
        // NOT FINISHED
        return false;
    }

    public boolean overlaps(Rectangle r) {
        // TODO
        // NOT FINISHED
        return false;
    }

    public boolean overlaps(Circle c) {
        // TODO
        // NOT FINISHED
        return false;
    }
}
