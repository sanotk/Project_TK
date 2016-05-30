package com.mypjgdx.esg.game.levels;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mypjgdx.esg.game.objects.AbstractEnemy;
import com.mypjgdx.esg.game.objects.Item;

public abstract class LevelGenerator {
    public abstract List<Item> createItems();
    public abstract List<AbstractEnemy> createEnemies();
    public abstract TiledMap createTiledMap();

}
