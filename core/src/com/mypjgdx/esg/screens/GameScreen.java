package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level1;
import com.mypjgdx.esg.game.objects.characters.Citizen;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.EnemyState;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.SwordWave;
import com.mypjgdx.esg.game.objects.weapons.Trap;
import com.mypjgdx.esg.game.objects.weapons.Weapon;
import com.mypjgdx.esg.ui.*;
import com.mypjgdx.esg.ui.Dialog;
import com.mypjgdx.esg.utils.GameSaveManager;
import com.mypjgdx.esg.utils.ItemLink;
import com.mypjgdx.esg.utils.MusicManager;
import com.mypjgdx.esg.utils.SolarState;

import java.util.ArrayList;

public class GameScreen extends AbstractGameScreen {

    private static final int SCENE_WIDTH = 1024;
    private static final int SCENE_HEIGHT = 576;

    public enum SystemWindow {
        SOLAR_CELL,
        CHARGE_CONTROLLER,
        BATTERY,
        INVERTER
    }

    private enum SolarLinkImage {
        SOLAR_ADD(Assets.instance.buttonSolarcellAdd),
        SOLAR_DELETE(Assets.instance.buttonSolarcellDel),
        BATTERY_ADD(Assets.instance.buttonBatteryAdd),
        BATTERY_DELETE(Assets.instance.buttonBatteryDel),
        CHARGE_ADD(Assets.instance.buttonChargeAdd),
        CHARGE_DELETE(Assets.instance.buttonChargeDel),
        INVERTER_ADD(Assets.instance.buttonInverterAdd),
        INVERTER_DELETE(Assets.instance.buttonInverterDel),
        DOOR_ADD(Assets.instance.buttonDoorAdd),
        DOOR_DELETE(Assets.instance.buttonDoorDel);

        static final SolarLinkImage[] values = SolarLinkImage.values();
        private TextureRegion region;

        SolarLinkImage(TextureRegion region) {
            this.region = region;
        }

        public static SolarLinkImage valueOf(TextureRegion region) {
            for (SolarLinkImage solarLinkImage : values) {
                if (solarLinkImage.region == region)
                    return solarLinkImage;
            }
            return null;
        }
    }

    /* กลุ่มที่ไม่จำเป็นต้อง save */

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private Stage stage;
    private Skin skin;

    private Label textBeam;
    private Label textTrap;
    private Label textTime;
    private Label textEnergyProduce;
    private Label textEnergyUse;
    private Label textEnergyStored;
    private Label textSun;
    private Label textTemperature;
    private Label textLiking;

    private PlayerTouchPad touchPad;
    private SwordAttackButton swordAttackButton;
    private SwordWaveAttackButton swordWaveAttackButton;
    private TrapAttackButton trapAttackButton;
    private TalkButton talkButton;

    private TextButton buttonAgree;
    private TextButton buttonRefuse;

    private Button iconEnergyLess;
    private Button iconHuman;
    private Button iconItem;
    private Button iconControl;
    private Button iconMission;
    private Button iconGuide;
    private Button iconStatus;

    private Button buttonControlWindow;
    private Button buttonGuideWindow;

    public Dialog dialog;

    private Button solarCellButton1;
    private Button solarCellButton2;
    private Button solarCellButton3;
    private Button solarCellButton4;

    private Label labelSolarCell1;
    private Label labelSolarCell2;
    private Label labelSolarCell3;
    private Label labelSolarCell4;

    private Window optionsWindow;
    private StatusWindow statusWindow;
    private ChartWindow chartWindow;

    private Window solarCellWindow;
    private ArrayList<SolarState> isComplete = new ArrayList<SolarState>();

    /* กลุ่มที่ต้อง save ไว้ก่อนเพื่อความชัวร์ */

    private MissionWindow missionWindow;

    private SolarState solarState;
    private ArrayList<SolarState> link = new ArrayList<SolarState>();

    private TextureRegionDrawable imageLink1;
    private TextureRegionDrawable imageLink2;
    private TextureRegionDrawable imageLink3;
    private TextureRegionDrawable imageLink4;

    private boolean animation_status = false;

    private boolean addedStoC;
    private boolean addedStoB;
    private boolean addedStoI;
    private boolean addedStoD;
    private boolean addedCtoB;
    private boolean addedCtoI;
    private boolean addedCtoD;
    private boolean addedBtoI;
    private boolean addedBtoD;
    private boolean addedItoD;

    private boolean dialogStart;
    private boolean dialogTrap;
    private boolean dialogSwordWave;

    private int enemyKilled;

    private int trueLink = 0;

    private int citizenCount = 0;

    private boolean controlShow = true;
    private int timeEvent = 0;

    public boolean stageFourClear;
    private boolean dialogCitizen2;

    private boolean stageTwoAfter;
    private boolean guideShow;
    private boolean missionStart;
    private boolean trapShow;
    private boolean swordShow;
    private boolean statusStart;
    private boolean dialogEnemy;
    private boolean dialogCitizen;
    private boolean dialogDoor1;
    private boolean dialogDoor2;
    private boolean dialogDoor3;
    private boolean dialogDoor4;
    private boolean dialogShow;

    private boolean stageTwoClear;
    private boolean stageThreeClear;

    public GameScreen(final Game game, final Window optionsWindow) {
        super(game);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        touchPad = new PlayerTouchPad();
        stage.addActor(touchPad);
        touchPad.setPosition(20, 20);

        swordAttackButton = new SwordAttackButton();
        stage.addActor(swordAttackButton);
        swordAttackButton.setPosition(stage.getWidth() - swordAttackButton.getWidth() - 20, 20);

        swordWaveAttackButton = new SwordWaveAttackButton();
        stage.addActor(swordWaveAttackButton);
        swordWaveAttackButton.setPosition(stage.getWidth() - swordWaveAttackButton.getWidth() - 140, 40);

        trapAttackButton = new TrapAttackButton();
        stage.addActor(trapAttackButton);
        trapAttackButton.setPosition(stage.getWidth() - trapAttackButton.getWidth() - 60, 135);

        talkButton = new TalkButton();
        stage.addActor(talkButton);
        talkButton.setPosition(stage.getWidth() - talkButton.getWidth() - 60, 400);


        dialog = new Dialog(Assets.instance.newFont, Assets.instance.dialogTexture, 65f, 120f);
        dialog.setPosition(
                SCENE_WIDTH / 2 - Assets.instance.dialogTexture.getWidth() * 0.5f,
                SCENE_HEIGHT / 4 - Assets.instance.dialogTexture.getHeight() * 0.5f);

        this.optionsWindow = optionsWindow;

        isComplete.add(SolarState.StoC);
        isComplete.add(SolarState.CtoB);
        isComplete.add(SolarState.CtoI);
        isComplete.add(SolarState.ItoD);
    }

    private void createButton() {

        TextButton.TextButtonStyle buttonControlStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable ControlUp = new TextureRegionDrawable(Assets.instance.iconControl);
        buttonControlStyle.up = ControlUp;
        buttonControlStyle.down = ControlUp.tint(Color.LIGHT_GRAY);
        Button buttonControl = new Button(buttonControlStyle);
        buttonControl.setPosition(SCENE_WIDTH - 48, SCENE_HEIGHT - 150);

        iconControl = new Button(buttonControlStyle);
        iconControl.setPosition(SCENE_WIDTH / 6 + 30, 145);

        iconControl.setVisible(false);

        TextButton.TextButtonStyle buttonMissionStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable missionUp = new TextureRegionDrawable(Assets.instance.iconMission);
        buttonMissionStyle.up = missionUp;
        buttonMissionStyle.down = missionUp.tint(Color.LIGHT_GRAY);
        Button buttonMission = new Button(buttonMissionStyle);
        buttonMission.setPosition(SCENE_WIDTH - 48, SCENE_HEIGHT - 200);

        iconMission = new Button(buttonMissionStyle);
        iconMission.setPosition(SCENE_WIDTH / 6 + 30, 145);
        iconMission.setVisible(false);

        TextButton.TextButtonStyle buttonGuideStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable GuideUp = new TextureRegionDrawable(Assets.instance.iconGuide);
        buttonGuideStyle.up = GuideUp;
        buttonGuideStyle.down = GuideUp.tint(Color.LIGHT_GRAY);
        Button buttonGuide = new Button(buttonGuideStyle);
        buttonGuide.setPosition(SCENE_WIDTH - 48, SCENE_HEIGHT - 250);

        iconGuide = new Button(buttonGuideStyle);
        iconGuide.setPosition(SCENE_WIDTH / 6 + 30, 145);
        iconGuide.setVisible(false);

        TextButton.TextButtonStyle buttonStatusStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable statusUp = new TextureRegionDrawable(Assets.instance.iconStatus);
        buttonStatusStyle.up = statusUp;
        buttonStatusStyle.down = statusUp.tint(Color.LIGHT_GRAY);
        Button buttonStatus = new Button(buttonStatusStyle);
        buttonStatus.setPosition(SCENE_WIDTH - 48, SCENE_HEIGHT - 300);

        iconStatus = new Button(buttonStatusStyle);
        iconStatus.setPosition(SCENE_WIDTH / 6 + 30, 145);
        iconStatus.setVisible(false);

        TextButton.TextButtonStyle buttonControlWindowStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable controlWindowUp = new TextureRegionDrawable(Assets.instance.controlWindow);
        buttonControlWindowStyle.up = controlWindowUp;
        buttonControlWindowStyle.down = controlWindowUp.tint(Color.LIGHT_GRAY);
        buttonControlWindow = new Button(buttonControlWindowStyle);
        buttonControlWindow.setPosition(SCENE_WIDTH - SCENE_WIDTH + 40, SCENE_HEIGHT - 360);

        TextButton.TextButtonStyle buttonGuideWindowStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable guidelWindowUp = new TextureRegionDrawable(Assets.instance.guide1Window);
        buttonGuideWindowStyle.up = guidelWindowUp;
        buttonGuideWindowStyle.down = guidelWindowUp.tint(Color.LIGHT_GRAY);
        buttonGuideWindow = new Button(buttonGuideWindowStyle);
        buttonGuideWindow.setPosition(SCENE_WIDTH / 4, SCENE_HEIGHT / 4 - 10);

        buttonGuideWindow.setVisible(false);

        TextButton.TextButtonStyle iconHumanStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable humanUp = new TextureRegionDrawable(Assets.instance.iconHuman);
        iconHumanStyle.up = humanUp;
        iconHumanStyle.over = humanUp.tint(Color.LIGHT_GRAY);
        iconHuman = new Button(iconHumanStyle);

        TextButton.TextButtonStyle iconItemStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable itemUp = new TextureRegionDrawable(Assets.instance.iconItem);
        iconItemStyle.up = itemUp;
        iconItemStyle.over = itemUp.tint(Color.LIGHT_GRAY);
        iconItem = new Button(iconItemStyle);

        TextButton.TextButtonStyle iconEnergyLessStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable energyLessUp = new TextureRegionDrawable(Assets.instance.iconEnergyLess);
        iconEnergyLessStyle.up = energyLessUp;
        iconEnergyLessStyle.over = energyLessUp.tint(Color.LIGHT_GRAY);
        iconEnergyLess = new Button(iconEnergyLessStyle);

        iconHuman.setVisible(false);
        iconItem.setVisible(false);
        iconEnergyLess.setVisible(false);

        TextButton.TextButtonStyle buttonAgreeStyle = new TextButton.TextButtonStyle();
        buttonAgreeStyle.up = new TextureRegionDrawable(Assets.instance.buttonGreen1);
        buttonAgreeStyle.down = new TextureRegionDrawable(Assets.instance.buttonGreen2);
        buttonAgreeStyle.font = Assets.instance.newFont;

        TextButton.TextButtonStyle buttonRefuseStyle = new TextButton.TextButtonStyle();
        buttonRefuseStyle.up = new TextureRegionDrawable(Assets.instance.buttonRed1);
        buttonRefuseStyle.down = new TextureRegionDrawable(Assets.instance.buttonRed2);
        buttonRefuseStyle.font = Assets.instance.newFont;

        buttonAgree = new TextButton("ตกลง", buttonAgreeStyle);
        buttonAgree.setWidth(50);
        buttonAgree.setHeight(25);
        buttonAgree.setPosition(SCENE_WIDTH / 6 + 20, 120);

        buttonAgree.setVisible(false);

        buttonRefuse = new TextButton("ปฎิเสธ", buttonRefuseStyle);
        buttonRefuse.setWidth(50);
        buttonRefuse.setHeight(25);
        buttonRefuse.setPosition(SCENE_WIDTH / 4, 120);

        buttonRefuse.setVisible(false);

        chartWindow = new ChartWindow(this, worldController);
        chartWindow.setVisible(false);

        statusWindow = new StatusWindow(worldController);
        statusWindow.setVisible(false);

        Window guideWindow = createGuideWindow();
        guideWindow.setVisible(false);

        solarCellWindow = createSolarCellWindow();
        solarCellWindow.setVisible(false);

        missionWindow = new MissionWindow(worldController);
        missionWindow.setVisible(false);
        missionWindow.setText("ภารกิจแรก สำรวจพื้นที่พร้อมทั้งกำจัดเหล่ามอนสเตอร์ทั้งหมด", 0);

        optionsWindow.setVisible(false);

        dialog.hide();

        stage.addActor(dialog);

        stage.addActor(new OptionButton(optionsWindow,SCENE_WIDTH - 50, SCENE_HEIGHT - 50));
        stage.addActor(buttonAgree);
        stage.addActor(buttonRefuse);
        stage.addActor(buttonMission);
        stage.addActor(buttonControl);
        stage.addActor(buttonStatus);
        stage.addActor(buttonGuide);
        stage.addActor(buttonControlWindow);
        stage.addActor(iconHuman);
        stage.addActor(iconItem);
        stage.addActor(new PlayPauseButton(worldController, SCENE_WIDTH - 50, SCENE_HEIGHT - 100));
        stage.addActor(iconEnergyLess);
        stage.addActor(iconGuide);
        stage.addActor(iconControl);
        stage.addActor(iconMission);
        stage.addActor(iconStatus);
        stage.addActor(buttonGuideWindow);

        stage.addActor(optionsWindow);
        stage.addActor(chartWindow);
        stage.addActor(statusWindow);
        stage.addActor(guideWindow);
        stage.addActor(solarCellWindow);
        stage.addActor(missionWindow);

        buttonMission.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionStart = true;
                missionWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
                worldController.level.player.timeStop = true;
            }
        });

        iconMission.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionStart = true;
                missionWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
                worldController.level.player.timeStop = true;
            }
        });

        buttonGuide.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (guideShow) {
                    guideShow = false;
                    buttonGuideWindow.setVisible(false);
                    worldController.level.player.timeStop = false;
                } else {
                    guideShow = true;
                    buttonGuideWindow.setVisible(true);
                    worldController.level.player.timeStop = true;
                }
            }
        });

        iconGuide.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (guideShow) {
                    guideShow = false;
                    buttonGuideWindow.setVisible(false);
                    worldController.level.player.timeStop = false;
                } else {
                    guideShow = true;
                    buttonGuideWindow.setVisible(true);
                    worldController.level.player.timeStop = true;
                }
            }
        });

        buttonGuideWindow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (guideShow) {
                    guideShow = false;
                    buttonGuideWindow.setVisible(false);
                    worldController.level.player.timeStop = false;
                } else {
                    guideShow = true;
                    buttonGuideWindow.setVisible(true);
                    worldController.level.player.timeStop = true;
                }
            }
        });

        buttonControl.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controlShow) {
                    controlShow = false;
                    buttonControlWindow.setVisible(false);
                } else {
                    controlShow = true;
                    buttonControlWindow.setVisible(true);
                }
            }
        });

        buttonControlWindow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controlShow) {
                    controlShow = false;
                    buttonControlWindow.setVisible(false);
                } else {
                    controlShow = true;
                    buttonControlWindow.setVisible(true);
                }
            }
        });

        iconControl.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controlShow) {
                    controlShow = false;
                    buttonControlWindow.setVisible(false);
                } else {
                    controlShow = true;
                    buttonControlWindow.setVisible(true);
                }
            }
        });

        buttonStatus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statusStart = true;
                statusWindow.show(worldController);
            }
        });

        iconStatus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statusStart = true;
                statusWindow.show(worldController);
            }
        });

        buttonAgree.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (trapShow) {
                    worldController.level.player.acceptTrap = true;
                    worldController.level.player.requestTrap = false;
                    dialogTrap = true;
                } else if (swordShow) {
                    worldController.level.player.acceptSwordWave = true;
                    worldController.level.player.requestSwordWave = false;
                    dialogSwordWave = true;
                } else {
                    MusicManager.instance.stop();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new GameScreen2(game, optionsWindow));
                        }
                    });
                }
                buttonAgree.setVisible(false);
                buttonRefuse.setVisible(false);
                dialog.hide();
                worldController.level.player.timeStop = false;
                dialogShow = false;
                trapShow = false;
                swordShow = false;
            }
        });

        buttonRefuse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (trapShow) {
                    worldController.level.player.acceptTrap = false;
                    worldController.level.player.requestTrap = false;
                } else if (swordShow) {
                    worldController.level.player.acceptSwordWave = false;
                    worldController.level.player.requestSwordWave = false;
                } else {
                    dialogDoor4 = false;
                    GameSaveManager.instance.save();
                    MusicManager.instance.stop();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new MenuScreen(game));
                            EnergyProducedBar.instance.energyProduced = 0;
                            EnergyUsedBar.instance.energyUse = 0;
                            BatteryBar.instance.batteryStorage = 0;
                        }
                    });
                }
                buttonAgree.setVisible(false);
                buttonRefuse.setVisible(false);
                dialog.hide();
                worldController.level.player.timeStop = false;
                dialogShow = false;
                trapShow = false;
                swordShow = false;
            }
        });


        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = Assets.instance.newFont;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.newFont;


        TextButton.TextButtonStyle buttonSunStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconSun = new TextureRegionDrawable(Assets.instance.iconSun);
        buttonSunStyle.up = iconSun;
        buttonSunStyle.over = iconSun.tint(Color.LIME);
        Button iconSunButton = new Button(buttonSunStyle);
        iconSunButton.setPosition(80, SCENE_HEIGHT - 50);

        textSun = new Label("", skin);
        textSun.setColor(0, 0, 0, 1);
        textSun.setStyle(labelStyle);
        textSun.setFontScale(1f, 1f);
        textSun.setPosition(105, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonTemperatureStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTemperature = new TextureRegionDrawable(Assets.instance.iconTemperature);
        buttonTemperatureStyle.up = iconTemperature;
        buttonTemperatureStyle.over = iconTemperature.tint(Color.LIME);
        Button iconTemperatureButton = new Button(buttonTemperatureStyle);
        iconTemperatureButton.setPosition(180, SCENE_HEIGHT - 50);

        textTemperature = new Label("", skin);
        textTemperature.setColor(0, 0, 0, 1);
        textTemperature.setStyle(labelStyle);
        textTemperature.setFontScale(1f, 1f);
        textTemperature.setPosition(205, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonCircleStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconCircle = new TextureRegionDrawable(Assets.instance.iconCircle);
        buttonCircleStyle.up = iconCircle;
        buttonCircleStyle.over = iconCircle.tint(Color.LIME);
        Button iconCircleButton = new Button(buttonCircleStyle);
        iconCircleButton.setPosition(230, SCENE_HEIGHT - 40);

        TextButton.TextButtonStyle buttonSwordStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconSword = new TextureRegionDrawable(Assets.instance.iconSword);
        buttonSwordStyle.up = iconSword;
        buttonSwordStyle.over = iconSword.tint(Color.LIME);
        Button iconSwordButton = new Button(buttonSwordStyle);
        iconSwordButton.setPosition(245, SCENE_HEIGHT - 50);

        textBeam = new Label("", skin);
        textBeam.setColor(0, 0, 0, 1);
        textBeam.setStyle(labelStyle);
        textBeam.setFontScale(1.f, 1.f);
        textBeam.setPosition(265, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonTrapStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTrap = new TextureRegionDrawable(Assets.instance.iconTrap);
        buttonTrapStyle.up = iconTrap;
        buttonTrapStyle.over = iconTrap.tint(Color.LIME);
        Button iconTrapButton = new Button(buttonTrapStyle);
        iconTrapButton.setPosition(350, SCENE_HEIGHT - 50);

        textTrap = new Label("", skin);
        textTrap.setColor(0, 0, 0, 1);
        textTrap.setStyle(labelStyle);
        textTrap.setFontScale(1f, 1f);
        textTrap.setPosition(375, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonTimeStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTime = new TextureRegionDrawable(Assets.instance.iconTime);
        buttonTimeStyle.up = iconTime;
        buttonTimeStyle.over = iconTime.tint(Color.LIME);
        Button iconTimeButton = new Button(buttonTimeStyle);
        iconTimeButton.setPosition(450, SCENE_HEIGHT - 50);

        textTime = new Label("", skin);
        textTime.setColor(0, 0, 0, 1);
        textTime.setStyle(labelStyle);
        textTime.setFontScale(1f, 1f);
        textTime.setPosition(475, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonEnergyPlusStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconEnergyPlus = new TextureRegionDrawable(Assets.instance.iconEnergyPlus);
        buttonEnergyPlusStyle.up = iconEnergyPlus;
        buttonEnergyPlusStyle.over = iconEnergyPlus.tint(Color.LIME);
        Button iconEnergyPlusButton = new Button(buttonEnergyPlusStyle);
        iconEnergyPlusButton.setPosition(550, SCENE_HEIGHT - 50);

        textEnergyProduce = new Label("", skin);
        textEnergyProduce.setColor(0, 0, 0, 1);
        textEnergyProduce.setStyle(labelStyle);
        textEnergyProduce.setFontScale(1, 1f);
        textEnergyProduce.setPosition(575, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonEnergyMinusStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconEnergyMinus = new TextureRegionDrawable(Assets.instance.iconEnergyMinus);
        buttonEnergyMinusStyle.up = iconEnergyMinus;
        buttonEnergyMinusStyle.over = iconEnergyMinus.tint(Color.LIME);
        Button iconEnergyMinusButton = new Button(buttonEnergyMinusStyle);
        iconEnergyMinusButton.setPosition(650, SCENE_HEIGHT - 50);

        textEnergyUse = new Label("", skin);
        textEnergyUse.setColor(0, 0, 0, 1);
        textEnergyUse.setStyle(labelStyle);
        textEnergyUse.setFontScale(1, 1f);
        textEnergyUse.setPosition(675, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonBatteryStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconBattery = new TextureRegionDrawable(Assets.instance.iconBattery);
        buttonBatteryStyle.up = iconBattery;
        buttonBatteryStyle.over = iconBattery.tint(Color.LIME);
        Button iconBatteryButton = new Button(buttonBatteryStyle);
        iconBatteryButton.setPosition(750, SCENE_HEIGHT - 50);

        textEnergyStored = new Label("", skin);
        textEnergyStored.setColor(0, 0, 0, 1);
        textEnergyStored.setStyle(labelStyle);
        textEnergyStored.setFontScale(1, 1f);
        textEnergyStored.setPosition(775, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonLikingStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconLiking = new TextureRegionDrawable(Assets.instance.iconLiking);
        buttonLikingStyle.up = iconLiking;
        buttonLikingStyle.over = iconLiking.tint(Color.LIME);
        Button iconLikingButton = new Button(buttonLikingStyle);
        iconLikingButton.setPosition(875, SCENE_HEIGHT - 50);

        textLiking = new Label("", skin);
        textLiking.setColor(0, 0, 0, 1);
        textLiking.setStyle(labelStyle);
        textLiking.setFontScale(1, 1f);
        textLiking.setPosition(900, SCENE_HEIGHT - 42);

        stage.addActor(iconSunButton);
        stage.addActor(iconTemperatureButton);
        stage.addActor(iconCircleButton);

        stage.addActor(iconSwordButton);
        stage.addActor(iconTrapButton);
        stage.addActor(iconTimeButton);
        stage.addActor(iconEnergyPlusButton);
        stage.addActor(iconEnergyMinusButton);
        stage.addActor(iconBatteryButton);
        stage.addActor(iconLikingButton);

        stage.addActor(textSun);
        stage.addActor(textTemperature);
        stage.addActor(textBeam);
        stage.addActor(textTrap);
        stage.addActor(textTime);
        stage.addActor(textEnergyProduce);
        stage.addActor(textEnergyUse);
        stage.addActor(textEnergyStored);
        stage.addActor(textLiking);
    }

    private Window createGuideWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
        style.titleFont = Assets.instance.newFont;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.newFont;
        labelStyle.fontColor = Color.WHITE;

        Button.ButtonStyle buttonChartStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonChartStyle.up = buttonRegion;
        buttonChartStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonChartStyle);

        Label text1 = new Label("การทำงานของระบบโซล่าเซลล์", skin);
        Label text2 = new Label("ตัวแผงโซล่าเซลล์จะคอยดูดซับพลังงานแสงอาทิตย์เปลี่ยนเป็นพลังงานไฟฟ้า", skin);
        Label text3 = new Label("โดยจะมีชาร์จคอนโทรลเลอร์ทำหน้าที่ควบคุมการชาร์จไฟจากแผงโซลาเซลล์ลงสู่แบตเตอรี่", skin);
        Label text4 = new Label("ชาร์จคอนโทรลเลอร์ยังทำหน้าที่ควบคุมการจ่ายไฟออกจากแบตเตอรี่", skin);
        Label text5 = new Label("ไฟฟ้าที่ผลิตได้จากแผงโซล่าเซลล์จะเป็นไฟฟ้ากระแสตรง", skin);
        Label text6 = new Label("ดังนั้นอุปกรณ์ไฟฟ้าภายในบ้านซึ่งใช้ไฟฟ้ากระแสสลับ จึงต้องต่อผ่านเครื่องอินเวอร์เตอร์เพื่อเปลี่ยนเป็นไฟฟ้ากระแสสลับก่อน", skin);
        Label text7 = new Label("และเนื่องจากอินเวอร์เตอร์ รวมทั้งชาร์จคอนโทรลเลอร์ต้องใช้ไฟฟ้าในการทำงาน จำเป็นต้องมีพลังงานภายในแบตเตอรี่ก่อนเล็กน้อย", skin);
        Label text8 = new Label("", skin);
        Label text9 = new Label("", skin);

        text1.setStyle(labelStyle);
        text2.setStyle(labelStyle);
        text3.setStyle(labelStyle);
        text4.setStyle(labelStyle);
        text5.setStyle(labelStyle);
        text6.setStyle(labelStyle);
        text7.setStyle(labelStyle);
        text8.setStyle(labelStyle);
        text9.setStyle(labelStyle);

        final Window guideWindow = new Window("แนะนำ", style);
        guideWindow.setModal(true);
        guideWindow.padTop(45);
        guideWindow.padLeft(40);
        guideWindow.padRight(40);
        guideWindow.padBottom(20);
        guideWindow.getTitleLabel().setAlignment(Align.center);
        guideWindow.row().padTop(10);
        guideWindow.add(text1);
        guideWindow.row().padTop(10);
        guideWindow.add(text2);
        guideWindow.row().padTop(10);
        guideWindow.add(text3);
        guideWindow.row().padTop(10);
        guideWindow.add(text4);
        guideWindow.row().padTop(10);
        guideWindow.add(text5);
        guideWindow.row().padTop(10);
        guideWindow.add(text6);
        guideWindow.row().padTop(10);
        guideWindow.add(text7);
        guideWindow.row().padTop(10);
        guideWindow.add(text8);
        guideWindow.row().padTop(10);
        guideWindow.add(text9);
        guideWindow.row().padTop(10);
        guideWindow.add(closeButton).colspan(3);
        guideWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guideWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.solarCellGuideWindow = false;
                worldController.level.player.timeStop = false;
            }
        });

        return guideWindow;
    }

    private Window createSolarCellWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
        style.titleFont = Assets.instance.newFont;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.newFont;
        labelStyle.fontColor = Color.WHITE;

        Button.ButtonStyle buttonSolarStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonSolarStyle.up = buttonRegion;
        buttonSolarStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonSolarStyle);

        imageLink1 = new TextureRegionDrawable((Assets.instance.buttonChargeAdd));
        imageLink2 = new TextureRegionDrawable((Assets.instance.buttonBatteryAdd));
        imageLink3 = new TextureRegionDrawable((Assets.instance.buttonInverterAdd));
        imageLink4 = new TextureRegionDrawable((Assets.instance.buttonDoorAdd));

        solarCellButton1 = new Button(imageLink1);
        solarCellButton2 = new Button(imageLink2);
        solarCellButton3 = new Button(imageLink3);
        solarCellButton4 = new Button(imageLink4);

        labelSolarCell1 = new Label("เชื่อมต่อไปยังตัวควบคุมการชาร์จ", skin);
        labelSolarCell2 = new Label("เชื่อมต่อไปยังแบตเตอรี", skin);
        labelSolarCell3 = new Label("เชื่อมต่อไปยังเครื่องแปลงกระแสไฟ", skin);
        labelSolarCell4 = new Label("เชื่อมต่อไปยังสถานที่หลบภัย", skin);

        labelSolarCell1.setStyle(labelStyle);
        labelSolarCell2.setStyle(labelStyle);
        labelSolarCell3.setStyle(labelStyle);
        labelSolarCell4.setStyle(labelStyle);

        final Window solarcellWindow = new Window("ตัวเลือกการเชื่อมต่อ", style);
        solarcellWindow.setSkin(skin);
        solarcellWindow.setModal(true);
        solarcellWindow.padTop(45);
        solarcellWindow.padLeft(40);
        solarcellWindow.padRight(40);
        solarcellWindow.padBottom(20);
        solarcellWindow.getTitleLabel().setAlignment(Align.center);
        solarcellWindow.row().padBottom(10).padTop(10);
        solarcellWindow.add(solarCellButton1);
        solarcellWindow.add(solarCellButton2).padLeft(20);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(labelSolarCell1);
        solarcellWindow.add(labelSolarCell2).padLeft(20);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(solarCellButton3);
        solarcellWindow.add(solarCellButton4).padLeft(20);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(labelSolarCell3);
        solarcellWindow.add(labelSolarCell4).padLeft(20);
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

    private void addLink(SolarState solarState) {
        if (link.size() != 0) {
            for (SolarState aLink : link) {
                if (aLink == solarState) {
                    System.out.print("มี" + aLink + "แล้ว");
                    return;
                }
            }
        }
        System.out.print("เพิ่ม" + solarState);
        link.add(solarState);
        addGuiLink(solarState);
    }

    private void deleteLink(SolarState solarState) {
        if (link.size() != 0) {
            for (int i = 0; i < link.size(); i++) {
                if (link.get(i) == solarState) {
                    System.out.print("ลบ" + link.get(i) + "แล้ว");
                    link.remove(solarState);
                    checkDeleteLink(solarState);
                    removeGuiLink(solarState);
                }
            }
        }
    }

    private void checkButton(final SystemWindow solarWindow) {
        Level1 level1 = (Level1) worldController.level;
        if (level1.solarCell1.nearPlayer() || level1.solarCell4.nearPlayer() || level1.solarCell7.nearPlayer()) {
            solarCellWindow.getTitleLabel().setText("ตัวเลือกการเชื่อมต่อของแผงโซล่าเซลล์");
        } else if (level1.charge.nearPlayer()) {
            solarCellWindow.getTitleLabel().setText("ตัวเลือกการเชื่อมต่อของตัวควบคุมการชาร์จ");
        } else if (level1.battery.nearPlayer()) {
            solarCellWindow.getTitleLabel().setText("ตัวเลือกการเชื่อมต่อของแบตเตอรี่");
        } else if (level1.inverter.nearPlayer()) {
            solarCellWindow.getTitleLabel().setText("ตัวเลือกการเชื่อมต่อของเครื่องแปลงกระแสไฟ");
        }
        String textCharge2 = "ยกเลิกการเชื่อมต่อไปยังตัวควบคุมการชาร์จ";
        if ((solarWindow == SystemWindow.SOLAR_CELL) && (!addedStoC)) {
            imageLink1.setRegion(Assets.instance.buttonChargeAdd);
            labelSolarCell1.setText("เชื่อมต่อไปยังตัวควบคุมการชาร์จ");
        } else if ((solarWindow == SystemWindow.SOLAR_CELL) && (addedStoC)) {
            imageLink1.setRegion(Assets.instance.buttonChargeDel);
            labelSolarCell1.setText(textCharge2);
        } else if (((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (!addedStoC))
                || ((solarWindow == SystemWindow.BATTERY) && (!addedStoB))
                || ((solarWindow == SystemWindow.INVERTER) && (!addedStoI))) {
            imageLink1.setRegion(Assets.instance.buttonSolarcellAdd);
            String textSolarcell = "เชื่อมต่อไปยังแผงโซล่าเซลล์";
            labelSolarCell1.setText(textSolarcell);
        } else {
            imageLink1.setRegion(Assets.instance.buttonSolarcellDel);
            String textSolarcell2 = "ยกเลิกการเชื่อมต่อไปยังแผงโซล่าเซลล์";
            labelSolarCell1.setText(textSolarcell2);
        }
        solarCellButton1.clearListeners();
        solarCellButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((solarWindow == SystemWindow.SOLAR_CELL) || (solarWindow == SystemWindow.CHARGE_CONTROLLER)) {
                    solarState = SolarState.StoC;
                } else if (solarWindow == SystemWindow.BATTERY) {
                    solarState = SolarState.StoB;
                } else {
                    solarState = SolarState.StoI;
                }
                if ((solarWindow == SystemWindow.SOLAR_CELL) && (!addedStoC)) {
                    addLink(solarState);
                } else if ((solarWindow == SystemWindow.SOLAR_CELL) && (addedStoC)) {
                    deleteLink(solarState);
                } else if (((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (!addedStoC))
                        || ((solarWindow == SystemWindow.BATTERY) && (!addedStoB))
                        || ((solarWindow == SystemWindow.INVERTER) && (!addedStoI))) {
                    addLink(solarState);
                } else {
                    deleteLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });

        String textBattery2 = "ยกเลิกการเชื่อมต่อไปยังแบตเตอรี";
        if (((solarWindow == SystemWindow.SOLAR_CELL) && (!addedStoB)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (!addedCtoB))) {
            imageLink2.setRegion(Assets.instance.buttonBatteryAdd);
            labelSolarCell2.setText("เชื่อมต่อไปยังแบตเตอรี");
        } else if (((solarWindow == SystemWindow.SOLAR_CELL) && (addedStoB)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (addedCtoB))) {
            imageLink2.setRegion(Assets.instance.buttonBatteryDel);
            labelSolarCell2.setText(textBattery2);
        } else if (((solarWindow == SystemWindow.BATTERY) && (!addedCtoB)) || ((solarWindow == SystemWindow.INVERTER) && (!addedCtoI))) {
            imageLink2.setRegion(Assets.instance.buttonChargeAdd);
            labelSolarCell2.setText("เชื่อมต่อไปยังตัวควบคุมการชาร์จ");
        } else {
            imageLink2.setRegion(Assets.instance.buttonChargeDel);
            labelSolarCell2.setText(textCharge2);
        }
        solarCellButton2.clearListeners();
        solarCellButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((solarWindow == SystemWindow.SOLAR_CELL)) {
                    solarState = SolarState.StoB;
                } else if ((solarWindow == SystemWindow.CHARGE_CONTROLLER) || (solarWindow == SystemWindow.BATTERY)) {
                    solarState = SolarState.CtoB;
                } else {
                    solarState = SolarState.CtoI;
                }
                if (((solarWindow == SystemWindow.SOLAR_CELL) && (!addedStoB)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (!addedCtoB))) {
                    addLink(solarState);
                } else if (((solarWindow == SystemWindow.SOLAR_CELL) && (addedStoB)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (addedCtoB))) {
                    deleteLink(solarState);
                } else if (((solarWindow == SystemWindow.BATTERY) && (!addedCtoB)) || ((solarWindow == SystemWindow.INVERTER) && (!addedCtoI))) {
                    addLink(solarState);
                } else {
                    deleteLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });
        if (((solarWindow == SystemWindow.SOLAR_CELL) && (!addedStoI)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (!addedCtoI))
                || ((solarWindow == SystemWindow.BATTERY) && (!addedBtoI))) {
            imageLink3.setRegion(Assets.instance.buttonInverterAdd);
            labelSolarCell3.setText("เชื่อมต่อไปยังเครื่องแปลงกระแสไฟ");
        } else if (((solarWindow == SystemWindow.SOLAR_CELL) && (addedStoI)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (addedCtoI))
                || ((solarWindow == SystemWindow.BATTERY) && (addedBtoI))) {
            imageLink3.setRegion(Assets.instance.buttonInverterDel);
            String textInverter2 = "ยกเลิกการเชื่อมต่อไปยังเครื่องแปลงกระแสไฟ";
            labelSolarCell3.setText(textInverter2);
        } else if ((solarWindow == SystemWindow.INVERTER) && (!addedBtoI)) {
            imageLink3.setRegion(Assets.instance.buttonBatteryAdd);
            labelSolarCell3.setText("เชื่อมต่อไปยังแบตเตอรี");
        } else {
            imageLink3.setRegion(Assets.instance.buttonBatteryDel);
            labelSolarCell3.setText(textBattery2);
        }
        solarCellButton3.clearListeners();
        solarCellButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((solarWindow == SystemWindow.SOLAR_CELL)) {
                    solarState = SolarState.StoI;
                } else if ((solarWindow == SystemWindow.CHARGE_CONTROLLER)) {
                    solarState = SolarState.CtoI;
                } else {
                    solarState = SolarState.BtoI;
                }
                if (((solarWindow == SystemWindow.SOLAR_CELL) && (!addedStoI)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (!addedCtoI))
                        || ((solarWindow == SystemWindow.BATTERY) && (!addedBtoI))) {
                    addLink(solarState);
                } else if (((solarWindow == SystemWindow.SOLAR_CELL) && (addedStoI)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (addedCtoI))
                        || ((solarWindow == SystemWindow.BATTERY) && (addedBtoI))) {
                    deleteLink(solarState);
                } else if ((solarWindow == SystemWindow.INVERTER) && (!addedBtoI)) {
                    addLink(solarState);
                } else {
                    deleteLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });
        if (((solarWindow == SystemWindow.SOLAR_CELL) && (!addedStoD)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (!addedCtoD))
                || ((solarWindow == SystemWindow.BATTERY) && (!addedBtoD)) || ((solarWindow == SystemWindow.INVERTER) && (!addedItoD))) {
            imageLink4.setRegion(Assets.instance.buttonDoorAdd);
            labelSolarCell4.setText("เชื่อมต่อไปยังสถานที่หลบภัย");
        } else {
            imageLink4.setRegion(Assets.instance.buttonDoorDel);
            String textDoor2 = "ยกเลิกการเชื่อมต่อไปยังสถานที่หลบภัย";
            labelSolarCell4.setText(textDoor2);

        }
        solarCellButton4.clearListeners();
        solarCellButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((solarWindow == SystemWindow.SOLAR_CELL)) {
                    solarState = SolarState.StoD;
                } else if ((solarWindow == SystemWindow.CHARGE_CONTROLLER)) {
                    solarState = SolarState.CtoD;
                } else if (solarWindow == SystemWindow.BATTERY) {
                    solarState = SolarState.BtoD;
                } else {
                    solarState = SolarState.ItoD;
                }
                if (((solarWindow == SystemWindow.SOLAR_CELL) && (!addedStoD)) || ((solarWindow == SystemWindow.CHARGE_CONTROLLER) && (!addedCtoD))
                        || ((solarWindow == SystemWindow.BATTERY) && (!addedBtoD)) || ((solarWindow == SystemWindow.INVERTER) && (!addedItoD))) {
                    addLink(solarState);
                } else {
                    deleteLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });
        solarCellWindow.pack();
    }

    private void checkDeleteLink(SolarState solarState) {
        if (solarState == SolarState.StoC) {
            addedStoC = false;
        } else if (solarState == SolarState.StoB) {
            addedStoB = false;
        } else if (solarState == SolarState.StoI) {
            addedStoI = false;
        } else if (solarState == SolarState.StoD) {
            addedStoD = false;
        } else if (solarState == SolarState.CtoB) {
            addedCtoB = false;
        } else if (solarState == SolarState.CtoI) {
            addedCtoI = false;
        } else if (solarState == SolarState.CtoD) {
            addedCtoD = false;
        } else if (solarState == SolarState.BtoI) {
            addedBtoI = false;
        } else if (solarState == SolarState.BtoD) {
            addedBtoD = false;
        } else if (solarState == SolarState.ItoD) {
            addedItoD = false;
        }
    }

    private void addGuiLink(SolarState solarState) {

        Level1 level1 = (Level1) worldController.level;

        if (solarState == SolarState.StoC) {
            addedStoC = true;
            ItemLink.linkItem(worldController,
                    level1.solarCell1,
                    level1.charge,
                    solarState);
            missionWindow.setCompleted(true, 4);
        } else if (solarState == SolarState.StoB) {
            addedStoB = true;
            ItemLink.linkItem(worldController,
                    level1.solarCell1,
                    level1.battery,
                    solarState);
        } else if (solarState == SolarState.StoI) {
            addedStoI = true;
            ItemLink.linkItem(worldController,
                    level1.solarCell1,
                    level1.inverter,
                    solarState);
        } else if (solarState == SolarState.StoD) {
            addedStoD = true;
            ItemLink.linkItem(worldController,
                    level1.solarCell1,
                    level1.door,
                    solarState);
        } else if (solarState == SolarState.CtoB) {
            addedCtoB = true;
            ItemLink.linkItem(worldController,
                    level1.charge,
                    level1.battery,
                    solarState);
            missionWindow.setCompleted(true, 5);
        } else if (solarState == SolarState.CtoI) {
            addedCtoI = true;
            ItemLink.linkItem(worldController,
                    level1.charge,
                    level1.inverter,
                    solarState);
            missionWindow.setCompleted(true, 6);
        } else if (solarState == SolarState.CtoD) {
            addedCtoD = true;
            ItemLink.linkItem(worldController,
                    level1.charge,
                    level1.door,
                    solarState);
        } else if (solarState == SolarState.BtoI) {
            addedBtoI = true;
            ItemLink.linkItem(worldController,
                    level1.battery,
                    level1.inverter,
                    solarState);
        } else if (solarState == SolarState.BtoD) {
            addedBtoD = true;
            ItemLink.linkItem(worldController,
                    level1.battery,
                    level1.door,
                    solarState);
        } else if (solarState == SolarState.ItoD) {
            addedItoD = true;
            ItemLink.linkItem(worldController,
                    level1.inverter,
                    level1.door,
                    solarState);
            missionWindow.setCompleted(true, 7);
        }
    }

    private void removeGuiLink(SolarState solarState) {
        for (int i = 0; i < worldController.level.links.size(); i++) {
            if (worldController.level.links.get(i).solarState == solarState) {
                System.out.print(worldController.level.links.get(i).solarState);
                if (solarState == SolarState.StoC) {
                    missionWindow.setCompleted(false, 4);
                } else if (solarState == SolarState.CtoB) {
                    missionWindow.setCompleted(false, 5);
                } else if (solarState == SolarState.CtoI) {
                    missionWindow.setCompleted(false, 6);
                } else if (solarState == SolarState.ItoD) {
                    missionWindow.setCompleted(false, 7);
                }
                worldController.level.links.remove(i);
                i--;
            }
        }
    }

    private void checkGameComplete() {
        trueLink = 0;
        if ((link.size() != 0) && (link.size() <= 4)) {
            for (SolarState aLink : link) {
                for (SolarState anIsComplete : isComplete) {
                    if (aLink == anIsComplete) {
                        trueLink += 1;
                        System.out.println(trueLink);
                    }
                }
            }
        }
    }

    private void controlAndDebug() {

        Player player = worldController.level.player;

        if (Gdx.input.isKeyJustPressed(Keys.M)) {
            MusicManager.instance.stop();
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MenuScreen(game));
                    EnergyProducedBar.instance.energyProduced = 0;
                    EnergyUsedBar.instance.energyUse = 0;
                    BatteryBar.instance.batteryStorage = 0;
                    LikingBar.instance.liking = 0;
                }
            });
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            for (Enemy enemy : worldController.level.enemies) {
                enemy.getStateMachine().changeState(EnemyState.DIE);
            }
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            for (int i = 0; i < worldController.level.citizens.size(); i++) {
                Citizen citizen = worldController.level.citizens.get(i);
                if (!citizen.runPlayer) {
                    dialogCitizen = true;
                    citizen.runPlayer = true;
                    citizenCount += 1;
                    LikingBar.instance.liking += 1;
                }
            }
        }

        if (!worldController.level.player.isAlive()) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new GameOverScreen(game));
                    EnergyProducedBar.instance.energyProduced = 0;
                    EnergyUsedBar.instance.energyUse = 0;
                    BatteryBar.instance.batteryStorage = 0;
                    LikingBar.instance.liking = 0;
                }
            });
        }

        if (worldController.level.player.timeCount <= 0) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new GameOverScreen(game));
                    EnergyProducedBar.instance.energyProduced = 0;
                    EnergyUsedBar.instance.energyUse = 0;
                    BatteryBar.instance.batteryStorage = 0;
                    LikingBar.instance.liking = 0;
                }
            });
        }

        if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                if (dialogShow) {
                    dialog.hide();
                    dialogShow = false;
                    player.timeStop = false;
                    player.status_find = false;
                    buttonAgree.setVisible(false);
                    buttonRefuse.setVisible(false);
                    dialogDoor1 = false;
                    dialogDoor2 = false;
                    dialogDoor3 = false;
                    dialogDoor4 = false;
                    trapShow = false;
                    swordShow = false;
                }
            } else {
                dialog.tryToChangePage();
            }
        }
    }

    private void textIconDraw() {
        textSun.setText(String.format(" %d", (int) SunBar.instance.sunTime) + " นาฬิกา");
        textTemperature.setText(String.format(" %d", (int) TemperatureBar.instance.Temperature));
        textBeam.setText(String.format(" %d", (int) SwordWaveBar.instance.energySwordWave) + " วัตต์");
        textTrap.setText(String.format(" %d", (int) TrapBar.instance.energyTrap) + " วัตต์");
        textTime.setText(String.format(" %d", worldController.level.player.timeCount) + " วินาที");
        textLiking.setText(String.format(" %d", (int) LikingBar.instance.liking));

        if (animation_status) {
            EnergyProducedBar.instance.energyProduced = 2700;
            textEnergyProduce.setText(String.format(" %d", (int) EnergyProducedBar.instance.energyProduced) + " วัตต์");
        } else {
            textEnergyProduce.setText(String.format(" %d", 0) + " วัตต์");
        }
        textEnergyUse.setText(String.format(" %d", (int) EnergyUsedBar.instance.energyUse) + " วัตต์");
        textEnergyStored.setText(String.format(" %d", (int) BatteryBar.instance.getBatteryStorage()) + " จูล");
    }

    private void dialogDraw() {

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        if (!dialogStart) {
            dialogAll();
            String text = "\"จากข้อมูลที่ได้รับมา สถานที่หลบภัยต้องอยู่ภายในพื้นที่แถบนี้ รีบเร่งมือค้นหาทางเข้าภายในเวลาที่กำหนด\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเริ่มเกม)\"";
            dialog.addWaitingPage(text);
            dialogStart = true;
            delayMission();
            timeEvent = player.timeCount - 1;
        }

        if (player.requestTrap && !dialogTrap) {
            player.requestTrap = false;
            trapShow = true;
            dialogAll();
            String text =
                    "\"คุณต้องการวางกับดักหรือไม่ กับดัก 1 อันใช้กำลังไฟฟ้า 100 วัตต์ เมื่อกับดักถูกทำลายถึงจะได้กำลังไฟฟ้าที่ใช้อยู่คืน\" \n\"(กดปุ่มตกลงเพื่อวางกับดัก หรือกดปุ่มปฎิเสธเมื่อไม่ต้องการวางกับดัก)\"";
            buttonAgree.setVisible(true);
            buttonRefuse.setVisible(true);
            dialog.addWaitingPage(text);
        } else if (player.requestTrap && dialogTrap) {
            player.requestTrap = false;
            player.acceptTrap = true;
            trapShow = false;
        }

        if (player.requestSwordWave && !dialogSwordWave) {
            player.requestSwordWave = false;
            swordShow = true;
            dialogAll();
            String text =
                    "\"คุณต้องการใช้ท่าคลื่นดาบหรือไม่ ใช้ 1 ครั้ง เสียกำลังไฟฟ้า 1000 วัตต์ เป็นเวลา 10 วินาที\" \n\"(กดปุ่มตกลงเพื่อใช้ท่าคลื่นดาบ หรือกดปุ่มปฎิเสธเมื่อไม่ต้องการใช้)\"";
            buttonAgree.setVisible(true);
            buttonRefuse.setVisible(true);
            dialog.addWaitingPage(text);
        } else if (player.requestSwordWave && dialogSwordWave) {
            player.requestSwordWave = false;
            player.acceptSwordWave = true;
            swordShow = false;
        }

        if ((level1.door.nearPlayer()) && (player.status_find)) {
            if (!animation_status) {
                if (!player.stageOneClear && !dialogDoor1) {
                    dialogDoor1 = true;
                    dialogAll();
                    String text =
                            "\"อันตราย! โปรดกำจัดมอนสเตอร์ให้หมดก่อน แล้วประชาชนที่ซ่อนตัวอยู่จะปรากฏตัวออกมา\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นตอ)\"";
                    dialog.addWaitingPage(text);
                    delayMission();
                } else if (player.stageOneClear && !stageTwoClear && !dialogDoor2) {
                    dialogDoor2 = true;
                    dialogAll();
                    String text =
                            "\"ยังตามหาประชาชนที่ซ่อนตัวอยู่ไม่ครบ กรุณาตามหาให้ครบก่อน (ทำได้โดยกดคุยกับประชาชน)\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นตอ)\"";
                    dialog.addWaitingPage(text);
                    delayMission();
                } else if (stageTwoClear && !stageThreeClear && !dialogDoor3) {
                    dialogDoor3 = true;
                    dialogAll();
                    String text =
                            "\"ไม่มีพลังงานขับเคลื่อนประตู กรุณาเชื่อมต่อระบบโซล่าเซลล์เพื่อผลิตพลังงานเข้าสู่สถานที่หลบภัย\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นตอ)\"";
                    dialog.addWaitingPage(text);
                    stageTwoAfter = true;
                    timeEvent = player.timeCount - 1;
                    missionStart = false;
                    delayMission();
                    missionWindow.setCompleted(true, 2);
                    missionWindow.setText("ภารกิจที่สี่ เชื่อมต่อระบบโซล่าเซลล์ (ทำได้โดยกดปุ่มคุยกับไอเท็มแล้วเลือกการเชื่อมต่อ)", 3);
                    missionWindow.setText("ภารกิจที่สี่ - หนึ่ง เชื่อมต่อโซล่าเซลล์กับตัวควบคุมการชาร์จ", 4);
                    missionWindow.setText("ภารกิจที่สี่ - สอง เชื่อมต่อตัวควบคุมการชาร์จกับแบตเตอรี่", 5);
                    missionWindow.setText("ภารกิจที่สี่ - สาม เชื่อมต่อตัวควบคุมการชาร์จกับเครื่องแปลงกระแสไฟ", 6);
                    missionWindow.setText("ภารกิจที่สี่ - สี่ เชื่อมต่อเครื่องแปลงกระแสไฟกับสถานที่หลบภัย", 7);
                }
            } else if (!dialogDoor4 && stageFourClear) {
                dialogDoor4 = true;
                dialogAll();
                String text =
                        "\"ยินดีต้อนรับสู่พื้นที่หลบภัย\" \n\"(กดปุ่มตกลงเพื่อเข้าไปยังสถานที่หลบภัย หรือกดปุ่มปฎิเสธเพื่อบันทึกและออกไปหน้าเมนู)\"";
                buttonAgree.setVisible(true);
                buttonRefuse.setVisible(true);
                dialog.addWaitingPage(text);
            }
        }
        if (player.stageOneClear && !dialogCitizen) {
            level1.enemies.clear();
            dialogCitizen = true;
            dialogAll();
            timeEvent = player.timeCount - 1;
            missionStart = false;
            String text =
                    "\"กำจัดมอนสเตอร์หมดแล้ว กรุณาตามหาประชาชนทั้งหมด (ทำได้เดินไปติดกับประชาชนและกดคุย)\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นตอ)\"";
            dialog.addWaitingPage(text);
            missionWindow.setCompleted(true, 0);
            missionWindow.setText("ภารกิจที่สอง ตามหาประชาชนที่อยู่ในพื้นที่นี้ให้ครบ (ทำได้โดยเดินไปติดกับประชาชนและกดคุย)", 1);
            delayMission();
        }

        if (player.timeCount <= 298 && !dialogEnemy && dialogStart) {
            for (int i = 0; i < worldController.level.enemies.size(); i++) {
                Enemy enemy = worldController.level.enemies.get(i);
                if (enemy.stateMachine.getCurrentState() == EnemyState.RUN_TO_PLAYER && !enemy.count) {
                    dialogEnemy = true;
                    dialogAll();
                    String text =
                            "\"ได้ยินเสียงของอะไรบางอย่างกำลังเคลื่อนไหวใกล้เข้ามา\" \n\"(กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.addWaitingPage(text);
                }
            }
        }

        if (player.stageOneClear && !stageTwoClear) {
            for (int i = 0; i < worldController.level.citizens.size(); i++) {
                Citizen citizen = worldController.level.citizens.get(i);
                if (citizen.overlapPlayer && !citizen.runPlayer) {
                    if (player.status_find) {
                        dialogCitizen = true;
                        dialogAll();
                        String text =
                                "\"ตามมา เราจะพาท่านไปยังสถานที่ปลอดภัย\" \n\"(กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                        LikingBar.instance.liking += 1;
                        citizen.runPlayer = true;
                        citizenCount += 1;
                        dialog.addWaitingPage(text);
                    }
                }
            }
        }

        if (citizenCount == worldController.level.citizens.size() && !dialogCitizen2) {
            dialogCitizen2 = true;
            stageTwoClear = true;
            dialogAll();
            timeEvent = player.timeCount - 1;
            missionStart = false;
            String text =
                    "\"รวบรวมประชาชนได้ครบแล้ว ลองไปตรวจสอบที่ประตูทางเข้าสถานที่หลบภัยอีกรอบ\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นตอ)\"";

            missionWindow.setCompleted(true, 1);
            missionWindow.setText("ภารกิจที่สาม พาประชาชนมายังสถานที่หลบภัย (เมื่อมาถึงให้กดคุยกับประตู)", 2);
            dialog.addWaitingPage(text);
            delayMission();
        }

        if (trueLink == 4 && !animation_status) {
            for (Item item : level1.items) {
                item.state = Item.ItemState.ONLOOP;
                item.resetAnimation();
            }
            stageThreeClear = true;
            level1.door.state = Item.ItemState.ON;
            animation_status = true;
            dialogAll();
            timeEvent = player.timeCount - 1;
            String text =
                    "\"ยอดเยี่ยม ประตูทางเข้าที่หลบภัยได้เปิดขึ้นแล้ว พาประชาชนเข้าไปสถานที่หลบภัยกันเถอะ\" \n\"(กด     เพื่อดูข้อมูลการใช้พลังงาน หรือกด Enter เพื่อเล่นตอ)\"";
            dialog.addWaitingPage(text);

            missionWindow.setCompleted(true, 3);
            missionWindow.setCompleted(true, 4);
            missionWindow.setCompleted(true, 5);
            missionWindow.setCompleted(true, 6);
            missionWindow.setCompleted(true, 7);
            missionWindow.setText("ภารกิจทั้งหมดเสร็จสิ้น สามารถเข้าไปยังพื้นที่ที่หลบภัยได้แล้ว (กดคุยกับประตูเพื่อเข้าไปยังที่หลบภัย)", 8);
            delayStatus();
        }

        if (player.timeCount <= timeEvent && !missionStart) {
            missionStart = true;
            missionWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            System.out.print("หยุด");
            player.timeStop = true;
        }

        if (player.timeCount <= timeEvent && !statusStart && stageThreeClear) {
            System.out.print("status");
            statusStart = true;
            statusWindow.show(worldController);
            player.timeStop = true;
        }
    }

    private void checkStageAndCount() {

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;
        if (enemyKilled == worldController.level.enemies.size() || worldController.level.enemies.size() == 0) {
            player.stageOneClear = true;
        }

        if (player.stageOneClear && citizenCount == level1.citizens.size() && !stageTwoClear) {
            stageTwoClear = true;
        }

        for (int i = 0; i < worldController.level.enemies.size(); i++) {
            Enemy enemy = worldController.level.enemies.get(i);
            if (enemy.stateMachine.getCurrentState() == EnemyState.DIE && !enemy.count) {
                BatteryBar.instance.addEnergy(1000);
                enemy.count = true;
                enemyKilled += 1;
            }
        }
    }

    private void checkWindow() {
        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        solarCellWindow.setPosition(
                stage.getWidth() / 2 - solarCellWindow.getWidth() / 2,
                stage.getHeight() / 2 - solarCellWindow.getHeight() / 2);
        if (!animation_status && player.status_find && stageTwoAfter) {
            if (level1.solarCell1.nearPlayer()) {
                checkButton(SystemWindow.SOLAR_CELL);
                solarCellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.charge.nearPlayer()) {
                checkButton(SystemWindow.CHARGE_CONTROLLER);
                solarCellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.battery.nearPlayer()) {
                checkButton(SystemWindow.BATTERY);
                solarCellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.inverter.nearPlayer()) {
                checkButton(SystemWindow.INVERTER);
                solarCellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else {
                player.status_find = false;
                player.status_windows_link = false;
                solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        }

        if (animation_status && stageThreeClear && !dialogDoor4 && level1.door.nearPlayer() && player.status_find) {
            chartWindow.show(worldController, enemyKilled);
        }
    }

    private void checkObject() {

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        boolean noItem = true;

        for (Item item : level1.items) {
            if (item.nearPlayer()) {
                noItem = false;
                break;
            }
        }

        boolean noCitizen = !player.questScreen1
                && !player.questScreen2
                && !player.questScreen3
                && !player.questScreen4
                && !player.questScreen5
                && !player.questScreen6;

        Vector2 iconPos = new Vector2(player.getPositionX(), player.getPositionY());
        worldRenderer.viewport.project(iconPos);
        iconPos.y = Gdx.graphics.getHeight() - 1 - iconPos.y;
        stage.screenToStageCoordinates(iconPos);

        iconHuman.setPosition(iconPos.x, iconPos.y + 50);
        iconItem.setPosition(iconPos.x, iconPos.y + 50);
        iconEnergyLess.setPosition(iconPos.x, iconPos.y + 50);

        for (Citizen citizen : level1.citizens) {
            if (player.bounds.overlaps(citizen.bounds) && !citizen.runPlayer) {
                iconHuman.setVisible(true);
            }
        }

        if (!noItem) {
            if (stageTwoAfter || level1.door.nearPlayer()) {
                iconItem.setVisible(true);
            }
        }

        if (player.energyLess) {
            iconEnergyLess.setVisible(true);
            delay();
        }

        if (stageThreeClear && !level1.door.nearPlayer()) {
            iconItem.setVisible(false);
        }

        if (noItem && noCitizen) {
            iconHuman.setVisible(false);
            iconItem.setVisible(false);
            player.status_find = false;
            player.status_windows_link = false;
            solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
        }
    }

    private void dialogAll() {
        dialog.show();
        dialog.clearPages();
        dialogShow = true;
        worldController.level.player.timeStop = true;
    }

    public void delay() {
        float delay = 0.3f; // seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                iconEnergyLess.setVisible(false);
                worldController.level.player.energyLess = false;
            }
        }, delay);
    }

    private void delayMission() {
        float delay = 2f; // seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                iconMission.setVisible(true);
            }
        }, delay);
    }

    private void delayStatus() {
        float delay = 1.5f; // seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                iconStatus.setVisible(true);
            }
        }, delay);
    }

    private void delayGuide() {
        float delay = 1.5f; // seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                iconGuide.setVisible(true);
            }
        }, delay);
    }

    @Override
    public void render(float deltaTime) {

        Player player = worldController.level.player;

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkObject();
        controlAndDebug();
        textIconDraw();
        dialogDraw();
        checkStageAndCount();
        checkWindow();

        if (!player.timeStop && !player.timeClear) {
            SunBar.instance.timeCount += 1 * deltaTime;
        }

        for (Weapon weapon : worldController.level.weapons) {
            if (weapon instanceof Trap) {
                if (weapon.isDestroyed()) {
                    EnergyUsedBar.instance.energyUse -= TrapBar.instance.energyTrap;
                }
            }
            if (weapon instanceof SwordWave) {
                if (weapon.isDestroyed()) {
                    EnergyUsedBar.instance.energyUse -= SwordWaveBar.instance.energySwordWave;
                }
            }
        }

        if (SunBar.instance.timeCount >= 60) {
            SunBar.instance.sunTime += 1;
            SunBar.instance.timeCount = 0;
        }

        if (!dialogShow) {
            iconMission.setVisible(false);
            iconStatus.setVisible(false);
            iconGuide.setVisible(false);
            iconControl.setVisible(false);
        }

        if (!worldController.level.player.timeStop && !worldController.level.player.timeClear) {
            BatteryBar.instance.update(deltaTime);
        }

        worldController.update(Gdx.graphics.getDeltaTime()); //อัพเดท Game World
        worldRenderer.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        GameSaveManager.instance.gameScreen = this;
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        worldController = new WorldController(new Level1());
        worldRenderer = new WorldRenderer(worldController);
        worldController.worldRenderer = worldRenderer;
        worldController.touchPad = touchPad;
        worldController.swordAttackButton = swordAttackButton;
        worldController.swordWaveAttackButton = swordWaveAttackButton;
        worldController.trapAttackButton = trapAttackButton;
        worldController.talkButton = talkButton;
        Gdx.input.setInputProcessor(stage);

        MusicManager.instance.stop();
        MusicManager.instance.play(MusicManager.Musics.MUSIC_2, true);

        createButton();
    }

    @Override
    public void hide() {
        stage.dispose();
        worldRenderer.dispose();
    }

    @Override
    public WorldController getWorldController() {
        return worldController;
    }

    @Override
    public Window getOptionWindow() {
        return optionsWindow;
    }
    @Override
    public void write(Json json) {
        json.writeValue("missionWindow", missionWindow);
        json.writeValue("solarState", solarState);
        json.writeValue("link", link);
        json.writeValue("imageLink1", SolarLinkImage.valueOf(imageLink1.getRegion()));
        json.writeValue("imageLink2", SolarLinkImage.valueOf(imageLink2.getRegion()));
        json.writeValue("imageLink3", SolarLinkImage.valueOf(imageLink3.getRegion()));
        json.writeValue("imageLink4", SolarLinkImage.valueOf(imageLink4.getRegion()));
        json.writeValue("animation_status", animation_status);
        json.writeValue("addedStoC", addedStoC);
        json.writeValue("addedStoB", addedStoB);
        json.writeValue("addedStoI", addedStoI);
        json.writeValue("addedStoD", addedStoD);
        json.writeValue("addedCtoB", addedCtoB);
        json.writeValue("addedCtoI", addedCtoI);
        json.writeValue("addedCtoD", addedCtoD);
        json.writeValue("addedBtoI", addedBtoI);
        json.writeValue("addedBtoD", addedBtoD);
        json.writeValue("addedItoD", addedItoD);
        json.writeValue("dialogStart", dialogStart);
        json.writeValue("dialogTrap", dialogTrap);
        json.writeValue("dialogSwordWave", dialogSwordWave);
        json.writeValue("enemyKilled", enemyKilled);
        json.writeValue("trueLink", trueLink);
        json.writeValue("citizenCount", citizenCount);
        json.writeValue("controlShow", controlShow);
        json.writeValue("timeEvent", timeEvent);
        json.writeValue("stageFourClear", stageFourClear);
        json.writeValue("dialogCitizen2", dialogCitizen2);
        json.writeValue("stageTwoAfter", stageTwoAfter);
        json.writeValue("guideShow", guideShow);
        json.writeValue("missionStart", missionStart);
        json.writeValue("trapShow", trapShow);
        json.writeValue("swordShow", swordShow);
        json.writeValue("statusStart", statusStart);
        json.writeValue("dialogEnemy", dialogEnemy);
        json.writeValue("dialogCitizen", dialogCitizen);
        json.writeValue("dialogDoor1", dialogDoor1);
        json.writeValue("dialogDoor2", dialogDoor2);
        json.writeValue("dialogDoor3", dialogDoor3);
        json.writeValue("dialogDoor4", dialogDoor4);
        json.writeValue("dialogShow", dialogShow);
        json.writeValue("stageTwoClear", stageTwoClear);
        json.writeValue("stageThreeClear", stageThreeClear);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        missionWindow.read(null, jsonData.get("missionWindow"));
        final String solarStateName = jsonData.getString("solarState");
        solarState = (solarStateName == null ? null : SolarState.valueOf(solarStateName));
        readLink(jsonData);
        imageLink1.setRegion(SolarLinkImage.valueOf(jsonData.getString("imageLink1")).region);
        imageLink2.setRegion(SolarLinkImage.valueOf(jsonData.getString("imageLink2")).region);
        imageLink3.setRegion(SolarLinkImage.valueOf(jsonData.getString("imageLink3")).region);
        imageLink4.setRegion(SolarLinkImage.valueOf(jsonData.getString("imageLink4")).region);
        animation_status = jsonData.getBoolean("animation_status");
        addedStoC = jsonData.getBoolean("addedStoC");
        addedStoB = jsonData.getBoolean("addedStoB");
        addedStoI = jsonData.getBoolean("addedStoI");
        addedStoD = jsonData.getBoolean("addedStoD");
        addedCtoB = jsonData.getBoolean("addedCtoB");
        addedCtoI = jsonData.getBoolean("addedCtoI");
        addedCtoD = jsonData.getBoolean("addedCtoD");
        addedBtoI = jsonData.getBoolean("addedBtoI");
        addedBtoD = jsonData.getBoolean("addedBtoD");
        addedItoD = jsonData.getBoolean("addedItoD");
        dialogStart = jsonData.getBoolean("dialogStart");
        dialogTrap = jsonData.getBoolean("dialogTrap");
        dialogSwordWave = jsonData.getBoolean("dialogSwordWave");
        enemyKilled = jsonData.getInt("enemyKilled");
        trueLink = jsonData.getInt("trueLink");
        citizenCount = jsonData.getInt("citizenCount");
        controlShow = jsonData.getBoolean("controlShow");
        timeEvent = jsonData.getInt("timeEvent");
        stageFourClear = jsonData.getBoolean("stageFourClear");
        dialogCitizen2 = jsonData.getBoolean("dialogCitizen2");
        stageTwoAfter = jsonData.getBoolean("stageTwoAfter");
        guideShow = jsonData.getBoolean("guideShow");
        missionStart = jsonData.getBoolean("missionStart");
        trapShow = jsonData.getBoolean("trapShow");
        swordShow = jsonData.getBoolean("swordShow");
        statusStart = jsonData.getBoolean("statusStart");
        dialogEnemy = jsonData.getBoolean("dialogEnemy");
        dialogCitizen = jsonData.getBoolean("dialogCitizen");
        dialogDoor1 = jsonData.getBoolean("dialogDoor1");
        dialogDoor2 = jsonData.getBoolean("dialogDoor2");
        dialogDoor3 = jsonData.getBoolean("dialogDoor3");
        dialogDoor4 = jsonData.getBoolean("dialogDoor4");
        dialogShow = jsonData.getBoolean("dialogShow");
        stageTwoClear = jsonData.getBoolean("stageTwoClear");
        stageThreeClear = jsonData.getBoolean("stageThreeClear");
    }

    private void readLink(JsonValue jsonData) {
        JsonValue linkJson = jsonData.get("link");
        link.clear();
        for (int i = 0; i < linkJson.size; i++) {
            link.add(SolarState.valueOf(linkJson.get(i).getString("value")));
        }
    }
}
