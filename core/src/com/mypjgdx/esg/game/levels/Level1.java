package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.*;

import java.util.ArrayList;
import java.util.List;

public class Level1 extends LevelGenerator {

    @Override
    public Player createPlayer(TiledMapTileLayer mapLayer) {
        return new Player(mapLayer, 100, 100);
    }
    @Override
    public List<Item> createItems(TiledMapTileLayer mapLayer, Player player) {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new SolarCell(mapLayer, player));
        items.add(new Inverter(mapLayer, player));
        items.add(new Battery(mapLayer, player));
        items.add(new Charge(mapLayer, player));
        items.add(new Door(mapLayer,player));
        return items;
    }

    @Override
    public List<Enemy> createEnemies(TiledMapTileLayer mapLayer,Player player) {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
//        enemies.add(new Pepo(mapLayer, player));
//        enemies.add(new Pepo(mapLayer, player));
//        enemies.add(new Pepo(mapLayer, player));
//        enemies.add(new PepoKnight(mapLayer, player));
//        enemies.add(new PepoKnight(mapLayer, player));
        return enemies;
    }

    @Override
    public TiledMap createTiledMap() {
        return Assets.instance.map1;
    }

}
