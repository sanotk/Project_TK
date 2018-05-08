package com.mypjgdx.esg.utils;

import com.mypjgdx.esg.game.objects.AbstractGameObject;

public class Distance {

    private Distance() {
    }

    public static double euclidean(AbstractGameObject objectA, AbstractGameObject objectB) {
        float dx = objectA.getPositionX() - objectB.getPositionX();
        float dy = objectA.getPositionY() - objectB.getPositionY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double manhattan(AbstractGameObject objectA, AbstractGameObject objectB) {
        float dx = objectA.getPositionX() - objectB.getPositionX();
        float dy = objectA.getPositionY() - objectB.getPositionY();
        return Math.abs(dx) + Math.abs(dy);
    }

}
