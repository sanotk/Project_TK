package com.mypjgdx.esg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.Map;
import com.mypjgdx.esg.screens.MenuScreen;
import com.mypjgdx.esg.utils.Pathfinding;

public class MyPjGdxGame extends Game  {

    @Override
    public void create () {
        Assets.instance.init();
        Assets.instance.music.play();
        Assets.instance.music.setPan(0.0f, 0.2f);
        Assets.instance.music.setLooping(true);

        setScreen(new MenuScreen(this));

        Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
        Pathfinding pfd = new Pathfinding(Map.MAP_01.getMapLayer());
        pfd.setStart(new Vector2(100,100));
        pfd.setGoal(new Vector2(200,200));

    }



    @Override
    public void dispose () {
        Assets.instance.dispose();
    }

    // comment
    // comment2
}
