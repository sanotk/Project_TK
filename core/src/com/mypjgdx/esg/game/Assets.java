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
    public TextureRegion arrow;
    public TextureRegion wave;
    public TextureRegion box;
    public TextureRegion beam;
    public TextureRegion trap;
    public TextureRegion enemyBall;

    public TextureRegion iconBow;
    public TextureRegion iconSword;
    public TextureRegion iconTrap;
    public TextureRegion iconEnergyPlus;
    public TextureRegion iconEnergyMinus;
    public TextureRegion iconBattery;
    public TextureRegion iconTime;

    public TextureAtlas sword;
    public TextureAtlas bow;
    public TextureRegion link;

    public TextureAtlas playerAltas;
    public TextureAtlas citizenAltas;
    public TextureAtlas solarCellAltas;
    public TextureAtlas batAltas;
    public TextureAtlas inverAltas;
    public TextureAtlas ccAltas;
    public TextureAtlas doorAltas;
    public TextureAtlas airAltas;
    public TextureAtlas comAltas;
    public TextureAtlas fanAltas;
    public TextureAtlas lampAltas;
    public TextureAtlas microwaveAltas;
    public TextureAtlas refrigeratorAltas;
    public TextureAtlas ricecookerAltas;
    public TextureAtlas switchAltas;
    public TextureAtlas tvAltas;
    public TextureAtlas waterpumpAltas;
    public TextureAtlas pollutionAtlas;
    public TextureAtlas gateAtlas;

    public TextureAtlas uiBlue;
    public TextureAtlas uiRed;

    public Texture rule1;
    public Texture white;
    public Texture light;

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
        manager.load("citizen_pack.atlas", TextureAtlas.class);
        manager.load("bg.png", Texture.class);
        manager.load("bullet.png", Texture.class);
        manager.load("bow_shot.png",Texture.class);
        manager.load("sword_wave.png",Texture.class);
        manager.load("box.png",Texture.class);
        manager.load("enemy_ball.png", Texture.class);



        manager.load("icon_sword.png",Texture.class);
        manager.load("icon_bow.png",Texture.class);
        manager.load("icon_trap.png",Texture.class);
        manager.load("icon_energy_plus.png",Texture.class);
        manager.load("icon_energy_minus.png",Texture.class);
        manager.load("icon_battery.png",Texture.class);
        manager.load("icon_time.png",Texture.class);

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
        manager.load("pollutioncontrol_pack.atlas", TextureAtlas.class);
        manager.load("gate_pack.atlas", TextureAtlas.class);

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
        manager.load("ui-red.atlas", TextureAtlas.class);manager.load("ui-red.atlas", TextureAtlas.class);

        manager.finishLoading();

        map1 = manager.get("map1.tmx");
        map2 = manager.get("map2.tmx");
        map3 = manager.get("map3.tmx");
        map4 = manager.get("map4.tmx");

        rule1 = manager.get("rule1.png");

        playerAltas = manager.get("player_pack.atlas");
        citizenAltas = manager.get("citizen_pack.atlas");

        light = manager.get("light.png");
        white = manager.get("white.png");

        bg = new TextureRegion((Texture)manager.get("bg.png"));
        bullet = new TextureRegion((Texture)manager.get("bullet.png"));
        enemyBall = new TextureRegion((Texture)manager.get("enemy_ball.png"));
        link = new TextureRegion((Texture)manager.get("link.png"));
        solarCellAltas = manager.get("solarcell_pack.atlas");
        ccAltas = manager.get("cc_pack.atlas");
        batAltas = manager.get("battery_pack.atlas");
        inverAltas = manager.get("inverter_pack.atlas");
        doorAltas = manager.get("door_pack.atlas");
        fanAltas = manager.get("fan_pack.atlas");
        airAltas = manager.get("air_pack.atlas");
        comAltas = manager.get("computer_pack.atlas");
        lampAltas = manager.get("lamp_pack.atlas");
        microwaveAltas = manager.get("microwave_pack.atlas");
        refrigeratorAltas = manager.get("refrigerator_pack.atlas");
        ricecookerAltas = manager.get("ricecooker_pack.atlas");
        switchAltas = manager.get("switch_pack.atlas");
        tvAltas = manager.get("tv_pack.atlas");
        waterpumpAltas = manager.get("waterpump_pack.atlas");
        pollutionAtlas = manager.get("pollutioncontrol_pack.atlas");
        gateAtlas = manager.get("gate_pack.atlas");

        trap = new TextureRegion((Texture)manager.get("trap.png"));
        beam = new TextureRegion((Texture)manager.get("beam.png"));
        box = new TextureRegion((Texture)manager.get("box.png"));

        iconBow = new TextureRegion((Texture)manager.get("icon_bow.png"));
        iconSword = new TextureRegion((Texture)manager.get("icon_sword.png"));
        iconTrap = new TextureRegion((Texture)manager.get("icon_trap.png"));
        iconEnergyPlus = new TextureRegion((Texture)manager.get("icon_energy_plus.png"));
        iconEnergyMinus = new TextureRegion((Texture)manager.get("icon_energy_minus.png"));
        iconBattery = new TextureRegion((Texture)manager.get("icon_battery.png"));
        iconTime = new TextureRegion((Texture)manager.get("icon_time.png"));

        sword = manager.get("sword_pack.atlas");
        bow = manager.get("bow_pack.atlas");
        arrow = new TextureRegion((Texture)manager.get("bow_shot.png"));
        wave = new TextureRegion((Texture)manager.get("sword_wave.png"));
        pepoAltas = manager.get("enemy1_pack.atlas");
        pepoKnightAltas = manager.get("enemy2_pack.atlas");
        pepoDevilAltas = manager.get("enemy3_pack.atlas");
        music = manager.get("music.mp3");
        introGame = manager.get("Dangerous.mp3");
        bulletSound = manager.get("bullet.wav");
        enemyBallSound = manager.get("enemy_ball.wav");
        beamSound = manager.get("beam.wav");
        trapSound = manager.get("trap.wav");

        uiBlue = manager.get("ui-blue.atlas");
        uiRed = manager.get("ui-red.atlas");

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
