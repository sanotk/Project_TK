package com.mypjgdx.esg.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.game.objects.Enemy;
import com.mypjgdx.esg.game.objects.Player;
import com.mypjgdx.esg.game.objects.Sword;

public class Level {

    public final Player player; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    List<Enemy> enemys = new ArrayList<Enemy>();
    public List<Sword> swords = new ArrayList<Sword>();
    public int count=0;
    public final Map map;   // แผนที่ในเกม
    public final int MAX_ENEMY = 5;

    public Level (Map map) {
        this.map = map;
        player = new Player((TiledMapTileLayer) map.getTiledMap().getLayers().get(0));
        for(int i = 0;i<MAX_ENEMY;i++){
        	enemys.add(new Enemy(map.getMapLayer(),player ,swords));
        }
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map.getTiledMap());
        tiledRenderer.render();

        batch.begin();
        player.render(batch);
        for(Enemy e: enemys) e.render(batch);
        for(Sword s: swords) s.render(batch);

        batch.end();

        shapeRenderer.begin(ShapeType.Line);
        //shapeRenderer.rect(player.bounds.x, player.bounds.y, player.bounds.width, player.bounds.height);
        shapeRenderer.end();
    }

    public void update(float deltaTime) {
        Iterator<Sword>it = swords.iterator();
        Iterator<Enemy>eit = enemys.iterator();
        while(it.hasNext()){
        	Sword s = it.next();
        	if (s.isDespawned())it.remove();
        }
        while(eit.hasNext()){
        	Enemy e = eit.next();
        	if (e.isDespawned())eit.remove();
        }
        player.update(deltaTime);
        for(Enemy e: enemys) e.update(deltaTime);
        for(Sword s: swords) s.update(deltaTime);
    }

}
