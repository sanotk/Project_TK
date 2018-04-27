package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.*;
import com.mypjgdx.esg.game.objects.items.*;
import com.mypjgdx.esg.game.objects.weapons.Bow;
import com.mypjgdx.esg.game.objects.weapons.NormalBow;
import com.mypjgdx.esg.game.objects.weapons.NormalSword;
import com.mypjgdx.esg.game.objects.weapons.Sword;

import java.util.ArrayList;
import java.util.List;

public class Level4Generator extends LevelGenerator {

    @Override
    public Player createPlayer(TiledMapTileLayer mapLayer) {
        return new Player(mapLayer, 100, 100);
    }
    @Override
    public List<Item> createItems(TiledMapTileLayer mapLayer, Player player, Level level) {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new SolarCell(mapLayer, player));
        items.add(new Inverter(mapLayer, player));
        items.add(new Battery(mapLayer, player));
        items.add(new Charge(mapLayer, player));
        items.add(new Door(mapLayer,player));
        level.hasSolarCell = false;
        return items;
    }

    @Override
    public List<Enemy> createEnemies(TiledMapTileLayer mapLayer,Player player) {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));
        enemies.add(new PepoDevil(mapLayer, player));
        return enemies;
    }

    @Override
    public List<Citizen> createCitizens(TiledMapTileLayer mapLayer, Player player) {
        return null;
    }

    @Override
    public List<Sword> createSwords(TiledMapTileLayer mapLayer, Player player, Level level) {
        ArrayList<Sword> swords = new ArrayList<Sword>();
        swords.add(new NormalSword(mapLayer, player));
        return swords;
    }

    @Override
    public List<Bow> createBows(TiledMapTileLayer mapLayer, Player player, Level level) {
        ArrayList<Bow> bows = new ArrayList<Bow>();
        bows.add(new NormalBow(mapLayer, player));
        return bows;
    }

    @Override
    public TiledMap createTiledMap() {
        return Assets.instance.map4;
    }

}
