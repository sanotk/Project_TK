package com.mypjgdx.esg.game;

import java.util.ArrayList;
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
    List<Enemy> enemy = new ArrayList<Enemy>();
    public List<Sword> swords = new ArrayList<Sword>();

    public final Map map;   // แผนที่ในเกม

    public Level (Map map) {
        this.map = map;
        player = new Player((TiledMapTileLayer) map.getTiledMap().getLayers().get(0));

        enemy.add(new Enemy((TiledMapTileLayer) map.getTiledMap().getLayers().get(0),player));
        enemy.add(new Enemy((TiledMapTileLayer) map.getTiledMap().getLayers().get(0),player));
        enemy.add(new Enemy((TiledMapTileLayer) map.getTiledMap().getLayers().get(0),player));
        enemy.add(new Enemy((TiledMapTileLayer) map.getTiledMap().getLayers().get(0),player));
        enemy.add(new Enemy((TiledMapTileLayer) map.getTiledMap().getLayers().get(0),player));
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map.getTiledMap());
        tiledRenderer.render();

        batch.begin();
        player.render(batch);
        for(Sword s: swords) s.render(batch);
        enemy.get(0).render(batch);
        batch.end();

        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.rect(player.bounds.x, player.bounds.y, player.bounds.width, player.bounds.height);
        shapeRenderer.end();
    }

    public void update(float deltaTime) {
        player.update(deltaTime);
        enemy.get(0).update(deltaTime);
        for(Sword s: swords) s.update(deltaTime);
    }

}
