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
import com.mypjgdx.esg.game.objects.Beam;
import com.mypjgdx.esg.game.objects.Bullet;
import com.mypjgdx.esg.game.objects.Enemy;
import com.mypjgdx.esg.game.objects.EnergyTube;
import com.mypjgdx.esg.game.objects.Item;
import com.mypjgdx.esg.game.objects.Player;
import com.mypjgdx.esg.game.objects.Trap;

public class Level{

    public TiledMap map;
    public Player player;
    public List<Item> items;
    public List<Enemy> enemies;
    public EnergyTube energyTube;

    public List<Bullet> bullets;
    public List<Trap> traps;
    public List<Beam> beams;

    public Level (LevelGenerator levelGenerator) {
        bullets = new ArrayList<Bullet>();
        traps = new ArrayList<Trap>();
        beams = new ArrayList<Beam>();

        energyTube = new EnergyTube(100);  // พลังงานเริ่มต้นมีค่า 100 วินาที
        energyTube.startDrainEnergy();

        init(levelGenerator);
    }

    public void init(LevelGenerator levelGenerator) {
        bullets.clear();
        traps.clear();
        beams.clear();

        map =  levelGenerator.createTiledMap();
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = levelGenerator.createPlayer(mapLayer);
        items = levelGenerator.createItems(mapLayer, player);
        enemies = levelGenerator.createEnemies(mapLayer, player, bullets, traps, beams);

        energyTube.init(mapLayer, player, items);
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map);
        tiledRenderer.render();

        batch.begin();
        //energyTube.render(batch);    <<<   ถ้าได้ atlas ของ EnergyTube แล้วค่อยคอมเม้นออก
        for (Bullet s: bullets) s.render(batch);
        for (Beam b: beams) b.render(batch);
        for (Trap t: traps) t.render(batch);
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
        Iterator<Bullet>it = bullets.iterator();
        Iterator<Beam>bit = beams.iterator();
        Iterator<Trap>tit = traps.iterator();
        Iterator<Enemy>eit = enemies.iterator();
        while(it.hasNext()){
        	Bullet s = it.next();
        	if (s.isDespawned()) it.remove();
        }
        while(bit.hasNext()){
        	Beam b = bit.next();
        	if (b.isDespawned()) bit.remove();
        }

        while(tit.hasNext()){
        	Trap t = tit.next();
        	if (t.isDespawned()) tit.remove();
        }

        while(eit.hasNext()){
            Enemy e = eit.next();
        	if (!e.isAlive()) eit.remove();
        }
        player.update(deltaTime);
        for(Item i: items) i.update(deltaTime);
        for(Enemy e: enemies) e.update(deltaTime);
        for(Bullet s: bullets) s.update(deltaTime);
        for(Beam b: beams) b.update(deltaTime);
        for(Trap t: traps) t.update(deltaTime);

        energyTube.update(deltaTime);
    }

}
