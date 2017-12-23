package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.game.levels.Level1;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;

import java.util.Random;

public class GameScreen extends AbstractGameScreen {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    SpriteBatch batch;
    public Texture bg;

    private Stage stage;
    private Skin skin;

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ

    private Label textBullet;
    private Label textBeam;
    private Label textTrap;
    private Label textTime;
    private Label energyLevel;
    private Label energyLevel2;
    private Label energyLevel3;

    public boolean status_finish =false;
    //private Label sunleft;
    //private Label sunright

    public enum solarcellState {
        StoC,
        StoB,
        StoI,
        StoD
    }

    public enum chargecontrollercState {
        CtoS,
        CtoB,
        CtoI,
        CtoD
    }

    public enum batteryState {
        BtoS,
        BtoC,
        BtoI,
        BtoD
    }

    public enum inverterState {
        ItoS,
        ItoC,
        ItoB,
        ItoD
    }

    public solarcellState solarState = null;
    public chargecontrollercState ccState = null;
    public batteryState batState = null;
    public inverterState inverState = null;

    private TextButton buttonStoC;
    private TextButton buttonStoB;
    private TextButton buttonStoI;
    private TextButton buttonStoD;

    private TextButton buttonCtoS;
    private TextButton buttonCtoB;
    private TextButton buttonCtoI;
    private TextButton buttonCtoD;

    private TextButton buttonItoS;
    private TextButton buttonItoC;
    private TextButton buttonItoB;
    private TextButton buttonItoD;

    private TextButton buttonBtoS;
    private TextButton buttonBtoC;
    private TextButton buttonBtoI;
    private TextButton buttonBtoD;

    private boolean animation_status = false;

    public GameScreen(Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");

        textBullet = new Label("Punch Max : " ,skin);
        textBullet.setColor(1, 1, 1, 1);
        textBullet.setFontScale(1f,1f);
        textBullet.setPosition(50, 550);

        textBeam = new Label("Z-Punch Max : " ,skin);
        textBeam.setColor(1, 1, 1, 1);
        textBeam.setFontScale(1.f,1.f);
        textBeam.setPosition(200, 550);

        textTrap = new Label("Trap Max : " ,skin);
        textTrap.setColor(1, 1, 1, 1);
        textTrap.setFontScale(1f,1f);
        textTrap.setPosition(350, 550);

        textTime = new Label("Time : " ,skin);
        textTime.setColor(1, 1, 1, 1);
        textTime.setFontScale(1f,1f);
        textTime.setPosition(450, 500);

        energyLevel = new Label("Energy : ", skin);
        energyLevel.setColor(1, 1, 1, 1);
        energyLevel.setFontScale(1,1f);
        energyLevel.setPosition(500, 550);

        energyLevel2 = new Label("Product Energy : ", skin);
        energyLevel2.setColor(1, 1, 1, 1);
        energyLevel2.setFontScale(1,1f);
        energyLevel2.setPosition(650, 550);

        energyLevel3 = new Label("Battery : ", skin);
        energyLevel3.setColor(1, 1, 1, 1);
        energyLevel3.setFontScale(1,1f);
        energyLevel3.setPosition(800, 550);

        stage.addActor(textBullet);
        stage.addActor(textBeam);
        stage.addActor(textTrap);
        stage.addActor(textTime);
        stage.addActor(energyLevel);
        stage.addActor(energyLevel2);
        stage.addActor(energyLevel3);

        int btn_w = 200;
        int btn_h = 50;

        buttonStoC = new TextButton("link to charge controler", skin);
        buttonStoC.setWidth(btn_w);
        buttonStoC.setHeight(btn_h);
        buttonStoC.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 450);

        buttonStoB = new TextButton("link to battery", skin);
        buttonStoB.setWidth(btn_w);
        buttonStoB.setHeight(btn_h);
        buttonStoB.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 380);

        buttonStoI = new TextButton("link to inverter", skin);
        buttonStoI.setWidth(btn_w);
        buttonStoI.setHeight(btn_h);
        buttonStoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 310);

        buttonStoD = new TextButton("link to door", skin);
        buttonStoD.setWidth(btn_w);
        buttonStoD.setHeight(btn_h);
        buttonStoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        stage.addActor(buttonStoC);
        stage.addActor(buttonStoB);
        stage.addActor(buttonStoI);
        stage.addActor(buttonStoD);

        buttonStoC.setVisible(false);
        buttonStoB.setVisible(false);
        buttonStoI.setVisible(false);
        buttonStoD.setVisible(false);

        buttonStoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarcellState.StoC;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonStoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarcellState.StoB;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonStoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarcellState.StoI;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonStoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarcellState.StoD;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoS = new TextButton("link to solarcell", skin);
        buttonItoS.setWidth(btn_w);
        buttonItoS.setHeight(btn_h);
        buttonItoS.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 350);

        buttonItoC = new TextButton("link to charge controler", skin);
        buttonItoC.setWidth(btn_w);
        buttonItoC.setHeight(btn_h);
        buttonItoC.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 280);

        buttonItoB = new TextButton("link to battery", skin);
        buttonItoB.setWidth(btn_w);
        buttonItoB.setHeight(btn_h);
        buttonItoB.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 210);

        buttonItoD = new TextButton("link to door", skin);
        buttonItoD.setWidth(btn_w);
        buttonItoD.setHeight(btn_h);
        buttonItoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        stage.addActor(buttonItoS);
        stage.addActor(buttonItoC);
        stage.addActor(buttonItoB);
        stage.addActor(buttonItoD);

        buttonItoS.setVisible(false);
        buttonItoC.setVisible(false);
        buttonItoB.setVisible(false);
        buttonItoD.setVisible(false);

        buttonItoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverState = inverterState.ItoS;
                System.out.print(inverState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverState = inverterState.ItoC;
                System.out.print(inverState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverState = inverterState.ItoB;
                System.out.print(inverState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverState = inverterState.ItoD;
                System.out.print(inverState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoS = new TextButton("link to solarcell", skin);
        buttonCtoS.setWidth(btn_w);
        buttonCtoS.setHeight(btn_h);
        buttonCtoS.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 350);

        buttonCtoB = new TextButton("link to battery", skin);
        buttonCtoB.setWidth(btn_w);
        buttonCtoB.setHeight(btn_h);
        buttonCtoB.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 280);

        buttonCtoI = new TextButton("link to inverter", skin);
        buttonCtoI.setWidth(btn_w);
        buttonCtoI.setHeight(btn_h);
        buttonCtoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 210);

        buttonCtoD = new TextButton("link to door", skin);
        buttonCtoD.setWidth(btn_w);
        buttonCtoD.setHeight(btn_h);
        buttonCtoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        stage.addActor(buttonCtoS);
        stage.addActor(buttonCtoB);
        stage.addActor(buttonCtoI);
        stage.addActor(buttonCtoD);

        buttonCtoS.setVisible(false);
        buttonCtoB.setVisible(false);
        buttonCtoI.setVisible(false);
        buttonCtoD.setVisible(false);

        buttonCtoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ccState = chargecontrollercState.CtoS;
                System.out.print(ccState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ccState = chargecontrollercState.CtoB;
                System.out.print(ccState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ccState = chargecontrollercState.CtoI;
                System.out.print(ccState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ccState = chargecontrollercState.CtoD;
                System.out.print(ccState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoS = new TextButton("link to solarcell", skin);
        buttonBtoS.setWidth(btn_w);
        buttonBtoS.setHeight(btn_h);
        buttonBtoS.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 350);

        buttonBtoC = new TextButton("link to charge controler", skin);
        buttonBtoC.setWidth(btn_w);
        buttonBtoC.setHeight(btn_h);
        buttonBtoC.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 280);

        buttonBtoI = new TextButton("link to inverter", skin);
        buttonBtoI.setWidth(btn_w);
        buttonBtoI.setHeight(btn_h);
        buttonBtoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 210);

        buttonBtoD = new TextButton("link to door", skin);
        buttonBtoD.setWidth(btn_w);
        buttonBtoD.setHeight(btn_h);
        buttonBtoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        stage.addActor(buttonBtoS);
        stage.addActor(buttonBtoC);
        stage.addActor(buttonBtoI);
        stage.addActor(buttonBtoD);

        buttonBtoS.setVisible(false);
        buttonBtoC.setVisible(false);
        buttonBtoI.setVisible(false);
        buttonBtoD.setVisible(false);

        buttonBtoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batState = batteryState.BtoS;
                System.out.print(batState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batState = batteryState.BtoC;
                System.out.print(batState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batState = batteryState.BtoI;
                System.out.print(batState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batState = batteryState.BtoD;
                System.out.print(batState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        textBullet.setText(String.format("Punch Max : %d", worldController.level.player.bulletCount));
        textBeam.setText(String.format("Z-Punch Max : %d", worldController.level.player.beamCount));
        textTrap.setText(String.format("Trap Max : %d", worldController.level.player.trapCount));
        textTime.setText(String.format("Time limit : %d", worldController.level.player.timeCount));
        energyLevel.setText(String.format("Energy : %d", (int)worldController.level.energyTube.energy));
        //
        // sunleft.setText(String.format("Sun Left"));

        if (Gdx.input.isKeyJustPressed(Keys.M)) {
            game.setScreen(new MenuScreen(game));
            return;
        }

        if (!worldController.level.player.isAlive()) {
            game.setScreen(new GameOverScreen(game));
            return;
        }

        if (worldController.level.player.timeCount <= 0) {
            game.setScreen(new GameOverScreen(game));
            return;
        }

        if((worldController.level.player.status_solarcell==true)&&(worldController.level.player.status_find == true)){
            buttonStoC.setVisible(true);
            buttonStoB.setVisible(true);
            buttonStoI.setVisible(true);
            buttonStoD.setVisible(true);
        }else{
            buttonStoC.setVisible(false);
            buttonStoB.setVisible(false);
            buttonStoI.setVisible(false);
            buttonStoD.setVisible(false);
        }

        if((worldController.level.player.status_battery==true)&&(worldController.level.player.status_find == true)){
            buttonBtoC.setVisible(true);
            buttonBtoS.setVisible(true);
            buttonBtoI.setVisible(true);
            buttonBtoD.setVisible(true);
        }else{
            buttonBtoC.setVisible(false);
            buttonBtoS.setVisible(false);
            buttonBtoI.setVisible(false);
            buttonBtoD.setVisible(false);
        }

        if((worldController.level.player.status_inverter==true)&&(worldController.level.player.status_find == true)){
            buttonItoC.setVisible(true);
            buttonItoB.setVisible(true);
            buttonItoS.setVisible(true);
            buttonItoD.setVisible(true);
        }else{
            buttonItoC.setVisible(false);
            buttonItoB.setVisible(false);
            buttonItoS.setVisible(false);
            buttonItoD.setVisible(false);
        }

        if((worldController.level.player.status_ccontroller==true)&&(worldController.level.player.status_find == true)){
            buttonCtoS.setVisible(true);
            buttonCtoB.setVisible(true);
            buttonCtoI.setVisible(true);
            buttonCtoD.setVisible(true);
        }else{
            buttonCtoS.setVisible(false);
            buttonCtoB.setVisible(false);
            buttonCtoI.setVisible(false);
            buttonCtoD.setVisible(false);
        }

        if((solarState == solarcellState.StoC)&&(ccState == chargecontrollercState.CtoB)&&(batState == batteryState.BtoC)
                &&(inverState==inverterState.ItoC)){
            status_finish =true;
        }

        Player player = worldController.level.player;
        boolean noItem = !player.status_battery
                && !player.status_ccontroller
                && !player.status_inverter
                && !player.status_solarcell;

            if (player.status_find && noItem) {
            player.status_find = false;
            player.status_windows_link = false;
        }

        boolean stageOneIsFinish = solarState==solarcellState.StoC
                && ccState==chargecontrollercState.CtoI
                && batState==batteryState.BtoC
                && inverState==inverterState.ItoD;
        if ((stageOneIsFinish)&&(!animation_status)){
            worldController.level.items.get(0).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(0).resetAnimation();
            worldController.level.items.get(1).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(1).resetAnimation();
            worldController.level.items.get(2).state = Item.ItemState.ON;
            worldController.level.items.get(2).resetAnimation();
            worldController.level.items.get(3).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(3).resetAnimation();
            worldController.level.items.get(4).state = Item.ItemState.ON;
            worldController.level.items.get(4).resetAnimation();
            animation_status = true;
            worldController.level.energyTube.energy += 100;
        }

        if((worldController.level.items.get(4).state == Item.ItemState.ON)&&(player.status_door==true)){
            game.setScreen(new GameScreen2(game));
        }

        for(int i = 0; i < worldController.level.enemies.size(); i++){
            Enemy enemy = worldController.level.enemies.get(i);
            if (enemy.dead && !enemy.count){
                worldController.level.energyTube.energy += 2;
                enemy.count = true;
            }
        }

        worldController.update(Gdx.graphics.getDeltaTime()); //อัพเดท Game World
        worldRenderer.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        worldController = new WorldController(new Level(new Level1()));
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
    }

    @Override
    public void pause() {}

}
