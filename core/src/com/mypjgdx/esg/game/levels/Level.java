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
import com.mypjgdx.esg.game.objects.AbstractEnemy;
import com.mypjgdx.esg.game.objects.Beam;
import com.mypjgdx.esg.game.objects.Bullet;
import com.mypjgdx.esg.game.objects.Item;
import com.mypjgdx.esg.game.objects.Player;
import com.mypjgdx.esg.game.objects.Trap;

public class Level{

    public Player player;
    public List<Item> items;
    public List<AbstractEnemy> enemies;
    public List<Bullet> bullets = new ArrayList<Bullet>();
    public List<Trap> traps = new ArrayList<Trap>();
    public List<Beam> beams = new ArrayList<Beam>();
    public TiledMap map;

    public Level (LevelGenerator levelGenerator) {
        map =  levelGenerator.createTiledMap();
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
        player = new Player(mapLayer) ;

        items = levelGenerator.createItems();
        enemies = levelGenerator.createEnemies(mapLayer, player, bullets, traps, beams);
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map);
        tiledRenderer.render();

        batch.begin();
        for (Bullet s: bullets) s.render(batch);
        for (Beam b: beams) b.render(batch);
        for (Trap t: traps) t.render(batch);
        player.render(batch);
        for (AbstractEnemy e: enemies) e.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        player.showHp (shapeRenderer);
        for (AbstractEnemy e:enemies) e.showHp(shapeRenderer);
        shapeRenderer.end();
    }

    public void update(float deltaTime) {
        Iterator<Bullet>it = bullets.iterator();
        Iterator<Beam>bit = beams.iterator();
        Iterator<Trap>tit = traps.iterator();
        Iterator<AbstractEnemy>eit = enemies.iterator();
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
            AbstractEnemy e = eit.next();
        	if (!e.isAlive()) eit.remove();
        }
        player.update(deltaTime);

        for(AbstractEnemy e: enemies) e.update(deltaTime);
        for(Bullet s: bullets) s.update(deltaTime);
        for(Beam b: beams) b.update(deltaTime);
        for(Trap t: traps) t.update(deltaTime);
    }


}
