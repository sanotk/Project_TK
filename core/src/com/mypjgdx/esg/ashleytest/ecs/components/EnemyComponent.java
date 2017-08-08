package com.mypjgdx.esg.ashleytest.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.mypjgdx.esg.utils.Pathfinding;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class EnemyComponent implements Component{
    public float findingRange;
    public Pathfinding pathFinding;

    public Entity player;
}
