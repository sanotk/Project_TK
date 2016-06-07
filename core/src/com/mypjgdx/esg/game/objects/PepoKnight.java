package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;

public class PepoKnight extends AbstractEnemy {

	public static final int MAXHEALTH = 8;
	public static final int MAXSPEED = 50;
	public static final float SCALE = 0.35f;

    public PepoKnight(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        super(Assets.instance.pepoKnightAltas);

        this.player = player;
        this.bullets = bullets;
        this.beams = beams;
        this.traps = traps;
        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);
        init(mapLayer, MAXHEALTH, MAXSPEED, SCALE);
    }
}
