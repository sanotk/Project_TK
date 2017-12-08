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
    public TextureRegion bullet;
    public TextureRegion beam;
    public TextureRegion trap;
    public TextureRegion enemyBall;
    public TextureAtlas playerAltas;
    public TextureAtlas solarCellAltas;
    public TextureAtlas batAltas;
    public TextureAtlas inverAltas;
    public TextureAtlas ccAltas;
    public TextureAtlas linkAltas;
    public TextureAtlas doorAltas;


    public Texture rule1;

    public TextureRegion bg;

    public TextureAtlas pepoAltas;
    public TextureAtlas pepoKnightAltas;
    public TextureAtlas pepoDevilAltas;
    public TiledMap map1;
    public TiledMap map2;
    public TiledMap map3;
    public TiledMap map4;
    public Music music;
    public Music introGame;
    public Sound bulletSound;
    public Sound beamSound;
    public Sound trapSound;
    public Sound enemyBallSound;

    private Assets() {}

    public void init() {
        manager = new AssetManager();
        manager.setErrorListener(this);
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("map1.tmx", TiledMap.class);
        manager.load("map2.tmx", TiledMap.class);
        manager.load("map3.tmx", TiledMap.class);
        manager.load("map4.tmx", TiledMap.class);

        manager.load("rule1.png" , Texture.class);

        manager.load("player_pack.atlas", TextureAtlas.class);
        manager.load("bg.png", Texture.class);
        manager.load("bullet.png", Texture.class);
        manager.load("enemy_ball.png", Texture.class);
        manager.load("solarcell_pack.atlas", TextureAtlas.class);
        manager.load("cc_pack.atlas", TextureAtlas.class);
        manager.load("inverter_pack.atlas", TextureAtlas.class);
        manager.load("battery_pack.atlas", TextureAtlas.class);
        manager.load("door_pack.atlas", TextureAtlas.class);
        manager.load("link_pack.atlas", TextureAtlas.class);
        manager.load("tv_pack.atlas", TextureAtlas.class);
        manager.load("fan_pack.atlas", TextureAtlas.class);
        manager.load("trap.png", Texture.class);
        manager.load("beam.png", Texture.class);
        manager.load("enemy1_pack.atlas", TextureAtlas.class);
        manager.load("enemy2_pack.atlas", TextureAtlas.class);
        manager.load("enemy3_pack.atlas", TextureAtlas.class);
        manager.load("music.mp3", Music.class);
        manager.load("Dangerous.mp3", Music.class);
        manager.load("bullet.wav", Sound.class);
        manager.load("beam.wav", Sound.class);
        manager.load("trap.wav", Sound.class);
        manager.load("enemy_ball.wav", Sound.class);

        manager.finishLoading();

        map1 = manager.get("map1.tmx");
        map2 = manager.get("map2.tmx");
        map3 = manager.get("map3.tmx");
        map4 = manager.get("map4.tmx");

        rule1 = manager.get("rule1.png");

        playerAltas = manager.get("player_pack.atlas");
        bg = new TextureRegion((Texture)manager.get("bg.png"));
        bullet = new TextureRegion((Texture)manager.get("bullet.png"));
        enemyBall = new TextureRegion((Texture)manager.get("enemy_ball.png"));
        solarCellAltas = manager.get("solarcell_pack.atlas");
        ccAltas = manager.get("cc_pack.atlas");
        batAltas = manager.get("battery_pack.atlas");
        inverAltas = manager.get("inverter_pack.atlas");
        doorAltas = manager.get("door_pack.atlas");
        linkAltas = manager.get("link_pack.atlas");
        trap = new TextureRegion((Texture)manager.get("trap.png"));
        beam = new TextureRegion((Texture)manager.get("beam.png"));
        pepoAltas = manager.get("enemy1_pack.atlas");
        pepoKnightAltas = manager.get("enemy2_pack.atlas");
        pepoDevilAltas = manager.get("enemy3_pack.atlas");
        music = manager.get("music.mp3");
        introGame = manager.get("Dangerous.mp3");
        bulletSound = manager.get("bullet.wav");
        enemyBallSound = manager.get("enemy_ball.wav");
        beamSound = manager.get("beam.wav");
        trapSound = manager.get("trap.wav");
    }

    public void init2() {
        manager = new AssetManager();
        manager.setErrorListener(this);
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("bullet.wav", Sound.class);
        manager.load("beam.wav", Sound.class);
        manager.load("trap.wav", Sound.class);
        manager.finishLoading();
        bulletSound = manager.get("bullet.wav");
        beamSound = manager.get("beam.wav");
        trapSound = manager.get("trap.wav");
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
