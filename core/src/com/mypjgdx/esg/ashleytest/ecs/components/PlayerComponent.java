package com.mypjgdx.esg.ashleytest.ecs.components;

import com.badlogic.ashley.core.Component;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;

/**
 * Created by Bill on 8/8/2560.
 */
public class PlayerComponent implements Component {

    public int health;
    public int trapCount;
    public int bulletCount;
    public int beamCount;

    public Player.PlayerState state;

    public Item item;
}
