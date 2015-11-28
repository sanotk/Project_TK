package com.mypjgdx.esg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.game.objects.Sano;

public class Level {

    public final Sano sano; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    public final Map map;   // แผนที่ในเกม

    public Level (Map map) {
        this.map = map;
        sano = new Sano();
    }

    public void render (OrthogonalTiledMapRenderer tiledRenderer, SpriteBatch batch) {
        tiledRenderer.setMap(map.getTiledMap());
        tiledRenderer.render();

        batch.begin();
        sano.render(batch);
        batch.end();
    }

    public void update(float deltaTime) {
        sano.update(deltaTime);
    }

}
