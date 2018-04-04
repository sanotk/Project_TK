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

import java.util.ArrayList;

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
    private ArrayList<solarcellState> link = new ArrayList<solarcellState>();
    private ArrayList<solarcellState> isComplete = new ArrayList<solarcellState>();


    private TextButton buttonStoC;
    private TextButton buttonStoB;
    private TextButton buttonStoI;
    private TextButton buttonStoD;

    private TextButton buttoncStoC;
    private TextButton buttoncStoB;
    private TextButton buttoncStoI;
    private TextButton buttoncStoD;

    private TextButton buttonCtoS;
    private TextButton buttonCtoB;
    private TextButton buttonCtoI;
    private TextButton buttonCtoD;

    private TextButton buttoncCtoS;
    private TextButton buttoncCtoB;
    private TextButton buttoncCtoI;
    private TextButton buttoncCtoD;

    private TextButton buttonBtoS;
    private TextButton buttonBtoC;
    private TextButton buttonBtoI;
    private TextButton buttonBtoD;

    private TextButton buttoncBtoS;
    private TextButton buttoncBtoC;
    private TextButton buttoncBtoI;
    private TextButton buttoncBtoD;

    private TextButton buttonItoS;
    private TextButton buttonItoC;
    private TextButton buttonItoB;
    private TextButton buttonItoD;

    private TextButton buttoncItoS;
    private TextButton buttoncItoC;
    private TextButton buttoncItoB;
    private TextButton buttoncItoD;

    private Button buttonOption;
    private BitmapFont font;
    private Window optionsWindow;

    private Window solarcellWindow;
    private Window chargeWindow;
    private Window batteryWindow;
    private Window inverterWindow;

    private boolean animation_status = false;

    private Button buttonRule;
    private Window ruleWindow;
    private Window chartWindow;

    private int trueLink = 0;

    public GameScreen(Game game ,final Window optionsWindow) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = new BitmapFont();

        this.optionsWindow = optionsWindow;

        isComplete.add(solarState.StoC);
        isComplete.add(solarState.CtoB);
        isComplete.add(solarState.CtoI);
        isComplete.add(solarState.ItoD);

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

        chartWindow =createChartWindow();
        chartWindow.setVisible(false);

        solarcellWindow =createSolarcellWindow();
        solarcellWindow.setVisible(false);

        chargeWindow =createChargeWindow();
        chargeWindow.setVisible(false);

        batteryWindow =createBatteryWindow();
        batteryWindow.setVisible(false);

        inverterWindow =createInverterWindow();
        inverterWindow.setVisible(false);

        optionsWindow.setVisible(false);

        stage.addActor(buttonOption);
        stage.addActor(buttonRule);

        stage.addActor(optionsWindow);
        stage.addActor(ruleWindow);
        stage.addActor(chartWindow);
        stage.addActor(solarcellWindow);
        stage.addActor(chargeWindow);
        stage.addActor(batteryWindow);
        stage.addActor(inverterWindow);

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

    private Window createSolarcellWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Button.ButtonStyle buttonSolarStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonSolarStyle.up = buttonRegion;
        buttonSolarStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonSolarStyle);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();
        buttonStyle2.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle2.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle2.font = font;

        buttonStoC = new TextButton("link to charge controller", buttonStyle);
        buttonStoB = new TextButton("link to battery", buttonStyle);
        buttonStoI = new TextButton("link to inverter", buttonStyle);
        buttonStoD = new TextButton("link to door", buttonStyle);

        buttoncStoC = new TextButton("canceled link to charge controller", buttonStyle2);
        buttoncStoB = new TextButton("canceled link to battery", buttonStyle2);
        buttoncStoI = new TextButton("canceled link to inverter", buttonStyle2);
        buttoncStoD = new TextButton("canceled link to door", buttonStyle2);

        final Window solarcellWindow = new Window("Choice", style);
        solarcellWindow.setModal(true);
        solarcellWindow.padTop(40);
        solarcellWindow.padLeft(40);
        solarcellWindow.padRight(40);
        solarcellWindow.padBottom(20);
        solarcellWindow.getTitleLabel().setAlignment(Align.center);
        solarcellWindow.row().padBottom(10).padTop(10);
        solarcellWindow.add(buttonStoC);
        solarcellWindow.add(buttonStoB).padLeft(10);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(buttoncStoC);
        solarcellWindow.add(buttoncStoB).padLeft(10);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(buttonStoI);
        solarcellWindow.add(buttonStoD).padLeft(10);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(buttoncStoI);
        solarcellWindow.add(buttoncStoD).padLeft(10);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(closeButton).colspan(2);
        solarcellWindow.pack();

        buttonStoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoC;
                addLink(solarState);
                hideButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonStoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoB;
                addLink(solarState);
                hideButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonStoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoI;
                addLink(solarState);
                hideButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonStoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoD;
                addLink(solarState);
                hideButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncStoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoC;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncStoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoB;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncStoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoI;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncStoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoD;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarcellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.status_find = false;
            }
        });

        buttoncStoC.setVisible(false);
        buttoncStoB.setVisible(false);
        buttoncStoI.setVisible(false);
        buttoncStoD.setVisible(false);

        return solarcellWindow;
    }

    private Window createChargeWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Button.ButtonStyle buttonSolarStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonSolarStyle.up = buttonRegion;
        buttonSolarStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonSolarStyle);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();
        buttonStyle2.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle2.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle2.font = font;

        buttonCtoS = new TextButton("link to solar cell", buttonStyle);
        buttonCtoB = new TextButton("link to battery", buttonStyle);
        buttonCtoI = new TextButton("link to inverter", buttonStyle);
        buttonCtoD = new TextButton("link to door", buttonStyle);

        buttoncCtoS = new TextButton("canceled link to solar cell", buttonStyle2);
        buttoncCtoB = new TextButton("canceled link to battery", buttonStyle2);
        buttoncCtoI = new TextButton("canceled link to inverter", buttonStyle2);
        buttoncCtoD = new TextButton("canceled link to door", buttonStyle2);

        final Window chargeWindow = new Window("Choice", style);
        chargeWindow.setModal(true);
        chargeWindow.padTop(40);
        chargeWindow.padLeft(40);
        chargeWindow.padRight(40);
        chargeWindow.padBottom(20);
        chargeWindow.getTitleLabel().setAlignment(Align.center);
        chargeWindow.row().padBottom(10).padTop(10);
        chargeWindow.add(buttonCtoS);
        chargeWindow.add(buttonCtoB).padLeft(10);
        chargeWindow.row().padTop(10);
        chargeWindow.add(buttoncCtoS);
        chargeWindow.add(buttoncCtoB).padLeft(10);
        chargeWindow.row().padTop(10);
        chargeWindow.add(buttonCtoI);
        chargeWindow.add(buttonCtoD).padLeft(10);
        chargeWindow.row().padTop(10);
        chargeWindow.add(buttoncCtoI);
        chargeWindow.add(buttoncCtoD).padLeft(10);
        chargeWindow.row().padTop(10);
        chargeWindow.add(closeButton).colspan(2);
        chargeWindow.pack();

        buttoncCtoS.setVisible(false);
        buttoncCtoB.setVisible(false);
        buttoncCtoI.setVisible(false);
        buttoncCtoD.setVisible(false);

        buttonCtoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoC;
                addLink(solarState);
                hideButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoB;
                addLink(solarState);
                hideButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoI;
                addLink(solarState);
                hideButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoD;
                addLink(solarState);
                hideButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncCtoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoC;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncCtoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoB;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncCtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoI;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncCtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoD;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chargeWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.status_find = false;
            }
        });

        return chargeWindow;
    }

    private Window createBatteryWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Button.ButtonStyle buttonSolarStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonSolarStyle.up = buttonRegion;
        buttonSolarStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonSolarStyle);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();
        buttonStyle2.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle2.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle2.font = font;

        buttonBtoS = new TextButton("link to solar cell", buttonStyle);
        buttonBtoC = new TextButton("link to charge controller", buttonStyle);
        buttonBtoI = new TextButton("link to inverter", buttonStyle);
        buttonBtoD = new TextButton("link to door", buttonStyle);

        buttoncBtoS = new TextButton("canceled link to solar cell", buttonStyle2);
        buttoncBtoC = new TextButton("canceled link to charge controller", buttonStyle2);
        buttoncBtoI = new TextButton("canceled link to inverter", buttonStyle2);
        buttoncBtoD = new TextButton("canceled link to door", buttonStyle2);

        buttoncBtoS.setVisible(false);
        buttoncBtoC.setVisible(false);
        buttoncBtoI.setVisible(false);
        buttoncBtoD.setVisible(false);

        final Window batteryWindow = new Window("Choice", style);
        batteryWindow.setModal(true);
        batteryWindow.padTop(40);
        batteryWindow.padLeft(40);
        batteryWindow.padRight(40);
        batteryWindow.padBottom(20);
        batteryWindow.getTitleLabel().setAlignment(Align.center);
        batteryWindow.row().padBottom(10).padTop(10);
        batteryWindow.add(buttonBtoS);
        batteryWindow.add(buttonBtoC).padLeft(10);
        batteryWindow.row().padTop(10);
        batteryWindow.add(buttoncBtoS);
        batteryWindow.add(buttoncBtoC).padLeft(10);
        batteryWindow.row().padTop(10);
        batteryWindow.add(buttonBtoI);
        batteryWindow.add(buttonBtoD).padLeft(10);
        batteryWindow.row().padTop(10);
        batteryWindow.add(buttoncBtoI);
        batteryWindow.add(buttoncBtoD).padLeft(10);
        batteryWindow.row().padTop(10);
        batteryWindow.add(closeButton).colspan(2);
        batteryWindow.pack();

        buttonBtoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoB;
                addLink(solarState);
                hideButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoB;
                addLink(solarState);
                hideButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.BtoI;
                addLink(solarState);
                hideButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.BtoD;
                addLink(solarState);
                hideButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncBtoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoB;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncBtoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoB;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncBtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.BtoD;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncBtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.BtoI;
                deleteLink(solarState);
                showButton (solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batteryWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.status_find = false;
            }
        });

        return batteryWindow;
    }

    private Window createInverterWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Button.ButtonStyle buttonSolarStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonSolarStyle.up = buttonRegion;
        buttonSolarStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonSolarStyle);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();
        buttonStyle2.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle2.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle2.font = font;

        buttonItoS = new TextButton("link to solar cell", buttonStyle);
        buttonItoC = new TextButton("link to charge controller", buttonStyle);
        buttonItoB = new TextButton("link to battery", buttonStyle);
        buttonItoD = new TextButton("link to door", buttonStyle);

        buttoncItoS = new TextButton("canceled link to solar cell", buttonStyle2);
        buttoncItoC = new TextButton("canceled link to charge controller", buttonStyle2);
        buttoncItoB = new TextButton("canceled link to battery", buttonStyle2);
        buttoncItoD = new TextButton("canceled link to door", buttonStyle2);

        final Window inverterWindow = new Window("Choice", style);
        inverterWindow.setModal(true);
        inverterWindow.padTop(40);
        inverterWindow.padLeft(40);
        inverterWindow.padRight(40);
        inverterWindow.padBottom(20);
        inverterWindow.getTitleLabel().setAlignment(Align.center);
        inverterWindow.row().padBottom(10).padTop(10);
        inverterWindow.add(buttonItoS);
        inverterWindow.add(buttonItoC).padLeft(10);
        inverterWindow.row().padTop(10);
        inverterWindow.add(buttoncItoS);
        inverterWindow.add(buttoncItoC).padLeft(10);
        inverterWindow.row().padTop(10);
        inverterWindow.add(buttonItoB);
        inverterWindow.add(buttonItoD).padLeft(10);
        inverterWindow.row().padTop(10);
        inverterWindow.add(buttoncItoB);
        inverterWindow.add(buttoncItoD).padLeft(10);
        inverterWindow.row().padTop(10);
        inverterWindow.add(closeButton).colspan(2);
        inverterWindow.pack();

        buttoncItoS.setVisible(false);
        buttoncItoC.setVisible(false);
        buttoncItoB.setVisible(false);
        buttoncItoD.setVisible(false);

        buttonItoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoI;
                addLink(solarState);
                hideButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoI;
                addLink(solarState);
                hideButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.BtoI;
                addLink(solarState);
                hideButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.ItoD;
                addLink(solarState);
                hideButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncItoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.StoI;
                deleteLink(solarState);
                showButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncItoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.CtoI;
                deleteLink(solarState);
                showButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncItoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.BtoI;
                deleteLink(solarState);
                showButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttoncItoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarState.ItoD;
                deleteLink(solarState);
                showButton(solarState);
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverterWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.status_find = false;
            }
        });

        return inverterWindow;
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

        int btn_w = 200;
        int btn_h = 50;

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

    }

    private void addLink (solarcellState solarState) {
        if(link.size() != 0) {
            for (int i = 0; i < link.size(); i++) {
                if (link.get(i) == solarState){
                    System.out.print("มี" + link.get(i) +"แล้ว");
                    return;
                }
            }
        }
        System.out.print("เพิ่ม" + solarState);
        link.add(solarState);
    }

    private void deleteLink (solarcellState solarState) {
        if(link.size() != 0) {
            for (int i = 0; i < link.size(); i++) {
                if (link.get(i) == solarState){
                    System.out.print("ลบ" + link.get(i) +"แล้ว");
                    link.remove(solarState);

                }
            }
        }
    }

    private void hideButton (solarcellState solarState) {
        if(solarState==solarState.StoC) {
            buttonCtoS.setVisible(false);
            buttonStoC.setVisible(false);
            buttoncStoC.setVisible(true);
            buttoncCtoS.setVisible(true);
        }else if(solarState==solarState.StoB) {
            buttonBtoS.setVisible(false);
            buttonStoB.setVisible(false);
            buttoncStoB.setVisible(true);
            buttoncBtoS.setVisible(true);
        }else if(solarState==solarState.StoI) {
            buttonItoS.setVisible(false);
            buttonStoI.setVisible(false);
            buttoncStoI.setVisible(true);
            buttoncItoS.setVisible(true);
        }else if(solarState==solarState.StoD) {
            buttonStoD.setVisible(false);
            buttoncStoD.setVisible(true);
        }else if(solarState==solarState.CtoB) {
            buttonCtoB.setVisible(false);
            buttonBtoC.setVisible(false);
            buttoncCtoB.setVisible(true);
            buttoncBtoC.setVisible(true);
        }else if(solarState==solarState.CtoI) {
            buttonCtoI.setVisible(false);
            buttonItoC.setVisible(false);
            buttoncCtoI.setVisible(true);
            buttoncItoC.setVisible(true);
        }else if(solarState==solarState.CtoD) {
            buttonCtoD.setVisible(false);
            buttoncCtoD.setVisible(true);
        }else if(solarState==solarState.BtoI) {
            buttonBtoI.setVisible(false);
            buttonItoB.setVisible(false);
            buttoncBtoI.setVisible(true);
            buttoncItoB.setVisible(true);
        }else if(solarState==solarState.BtoD) {
            buttonBtoD.setVisible(false);
            buttoncBtoD.setVisible(true);
        }else if(solarState==solarState.ItoD) {
            buttonItoD.setVisible(false);
            buttoncItoD.setVisible(true);
        }
    }

    private void showButton (solarcellState solarState) {
        if(solarState==solarState.StoC) {
            buttonCtoS.setVisible(true);
            buttonStoC.setVisible(true);
            buttoncStoC.setVisible(false);
            buttoncCtoS.setVisible(false);
        }else if(solarState==solarState.StoB) {
            buttonBtoS.setVisible(true);
            buttonStoB.setVisible(true);
            buttoncStoB.setVisible(false);
            buttoncBtoS.setVisible(false);
        }else if(solarState==solarState.StoI) {
            buttonItoS.setVisible(true);
            buttonStoI.setVisible(true);
            buttoncStoI.setVisible(false);
            buttoncItoS.setVisible(false);
        }else if(solarState==solarState.StoD) {
            buttonStoD.setVisible(true);
            buttoncStoD.setVisible(false);
        }else if(solarState==solarState.CtoB) {
            buttonCtoB.setVisible(true);
            buttonBtoC.setVisible(true);
            buttoncCtoB.setVisible(false);
            buttoncBtoC.setVisible(false);
        }else if(solarState==solarState.CtoI) {
            buttonCtoI.setVisible(true);
            buttonItoC.setVisible(true);
            buttoncCtoI.setVisible(false);
            buttoncItoC.setVisible(false);
        }else if(solarState==solarState.CtoD) {
            buttonCtoD.setVisible(true);
            buttoncCtoD.setVisible(false);
        }else if(solarState==solarState.BtoI) {
            buttonBtoI.setVisible(true);
            buttonItoB.setVisible(true);
            buttoncBtoI.setVisible(false);
            buttoncItoB.setVisible(false);
        }else if(solarState==solarState.BtoD) {
            buttonBtoD.setVisible(true);
            buttoncBtoD.setVisible(false);
        }else if(solarState==solarState.ItoD) {
            buttonItoD.setVisible(true);
            buttoncItoD.setVisible(false);
        }
    }

    private void checkGameComplete () {
        trueLink = 0;
        if((link.size() != 0) && (link.size() <= 4)) {
            for (int i = 0; i < link.size(); i++) {
                for (int j = 0; j < isComplete.size(); j++){
                    if (link.get(i) == isComplete.get(j)){
                        trueLink += 1;
                        System.out.println(trueLink);
                    }
                }
                //if(i>trueLink) System.out.print(link.get(i-1) + "เป็นการเชื่อมต่อที่ไม่ถูกต้อง");
            }
        }
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
            solarcellWindow.setPosition(
                    Gdx.graphics.getWidth() / 2 -  solarcellWindow.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 -  solarcellWindow.getHeight() / 2);
            solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
        }else{
            solarcellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
        }

        if((worldController.level.player.status_ccontroller==true)&&(worldController.level.player.status_find == true)){
            chargeWindow.setPosition(
                    Gdx.graphics.getWidth() / 2 -  chargeWindow.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 -  chargeWindow.getHeight() / 2);
            chargeWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
        }else{
            chargeWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
        }

        if((worldController.level.player.status_battery==true)&&(worldController.level.player.status_find == true)){
            batteryWindow.setPosition(
                    Gdx.graphics.getWidth() / 2 -  batteryWindow.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 -  batteryWindow.getHeight() / 2);
            batteryWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
        }else{
            batteryWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
        }

        if((worldController.level.player.status_inverter==true)&&(worldController.level.player.status_find == true)){
            inverterWindow.setPosition(
                    Gdx.graphics.getWidth() / 2 -  inverterWindow.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 -  inverterWindow.getHeight() / 2);
            inverterWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
        }else{
            inverterWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
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
