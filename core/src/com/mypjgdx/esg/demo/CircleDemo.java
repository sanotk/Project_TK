package com.mypjgdx.esg.demo;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/** คลาสสำหรับการสาธิตเกี่ยวกับ ..
 * การใช้คลาส Circle เบื้องต้น
 * การใช้คลาส ShapeRenderer วาดรูปทรงวงกลม
 * การใช้งาน Collection ที่ชื่อ Array
*
* @author S-Kyousuke
*/

public class CircleDemo extends AbstractDemo {

    private Array<Circle> circles;
    private static int MAX_CIRCLE = 64;
    private static int MAX_CIRCLE_RADIUS = 100;

    @Override
    protected void init() {
        circles =  new Array<Circle>();
        for (int i = 0; i<MAX_CIRCLE; i++) {
            circles.add(new Circle(
                            MathUtils.random(SCENE_WIDTH),
                            MathUtils.random(SCENE_HEIGHT),
                            MathUtils.random(MAX_CIRCLE_RADIUS)));
        }
    }

    @Override
    protected void events() {}

    @Override
    protected void logic() {
        for (Circle c: circles) {
            c.set(++c.x % SCENE_WIDTH,
                    ++c.y % SCENE_HEIGHT,
                    ++c.radius % MAX_CIRCLE_RADIUS);
        }
    }

    @Override
    protected void draw() {
        shapeRenderer.begin(ShapeType.Line);
        for (Circle c: circles) {
            shapeRenderer.circle(c.x, c.y, c.radius);
        }
        shapeRenderer.end();
    }

}
