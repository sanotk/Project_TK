package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.Sword;

import java.util.List;

public abstract class LevelGenerator {
    public abstract Player createPlayer(TiledMapTileLayer mapLayer);
    public abstract List<Enemy> createEnemies(TiledMapTileLayer mapLayer,Player player);
    public abstract TiledMap createTiledMap();
	public abstract List<Item> createItems(TiledMapTileLayer mapLayer, Player player, Level level);
    public abstract List<Sword> createSwords(TiledMapTileLayer mapLayer, Player player, Level level);

}
