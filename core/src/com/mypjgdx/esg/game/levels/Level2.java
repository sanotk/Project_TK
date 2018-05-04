package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.mypjgdx.esg.game.Assets;

public class Level2 extends Level {

    public Level2(LevelGenerator levelGenerator) {
        super(levelGenerator);

        citizens.get(0).setGoalItem(items.get(3));
        citizens.get(1).setGoalItem(items.get(2));
        citizens.get(2).setGoalItem(items.get(5));
        citizens.get(3).setGoalItem(items.get(8));
        citizens.get(4).setGoalItem(items.get(9));
        citizens.get(5).setGoalItem(items.get(1));

        items.get(1).setEnergyBurn(500);
        items.get(2).setEnergyBurn(100);
        items.get(3).setEnergyBurn(100);
        items.get(4).setEnergyBurn(100);
        items.get(5).setEnergyBurn(500);
        items.get(6).setEnergyBurn(100);
        items.get(7).setEnergyBurn(100);
        items.get(8).setEnergyBurn(100);
        items.get(9).setEnergyBurn(100);
    }

    @Override
    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {
        batch.begin();
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batch.draw(lightFbo.getColorBufferTexture(),
                camera.position.x - camera.viewportWidth * camera.zoom / 2,
                camera.position.y - camera.viewportHeight * camera.zoom / 2,
                0, 0,
                lightFbo.getColorBufferTexture().getWidth(), lightFbo.getColorBufferTexture().getHeight(),
                1 * camera.zoom, 1 * camera.zoom,
                0,
                0, 0,
                lightFbo.getColorBufferTexture().getWidth(), lightFbo.getColorBufferTexture().getHeight(),
                false, true);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.end();
    }

    @Override
    public void createFbo(SpriteBatch batch, FrameBuffer lightFbo) {
        lightFbo.begin();

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(1, 1, 1, 1);
        batch.draw(Assets.instance.light,
                player.getPositionX() + player.origin.x
                        - Assets.instance.light.getWidth() / 2f,
                player.getPositionY() + player.origin.y
                        - Assets.instance.light.getHeight() / 2f);
        batch.draw(Assets.instance.light,
                items.get(0).p_x + items.get(0).origin.x
                        - Assets.instance.light.getWidth() / 2f,
                items.get(0).p_y + items.get(0).origin.y
                        - Assets.instance.light.getHeight() / 2f);
        batch.end();

        FrameBuffer.unbind();
    }
}
