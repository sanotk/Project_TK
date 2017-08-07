package com.mypjgdx.esg.ashleytest.components;

import com.badlogic.ashley.core.Component;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.utils.Direction;

/**
 * Created by Bill on 8/8/2560.
 */
public class PlayerComponent implements Component {

    public int health;
    public int trapCount;
    public int bulletCount;
    public int beamCount;

    public float movingSpeed;
    public Direction viewDirection;

    public Player.PlayerAnimation playerAnimation;
    public Player.PlayerState state;

    public Item item;
}
