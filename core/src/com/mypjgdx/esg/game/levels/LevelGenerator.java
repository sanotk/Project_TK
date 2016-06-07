package com.mypjgdx.esg.game.levels;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.objects.Beam;
import com.mypjgdx.esg.game.objects.Bullet;
import com.mypjgdx.esg.game.objects.Enemy;
import com.mypjgdx.esg.game.objects.Item;
import com.mypjgdx.esg.game.objects.Player;
import com.mypjgdx.esg.game.objects.Trap;

public abstract class LevelGenerator {
    public abstract Player createPlayer(TiledMapTileLayer mapLayer);
    public abstract List<Enemy> createEnemies(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams);
    public abstract TiledMap createTiledMap();
	public abstract List<Item> createItems(TiledMapTileLayer mapLayer, Player player);

}
