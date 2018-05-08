package com.mypjgdx.esg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager manager;

    public Skin skin;
    public BitmapFont font;

    public TextureRegion bullet;
    public TextureRegion arrow;
    public TextureRegion wave;
    public TextureRegion box;
    public TextureRegion beam;
    public TextureRegion trap;
    public TextureRegion enemyBall;
    public TextureAtlas sword;
    public TextureAtlas bow;
    public TextureRegion link;
    public TextureAtlas playerAtlas;
    public TextureAtlas citizenAtlas;
    public TextureAtlas solarCellAtlas;
    public TextureAtlas batAtlas;
    public TextureAtlas inverAtlas;
    public TextureAtlas ccAtlas;
    public TextureAtlas doorAtlas;
    public TextureAtlas airAtlas;
    public TextureAtlas comAtlas;
    public TextureAtlas fanAtlas;
    public TextureAtlas lampAtlas;
    public TextureAtlas microwaveAtlas;
    public TextureAtlas refrigeratorAtlas;
    public TextureAtlas riceCookerAtlas;
    public TextureAtlas switchAtlas;
    public TextureAtlas tvAtlas;
    public TextureAtlas waterPumpAtlas;

    public TextureAtlas uiBlue;
    public TextureAtlas uiRed;

    public Texture rule1;
    public Texture white;
    public Texture light;

    public TextureRegion bg;

    public TextureAtlas pepoAtlas;
    public TextureAtlas pepoKnightAtlas;
    public TextureAtlas pepoDevilAtlas;
    public TiledMap map1;
    public TiledMap map2;
    public TiledMap map3;
    public TiledMap map4;
    public Music music;
    public Music introGame;
    public Sound arrowSound;
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
        manager.load("uiskin.json", Skin.class, new SkinLoader.SkinParameter("uiskin.atlas"));

        manager.load("rule1.png" , Texture.class);

        manager.load("player_pack.atlas", TextureAtlas.class);
        manager.load("citizen_pack.atlas", TextureAtlas.class);
        manager.load("bg.png", Texture.class);
        manager.load("bullet.png", Texture.class);
        manager.load("bow_shot.png",Texture.class);
        manager.load("sword_wave.png",Texture.class);
        manager.load("box.png",Texture.class);
        manager.load("enemy_ball.png", Texture.class);

        manager.load("sword_pack.atlas", TextureAtlas.class);
        manager.load("bow_pack.atlas", TextureAtlas.class);

        manager.load("solarcell_pack.atlas", TextureAtlas.class);
        manager.load("cc_pack.atlas", TextureAtlas.class);
        manager.load("inverter_pack.atlas", TextureAtlas.class);
        manager.load("battery_pack.atlas", TextureAtlas.class);
        manager.load("door_pack.atlas", TextureAtlas.class);
        manager.load("link.png", Texture.class);
        manager.load("tv_pack.atlas", TextureAtlas.class);
        manager.load("air_pack.atlas", TextureAtlas.class);
        manager.load("computer_pack.atlas", TextureAtlas.class);
        manager.load("fan_pack.atlas", TextureAtlas.class);
        manager.load("lamp_pack.atlas", TextureAtlas.class);
        manager.load("microwave_pack.atlas", TextureAtlas.class);
        manager.load("refrigerator_pack.atlas", TextureAtlas.class);
        manager.load("ricecooker_pack.atlas", TextureAtlas.class);
        manager.load("switch_pack.atlas", TextureAtlas.class);
        manager.load("waterpump_pack.atlas", TextureAtlas.class);

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
        manager.load("light.png", Texture.class);
        manager.load("white.png", Texture.class);

        manager.load("ui-blue.atlas", TextureAtlas.class);
        manager.load("ui-red.atlas", TextureAtlas.class);

        manager.finishLoading();

        skin = manager.get("uiskin.json");

        map1 = manager.get("map1.tmx");
        map2 = manager.get("map2.tmx");
        map3 = manager.get("map3.tmx");
        map4 = manager.get("map4.tmx");

        rule1 = manager.get("rule1.png");

        playerAtlas = manager.get("player_pack.atlas");
        citizenAtlas = manager.get("citizen_pack.atlas");

        light = manager.get("light.png");
        white = manager.get("white.png");

        bg = new TextureRegion((Texture)manager.get("bg.png"));
        bullet = new TextureRegion((Texture)manager.get("bullet.png"));
        enemyBall = new TextureRegion((Texture)manager.get("enemy_ball.png"));
        link = new TextureRegion((Texture)manager.get("link.png"));
        solarCellAtlas = manager.get("solarcell_pack.atlas");
        ccAtlas = manager.get("cc_pack.atlas");
        batAtlas = manager.get("battery_pack.atlas");
        inverAtlas = manager.get("inverter_pack.atlas");
        doorAtlas = manager.get("door_pack.atlas");
        fanAtlas = manager.get("fan_pack.atlas");
        airAtlas = manager.get("air_pack.atlas");
        comAtlas = manager.get("computer_pack.atlas");
        lampAtlas = manager.get("lamp_pack.atlas");
        microwaveAtlas = manager.get("microwave_pack.atlas");
        refrigeratorAtlas = manager.get("refrigerator_pack.atlas");
        riceCookerAtlas = manager.get("ricecooker_pack.atlas");
        switchAtlas = manager.get("switch_pack.atlas");
        tvAtlas = manager.get("tv_pack.atlas");
        waterPumpAtlas = manager.get("waterpump_pack.atlas");

        trap = new TextureRegion((Texture)manager.get("trap.png"));
        beam = new TextureRegion((Texture)manager.get("beam.png"));
        box = new TextureRegion((Texture)manager.get("box.png"));

        sword = manager.get("sword_pack.atlas");
        bow = manager.get("bow_pack.atlas");
        arrow = new TextureRegion((Texture)manager.get("bow_shot.png"));
        wave = new TextureRegion((Texture)manager.get("sword_wave.png"));
        pepoAtlas = manager.get("enemy1_pack.atlas");
        pepoKnightAtlas = manager.get("enemy2_pack.atlas");
        pepoDevilAtlas = manager.get("enemy3_pack.atlas");
        music = manager.get("music.mp3");
        introGame = manager.get("Dangerous.mp3");
        arrowSound = manager.get("bullet.wav");
        enemyBallSound = manager.get("enemy_ball.wav");
        beamSound = manager.get("beam.wav");
        trapSound = manager.get("trap.wav");

        uiBlue = manager.get("ui-blue.atlas");
        uiRed = manager.get("ui-red.atlas");

        font = new BitmapFont();
    }

    public <T> T get(String fileName) {
        return manager.get(fileName);
    }

    public <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", throwable);
    }

    @Override
    public void dispose() {
        manager.dispose();
        font.dispose();
    }

}
