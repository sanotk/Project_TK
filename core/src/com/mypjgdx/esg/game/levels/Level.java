package com.mypjgdx.esg.game.levels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.EnergyTube;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.Weapon;

public class Level{

    public TiledMap map;
    public Player player;
    public List<Item> items;
    public List<Enemy> enemies;
    public List<Weapon> weapons;
    public TiledMapTileLayer mapLayer;

    public EnergyTube energyTube;

    public Level (LevelGenerator levelGenerator) {
        weapons = new ArrayList<Weapon>();

        energyTube = new EnergyTube(100);  // พลังงานเริ่มต้นมีค่า 100 วินาที
        energyTube.startDrainEnergy();

        init(levelGenerator);
    }

    public void init(LevelGenerator levelGenerator) {
        weapons.clear();

        map =  levelGenerator.createTiledMap();
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = levelGenerator.createPlayer(mapLayer);
        items = levelGenerator.createItems(mapLayer, player);
        enemies = levelGenerator.createEnemies(mapLayer, player);

        energyTube.init(mapLayer, player);
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map);
        tiledRenderer.render();

        batch.begin();
        //energyTube.render(batch);    <<<   ถ้าได้ atlas ของ EnergyTube แล้วค่อยคอมเม้นออก
        for (Weapon w: weapons) w.render(batch);
        player.render(batch);
        for (Item i: items) i.render(batch);
        for (Enemy e: enemies) e.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        player.showHp (shapeRenderer);
        for (Enemy e:enemies) e.showHp(shapeRenderer);
        shapeRenderer.end();
    }

    public void update(float deltaTime) {
        Iterator<Weapon> weaponIterator = weapons.iterator();
        Iterator<Enemy>enemyIterator = enemies.iterator();
        while(weaponIterator.hasNext()){
            Weapon w = weaponIterator.next();
        	if (w.isDestroyed()) weaponIterator.remove();
        }
        while(enemyIterator.hasNext()){
            Enemy e = enemyIterator.next();
        	if (!e.isAlive()) enemyIterator.remove();
        }
        player.update(deltaTime ,weapons);
        for(Item i: items) i.update(deltaTime);
        for(Enemy e: enemies) e.update(deltaTime, weapons);
        for(Weapon w: weapons) w.update(deltaTime);

        energyTube.update(deltaTime);
    }

}
