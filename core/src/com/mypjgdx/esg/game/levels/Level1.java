package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Pepo;
import com.mypjgdx.esg.game.objects.characters.PepoKnight;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.*;
import com.mypjgdx.esg.game.objects.weapons.NormalBow;
import com.mypjgdx.esg.game.objects.weapons.NormalSword;

import java.util.ArrayList;

public class Level1 extends Level {

    public Level1() {
        map = Assets.instance.map1;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(mapLayer, 1000, 100);

        items.add(new SolarCell(mapLayer, player));
        items.add(new Inverter(mapLayer, player));
        items.add(new Battery(mapLayer, player));
        items.add(new Charge(mapLayer, player));
        items.add(new Door(mapLayer,player));
        hasSolarCell = false;

        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));
        enemies.add(new PepoKnight(mapLayer, player));

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
