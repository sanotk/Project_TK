package com.mypjgdx.esg.utils;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class PolygonUtils {

    private PolygonUtils() {}

    private static final Polygon tmpRectPolygon = new Polygon(new float[8]);

    public static void flipPolygonX(Polygon polygon) {
        for (int i = 0; i < polygon.getVertices().length; i += 2) {
            polygon.getVertices()[i] = polygon.getOriginX() * 2 - polygon.getVertices()[i];
        }
    }
    public static void flipPolygonY(Polygon polygon) {
        for (int i = 1; i < polygon.getVertices().length; i += 2) {
            polygon.getVertices()[i] =  polygon.getOriginY() * 2 - polygon.getVertices()[i];
        }
    }

    public static boolean overlapConvexPolygons(Polygon polygon, Rectangle rectangle) {
        float[] vertices = tmpRectPolygon.getVertices();
        tmpRectPolygon.setPosition(rectangle.x, rectangle.y);

        vertices[0] = 0;
        vertices[1] = 0;

        vertices[2] = rectangle.width;
        vertices[3] = 0;

        vertices[4] = rectangle.width;
        vertices[5] = rectangle.height;

        vertices[6] = 0;
        vertices[7] = rectangle.height;

        return Intersector.overlapConvexPolygons(tmpRectPolygon, polygon);
    }
}
