package com.mypjgdx.esg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.game.objects.Enemy;
import com.mypjgdx.esg.game.objects.Player;

public class Level {

    public final Player player; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    public final Enemy enemy; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    public final Map map;   // แผนที่ในเกม

    public Level (Map map) {
        this.map = map;
        player = new Player((TiledMapTileLayer) map.getTiledMap().getLayers().get(0));
        enemy = new Enemy((TiledMapTileLayer) map.getTiledMap().getLayers().get(0));

        //collisionBoxes = map.getTiledMap().getLayers().get("Collisions").getObjects();
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map.getTiledMap());
        tiledRenderer.render();

        batch.begin();
        player.render(batch);
        enemy.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeType.Line);
        //shapeRenderer.rect(player.bounds.x, player.bounds.y, player.bounds.width, player.bounds.height);
        shapeRenderer.end();
    }

    public void update(float deltaTime) {
        player.update(deltaTime);
        enemy.update(deltaTime);
    }

}
