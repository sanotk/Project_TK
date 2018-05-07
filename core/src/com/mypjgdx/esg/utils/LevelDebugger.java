package com.mypjgdx.esg.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.Pools;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.game.objects.characters.Citizen;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.*;

public class LevelDebugger {
    public static final LevelDebugger instance = new LevelDebugger();

    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int ITEM = 2;
    public static final int CITIZEN = 3;
    public static final int ARROW = 4;
    public static final int TRAP = 5;
    public static final int SWORD_WAVE = 6;
    public static final int SWORD_HIT = 7;
    public static final int BOW = 8;
    public static final int SWORD = 9;

    public static final int MAP_GRID = 10;
    public static final int MAP_BLOCKED = 11;
    public static final int MAP_SOLAR_CELL= 12;
    public static final int MAP_INVERTER = 13;
    public static final int MAP_CONTROLLER = 14;
    public static final int MAP_BATTERY = 15;
    public static final int MAP_FAN2 = 16;
    public static final int MAP_REFRIGERATOR = 17;
    public static final int MAP_TV = 18;
    public static final int MAP_COMPUTER = 19;
    public static final int MAP_PUMP = 20;
    public static final int MAP_FAN = 21;
    public static final int MAP_AIR_CONDITIONER = 22;
    public static final int MAP_RICE_COOKER = 23;
    public static final int MAP_MICROWAVE = 24;
    public static final int MAP_SWITCH = 25;


    public static final int MAP_ALL = 100;

    private IntSet settings = new IntSet();
    private Level level;
    private ShapeRenderer renderer;

    private enum TiledDrawingData {
        BLOCKED("blocked", Color.RED),
        SOLAR_CELL("solarcell", Color.BLUE),
        INVERTER("inverter", Color.valueOf("#ffbf00")),
        CONTROLLER("ccontroller", Color.valueOf("#56f340")),
        BATTERY("battery", Color.GOLD),
        DOOR("door", Color.valueOf("#8000ff")),
        REFRIGERATOR("refrigerator", Color.PINK),
        TV("tv", Color.FIREBRICK),
        COMPUTER("com", Color.CORAL),
        PUMP("pump", Color.CHARTREUSE),
        FAN("fan", Color.FOREST),
        FAN2("fan2", Color.BLUE),
        AIR_CONDITIONER("air", Color.NAVY),
        RICE_COOKER("cooker", Color.TEAL),
        MICROWAVE("microwave",Color.valueOf("#ff00bf")),
        SWITCH("switch", Color.CYAN);

        public final String propertyName;
        public final Color color;

        TiledDrawingData(String propertyName, Color color) {
            this.propertyName = propertyName;
            this.color = color;
        }
    }

    private LevelDebugger() {
    }

    public void debug(Level level, ShapeRenderer renderer) {
        this.level = level;
        this.renderer = renderer;

        boolean drawing = renderer.isDrawing();
        Color oldColor = renderer.getColor();
        ShapeRenderer.ShapeType shapeType = renderer.getCurrentType();

        if (drawing) {
            renderer.end();
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        drawMapTileOverlay();
        drawEntityBoundsFilled();
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        drawEntityBounds();
        drawMapGrid();
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (drawing) {
            renderer.begin(shapeType);
            renderer.setColor(oldColor);
        }
    }

    private void drawEntityBoundsFilled() {
        Color color = Pools.obtain(Color.class).set(1, 0.5f, 0, 0.6f);
        renderer.setColor(color);
        Pools.free(color);
        if (settings.contains(ARROW)) {
            for (Weapon weapon : level.weapons) {
                if (weapon instanceof Arrow) {
                    drawBounds(weapon.bounds);
                }
            }
        }
        if (settings.contains(SWORD_WAVE)) {
            for (Weapon weapon : level.weapons) {
                if (weapon instanceof SwordWave) {
                    drawBounds(weapon.bounds);
                }
            }
        }
        if (settings.contains(SWORD_HIT)) {
            for (Weapon weapon : level.weapons) {
                if (weapon instanceof SwordHit) {
                    drawBounds(weapon.bounds);
                }
            }
        }
        if (settings.contains(TRAP)) {
            for (Weapon weapon : level.weapons) {
                if (weapon instanceof Trap) {
                    drawBounds(weapon.bounds);
                }
            }
        }
    }

    private void drawMapGrid() {
        if (!settings.contains(MAP_GRID)) return;
        renderer.setColor(Color.BLACK);
        for (int i = 0; i < level.mapLayer.getWidth(); i++) {
            for (int j = 0; j < level.mapLayer.getHeight(); j++) {
                final float tileWidth = level.mapLayer.getTileWidth();
                final float tileHeight = level.mapLayer.getTileHeight();
                renderer.rect(i * tileWidth, j * tileHeight, tileWidth, tileHeight);
            }
        }
    }
    private void drawMapTileOverlay() {
        if (settings.contains(MAP_BLOCKED)) {
            drawMapTileOverlay(TiledDrawingData.BLOCKED);
        }
        if (settings.contains(MAP_SOLAR_CELL)) {
            drawMapTileOverlay(TiledDrawingData.SOLAR_CELL);
        }
        if (settings.contains(MAP_INVERTER)) {
            drawMapTileOverlay(TiledDrawingData.INVERTER);
        }
        if (settings.contains(MAP_CONTROLLER)) {
            drawMapTileOverlay(TiledDrawingData.CONTROLLER);
        }
        if (settings.contains(MAP_BATTERY)) {
            drawMapTileOverlay(TiledDrawingData.BATTERY);
        }
//        if (settings.contains(MAP_DOOR)) {
//            drawMapTileOverlay(TiledDrawingData.DOOR);
//        }
        if (settings.contains(MAP_REFRIGERATOR)) {
            drawMapTileOverlay(TiledDrawingData.REFRIGERATOR);
        }
        if (settings.contains(MAP_TV)) {
            drawMapTileOverlay(TiledDrawingData.TV);
        }
        if (settings.contains(MAP_COMPUTER)) {
            drawMapTileOverlay(TiledDrawingData.COMPUTER);
        }
        if (settings.contains(MAP_PUMP)) {
            drawMapTileOverlay(TiledDrawingData.PUMP);
        }
        if (settings.contains(MAP_FAN)) {
            drawMapTileOverlay(TiledDrawingData.FAN);
        }
        if (settings.contains(MAP_FAN2)) {
            drawMapTileOverlay(TiledDrawingData.FAN2);
        }
        if (settings.contains(MAP_AIR_CONDITIONER)) {
            drawMapTileOverlay(TiledDrawingData.AIR_CONDITIONER);
        }
        if (settings.contains(MAP_RICE_COOKER)) {
            drawMapTileOverlay(TiledDrawingData.RICE_COOKER);
        }
        if (settings.contains(MAP_MICROWAVE)) {
            drawMapTileOverlay(TiledDrawingData.MICROWAVE);
        }
        if (settings.contains(MAP_SWITCH)) {
            drawMapTileOverlay(TiledDrawingData.SWITCH);
        }}

    private void drawMapTileOverlay(TiledDrawingData tiledDrawingData) {
        renderer.setColor(tiledDrawingData.color.r, tiledDrawingData.color.g, tiledDrawingData.color.b, 0.6f);
        for (int i = 0; i < level.mapLayer.getWidth(); i++) {
            for (int j = 0; j < level.mapLayer.getHeight(); j++) {
                TiledMapTileLayer.Cell cell = level.mapLayer.getCell(i, j);
                if (cell != null && cell.getTile().getProperties().containsKey(tiledDrawingData.propertyName)) {
                    final float tileWidth = level.mapLayer.getTileWidth();
                    final float tileHeight = level.mapLayer.getTileHeight();
                    renderer.rect(i * tileWidth, j * tileHeight, tileWidth, tileHeight);
                }
            }
        }
    }

    public void enable(int setting) {
        if (setting == MAP_ALL) {
            for (int i = MAP_GRID; i <= MAP_SWITCH; i++) {
                settings.add(i);
            }
        } else {
            settings.add(setting);
        }
    }

    public void disable(int setting) {
        if (setting == MAP_ALL) {
            for (int i = MAP_GRID; i <= MAP_SWITCH; i++) {
                settings.add(i);
            }
        } else {
            settings.remove(setting);
        }
    }

    private void drawEntityBounds() {
        renderer.setColor(Color.RED);

        if (settings.contains(PLAYER)) {
            drawBounds(level.player.bounds);
        }
        if (settings.contains(ENEMY)) {
            for (Enemy enemy : level.enemies) {
                drawBounds(enemy.bounds);
            }
        }
        if (settings.contains(CITIZEN)) {
            for (Citizen citizen : level.citizens) {
                drawBounds(citizen.bounds);
            }
        }
        if (settings.contains(ITEM)) {
            for (Item item : level.items) {
                drawBounds(item.bounds);
            }
        }

        renderer.setColor(Color.BLUE);
        if (settings.contains(BOW)) {
            for (Bow bow : level.bows) {
                drawBounds(bow.bounds);
            }
        }

        renderer.setColor(Color.CYAN);
        if (settings.contains(SWORD)) {
            for (Sword sword : level.swords) {
                drawBounds(sword.bounds);
            }
        }
    }

    private void drawBounds(Rectangle bounds) {
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
