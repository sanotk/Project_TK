package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Pepo;
import com.mypjgdx.esg.game.objects.characters.PepoKnight;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.*;
import com.mypjgdx.esg.game.objects.weapons.Sword;

import java.util.ArrayList;
import java.util.List;

public class Level3Generator extends LevelGenerator {

    @Override
    public Player createPlayer(TiledMapTileLayer mapLayer) {
        return new Player(mapLayer, 100, 100);
    }
    @Override
    public List<Item> createItems(TiledMapTileLayer mapLayer, Player player, Level level) {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Switch(mapLayer, player));
        items.add(new Television(mapLayer, player));
        items.add(new Waterfilter(mapLayer, player));
        items.add(new Waterheater(mapLayer, player));
        items.add(new Microwave(mapLayer, player));
        items.add(new Waterpump(mapLayer, player));
        items.add(new Airconditioner(mapLayer, player));
        items.add(new Computer(mapLayer, player));
        items.add(new Electroacoustics(mapLayer, player));
        items.add(new Fan(mapLayer, player));
        items.add(new Refrigerator(mapLayer, player));
        return items;
    }

    @Override
    public List<Sword> createSwords(TiledMapTileLayer mapLayer, Player player, Level level) {
        return null;
    }

    @Override
    public List<Enemy> createEnemies(TiledMapTileLayer mapLayer,Player player) {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));
        return enemies;
    }

    @Override
    public TiledMap createTiledMap() {
        return Assets.instance.map3;
    }

}
