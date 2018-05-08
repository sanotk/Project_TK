package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.mypjgdx.esg.collision.TiledCollisionProperty;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.EnemyBuilder;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.items.ItemBuilder;
import com.mypjgdx.esg.screens.GameOverScreen;
import com.mypjgdx.esg.ui.ChartWindow;
import com.mypjgdx.esg.ui.EnergyBar;
import com.mypjgdx.esg.ui.Hud;

public class Level1 extends Level {

    private float remainingTime = 300;

    private Hud hud;
    private boolean doorOpened;
    private ChartWindow chartWindow;

    public Level1() {
        this.map =  Assets.instance.map1;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        addPlayer(new Player(), 1000, 100);

        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO.getNew());
        addEnemy(EnemyBuilder.PEPO_KNIGHT.getNew());
        addEnemy(EnemyBuilder.PEPO_KNIGHT.getNew());

        Item solarCell = ItemBuilder.SOLAR_CELL.getNew();
        Item inverter = ItemBuilder.INVERTER.getNew();
        Item battery = ItemBuilder.BATTERY.getNew();
        Item chargeController = ItemBuilder.CONTROLLER.getNew();
        Item door = ItemBuilder.DOOR.getNew();

        solarCell.setCorrectLinkingItems(chargeController);
        inverter.setCorrectLinkingItems(chargeController, door);
        battery.setCorrectLinkingItems(chargeController);
        chargeController.setCorrectLinkingItems(solarCell, battery, inverter);
        door.setCorrectLinkingItems(inverter);

        addItem(solarCell, 1500, 1100);
        addItem(inverter, 1700, 1250);
        addItem(battery, 1700, 950);
        addItem(chargeController, 1700, 1100);
        addItem(door, 1850, 1250);
    }

    @Override
    public void onEnter() {
        final Stage stage = gameScreen.getStage();

        hud = new Hud();
        hud.setPosition(stage.getWidth() / 2, stage.getHeight() - 30, Align.center);

        chartWindow = new ChartWindow();

        stage.addActor(hud);
    }

    @Override
    public void onExit() {
        hud.remove();
        chartWindow.remove();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        remainingTime -= deltaTime;
        hud.update(player.arrowCount, player.swordWaveCount, player.trapCount, remainingTime, EnergyBar.instance.energy);

        if (isCorrectLinked() && !doorOpened) {
            for (Item item : items) {
                item.turnOn();
            }
            doorOpened = true;
            EnergyBar.instance.energy += 100;
            chartWindow.show(gameScreen.getStage());
        }

        if (doorOpened && player.isCollide(TiledCollisionProperty.DOOR)) {
            gameScreen.changeLevel(new Level2());
        }

        if (player.isDead() || remainingTime <= 0) {
            final Game game = gameScreen.getGame();
            game.setScreen(new GameOverScreen(game));
        }
    }

    private boolean isCorrectLinked() {
        for (int i = 0; i < items.size; i++) {
            if (!items.get(i).isCorrectLinked())
                return false;
        }
        return true;
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
