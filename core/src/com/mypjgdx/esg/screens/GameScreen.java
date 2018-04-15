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
import com.mypjgdx.esg.utils.ItemLink;
import com.mypjgdx.esg.utils.SolarState;

import java.util.ArrayList;

public class GameScreen extends AbstractGameScreen {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private ItemLink itemLink;

    SpriteBatch batch;
    public Texture bg;

    private Stage stage;
    private Skin skin;;

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

    public enum systemWindow {
        solarcell,
        chargecontroller,
        battery,
        inverter
    }

    public SolarState solarState = null;
    public systemWindow solarWindow = null;

    private ArrayList<SolarState> link = new ArrayList<SolarState>();
    private ArrayList<SolarState> isComplete = new ArrayList<SolarState>();
    private ArrayList<ItemLink> itemLinks = new ArrayList<ItemLink>();

    private TextButton buttonLink1;
    private TextButton buttonLink2;
    private TextButton buttonLink3;
    private TextButton buttonLink4;

    private Button buttonOption;
    private BitmapFont font;
    private Window optionsWindow;

    private Window solarcellWindow;

    private boolean animation_status = false;

    private Button buttonRule;
    private Window ruleWindow;
    private Window chartWindow;

    private boolean addedStoC = false;
    private boolean addedStoB = false;
    private boolean addedStoI = false;
    private boolean addedStoD = false;
    private boolean addedCtoB = false;
    private boolean addedCtoI = false;
    private boolean addedCtoD = false;
    private boolean addedBtoI = false;
    private boolean addedBtoD = false;
    private boolean addedItoD = false;

    private float startX;
    private float startY;
    private float goalX;
    private float goalY;
    private float startWidth;
    private float startHeight;
    private float goalWidth;
    private float goalHeight;

    private int trueLink = 0;

    private TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    private TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();

    public GameScreen(Game game, final Window optionsWindow) {
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

        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        buttonStyle2.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle2.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle2.font = font;

        ruleWindow = createRuleWindow();
        ruleWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - ruleWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - ruleWindow.getHeight() / 2);
        ruleWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));

        chartWindow = createChartWindow();
        chartWindow.setVisible(false);

        solarcellWindow = createSolarcellWindow();
        solarcellWindow.setVisible(false);

        optionsWindow.setVisible(false);

        stage.addActor(buttonOption);
        stage.addActor(buttonRule);

        stage.addActor(optionsWindow);
        stage.addActor(ruleWindow);
        stage.addActor(chartWindow);
        stage.addActor(solarcellWindow);

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 - optionsWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - optionsWindow.getHeight() / 2);
                optionsWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        buttonRule.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ruleWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 - ruleWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - ruleWindow.getHeight() / 2);
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

        buttonLink1 = new TextButton("link to charge controller", buttonStyle);
        buttonLink2 = new TextButton("link to battery", buttonStyle);
        buttonLink3 = new TextButton("link to inverter", buttonStyle);
        buttonLink4 = new TextButton("link to door", buttonStyle);

        final Window solarcellWindow = new Window("Choice", style);
        solarcellWindow.setModal(true);
        solarcellWindow.padTop(40);
        solarcellWindow.padLeft(40);
        solarcellWindow.padRight(40);
        solarcellWindow.padBottom(20);
        solarcellWindow.getTitleLabel().setAlignment(Align.center);
        solarcellWindow.row().padBottom(10).padTop(10);
        solarcellWindow.add(buttonLink1);
        solarcellWindow.add(buttonLink2).padLeft(10);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(buttonLink3);
        solarcellWindow.add(buttonLink4).padLeft(10);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(closeButton).colspan(2);
        solarcellWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarcellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        return solarcellWindow;
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

    private void createbutton() {

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        int btn_w = 200;
        int btn_h = 50;

        textBullet = new Label("Bullet Max : ", skin);
        textBullet.setColor(1, 1, 1, 1);
        textBullet.setFontScale(1f, 1f);
        textBullet.setPosition(50, SCENE_HEIGHT - 50);

        textBeam = new Label("Z-Bullet Max : ", skin);
        textBeam.setColor(1, 1, 1, 1);
        textBeam.setFontScale(1.f, 1.f);
        textBeam.setPosition(200, SCENE_HEIGHT - 50);

        textTrap = new Label("Trap Max : ", skin);
        textTrap.setColor(1, 1, 1, 1);
        textTrap.setFontScale(1f, 1f);
        textTrap.setPosition(350, SCENE_HEIGHT - 50);

        textTime = new Label("Time : ", skin);
        textTime.setColor(1, 1, 1, 1);
        textTime.setFontScale(1f, 1f);
        textTime.setPosition(450, SCENE_HEIGHT - 100);

        energyLevel = new Label("Energy : ", skin);
        energyLevel.setColor(1, 1, 1, 1);
        energyLevel.setFontScale(1, 1f);
        energyLevel.setPosition(500, SCENE_HEIGHT - 50);

        energyLevel2 = new Label("Product Energy : ", skin);
        energyLevel2.setColor(1, 1, 1, 1);
        energyLevel2.setFontScale(1, 1f);
        energyLevel2.setPosition(650, SCENE_HEIGHT - 50);

        energyLevel3 = new Label("Battery : ", skin);
        energyLevel3.setColor(1, 1, 1, 1);
        energyLevel3.setFontScale(1, 1f);
        energyLevel3.setPosition(800, SCENE_HEIGHT - 50);

        stage.addActor(textBullet);
        stage.addActor(textBeam);
        stage.addActor(textTrap);
        stage.addActor(textTime);
        stage.addActor(energyLevel);
        stage.addActor(energyLevel2);
        stage.addActor(energyLevel3);

    }

    private void addLink(SolarState solarState) {
        if (link.size() != 0) {
            for (int i = 0; i < link.size(); i++) {
                if (link.get(i) == solarState) {
                    System.out.print("มี" + link.get(i) + "แล้ว");
                    return;
                }
            }
        }
        System.out.print("เพิ่ม" + solarState);
        link.add(solarState);
    }

    private void deleteLink(SolarState solarState) {
        if (link.size() != 0) {
            for (int i = 0; i < link.size(); i++) {
                if (link.get(i) == solarState) {
                    System.out.print("ลบ" + link.get(i) + "แล้ว");
                    link.remove(solarState);
                }
            }
        }
    }

    private void checkButton(final systemWindow solarWindow) {
        if ((solarWindow == systemWindow.solarcell) && (!addedStoC)) {
            buttonLink1.setText("Link to Charge Controller");
            buttonLink1.setStyle(buttonStyle);
        } else if ((solarWindow == systemWindow.solarcell) && (addedStoC)) {
            buttonLink1.setText("Canceled Link to Charge Controller");
            buttonLink1.setStyle(buttonStyle2);
        } else if (((solarWindow == systemWindow.chargecontroller) && (!addedStoC))
                || ((solarWindow == systemWindow.battery) && (!addedStoB))
                || ((solarWindow == systemWindow.inverter) && (!addedStoI))) {
            buttonLink1.setText("Link to Solar Cell");
            buttonLink1.setStyle(buttonStyle);
        } else {
            buttonLink1.setText("Canceled Link to Solar Cell");
            buttonLink1.setStyle(buttonStyle2);
        }
        buttonLink1.clearListeners();
        buttonLink1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((solarWindow == systemWindow.solarcell) || (solarWindow == systemWindow.chargecontroller)) {
                    solarState = solarState.StoC;
                } else if (solarWindow == systemWindow.battery) {
                    solarState = solarState.StoB;
                } else {
                    solarState = solarState.StoI;
                }
                if ((solarWindow == systemWindow.solarcell) && (!addedStoC)) {
                    addLink(solarState);
                    checkAddedLink(solarState);
                } else if ((solarWindow == systemWindow.solarcell) && (addedStoC)) {
                    deleteLink(solarState);
                    checkDeledLink(solarState);
                } else if (((solarWindow == systemWindow.chargecontroller) && (!addedStoC))
                        || ((solarWindow == systemWindow.battery) && (!addedStoB))
                        || ((solarWindow == systemWindow.inverter) && (!addedStoI))) {
                    addLink(solarState);
                    checkAddedLink(solarState);
                } else {
                    deleteLink(solarState);
                    checkDeledLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        if (((solarWindow == systemWindow.solarcell) && (!addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoB))) {
            buttonLink2.setText("Link to Battery");
            buttonLink2.setStyle(buttonStyle);
        } else if (((solarWindow == systemWindow.solarcell) && (addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoB))) {
            buttonLink2.setText("Canceled Link to Battery");
            buttonLink2.setStyle(buttonStyle2);
        } else if (((solarWindow == systemWindow.battery) && (!addedCtoB)) || ((solarWindow == systemWindow.inverter) && (!addedCtoI))) {
            buttonLink2.setText("Link to Charge Controller");
            buttonLink2.setStyle(buttonStyle);
        } else {
            buttonLink2.setText("Canceled Link to Charge Controller");
            buttonLink2.setStyle(buttonStyle2);
        }
        buttonLink2.clearListeners();
        buttonLink2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((solarWindow == systemWindow.solarcell)) {
                    solarState = solarState.StoB;
                } else if ((solarWindow == systemWindow.chargecontroller) || (solarWindow == systemWindow.battery)) {
                    solarState = solarState.CtoB;
                } else {
                    solarState = solarState.CtoI;
                }
                if (((solarWindow == systemWindow.solarcell) && (!addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoB))) {
                    addLink(solarState);
                    checkAddedLink(solarState);
                } else if (((solarWindow == systemWindow.solarcell) && (addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoB))) {
                    deleteLink(solarState);
                    checkDeledLink(solarState);
                } else if (((solarWindow == systemWindow.battery) && (!addedCtoB)) || ((solarWindow == systemWindow.inverter) && (!addedCtoI))) {
                    addLink(solarState);
                    checkAddedLink(solarState);
                } else {
                    deleteLink(solarState);
                    checkDeledLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });
        if (((solarWindow == systemWindow.solarcell) && (!addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoI))
                || ((solarWindow == systemWindow.battery) && (!addedBtoI))) {
            buttonLink3.setText("Link to Inverter");
            buttonLink3.setStyle(buttonStyle);
        } else if (((solarWindow == systemWindow.solarcell) && (addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoI))
                || ((solarWindow == systemWindow.battery) && (addedBtoI))) {
            buttonLink3.setText("Canceled Link to Inverter");
            buttonLink3.setStyle(buttonStyle2);
        } else if ((solarWindow == systemWindow.inverter) && (addedBtoI)) {
            buttonLink3.setText("Link to Battery");
            buttonLink3.setStyle(buttonStyle);
        } else {
            buttonLink3.setText("Canceled Link to Battery");
            buttonLink3.setStyle(buttonStyle2);
        }
        buttonLink3.clearListeners();
        buttonLink3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((solarWindow == systemWindow.solarcell)) {
                    solarState = solarState.StoI;
                } else if ((solarWindow == systemWindow.chargecontroller)) {
                    solarState = solarState.CtoI;
                } else {
                    solarState = solarState.BtoI;
                }
                if (((solarWindow == systemWindow.solarcell) && (!addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoI))
                        || ((solarWindow == systemWindow.battery) && (!addedBtoI))) {
                    addLink(solarState);
                    checkAddedLink(solarState);
                } else if (((solarWindow == systemWindow.solarcell) && (addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoI))
                        || ((solarWindow == systemWindow.battery) && (addedBtoI))) {
                    deleteLink(solarState);
                    checkDeledLink(solarState);
                } else if ((solarWindow == systemWindow.inverter) && (!addedBtoI)) {
                    addLink(solarState);
                    checkAddedLink(solarState);
                } else {
                    deleteLink(solarState);
                    checkDeledLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });
        if (((solarWindow == systemWindow.solarcell) && (!addedStoD)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoD))
                || ((solarWindow == systemWindow.battery) && (!addedBtoD)) || ((solarWindow == systemWindow.inverter) && (!addedItoD))) {
            buttonLink4.setText("Link to Door");
            buttonLink4.setStyle(buttonStyle);
        } else {
            buttonLink4.setText("Canceled Link to Door");
            buttonLink4.setStyle(buttonStyle2);
        }
        buttonLink4.clearListeners();
        buttonLink4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((solarWindow == systemWindow.solarcell)) {
                    solarState = solarState.StoD;
                } else if ((solarWindow == systemWindow.chargecontroller)) {
                    solarState = solarState.CtoD;
                } else if (solarWindow == systemWindow.battery) {
                    solarState = solarState.BtoD;
                } else {
                    solarState = solarState.ItoD;
                }
                if (((solarWindow == systemWindow.solarcell) && (!addedStoD)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoD))
                        || ((solarWindow == systemWindow.battery) && (!addedBtoD)) || ((solarWindow == systemWindow.inverter) && (!addedItoD))) {
                    addLink(solarState);
                    checkAddedLink(solarState);
                } else {
                    deleteLink(solarState);
                    checkDeledLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });
        solarcellWindow.pack();
    }

    private void checkAddedLink(SolarState solarState) {
        if (solarState == SolarState.StoC) {
            addedStoC = true;
            startX = findItem(SolarCell.class).p_x;
            startY = findItem(SolarCell.class).p_y;
            startWidth = findItem(SolarCell.class).bounds.width;
            startHeight = findItem(SolarCell.class).bounds.height;
            goalX = findItem(Charge.class).p_x;
            goalY = findItem(Charge.class).p_y;
            goalWidth = findItem(Charge.class).bounds.width;
            goalHeight = findItem(Charge.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.StoB) {
            addedStoB = true;
            startX = findItem(SolarCell.class).p_x;
            startY = findItem(SolarCell.class).p_y;
            startWidth = findItem(SolarCell.class).bounds.width;
            startHeight = findItem(SolarCell.class).bounds.height;
            goalX = findItem(Battery.class).p_x;
            goalY = findItem(Battery.class).p_y;
            goalWidth = findItem(Battery.class).bounds.width;
            goalHeight = findItem(Battery.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.StoI) {
            addedStoI = true;
            startX = findItem(SolarCell.class).p_x;
            startY = findItem(SolarCell.class).p_y;
            startWidth = findItem(SolarCell.class).bounds.width;
            startHeight = findItem(SolarCell.class).bounds.height;
            goalX = findItem(Inverter.class).p_x;
            goalY = findItem(Inverter.class).p_y;
            goalWidth = findItem(Inverter.class).bounds.width;
            goalHeight = findItem(Inverter.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.StoD) {
            addedStoD = true;
            startX = findItem(SolarCell.class).p_x;
            startY = findItem(SolarCell.class).p_y;
            startWidth = findItem(SolarCell.class).bounds.width;
            startHeight = findItem(SolarCell.class).bounds.height;
            goalX = findItem(Door.class).p_x;
            goalY = findItem(Door.class).p_y;
            goalWidth = findItem(Door.class).bounds.width;
            goalHeight = findItem(Door.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.CtoB) {
            addedCtoB = true;
            startX = findItem(Charge.class).p_x;
            startY = findItem(Charge.class).p_y;
            startWidth = findItem(Charge.class).bounds.width;
            startHeight = findItem(Charge.class).bounds.height;
            goalX = findItem(Battery.class).p_x;
            goalY = findItem(Battery.class).p_y;
            goalWidth = findItem(Battery.class).bounds.width;
            goalHeight = findItem(Battery.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.CtoI) {
            addedCtoI = true;
            startX = findItem(Charge.class).p_x;
            startY = findItem(Charge.class).p_y;
            startWidth = findItem(Charge.class).bounds.width;
            startHeight = findItem(Charge.class).bounds.height;
            goalX = findItem(Inverter.class).p_x;
            goalY = findItem(Inverter.class).p_y;
            goalWidth = findItem(Inverter.class).bounds.width;
            goalHeight = findItem(Inverter.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.CtoD) {
            addedCtoD = true;
            startX = findItem(Charge.class).p_x;
            startY = findItem(Charge.class).p_y;
            startWidth = findItem(Charge.class).bounds.width;
            startHeight = findItem(Charge.class).bounds.height;
            goalX = findItem(Door.class).p_x;
            goalY = findItem(Door.class).p_y;
            goalWidth = findItem(Door.class).bounds.width;
            goalHeight = findItem(Door.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.BtoI) {
            addedBtoI = true;
            startX = findItem(Battery.class).p_x;
            startY = findItem(Battery.class).p_y;
            startWidth = findItem(Battery.class).bounds.width;
            startHeight = findItem(Battery.class).bounds.height;
            goalX = findItem(Inverter.class).p_x;
            goalY = findItem(Inverter.class).p_y;
            goalWidth = findItem(Inverter.class).bounds.width;
            goalHeight = findItem(Inverter.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.BtoD) {
            addedBtoD = true;
            startX = findItem(Battery.class).p_x;
            startY = findItem(Battery.class).p_y;
            startWidth = findItem(Battery.class).bounds.width;
            startHeight = findItem(Battery.class).bounds.height;
            goalX = findItem(Door.class).p_x;
            goalY = findItem(Door.class).p_y;
            goalWidth = findItem(Door.class).bounds.width;
            goalHeight = findItem(Door.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        } else if (solarState == SolarState.ItoD) {
            addedItoD = true;
            startX = findItem(Inverter.class).p_x;
            startY = findItem(Inverter.class).p_y;
            startWidth = findItem(Inverter.class).bounds.width;
            startHeight = findItem(Inverter.class).bounds.height;
            goalX = findItem(Door.class).p_x;
            goalY = findItem(Door.class).p_y;
            goalWidth = findItem(Door.class).bounds.width;
            goalHeight = findItem(Door.class).bounds.height;
            ItemLink itemLink = new ItemLink(worldController.level.mapLayer,startX,startY,startWidth,startHeight,
                    goalX,goalY,goalWidth, goalHeight,worldController.level.etcs,solarState);
            itemLinks.add(itemLink);
        }
    }

    private void checkDeledLink(SolarState solarState) {
        if (solarState == SolarState.StoC) {
            addedStoC = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.StoB) {
            addedStoB = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.StoI) {
            addedStoI = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.StoD) {
            addedStoD = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.CtoB) {
            addedCtoB = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.CtoI) {
            addedCtoI = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.CtoD) {
            addedCtoD = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.BtoI) {
            addedBtoI = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.BtoD) {
            addedBtoD = false;
            removeGuiLink(solarState);
        } else if (solarState == SolarState.ItoD) {
            addedItoD = false;
            removeGuiLink(solarState);
        }
    }

    private void removeGuiLink(SolarState solarState){
        for(int i = 0; i < itemLinks.size();i++){
            if(itemLinks.get(i).solarState==solarState){
                itemLinks.get(i).etcList.clear();
                itemLinks.remove(i);
            }
        }
    }

    private void checkGameComplete() {
        trueLink = 0;
        if ((link.size() != 0) && (link.size() <= 4)) {
            for (int i = 0; i < link.size(); i++) {
                for (int j = 0; j < isComplete.size(); j++) {
                    if (link.get(i) == isComplete.get(j)) {
                        trueLink += 1;
                        System.out.println(trueLink);
                    }
                }
                //if(x>trueLink) System.out.print(link.get(x-1) + "เป็นการเชื่อมต่อที่ไม่ถูกต้อง");
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
        energyLevel.setText(String.format("Energy : %d", (int) worldController.level.energyTube.energy));
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

        solarcellWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - solarcellWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - solarcellWindow.getHeight() / 2);
        if(!animation_status){
            if ((worldController.level.player.status_solarcell == true) && (worldController.level.player.status_find == true)) {
                solarWindow = systemWindow.solarcell;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if ((worldController.level.player.status_ccontroller == true) && (worldController.level.player.status_find == true)) {
                solarWindow = systemWindow.chargecontroller;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if ((worldController.level.player.status_battery == true) && (worldController.level.player.status_find == true)) {
                solarWindow = systemWindow.battery;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if ((worldController.level.player.status_inverter == true) && (worldController.level.player.status_find == true)) {
                solarWindow = systemWindow.inverter;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else {
                solarcellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
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

        if ((trueLink == 4) && (!animation_status)) {
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
                    Gdx.graphics.getWidth() / 2 - chartWindow.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - chartWindow.getHeight() / 2);
            chartWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            worldController.level.energyTube.energy += 100;
        }

        if ((findItem(Door.class).state == Item.ItemState.ON) && (player.status_door == true)) {
            game.setScreen(new GameScreen2(game, optionsWindow));
        }

        for (int i = 0; i < worldController.level.enemies.size(); i++) {
            Enemy enemy = worldController.level.enemies.get(i);
            if (enemy.dead && !enemy.count) {
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
    public void pause() {
    }

    private Item findItem(Class clazz) {
        for (int i = 0; i < worldController.level.items.size(); i++) {
            if (clazz.isInstance(worldController.level.items.get(i)))
                return worldController.level.items.get(i);
        }
        return null;
    }

}
