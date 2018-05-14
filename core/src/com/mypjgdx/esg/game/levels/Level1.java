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

    public Item solarCell;
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
        map = Assets.instance.map1;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(mapLayer, 1500, 1000);

        solarCell = new SolarCell(mapLayer, player);
        inverter = new Inverter(mapLayer, player);
        battery = new Battery(mapLayer, player);
        charge = new Charge(mapLayer, player);
        door = new Door(mapLayer, player);

        items.add(solarCell);
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

        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        FrameBuffer.unbind();
    }
}
