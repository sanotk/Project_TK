package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.collision.TiledCollisionProperty;
import com.mypjgdx.esg.game.objects.Link;
import com.mypjgdx.esg.game.objects.characters.Citizen;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.Weapon;
import com.mypjgdx.esg.screens.GameScreen;

public abstract class Level {

    public TiledMap map;
    public TiledMapTileLayer mapLayer;

    public Player player;

    public Array<Enemy> enemies = new Array<Enemy>();
    public Array<Weapon> weapons = new Array<Weapon>();
    public Array<Item> items = new Array<Item>();
    public Array<Link> links = new Array<Link>();
    public Array<Citizen> citizens = new Array<Citizen>();

    protected GameScreen gameScreen;

    public void update(float deltaTime) {
        player.update(deltaTime);
        for (Link link : links) link.update(deltaTime);
        for (Item item : items) item.update(deltaTime);
        for (Enemy enemy : enemies) enemy.update(deltaTime);
        for (Weapon weapon : weapons) weapon.update(deltaTime);
        for (Citizen citizen : citizens) citizen.update(deltaTime);
    }

    public void render(SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer renderer) {
        tiledRenderer.setMap(map);
        tiledRenderer.render();

        batch.begin();
        for (Weapon weapon : weapons) weapon.render(batch);
        for (Link link : links) link.render(batch);
        for (Item item : items) item.render(batch);
        player.render(batch);
        for (Enemy enemy : enemies) enemy.render(batch);
        for (Citizen citizen : citizens) citizen.render(batch);
        batch.end();

        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.RED);
        player.showHp(renderer);
        for (Enemy enemy : enemies) enemy.showHp(renderer);
        renderer.end();

        renderer.begin(ShapeType.Line);
        renderer.setColor(Color.RED);
//        for (Weapon weapon : weapons) {
//            if (weapon instanceof SwordWave || weapon instanceof Arrow)
//                weapon.debug(renderer);
//        }
        player.debug(renderer);
        for (Enemy enemy : enemies) enemy.debug(renderer);
        for (Citizen citizen : citizens) citizen.debug(renderer);


//        for (MapLayer layer : map.getLayers()) {
//            if (layer instanceof TiledMapTileLayer) {
//                TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
//                for (int i = 0; i < tileLayer.getWidth(); i++) {
//                    for (int j = 0; j < tileLayer.getHeight(); j++) {
//                        TiledMapTileLayer.Cell cell = tileLayer.getCell(i, j);
//                        TiledMapTile tile = cell.getTile();
//                        if (tile != null && tile.getProperties().containsKey("blocked")) {
//                            renderer.rect(i * tileLayer.getTileWidth(), j * tileLayer.getTileHeight(),
//                                    tileLayer.getTileWidth(), tileLayer.getTileHeight());
//                        }
//                    }
//                }
//            }
//        }

        renderer.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < mapLayer.getWidth(); i++) {
            for (int j = 0; j < mapLayer.getHeight(); j++) {
                renderer.rect(i * mapLayer.getTileWidth(), j * mapLayer.getTileHeight(),
                        mapLayer.getTileWidth(), mapLayer.getTileHeight());
            }
        }

        renderer.setColor(Color.BLUE);
        for (int i = 0; i < mapLayer.getWidth(); i++) {
            for (int j = 0; j < mapLayer.getHeight(); j++) {
                final TiledMapTileLayer.Cell cell = mapLayer.getCell(i, j);
                if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(
                        TiledCollisionProperty.BLOCKED.propertyName
                )) {
                    renderer.rect(i * mapLayer.getTileWidth(), j * mapLayer.getTileHeight(),
                            mapLayer.getTileWidth(), mapLayer.getTileHeight());
                }
            }
        }

        renderer.end();
    }

    public void createFbo(SpriteBatch batch, FrameBuffer lightFbo) {
    }

    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {
    }

    public void onEnter() {
    }

    public void onExit() {
    }

    public void addPlayer(Player player, float x, float y) {
        this.player = player;
        player.init(mapLayer, x, y);
        player.setLevel(this);
    }


    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        enemy.init(mapLayer, player);
        enemy.setWeapons(weapons);
        enemy.setEnemies(enemies);
    }

    public void addCitizen(Citizen citizen, Item target) {
        citizens.add(citizen);
        citizen.init(mapLayer, player);
        citizen.setTargetItem(target);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
        weapon.init(mapLayer, player);
        weapon.setWeapons(weapons);
    }

    public void addItem(Item item, float x, float y) {
        items.add(item);
        item.init(mapLayer, x, y);
        item.setLinks(links);
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

}
