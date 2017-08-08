package com.mypjgdx.esg.ashleytest.ecs.components;

import com.badlogic.ashley.core.Component;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class StatusComponent implements Component {

    public float health;

    public boolean dead;
    public boolean invulnerable;
    public boolean knockback;
    public boolean stun;

    public long invulnerableStopTime;
    public long stunStopTime;
}
