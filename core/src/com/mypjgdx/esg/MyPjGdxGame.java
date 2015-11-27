package com.mypjgdx.esg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;

public class MyPjGdxGame extends ApplicationAdapter  {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    @Override
    public void create () {
        Assets.instance.init();

        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);

        Assets.instance.music.play();
        Assets.instance.music.setLooping(true);
    }

    @Override
    public void resize(int width, int height) { //ปรับขนาด viewport ให้เหมาะสมกับขนาดจอที่เปลี่ยนไป
        worldRenderer.resize(width, height);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1); //เคลียหน้าจอ
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //เคลียหน้าจอ

        worldController.update(Gdx.graphics.getDeltaTime()); //อัพเดท Game World
        worldRenderer.render();
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
        Assets.instance.dispose();
    }

}
