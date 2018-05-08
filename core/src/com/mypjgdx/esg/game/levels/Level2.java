package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.*;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.items.ItemBuilder;
import com.mypjgdx.esg.screens.GameOverScreen;
import com.mypjgdx.esg.ui.EnergyBar;
import com.mypjgdx.esg.ui.Hud;

public class Level2 extends Level {

    private float remainingTime = 300;

    private Hud hud;
    private Item lightSwitch;
    private boolean lightOn;

    public Level2() {
        this.map = Assets.instance.map2;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        addPlayer(new Player(), 100, 1000);

        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO_KNIGHT.getNew());
        addEnemy(EnemyBuilder.PEPO_KNIGHT.getNew());

        lightSwitch = ItemBuilder.SWITCH.getNew();
        Item airConditioner = ItemBuilder.AIR_CONDITIONER.getNew();
        Item computer = ItemBuilder.COMPUTER.getNew();
        Item fan = ItemBuilder.FAN.getNew();
        Item riceCooker = ItemBuilder.RICE_COOKER.getNew();
        Item refrigerator = ItemBuilder.REFRIGERATOR.getNew();

        addItem(lightSwitch, 1100, 1100);
        addItem(ItemBuilder.TV.getNew(), 850, 900);
        addItem(ItemBuilder.MICROWAVE.getNew(), 300, 1050);
        addItem(ItemBuilder.WATER_PUMP.getNew(), 200, 1000);
        addItem(airConditioner, 300, 700);
        addItem(computer, 850, 1050);
        addItem(fan, 950, 800);
        addItem(ItemBuilder.FAN.getNew(), 750, 800);
        addItem(refrigerator, 450, 1000);
        addItem(riceCooker, 350, 1050);

        addCitizen(CitizenBuilder.CITIZEN_1.getNew(), airConditioner);
        addCitizen(CitizenBuilder.CITIZEN_2.getNew(), computer);
        addCitizen(CitizenBuilder.CITIZEN_3.getNew(), fan);
        addCitizen(CitizenBuilder.CITIZEN_4.getNew(), riceCooker);
        addCitizen(CitizenBuilder.CITIZEN_5.getNew(), computer);
        addCitizen(CitizenBuilder.CITIZEN_6.getNew(), refrigerator);
    }

    @Override
    public void onEnter() {
        final Stage stage = gameScreen.getStage();

        hud = new Hud();
        hud.setPosition(stage.getWidth() / 2, stage.getHeight() - 30, Align.center);

        stage.addActor(hud);
    }

    @Override
    public void onExit() {
        hud.remove();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        remainingTime -= deltaTime;
        hud.update(player.arrowCount, player.swordWaveCount, player.trapCount, remainingTime, EnergyBar.instance.energy);

        if (player.isDead() || remainingTime <= 0) {
            final Game game = gameScreen.getGame();
            game.setScreen(new GameOverScreen(game));
        }

        if (lightSwitch.isTriggered() && lightSwitch.getState() == Item.ItemState.OFF) {
            lightSwitch.turnOn();
        }
        if (!lightOn && lightSwitch.getState() == Item.ItemState.ON) {
            lightOn = true;
            EnergyBar.instance.energy += 100;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            for (Citizen citizen : citizens) {
                citizen.getStateMachine().changeState(CitizenState.RUN_TO_ITEM);
            }
        }
    }

    @Override
    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {
        if (lightOn) return;

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
        if (lightOn) return;

        lightFbo.begin();

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(1, 1, 1, 1);
        batch.draw(Assets.instance.light,
                player.getPositionX() + player.origin.x - Assets.instance.light.getWidth() / 2f,
                player.getPositionY() + player.origin.y - Assets.instance.light.getHeight() / 2f);
        batch.draw(Assets.instance.light,
                lightSwitch.getPositionX() + lightSwitch.origin.x - Assets.instance.light.getWidth() / 2f,
                lightSwitch.getPositionY() + lightSwitch.origin.y - Assets.instance.light.getHeight() / 2f);
        batch.end();

        FrameBuffer.unbind();
    }
}
