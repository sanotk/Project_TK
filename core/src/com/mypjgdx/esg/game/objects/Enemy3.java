package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;

public class Enemy3 extends AbstractEnemy{

    public Enemy3(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        super(Assets.instance.enemy3Altas);

        SCALE = 0.15f;
        INITIAL_HEALTH =  15;
        INITIAL_MOVING_SPEED = 80;

        this.player = player;
        this.bullets = bullets;
        this.beams = beams;
        this.traps = traps;
        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);
        init(mapLayer);
    }

}
