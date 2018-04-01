package com.mypjgdx.esg;

import com.badlogic.gdx.Game;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.screens.MenuScreen;



public class MyPjGdxGame extends Game  {

    @Override
    public void create () {

        Assets.instance.init();

        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose () {
        Assets.instance.dispose();
    }

    // comment
    // comment2
}
