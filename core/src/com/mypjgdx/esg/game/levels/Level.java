package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.game.objects.characters.Citizen;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.characters.PlayerStalkerPosition;
import com.mypjgdx.esg.game.objects.etcs.Link;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.Bow;
import com.mypjgdx.esg.game.objects.weapons.Sword;
import com.mypjgdx.esg.game.objects.weapons.Weapon;
import com.mypjgdx.esg.ui.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Level implements Json.Serializable {

    public TiledMap map;
    public Player player;
    public List<Item> items;
    public List<Enemy> enemies;
    public List<Citizen> citizens;
    public List<Weapon> weapons;
    public List<Sword> swords;
    public List<Bow> bows;
    public List<Link> links;
    public TiledMapTileLayer mapLayer;

    public boolean hasSolarCell;


    public Level() {
        weapons = new ArrayList<Weapon>();
        swords = new ArrayList<Sword>();
        bows = new ArrayList<Bow>();

        links = new ArrayList<Link>();
        items = new ArrayList<Item>();
        enemies = new ArrayList<Enemy>();
        citizens = new ArrayList<Citizen>();

        init();
    }

    public void init() {
        weapons.clear();
        links.clear();
    }

    public void render(SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map);
        tiledRenderer.render();

        batch.begin();

        for (Weapon w : weapons) w.render(batch);
        for (Link e : links) e.render(batch);
        for (Item i : items) i.render(batch);
        for (Enemy e : enemies) e.render(batch);
        if (player.stageOneClear && player.isSwitch) {
            for (Citizen c : citizens) c.render(batch);
        }
        player.render(batch);
        for (Sword s : swords) s.render(batch);
        for (Bow b : bows) b.render(batch);

        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        player.showHp(shapeRenderer);
        for (Enemy e : enemies) e.showHp(shapeRenderer);
        //for (Item i: items) i.debug(shapeRenderer);
//        player.debug(shapeRenderer);
        shapeRenderer.end();


//        shapeRenderer.begin(ShapeType.Line);

//
//        for (Sword s : swords) s.debug(shapeRenderer);
//        for (Bow b : bows) b.debug(shapeRenderer);
//        for (Enemy e : enemies) e.debug(shapeRenderer);
//
//        for (Weapon weapon : weapons) {
//            if (weapon instanceof SwordHit) {
//                SwordHit swordHit = (SwordHit) weapon;
//                swordHit.debug(shapeRenderer);
//            }
//        }
//        player.debug(shapeRenderer);
//        for (Citizen citizen : citizens) {
//            citizen.debug(shapeRenderer);
//        }
//        shapeRenderer.end();
//
//        LevelDebugger.instance.enable(LevelDebugger.PLAYER);
//        LevelDebugger.instance.enable(LevelDebugger.ENEMY);
//        LevelDebugger.instance.enable(LevelDebugger.CITIZEN);
//        LevelDebugger.instance.enable(LevelDebugger.ARROW);
//        LevelDebugger.instance.enable(LevelDebugger.TRAP);
//        LevelDebugger.instance.enable(LevelDebugger.SWORD_HIT);
//        LevelDebugger.instance.enable(LevelDebugger.SWORD_WAVE);
//        LevelDebugger.instance.enable(LevelDebugger.SWORD);
//        LevelDebugger.instance.enable(LevelDebugger.BOW);
//        LevelDebugger.instance.enable(LevelDebugger.MAP_ALL);
//        LevelDebugger.instance.debug(this, shapeRenderer);
    }

    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {
    }

    public void createFbo(SpriteBatch batch, FrameBuffer lightFbo) {
    }

    public void update(float deltaTime) {

        Iterator<Weapon> weaponIterator = weapons.iterator();
        Iterator<Link> etcIterator = links.iterator();
        Iterator<Enemy> enemyIterator = enemies.iterator();
        Iterator<Citizen> citizenIterator = citizens.iterator();

        while (weaponIterator.hasNext()) {
            Weapon w = weaponIterator.next();
            if (w.isDestroyed()) weaponIterator.remove();
        }

        int p = 0;
        boolean firstSkipped = false;
        for (PlayerStalkerPosition.IntPosition position : player.stalkerPosition.getPositions()) {
            if (!firstSkipped) {
                firstSkipped = true;
                continue;
            }
            citizens.get(p).setStalkingPosition(position.x,position.y);
            p++;
        }

        while (etcIterator.hasNext()) {
            Link e = etcIterator.next();
            if (e.isDestroyed()) etcIterator.remove();
        }

        while (enemyIterator.hasNext()) {
            Enemy e = enemyIterator.next();
            if (!e.isAlive()) enemyIterator.remove();
        }

        player.update(deltaTime, weapons, citizens);

        for (Link link : links) link.update(deltaTime);
        for (Item i : items) i.update(deltaTime);
        for (Enemy e : enemies) e.update(deltaTime, weapons);
        if (player.stageOneClear) {
            for (Citizen c : citizens) c.update(deltaTime);
        }
        for (Weapon w : weapons) w.update(deltaTime);
        for (Sword s : swords) s.update(deltaTime);
        for (Bow b : bows) b.update(deltaTime);
    }

    @Override
    public void write(Json json) {
        json.writeValue("player", player);
        json.writeValue("links", links);
        json.writeValue("enemies", enemies);
        json.writeValue("citizens", citizens);
        json.writeValue("items", items);
        json.writeValue("EnergyProducedBar", EnergyProducedBar.instance);
        json.writeValue("EnergyUsedBar", EnergyUsedBar.instance);
        json.writeValue("BatteryBar", BatteryBar.instance);
        json.writeValue("ArrowBar", ArrowBar.instance);
        json.writeValue("SwordWaveBar", SwordWaveBar.instance);
        json.writeValue("TrapBar", TrapBar.instance);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
//        System.out.println(jsonData.get("player"));
    }
}
