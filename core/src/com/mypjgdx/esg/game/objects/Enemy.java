package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;

public class Enemy extends AbstractEnemy {

	public static final int MAXHEALTH = 5;
	public static final int MAXSPEED = 60;
	public static final float SCALE = 0.35f;

    public Enemy(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        super(Assets.instance.enemy1Altas);

        this.player = player;
        this.bullets = bullets;
        this.beams = beams;
        this.traps = traps;
        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);
        init(mapLayer, MAXHEALTH, MAXSPEED, SCALE);
    }

}
