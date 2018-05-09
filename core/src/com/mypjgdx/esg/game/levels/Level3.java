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

public class Level3 extends Level {

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


    public Level3() {
        map = Assets.instance.map3;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(mapLayer, 100, 1000);

        switchItem = new Switch(mapLayer, player);
        television = new Television(mapLayer, player);
        microwave = new Microwave(mapLayer, player);
        waterPump = new Waterpump(mapLayer, player);
        airConditioner = new AirConditioner(mapLayer, player);
        computer = new Computer(mapLayer, player);
        fan1 = new Fan(mapLayer, player);
        fan2 = new Fan(mapLayer, player, 750, 850);
        refrigerator = new Refrigerator(mapLayer, player);
        riceCooker = new RiceCooker(mapLayer, player);
        gate = new Gate(mapLayer,player);

        items.add(switchItem);
        items.add(television);
        items.add(microwave);
        items.add(waterPump);
        items.add(airConditioner);
        items.add(computer);
        items.add(fan1);
        items.add(fan2);
        items.add(refrigerator);
        items.add(riceCooker);
        items.add(gate);


        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
        enemies.add(new Pepo(mapLayer, player));
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

        citizen1.setGoalItem(airConditioner);
        citizen2.setGoalItem(microwave);
        citizen3.setGoalItem(computer);
        citizen4.setGoalItem(refrigerator);
        citizen5.setGoalItem(riceCooker);
        citizen6.setGoalItem(television);

        television.setEnergyBurn(100);
        microwave.setEnergyBurn(800);
        waterPump.setEnergyBurn(250);
        airConditioner.setEnergyBurn(1300);
        computer.setEnergyBurn(500);
        fan1.setEnergyBurn(60);
        fan2.setEnergyBurn(60);
        refrigerator.setEnergyBurn(150);
        riceCooker.setEnergyBurn(800);
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
