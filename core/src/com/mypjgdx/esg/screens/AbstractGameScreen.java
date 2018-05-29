package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.game.WorldController;

public abstract class AbstractGameScreen implements Screen, Json.Serializable {

    public final Game game;

    public AbstractGameScreen (Game game) {
        this.game = game;
    }

    @Override
    public void pause () {}

    @Override
    public void resume () {}

    @Override
    public void dispose () {}

    @Override
    public void write(Json json) {}

    @Override
    public void read(Json json, JsonValue jsonData) {}

    public WorldController getWorldController() {
        return null;
    }

    public Window getOptionWindow() {
        return null;
    }
}
