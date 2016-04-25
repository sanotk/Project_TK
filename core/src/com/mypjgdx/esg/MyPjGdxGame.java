package com.mypjgdx.esg;

import com.badlogic.gdx.Game;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.screens.MenuScreen;



public class MyPjGdxGame extends Game  {

    @Override
    public void create () {
        Assets.instance.init();
        Assets.instance.music.play();
        Assets.instance.music.setPan(0.0f, 0.2f);
        Assets.instance.music.setLooping(true);

        setScreen(new MenuScreen(this));
    }



    @Override
    public void dispose () {
        Assets.instance.dispose();
    }

    // comment
    // comment2
}
