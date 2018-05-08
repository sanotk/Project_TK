package com.mypjgdx.esg.game.objects.characters;

import com.mypjgdx.esg.utils.Direction;

public interface Damageable {

    boolean takeDamage(float damage, float knockbackSpeed, float knockbackAngle);
    Direction getViewDirection();

}
