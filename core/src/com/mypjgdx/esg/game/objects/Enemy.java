package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;

public class Enemy extends AbstractEnemy {

    public Enemy(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        super(Assets.instance.enemy1Altas);

        SCALE = 0.1f;
        INITIAL_HEALTH =  5;
        INITIAL_MOVING_SPEED = 60;

        this.player = player;
        this.bullets = bullets;
        this.beams = beams;
        this.traps = traps;
        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);
        init(mapLayer);
    }

}
