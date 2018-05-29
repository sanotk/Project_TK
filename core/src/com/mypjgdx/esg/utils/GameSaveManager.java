package com.mypjgdx.esg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mypjgdx.esg.screens.*;

public class GameSaveManager implements Json.Serializable {

    public static final GameSaveManager instance = new GameSaveManager();

    public AbstractGameScreen gameScreen;

    private GameSaveManager() {
    }

    public void save() {
        FileHandle file = Gdx.files.absolute("C:\\Data\\save.txt");
        Json json = new Json(JsonWriter.OutputType.json);
        json.toJson(this, file);
        System.out.print(json.prettyPrint(this));
    }

    public void load() {
        FileHandle file = Gdx.files.absolute("C:\\Data\\save.txt");
        read(null, new JsonReader().parse(file));
    }

    @Override
    public void write(Json json) {
        json.writeValue("level", gameScreen.getWorldController().level);
        json.writeValue("gameScreen", gameScreen);
    }

    @Override
    public void read(Json json, final JsonValue jsonData) {
        String levelName = jsonData.get("level").get("name").asString();
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
                newGameScreen.getWorldController().level.read(null, jsonData.get("level"));
                newGameScreen.read(null, jsonData.get("gameScreen"));
            }
        });
    }
}
