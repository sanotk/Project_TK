package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.etcs.Etc;
import com.mypjgdx.esg.game.objects.items.EnergyTube;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.Weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Level{

    public TiledMap map;
    public Player player;
    public List<Item> items;
    public List<Enemy> enemies;
    public List<Weapon> weapons;
    public List<Etc> etcs;
    public TiledMapTileLayer mapLayer;

    public EnergyTube energyTube;

    public boolean hasSolarCell;

    public Level (LevelGenerator levelGenerator) {
        weapons = new ArrayList<Weapon>();
        etcs = new ArrayList<Etc>();

        energyTube = new EnergyTube(0);  // พลังงานเริ่มต้นมีค่า 100 วินาที
        //energyTube.startDrainEnergy();

        init(levelGenerator);
    }

    public void init(LevelGenerator levelGenerator) {
        weapons.clear();
        etcs.clear();

        map =  levelGenerator.createTiledMap();
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = levelGenerator.createPlayer(mapLayer);
        items = levelGenerator.createItems(mapLayer, player, this);
        enemies = levelGenerator.createEnemies(mapLayer, player);

        energyTube.init(mapLayer, player);
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map);
        tiledRenderer.render();

        batch.begin();

        for (Weapon w: weapons) w.render(batch);
        for (Etc e: etcs) e.render(batch);
        for (Item i: items) i.render(batch);
        player.render(batch);
        for (Enemy e: enemies) e.render(batch);

        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        player.showHp (shapeRenderer);
        for (Enemy e:enemies) e.showHp(shapeRenderer);
        shapeRenderer.end();
    }

    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {}

    public void createFbo(SpriteBatch batch, FrameBuffer lightFbo)  {}

    public void update(float deltaTime) {

        Iterator<Weapon> weaponIterator = weapons.iterator();
        Iterator<Etc> etcIterator = etcs.iterator();
        Iterator<Enemy>enemyIterator = enemies.iterator();

        while(weaponIterator.hasNext()){
            Weapon w = weaponIterator.next();
        	if (w.isDestroyed()) weaponIterator.remove();
        }

        while(etcIterator.hasNext()){
            Etc e = etcIterator.next();
            if (e.isDestroyed()) etcIterator.remove();
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
