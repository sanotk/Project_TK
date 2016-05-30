package com.mypjgdx.esg.game.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AbstractEnemy;
import com.mypjgdx.esg.game.objects.Item;

public class Level1 extends LevelGenerator {

    @Override
    public List<Item> createItems() {
        return new ArrayList<Item>();
    }

    @Override
    public List<AbstractEnemy> createEnemies() {
        return new ArrayList<AbstractEnemy>();
    }

    @Override
    public TiledMap createTiledMap() {
        return Assets.instance.map1;
    }

}
