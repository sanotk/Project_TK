package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class AbstractGameScreen implements Screen {

    protected final Game game;

    AbstractGameScreen (Game game) {
        this.game = game;
    }

    @Override
    public void show () {}

    @Override
    public void hide () {}

    @Override
    public void pause () {}

    @Override
    public void resume () {}

    @Override
    public void dispose () {}

    public Game getGame() {
        return game;
    }
}
