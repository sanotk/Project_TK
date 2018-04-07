package com.mypjgdx.esg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mypjgdx.esg.game.Assets;

public class ExampleGameAi extends ApplicationAdapter {

    private static final int MOVE_TO_PLAYER = 0;

    SpriteBatch batch;
    MyEnemy enemy;
    MyEnemy enemy2;


    @Override
    public void create() {
        batch = new SpriteBatch();
        enemy = new MyEnemy();
        enemy2 = new MyEnemy();

        Assets.instance.init();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            MessageManager.getInstance().dispatchMessage(1, enemy2, enemy, MOVE_TO_PLAYER, null, false);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            enemy.getStateMachine().changeState(MyEnemyState.WANDER);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            enemy.getStateMachine().changeState(MyEnemyState.RUN_TO_PLAYER);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            enemy.getStateMachine().changeState(MyEnemyState.DIE);
        }

        enemy.update();

        GdxAI.getTimepiece().update(Gdx.graphics.getDeltaTime());
        MessageManager.getInstance().update();
    }


    @Override
    public void dispose() {
        batch.dispose();
        Assets.instance.dispose();
    }
}
