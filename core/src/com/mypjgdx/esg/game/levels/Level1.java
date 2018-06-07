package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.*;
import com.mypjgdx.esg.game.objects.items.*;
import com.mypjgdx.esg.game.objects.weapons.NormalBow;
import com.mypjgdx.esg.game.objects.weapons.NormalSword;

public class Level1 extends Level {

    public Item solarCell1;
    public Item solarCell2;
    public Item solarCell3;
    public Item solarCell4;
    public Item solarCell5;
    public Item solarCell6;
    public Item solarCell7;
    public Item solarCell8;
    public Item solarCell9;
    public Item inverter;
    public Item battery;
    public Item charge;
    public Item door;

    public Citizen citizen1;
    public Citizen citizen2;
    public Citizen citizen3;
    public Citizen citizen4;
    public Citizen citizen5;
    public Citizen citizen6;

    public Level1() {
        name = "Level1";

        map = Assets.instance.map1;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(mapLayer, 100, 100);

        solarCell1 = new SolarCell(mapLayer, player);
        solarCell2 = new SolarCell(mapLayer, player ,1500f ,1145f);
        solarCell3 = new SolarCell(mapLayer, player ,1500f ,1195f);
        solarCell4 = new SolarCell(mapLayer, player ,1400f ,1095f);
        solarCell5 = new SolarCell(mapLayer, player ,1400f ,1145f);
        solarCell6 = new SolarCell(mapLayer, player ,1400f ,1195f);
        solarCell7 = new SolarCell(mapLayer, player ,1300f ,1095f);
        solarCell8 = new SolarCell(mapLayer, player ,1300f ,1145f);
        solarCell9 = new SolarCell(mapLayer, player ,1300f ,1195f);
        inverter = new Inverter(mapLayer, player);
        battery = new Battery(mapLayer, player);
        charge = new Charge(mapLayer, player);
        door = new Door(mapLayer, player);

        items.add(solarCell1);
        items.add(solarCell2);
        items.add(solarCell3);
        items.add(solarCell4);
        items.add(solarCell5);
        items.add(solarCell6);
        items.add(solarCell7);
        items.add(solarCell8);
        items.add(solarCell9);
        items.add(inverter);
        items.add(battery);
        items.add(charge);
        items.add(door);

        hasSolarCell = false;

        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));
        for (Enemy enemy : enemies) {
            enemy.setDroppedItems(droppedItems);
        }

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

        bows.add(new NormalBow(mapLayer, player));
        swords.add(new NormalSword(mapLayer, player));
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
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        FrameBuffer.unbind();
    }
}
