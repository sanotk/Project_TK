package com.mypjgdx.esg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.game.objects.Sano;

public class Level {

    public final Sano sano; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    private TiledMap map;   // แผนที่ในเกม

    public Level () {
        this(null, new Sano());
        setMap(Map.MAP_01);
    }

    public Level (TiledMap map, Sano sano) {
        this.sano = sano;
        this.map = map;
    }

    public void render (OrthogonalTiledMapRenderer tiledRenderer, SpriteBatch batch) {
        tiledRenderer.setMap(map);
        tiledRenderer.render();

        batch.begin();
        sano.render(batch);
        batch.end();
    }

    public void nextMap() {
        this.map = Map.getMap(map).next().getTiledMap();
    }

    public void setMap(Map map) {
        this.map = map.getTiledMap();
    }

}
