package com.mypjgdx.esg.game.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.etcs.Etc;
import com.mypjgdx.esg.game.objects.etcs.Link;
import com.mypjgdx.esg.game.objects.items.Item;

public abstract class LevelGenerator {
    public abstract Player createPlayer(TiledMapTileLayer mapLayer);
    public abstract List<Enemy> createEnemies(TiledMapTileLayer mapLayer,Player player);
    public abstract TiledMap createTiledMap();
	public abstract List<Item> createItems(TiledMapTileLayer mapLayer, Player player);
    public abstract ArrayList<Link> createEtcs(TiledMapTileLayer mapLayer, Player player);

}
