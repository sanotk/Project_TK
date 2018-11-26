package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.*;
import com.mypjgdx.esg.game.objects.items.Door;
import com.mypjgdx.esg.game.objects.items.Item;

public class Level1 extends Level {

    public Citizen citizen1;
    public Citizen citizen2;
    public Citizen citizen3;
    public Citizen citizen4;
    public Citizen citizen5;
    public Citizen citizen6;

    public Item door;

    public Level1() {
        name = "Level1";

        map = Assets.instance.map1;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(mapLayer, 100, 100);


        door = new Door(mapLayer, player);

        items.add(door);

        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));

        citizen1 = new Citizen1(mapLayer, player);
        citizen2 = new Citizen2(mapLayer, player);
        citizen3 = new Citizen3(mapLayer, player);
        citizen4 = new Citizen4(mapLayer, player);
        citizen5 = new Citizen5(mapLayer, player);
        citizen6 = new Citizen6(mapLayer, player);

        citizens.add(citizen1);
        citizens.add(citizen2);
        citizens.add(citizen3);
        citizens.add(citizen4);
        citizens.add(citizen5);
        citizens.add(citizen6);

        citizen1.setGoalItem(door);
        citizen2.setGoalItem(door);
        citizen3.setGoalItem(door);
        citizen4.setGoalItem(door);
        citizen5.setGoalItem(door);
        citizen6.setGoalItem(door);

//        for (Enemy enemy : enemies) {
//            enemy.setDroppedItems(droppedItems);
//        }

    }

    @Override
    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {
        if (player.isSwitch) {
            batch.begin();
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
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
        } else if (player.focusCamera.getFocus1()) {
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
        } else {
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
    }

    @Override
    public void createFbo(SpriteBatch batch, FrameBuffer lightFbo) {
        lightFbo.begin();
        Color color = Color.valueOf("#20e8ff");
        Gdx.gl.glClearColor(color.r, color.g, color.b, 0.15f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
            batch.draw(Assets.instance.light,
                    player.getPositionX() + player.origin.x
                            - Assets.instance.light.getWidth() / 2f,
                    player.getPositionY() + player.origin.y
                            - Assets.instance.light.getHeight() / 2f);
        batch.end();
        FrameBuffer.unbind();
    }
}
