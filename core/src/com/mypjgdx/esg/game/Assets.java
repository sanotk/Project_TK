package com.mypjgdx.esg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager manager;

    public TextureAtlas sanoAltas;
    public TiledMap map;
    public Music music;

    private Assets() {}

    public void init() {
        if ( manager != null) dispose();
        else manager = new AssetManager();

        manager.setErrorListener(this);
        manager.setLoader(TiledMap.class, new TmxMapLoader());

        manager.load("char_pack.atlas", TextureAtlas.class);
        manager.load("map.tmx", TiledMap.class);
        manager.load("music.mp3", Music.class);

        manager.finishLoading();

        sanoAltas = manager.get("char_pack.atlas");
        map = manager.get("map.tmx");
        music = manager.get("music.mp3");

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
