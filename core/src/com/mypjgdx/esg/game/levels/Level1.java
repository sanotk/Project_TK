package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class Level1 extends Level {

    public Level1(LevelGenerator levelGenerator) {
        super(levelGenerator);
    }

    @Override
    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {

        batch.begin();

        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_COLOR);
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

        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        FrameBuffer.unbind();
    }
}
