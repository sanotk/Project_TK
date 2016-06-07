package com.mypjgdx.esg.game.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.Beam;
import com.mypjgdx.esg.game.objects.Bullet;
import com.mypjgdx.esg.game.objects.Enemy;
import com.mypjgdx.esg.game.objects.Item;
import com.mypjgdx.esg.game.objects.Pepo;
import com.mypjgdx.esg.game.objects.PepoDevil;
import com.mypjgdx.esg.game.objects.PepoKnight;
import com.mypjgdx.esg.game.objects.Player;
import com.mypjgdx.esg.game.objects.SolarCell;
import com.mypjgdx.esg.game.objects.Trap;

public class Level1 extends LevelGenerator {

    @Override
    public Player createPlayer(TiledMapTileLayer mapLayer) {
        return new Player(mapLayer, 100, 100);
    }
    @Override
    public List<Item> createItems(TiledMapTileLayer mapLayer, Player player) {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new SolarCell(mapLayer, player));
        return items;
    }

    @Override
    public List<Enemy> createEnemies(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Pepo(mapLayer, player, bullets, traps, beams));
        enemies.add(new PepoKnight(mapLayer, player, bullets, traps, beams));
        enemies.add(new PepoDevil(mapLayer, player, bullets, traps, beams));
        return enemies;
    }

    @Override
    public TiledMap createTiledMap() {
        return Assets.instance.map1;
    }

}
