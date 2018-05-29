package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mypjgdx.esg.game.WorldController;

public abstract class AbstractGameScreen implements Screen, Json.Serializable {

    public final Game game;

    public AbstractGameScreen (Game game) {
        this.game = game;
    }

    public abstract void render (float deltaTime);

    @Override
    public abstract void resize (int width, int height);

    @Override
    public abstract void show ();

    @Override
    public abstract void hide ();

    @Override
    public abstract void pause ();

    @Override
    public void resume () {}

    @Override
    public void dispose () {}

    @Override
    public void write(Json json) {}

    @Override
    public void read(Json json, JsonValue jsonData) {}

    public void load() {}

    public void save() {
        FileHandle file = Gdx.files.absolute("C:\\Data\\save.txt");
        Json json = new Json(JsonWriter.OutputType.json);

        System.out.print(json.prettyPrint(getWorldController().level));
        json.toJson(getWorldController().level, file);
    }

    public WorldController getWorldController() {
        return null;
    }

    public Window getOptionWindow() {
        return null;
    }
}
