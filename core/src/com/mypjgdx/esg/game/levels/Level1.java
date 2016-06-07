package com.mypjgdx.esg.game.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AbstractEnemy;
import com.mypjgdx.esg.game.objects.Beam;
import com.mypjgdx.esg.game.objects.Bullet;
import com.mypjgdx.esg.game.objects.Enemy;
import com.mypjgdx.esg.game.objects.Enemy2;
import com.mypjgdx.esg.game.objects.Enemy3;
import com.mypjgdx.esg.game.objects.Item;
import com.mypjgdx.esg.game.objects.Player;
import com.mypjgdx.esg.game.objects.SolarCell;
import com.mypjgdx.esg.game.objects.Trap;

public class Level1 extends LevelGenerator {

    @Override
    public List<Item> createItems(TiledMapTileLayer mapLayer, Player player) {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new SolarCell(mapLayer, player));

        return items;
    }

    @Override
    public List<AbstractEnemy> createEnemies(TiledMapTileLayer mapLayer,Player player, List<Bullet> bullets, List<Trap> traps, List<Beam> beams) {
        ArrayList<AbstractEnemy> enemies = new ArrayList<AbstractEnemy>();
        enemies.add(new Enemy(mapLayer, player, bullets, traps, beams));
        enemies.add(new Enemy2(mapLayer, player, bullets, traps, beams));
        enemies.add(new Enemy3(mapLayer, player, bullets, traps, beams));
        return enemies;
    }

    @Override
    public TiledMap createTiledMap() {
        return Assets.instance.map1;
    }

	@Override
	public List<Item> createItems() {
		// TODO Auto-generated method stub
		return null;
	}

}
