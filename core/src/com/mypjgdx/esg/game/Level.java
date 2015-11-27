package com.mypjgdx.esg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mypjgdx.esg.game.objects.Sano;

public class Level {

    public Sano sano; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    public TiledMap map;   // แผนที่ในเกม

    public Level () {
        init();
    }

    private void init () {
        sano = new Sano();
        map = Assets.instance.map;
    }

    public void render(SpriteBatch batch) {
        sano.render(batch);
    }
}
