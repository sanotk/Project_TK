package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;

public class Enemy2 extends AbstractEnemy {

    public Enemy2(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        super(Assets.instance.enemy2Altas);

        SCALE = 0.1f;
        INITIAL_HEALTH =  8;
        INITIAL_MOVING_SPEED = 50;

        this.player = player;
        this.bullets = bullets;
        this.beams = beams;
        this.traps = traps;
        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);
        init(mapLayer);
    }
}
