package com.mypjgdx.esg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.EnemySpawner;
import com.mypjgdx.esg.game.objects.etcs.Link;
import com.mypjgdx.esg.screens.*;
import com.mypjgdx.esg.ui.BatteryBar;
import com.mypjgdx.esg.ui.EnergyProducedBar;
import com.mypjgdx.esg.ui.EnergyUsedBar;

public class GameSaveManager {

    public static final GameSaveManager instance = new GameSaveManager();

    public AbstractGameScreen gameScreen;

    private GameSaveManager() {
    }

    public void save() {
        FileHandle file = Gdx.files.absolute("C:\\Data\\save.txt");
        Json json = new Json(JsonWriter.OutputType.json);
        System.out.print(json.prettyPrint(gameScreen.getWorldController().level));
        json.toJson(gameScreen.getWorldController().level, file);
    }

    public void load() {
        FileHandle file = Gdx.files.absolute("C:\\Data\\save.txt");
        JsonReader reader = new JsonReader();
        final JsonValue saveData = reader.parse(file);

        String levelName = saveData.get("name").asString();
        final AbstractGameScreen newGameScreen;

        if (levelName.equals("Level1")) {
            newGameScreen = new GameScreen(gameScreen.game, gameScreen.getOptionWindow());
        } else if (levelName.equals("Level2")) {
            newGameScreen = new GameScreen2(gameScreen.game, gameScreen.getOptionWindow());
        } else if (levelName.equals("Level3")) {
            newGameScreen = new GameScreen3(gameScreen.game, gameScreen.getOptionWindow());
        } else if (levelName.equals("Level4")) {
            newGameScreen = new GameScreen4(gameScreen.game, gameScreen.getOptionWindow());
        } else {
            newGameScreen = null;
        }

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gameScreen.game.setScreen(newGameScreen);

                Level level = newGameScreen.getWorldController().level;

                loadPlayer(level, saveData);
                EnergyProducedBar.instance.read(null, saveData);
                EnergyUsedBar.instance.read(null, saveData);
                BatteryBar.instance.read(null, saveData);
                loadCitizens(level, saveData);
                loadItems(level, saveData);
                loadLinks(level, saveData);
                loadEnemies(level, saveData);
            }
        });
    }

    private void loadEnemies(Level level, JsonValue saveData) {
        JsonValue enemies = saveData.get("enemies");
        if (enemies.isArray()) {
            level.enemies.clear();
            for (int i = 0; i < enemies.size; i++) {
                Enemy enemy = EnemySpawner.valueOf(enemies.get(i).getString("type")).spawn();
                enemy.setPlayer(level.player);
                enemy.init(level.mapLayer);
                enemy.read(null, enemies.get(i));
                level.enemies.add(enemy);
            }
        }
    }

    private void loadLinks(Level level, JsonValue saveData) {
        JsonValue links = saveData.get("links");
        if (links.isArray()) {
            level.links.clear();
            for (int i = 0; i < links.size; i++) {
                Link link = new Link();
                link.read(null, links.get(i));
                link.init(level.mapLayer);
                level.links.add(link);
            }
        }
    }

    private void loadCitizens(Level level, JsonValue saveData) {
        JsonValue citizens = saveData.get("citizens");
        if (citizens.isArray()) {
            for (int i = 0; i < citizens.size; i++) {
                level.citizens.get(i).read(null, citizens.get(i));
            }
        }
    }


    private void loadItems(Level level, JsonValue saveData) {
        JsonValue items = saveData.get("items");
        if (items.isArray()) {
            for (int i = 0; i < items.size; i++) {
                level.items.get(i).read(null, items.get(i));
            }
        }
    }

    private void loadPlayer(Level level, JsonValue saveData) {
        level.player.read(null, saveData);
    }
}
