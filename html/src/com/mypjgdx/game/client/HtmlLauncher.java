package com.mypjgdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mypjgdx.game.MyPjGdxGame;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration(1280, 720);
    }

    @Override
    public ApplicationListener getApplicationListener () {
        return new MyPjGdxGame();
    }
}