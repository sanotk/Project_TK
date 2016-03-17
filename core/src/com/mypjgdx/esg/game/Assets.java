package com.mypjgdx.esg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();
    public AssetManager manager;
    public TextureRegion sword;
    public TextureAtlas playerAltas;

    public TextureAtlas enemyAltas;
    public TiledMap map1;
    public TiledMap map2;
    public TiledMap map3;
    public TiledMap map4;
    public Music music;
    public Sound bullet;

    private Assets() {}

    public void init() {
        manager = new AssetManager();
        manager.setErrorListener(this);
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("map1.tmx", TiledMap.class);
        manager.load("map2.tmx", TiledMap.class);
        manager.load("map3.tmx", TiledMap.class);
        manager.load("map4.tmx", TiledMap.class);

        manager.load("player_pack.atlas", TextureAtlas.class);
        manager.load("sword.png", Texture.class);
        manager.load("mon_pack.atlas", TextureAtlas.class);
        manager.load("music.mp3", Music.class);
        manager.load("bullet.wav", Sound.class);

        manager.finishLoading();

        map1 = manager.get("map1.tmx");
        map2 = manager.get("map2.tmx");
        map3 = manager.get("map3.tmx");
        map4 = manager.get("map4.tmx");

        playerAltas = manager.get("player_pack.atlas");
        sword = new TextureRegion((Texture)manager.get("sword.png"));
        enemyAltas = manager.get("mon_pack.atlas");
        music = manager.get("music.mp3");
        bullet = manager.get("bullet.wav");
    }

    public void init2() {
        manager = new AssetManager();
        manager.setErrorListener(this);
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("bullet.wav", Sound.class);
        manager.finishLoading();
        bullet = manager.get("bullet.wav");
    }


    @SuppressWarnings("rawtypes")
    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", throwable);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }

}
