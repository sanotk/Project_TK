package com.mypjgdx.esg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mypjgdx.esg.screens.*;

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
            throw new Error("Can't load save file: Wrong level name!");
        }

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gameScreen.game.setScreen(newGameScreen);
                newGameScreen.getWorldController().level.read(null, saveData);
                newGameScreen.read(null, saveData);
            }
        });
    }

}
