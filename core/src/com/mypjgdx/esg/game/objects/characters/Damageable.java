package com.mypjgdx.esg.game.objects.characters;

import com.mypjgdx.esg.utils.Direction;

public interface Damageable {

    public boolean takeDamage (float damage, float knockbackSpeed, float knockbackAngle);
    public Direction getViewDirection();

}
