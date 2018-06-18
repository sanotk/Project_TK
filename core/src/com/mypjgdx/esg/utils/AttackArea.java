package com.mypjgdx.esg.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class AttackArea {

    public static final AttackArea nullArea = new AttackArea(new float[] {0, 0, 0, 0, 0, 0}, 0, 0, 0);

    private Polygon polygon;

    private boolean flipX;
    private boolean flipY;

    public AttackArea(float[] area, float scale, float width, float height) {
        Polygon polygon = new Polygon(area);
        for (int i = 0; i < area.length; i++) {
            area[i] *= scale;
        }
        polygon.setOrigin(width / 2f, height / 2f);
        this.polygon = polygon;
    }

    public void setFlip(boolean flipX, boolean flipY) {
        if (this.flipX != flipX) {
            PolygonUtils.flipPolygonX(polygon);
            this.flipX = flipX;
        }
        if (this.flipY != flipY) {
            PolygonUtils.flipPolygonY(polygon);
            this.flipY = flipY;
        }
    }

    public boolean overlaps(Rectangle bounds) {
        if (this == nullArea) return false;
        return PolygonUtils.overlapConvexPolygons(polygon, bounds);
    }

    public void debug(ShapeRenderer renderer) {
        renderer.setColor(Color.PURPLE);
        renderer.polygon(polygon.getTransformedVertices());
    }

    public void setPosition(float x, float y) {
        polygon.setPosition(x, y);
    }

    public void setRotation(float degrees) {
        polygon.setRotation(degrees);
    }
}
