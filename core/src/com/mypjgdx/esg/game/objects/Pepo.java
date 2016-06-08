package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class Pepo extends Enemy {

	public static final int MAX_HEALTH = 5;
	public static final int MAX_SPEED = 60;
	public static final float SCALE = 0.35f;

    public Pepo(TiledMapTileLayer mapLayer, Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        super(Assets.instance.pepoAltas, SCALE, SCALE, mapLayer);

        this.player = player;
        this.bullets = bullets;
        this.beams = beams;
        this.traps = traps;
        this.movingSpeed = MAX_SPEED;
        this.maxHealth = MAX_HEALTH;

        init(mapLayer);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
