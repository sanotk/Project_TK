package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.MusicManager;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level1;
import com.mypjgdx.esg.game.levels.Level1Generator;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.*;

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
    private Label textRule;

    public boolean status_finish =false;
    //private Label sunleft;
    //private Label sunright

    public enum solarcellState {
        StoC,
        StoB,
        StoI,
        StoD,
        CtoB,
        CtoI,
        CtoD,
        BtoI,
        BtoD,
        ItoD
    }

    public solarcellState solarState = null;

    private TextButton buttonStoC;
    private TextButton buttonStoB;
    private TextButton buttonStoI;
    private TextButton buttonStoD;

    private TextButton buttonCtoB;
    private TextButton buttonCtoI;
    private TextButton buttonCtoD;

    private TextButton buttonBtoI;
    private TextButton buttonBtoD;

    private TextButton buttonItoD;

    private Button buttonOption;
    private BitmapFont font;
    private Window optionsWindow;

    private boolean animation_status = false;

    private Button buttonRule;
    private Window ruleWindow;
    private Window chartWindow;

    private boolean windowSolar = false;
    private boolean windowBat = false;
    private boolean windowInver = false;
    private boolean windowCcontroll = false;

    private int trueLink = 0;

    public GameScreen(Game game ,final Window optionsWindow) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = new BitmapFont();

        this.optionsWindow = optionsWindow;



        TextButton.TextButtonStyle buttonToolStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable toolUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_tools"));
        buttonToolStyle.up = toolUp;
        buttonToolStyle.down = toolUp.tint(Color.LIGHT_GRAY);
        buttonOption = new Button(buttonToolStyle);
        buttonOption.setPosition(SCENE_WIDTH - 50, SCENE_HEIGHT - 50);

        TextButton.TextButtonStyle buttonRuleStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable ruleUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_pause"));
        buttonRuleStyle.up = ruleUp;
        buttonRuleStyle.down = ruleUp.tint(Color.LIGHT_GRAY);
        buttonRule = new Button(buttonRuleStyle);
        buttonRule.setPosition(SCENE_WIDTH - 100, SCENE_HEIGHT - 50);

        ruleWindow = createRuleWindow();
        ruleWindow.setPosition(
                Gdx.graphics.getWidth() / 2 -  ruleWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 -  ruleWindow.getHeight() / 2);
        ruleWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
        //ruleWindow.setVisible(true);

        chartWindow =createChartWindow();
        chartWindow.setVisible(false);

        optionsWindow.setVisible(false);

        stage.addActor(buttonOption);
        stage.addActor(buttonRule);

        stage.addActor(optionsWindow);
        stage.addActor(ruleWindow);
        stage.addActor(chartWindow);

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 -  optionsWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 -  optionsWindow.getHeight() / 2);
                optionsWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        buttonRule.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ruleWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 -  ruleWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 -  ruleWindow.getHeight() / 2);
                ruleWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
                worldController.level.player.timeStop = true;
            }
        });

        createbutton();
        batch = new SpriteBatch();
    }

    private Window createChartWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Button.ButtonStyle buttonChartStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonChartStyle.up = buttonRegion;
        buttonChartStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonChartStyle);

        final Window chartWindow = new Window("Chart", style);
        chartWindow.setModal(true);
        chartWindow.padTop(40);
        chartWindow.padLeft(40);
        chartWindow.padRight(40);
        chartWindow.padBottom(20);
        chartWindow.getTitleLabel().setAlignment(Align.center);
        chartWindow.row().padBottom(10).padTop(10);
        chartWindow.row().padTop(10);
        chartWindow.add(closeButton).colspan(3);
        chartWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chartWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });


        return chartWindow;
    }


    private Window createRuleWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Button.ButtonStyle buttonRuleStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonRuleStyle.up = buttonRegion;
        buttonRuleStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonRuleStyle);

        final Window ruleWindow = new Window("Rule", style);
        ruleWindow.setModal(true);
        ruleWindow.padTop(40);
        ruleWindow.padLeft(40);
        ruleWindow.padRight(40);
        ruleWindow.padBottom(20);
        ruleWindow.getTitleLabel().setAlignment(Align.center);
        ruleWindow.row().padBottom(10).padTop(10);
        ruleWindow.row().padTop(10);
        ruleWindow.add(closeButton).colspan(3);
        ruleWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ruleWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.timeStop = false;
            }
        });


        return ruleWindow;
    }

    public  void createbutton() {

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        textBullet = new Label("Bullet Max : " ,skin);
        textBullet.setColor(1, 1, 1, 1);
        textBullet.setFontScale(1f,1f);
        textBullet.setPosition(50, SCENE_HEIGHT - 50);

        textBeam = new Label("Z-Bullet Max : " ,skin);
        textBeam.setColor(1, 1, 1, 1);
        textBeam.setFontScale(1.f,1.f);
        textBeam.setPosition(200, SCENE_HEIGHT - 50);

        textTrap = new Label("Trap Max : " ,skin);
        textTrap.setColor(1, 1, 1, 1);
        textTrap.setFontScale(1f,1f);
        textTrap.setPosition(350, SCENE_HEIGHT - 50);

        textTime = new Label("Time : " ,skin);
        textTime.setColor(1, 1, 1, 1);
        textTime.setFontScale(1f,1f);
        textTime.setPosition(450, SCENE_HEIGHT - 100);

        energyLevel = new Label("Energy : ", skin);
        energyLevel.setColor(1, 1, 1, 1);
        energyLevel.setFontScale(1,1f);
        energyLevel.setPosition(500, SCENE_HEIGHT - 50);

        energyLevel2 = new Label("Product Energy : ", skin);
        energyLevel2.setColor(1, 1, 1, 1);
        energyLevel2.setFontScale(1,1f);
        energyLevel2.setPosition(650, SCENE_HEIGHT - 50);

        energyLevel3 = new Label("Battery : ", skin);
        energyLevel3.setColor(1, 1, 1, 1);
        energyLevel3.setFontScale(1,1f);
        energyLevel3.setPosition(800, SCENE_HEIGHT - 50);


        stage.addActor(textBullet);
        stage.addActor(textBeam);
        stage.addActor(textTrap);
        stage.addActor(textTime);
        stage.addActor(energyLevel);
        stage.addActor(energyLevel2);
        stage.addActor(energyLevel3);

        int btn_w = 200;
        int btn_h = 50;

        buttonStoC = new TextButton("link to charge controler", buttonStyle);
        buttonStoC.setWidth(btn_w);
        buttonStoC.setHeight(btn_h);
        buttonStoC.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-200);

        buttonStoB = new TextButton("link to battery", buttonStyle);
        buttonStoB.setWidth(btn_w);
        buttonStoB.setHeight(btn_h);
        buttonStoB.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-270);

        buttonStoI = new TextButton("link to inverter", buttonStyle);
        buttonStoI.setWidth(btn_w);
        buttonStoI.setHeight(btn_h);
        buttonStoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-340);

        buttonStoD = new TextButton("link to door", buttonStyle);
        buttonStoD.setWidth(btn_w);
        buttonStoD.setHeight(btn_h);
        buttonStoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-410);

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

        buttonItoD = new TextButton("link to door", buttonStyle);
        buttonItoD.setWidth(btn_w);
        buttonItoD.setHeight(btn_h);
        buttonItoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-410);

        stage.addActor(buttonItoD);

        buttonItoD.setVisible(false);

        buttonItoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.ItoD;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoB = new TextButton("link to battery", buttonStyle);
        buttonCtoB.setWidth(btn_w);
        buttonCtoB.setHeight(btn_h);
        buttonCtoB.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-270);

        buttonCtoI = new TextButton("link to inverter", buttonStyle);
        buttonCtoI.setWidth(btn_w);
        buttonCtoI.setHeight(btn_h);
        buttonCtoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-340);

        buttonCtoD = new TextButton("link to door", buttonStyle);
        buttonCtoD.setWidth(btn_w);
        buttonCtoD.setHeight(btn_h);
        buttonCtoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-410);

        stage.addActor(buttonCtoB);
        stage.addActor(buttonCtoI);
        stage.addActor(buttonCtoD);

        buttonCtoB.setVisible(false);
        buttonCtoI.setVisible(false);
        buttonCtoD.setVisible(false);

        buttonCtoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoB;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoI;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoD;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoI = new TextButton("link to inverter", buttonStyle);
        buttonBtoI.setWidth(btn_w);
        buttonBtoI.setHeight(btn_h);
        buttonBtoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-340);

        buttonBtoD = new TextButton("link to door", buttonStyle);
        buttonBtoD.setWidth(btn_w);
        buttonBtoD.setHeight(btn_h);
        buttonBtoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, SCENE_HEIGHT-410);

        stage.addActor(buttonBtoI);
        stage.addActor(buttonBtoD);

        buttonBtoI.setVisible(false);
        buttonBtoD.setVisible(false);

        buttonBtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.BtoI;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.BtoD;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        textBullet.setText(String.format("Bullet Max : %d", worldController.level.player.bulletCount));
        textBeam.setText(String.format("Z-Bullet Max : %d", worldController.level.player.beamCount));
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
            buttonStoB.setVisible(true);
            buttonCtoB.setVisible(true);
            buttonBtoI.setVisible(true);
            buttonBtoD.setVisible(true);
        }else{
            //buttonStoB.setVisible(false);
            buttonCtoB.setVisible(false);
            buttonBtoI.setVisible(false);
            buttonBtoD.setVisible(false);
        }

        if((worldController.level.player.status_inverter==true)&&(worldController.level.player.status_find == true)){
            buttonStoI.setVisible(true);
            buttonCtoI.setVisible(true);
            buttonBtoI.setVisible(true);
            buttonItoD.setVisible(true);
        }else{
            //buttonStoI.setVisible(false);
            buttonCtoI.setVisible(false);
            buttonBtoI.setVisible(false);
            buttonItoD.setVisible(false);
        }

        if((worldController.level.player.status_ccontroller==true)&&(worldController.level.player.status_find == true)){
            buttonStoC.setVisible(true);
            buttonCtoB.setVisible(true);
            buttonCtoI.setVisible(true);
            buttonCtoD.setVisible(true);
        }else{
            //buttonStoC.setVisible(false);
            buttonCtoB.setVisible(false);
            buttonCtoI.setVisible(false);
            buttonCtoD.setVisible(false);
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

        if ((trueLink==4)&&(!animation_status)){
            findItem(SolarCell.class).state = Item.ItemState.ONLOOP;
            findItem(SolarCell.class).resetAnimation();
            findItem(Inverter.class).state = Item.ItemState.ONLOOP;
            findItem(Inverter.class).resetAnimation();
            findItem(Battery.class).state = Item.ItemState.ON;
            findItem(Battery.class).resetAnimation();
            findItem(Charge.class).state = Item.ItemState.ONLOOP;
            findItem(Charge.class).resetAnimation();
            findItem(Door.class).state = Item.ItemState.ON;
            findItem(Door.class).resetAnimation();
            animation_status = true;
            chartWindow.setPosition(
                    Gdx.graphics.getWidth() / 2 -  chartWindow.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 -  chartWindow.getHeight() / 2);
            chartWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            worldController.level.energyTube.energy += 100;
        }

        if((findItem(Door.class).state == Item.ItemState.ON)&&(player.status_door==true)){
            game.setScreen(new GameScreen2(game, optionsWindow));
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
        stage.draw(); //การทำงาน
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        worldController = new WorldController(new Level1(new Level1Generator()));
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setInputProcessor(stage);

        MusicManager.instance.stop();
        MusicManager.instance.play(MusicManager.Musics.MUSIC_2, true);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        font.dispose();
    }

    @Override
    public void pause() {}


    private Item findItem(Class clazz) {
        for (int i = 0; i < worldController.level.items.size(); i++) {
            if (clazz.isInstance(worldController.level.items.get(i)))
                return worldController.level.items.get(i);
        }
        return null;
    }

}
