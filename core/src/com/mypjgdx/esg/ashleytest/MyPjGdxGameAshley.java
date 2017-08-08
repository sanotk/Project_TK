package com.mypjgdx.esg.ashleytest;

import com.badlogic.gdx.Game;
import com.mypjgdx.esg.ashleytest.screens.GameScreen;
import com.mypjgdx.esg.game.Assets;

/**
 *
 * Created by Bill on 7/8/2560.
 */
public class MyPjGdxGameAshley extends Game {

    @Override
    public void create() {
        Assets.instance.init();
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }
}
