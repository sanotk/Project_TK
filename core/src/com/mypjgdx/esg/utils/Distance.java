package com.mypjgdx.esg.utils;

import com.mypjgdx.esg.game.objects.AbstractGameObject;

public class Distance {

    public static double line(AbstractGameObject objectA, AbstractGameObject objectB) {
        float xdiff = objectA.getPositionX() - objectB.getPositionX();
        float ydiff = objectA.getPositionY() - objectB.getPositionY();
        return Math.sqrt(xdiff*xdiff + ydiff*ydiff);
    }

    public static double absoluteXY(AbstractGameObject objectA, AbstractGameObject objectB) {
        float xdiff = objectA.getPositionX() - objectB.getPositionX();
        float ydiff = objectA.getPositionY() - objectB.getPositionY();
        return Math.abs(xdiff+ydiff);
    }

}
