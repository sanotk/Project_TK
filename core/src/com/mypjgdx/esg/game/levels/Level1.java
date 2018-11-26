package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Citizen;
import com.mypjgdx.esg.game.objects.characters.Pepo;
import com.mypjgdx.esg.game.objects.characters.PepoKnight;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Gate;
import com.mypjgdx.esg.game.objects.items.Item;

public class Level1 extends Level {

    public Citizen citizen1;
    public Citizen citizen2;
    public Citizen citizen3;
    public Citizen citizen4;
    public Citizen citizen5;
    public Citizen citizen6;
    public Item switchItem;
    public Item television;
    public Item microwave;
    public Item waterPump;
    public Item airConditioner;
    public Item computer;
    public Item fan1;
    public Item fan2;
    public Item refrigerator;
    public Item riceCooker;
    public Item gate;
    public Item lamp1;
    public Item lamp2;
    public Item lamp3;
    public Item lamp4;
    public Item lamp5;
    public Item lamp6;
    public Item lamp7;
    public Item lamp8;
    public Item lamp9;

    public Level1() {
        name = "Level1";

        map = Assets.instance.map1;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(mapLayer, 100, 1000);


        gate = new Gate(mapLayer, player);

        items.add(gate);

        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));

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
