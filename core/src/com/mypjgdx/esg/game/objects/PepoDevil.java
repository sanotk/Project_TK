package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class PepoDevil extends Enemy{

	public static final int MAX_HEALTH  = 15;
	public static final int MAX_SPEED = 80;
	public static final float SCALE = 0.5f;

    public PepoDevil(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        super(Assets.instance.enemy3Altas, SCALE, SCALE, mapLayer);

        this.player = player;
        this.bullets = bullets;
        this.beams = beams;
        this.traps = traps;
        this.movingSpeed = MAX_SPEED;
        this.maxHealth = MAX_HEALTH;

        init(mapLayer);
    }

}
