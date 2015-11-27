package com.mypjgdx.esg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.mypjgdx.esg.utils.TileMapLoader;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager manager;

    public TextureAtlas levelAtlas;
    public TileMap backTileMap1;
    public TileMap frontTileMap1;
    public Music backgroundMusic;

    private Assets() {}

    public void init() {
        if ( manager != null) dispose();
        else manager = new AssetManager();

        manager.setErrorListener(this);
        manager.setLoader(TileMap.class, new TileMapLoader(new InternalFileHandleResolver()));

        manager.load("map_first.atlas", TextureAtlas.class);
        manager.load("mix_map_1.csv", TileMap.class);
        manager.load("mix_map_2.csv", TileMap.class);
        manager.load("Breaktime_Silent_Film_Light.mp3", Music.class);

        manager.finishLoading();

        levelAtlas = manager.get("map_first.atlas");
        backTileMap1 = manager.get("mix_map_1.csv");
        frontTileMap1 = manager.get("mix_map_2.csv");
        backgroundMusic = manager.get("Breaktime_Silent_Film_Light.mp3");
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
