package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.game.objects.characters.Citizen;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.etcs.Etc;
import com.mypjgdx.esg.game.objects.items.EnergyTube;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.Bow;
import com.mypjgdx.esg.game.objects.weapons.Sword;
import com.mypjgdx.esg.game.objects.weapons.SwordHit;
import com.mypjgdx.esg.game.objects.weapons.Weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Level {

    public TiledMap map;
    public Player player;
    public List<Item> items;
    public List<Enemy> enemies;
    public List<Citizen> citizens;
    public List<Weapon> weapons;
    public List<Sword> swords;
    public List<Bow> bows;
    public List<Etc> etcs;
    public TiledMapTileLayer mapLayer;

    public EnergyTube energyTube;

    public boolean hasSolarCell;

    public Level(LevelGenerator levelGenerator) {
        weapons = new ArrayList<Weapon>();
        swords = new ArrayList<Sword>();
        bows = new ArrayList<Bow>();

        etcs = new ArrayList<Etc>();

        energyTube = new EnergyTube(0);  // พลังงานเริ่มต้นมีค่า 100 วินาที

        init(levelGenerator);
    }

    public void init(LevelGenerator levelGenerator) {
        weapons.clear();
        etcs.clear();

        map = levelGenerator.createTiledMap();
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = levelGenerator.createPlayer(mapLayer);
        items = levelGenerator.createItems(mapLayer, player, this);
        enemies = levelGenerator.createEnemies(mapLayer, player);
        citizens = levelGenerator.createCitizens(mapLayer, player);
        swords = levelGenerator.createSwords(mapLayer, player, this);
        bows = levelGenerator.createBows(mapLayer, player, this);

        energyTube.init(mapLayer, player);
    }

    public void render(SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map);
        tiledRenderer.render();

        batch.begin();

        for (Weapon w : weapons) w.render(batch);
        for (Etc e : etcs) e.render(batch);
        for (Item i : items) i.render(batch);
        for (Enemy e : enemies) e.render(batch);
        if (player.stageoneclear) {
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
        shapeRenderer.end();

        shapeRenderer.begin(ShapeType.Line);
        /*
        for (Sword s : swords) s.debug(shapeRenderer);
        for (Bow b : bows) b.debug(shapeRenderer);
        for (Enemy e : enemies) e.debug(shapeRenderer);
        */
        for (Weapon weapon : weapons) {
            if (weapon instanceof SwordHit) {
                SwordHit swordHit = (SwordHit) weapon;
                swordHit.debug(shapeRenderer);
            }
        }
        shapeRenderer.end();

    }

    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {
    }

    public void createFbo(SpriteBatch batch, FrameBuffer lightFbo) {
    }

    public void update(float deltaTime) {

        Iterator<Weapon> weaponIterator = weapons.iterator();
        Iterator<Etc> etcIterator = etcs.iterator();
        Iterator<Enemy> enemyIterator = enemies.iterator();
        Iterator<Citizen> citizenIterator = citizens.iterator();

        while (weaponIterator.hasNext()) {
            Weapon w = weaponIterator.next();
            if (w.isDestroyed()) weaponIterator.remove();
        }

        while (etcIterator.hasNext()) {
            Etc e = etcIterator.next();
            if (e.isDestroyed()) etcIterator.remove();
        }

        while (enemyIterator.hasNext()) {
            Enemy e = enemyIterator.next();
            if (!e.isAlive()) enemyIterator.remove();
        }

        player.update(deltaTime, weapons);

        for (Etc etc : etcs) etc.update(deltaTime);
        for (Item i : items) i.update(deltaTime);
        for (Enemy e : enemies) e.update(deltaTime, weapons);
        for (Citizen c : citizens) c.update(deltaTime);
        for (Weapon w : weapons) w.update(deltaTime);
        for (Sword s : swords) s.update(deltaTime);
        for (Bow b : bows) b.update(deltaTime);
        energyTube.update(deltaTime);
    }

}
