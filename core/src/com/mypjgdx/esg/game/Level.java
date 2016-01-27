package com.mypjgdx.esg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mypjgdx.esg.game.objects.Player;

public class Level {

    public final Player player; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    public final Map map;   // แผนที่ในเกม

    private MapObjects collisionBoxes; // กล่องกำแพง

    public Level (Map map) {
        this.map = map;
        player = new Player((TiledMapTileLayer) map.getTiledMap().getLayers().get(0));

        collisionBoxes = map.getTiledMap().getLayers().get("Collisions").getObjects();
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map.getTiledMap());
        tiledRenderer.render();

        batch.begin();
        player.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeType.Line);
        for (MapObject object : collisionBoxes) {
            RectangleMapObject collisionBox = (RectangleMapObject) object;
            Rectangle box = collisionBox.getRectangle();
            shapeRenderer.rect(box.x, box.y, box.width, box.height);
        }
        //shapeRenderer.rect(player.bounds.x, player.bounds.y, player.bounds.width, player.bounds.height);
        shapeRenderer.end();
    }

    public void update(float deltaTime) {
        player.update(deltaTime);
    }

}
