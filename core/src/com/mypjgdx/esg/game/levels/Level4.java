package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.Water;
import com.mypjgdx.esg.game.objects.characters.*;
import com.mypjgdx.esg.game.objects.items.*;

public class Level4 extends Level {

    public Item switchItem;
    public Item pollutionControll;
    public Item gate;
    public Item hydroPower;

    public Level4() {
        name = "Level4";

        map = Assets.instance.map4;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(mapLayer, 600, 200);

        switchItem = new Switch(mapLayer, player);
        pollutionControll = new PollutionController(mapLayer,player);
        gate = new Gate(mapLayer,player);
        hydroPower = new HydroPower(mapLayer,player);

        items.add(switchItem);
        items.add(pollutionControll);
        items.add(gate);
        items.add(hydroPower);
        pollutionControll.setEnergyBurn(10900);
        enemies.add(new PepoKnight(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));
        enemies.add(new PepoDevil(mapLayer, player));
        for (Enemy enemy : enemies) {
            enemy.setDroppedItems(droppedItems);
        }

        Citizen citizen1 = new Citizen1(mapLayer, player, 600, 200);
        Citizen citizen2 = new Citizen2(mapLayer, player, 600, 200);
        Citizen citizen3 = new Citizen3(mapLayer, player, 600, 200);
        Citizen citizen4 = new Citizen4(mapLayer, player, 600, 200);
        Citizen citizen5 = new Citizen5(mapLayer, player, 600, 200);
        Citizen citizen6 = new Citizen6(mapLayer, player, 600, 200);

        citizens.add(citizen1);
        citizens.add(citizen2);
        citizens.add(citizen3);
        citizens.add(citizen4);
        citizens.add(citizen5);
        citizens.add(citizen6);

        addWater(100, 450, 3, 12, 1f);
        addWater(100, 200, 3, 2, 1f);
    }

    @Override
    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {
        if(player.isSwitch){
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
        }
        else {
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
        Color color = Color.valueOf("#f44242");
        if (player.isSwitch) {
            Gdx.gl.glClearColor(color.r, color.g, color.b, 0.15f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        } else {
            Gdx.gl.glClearColor(0.1f, 0.05f, 0.05f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.setColor(1, 1, 1, 1);
            batch.draw(Assets.instance.light,
                    player.getPositionX() + player.origin.x
                            - Assets.instance.light.getWidth() / 2f,
                    player.getPositionY() + player.origin.y
                            - Assets.instance.light.getHeight() / 2f);
            batch.draw(Assets.instance.light,
                    switchItem.p_x + switchItem.origin.x
                            - Assets.instance.light.getWidth() / 2f,
                    switchItem.p_y + switchItem.origin.y
                            - Assets.instance.light.getHeight() / 2f);
            batch.end();
        }
        FrameBuffer.unbind();
    }

    private void addWater(float startX, float startY, int tileCountX, int tileCountY, float flowSpeed) {
        for (int i = 0; i < tileCountX; i++) {
            for (int j = 0; j < tileCountY; j++) {
                Water water = new Water();
                water.setPosition(startX + i * 50,startY + j * 50);
                water.setFlowSpeed(flowSpeed);
                objects.add(water);
            }
        }
    }
}
