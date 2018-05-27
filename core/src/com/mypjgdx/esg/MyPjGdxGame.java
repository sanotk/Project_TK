package com.mypjgdx.esg;

import com.badlogic.gdx.Game;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.screens.MenuScreen;
import com.mypjgdx.esg.utils.SettingManager;


public class MyPjGdxGame extends Game  {

    @Override
    public void create () {
        Assets.instance.init();
        SettingManager.instance.load();

        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose () {
        Assets.instance.dispose();
    }
}
