package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.MusicManager;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level1;
import com.mypjgdx.esg.game.objects.characters.Citizen;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.EnemyState;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.ui.*;
import com.mypjgdx.esg.ui.Dialog;
import com.mypjgdx.esg.utils.ItemLink;
import com.mypjgdx.esg.utils.SolarState;

import java.util.ArrayList;

public class GameScreen extends AbstractGameScreen {

    private Button iconEnergyLess;
    private TextButton.TextButtonStyle buttonPlayStyle;
    private TextureRegionDrawable playUp;
    private Button buttonPlay;
    private Button buttonPause;
    private Button iconHuman;
    private Button iconItem;
    private Button buttonControlWindow;
    private Button buttonControl;
    private Button iconControl;
    private Button buttonMission;
    private Button iconMission;
    private Button buttonGuide;
    private Button iconGuide;
    private Button buttonStatus;
    private Button iconStatus;
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean controlShow = true;
    private ItemLink itemLink;
    SpriteBatch batch;
    public Texture bg;
    private Stage stage;
    private Skin skin;

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ

    private TextButton buttonAgree;
    private TextButton buttonRefuse;

    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelStyle2;
    private Label textBeam;
    private Label textTrap;
    private Label textTime;
    private Label energyLevel;
    private Label energyLevel2;
    private Label energyLevel3;

    private Label textMission1;
    private Label textMission2;
    private Label textMission3;
    private Label textMission4;
    private Label textMission5;
    private Label textMission6;
    private Label textMission7;

    private Label text1;
    private Label text2;
    private Label text3;
    private Label text4;
    private Label text5;
    private Label text6;
    private Label text7;
    private Label text8;
    private Label text9;

    private String textSolarcell = "เชื่อมต่อไปยังแผงโซล่าเซลล์";
    private String textCharge = "เชื่อมต่อไปยังตัวควบคุมการชาร์จ";
    private String textBattery = "เชื่อมต่อไปยังแบตเตอรี";
    private String textInverter = "เชื่อมต่อไปยังเครื่องแปลงกระแสไฟ";
    private String textDoor = "เชื่อมต่อไปยังสถานที่หลบภัย";

    private String textSolarcell2 = "ยกเลิกการเชื่อมต่อไปยังแผงโซล่าเซลล์";
    private String textCharge2 = "ยกเลิกการเชื่อมต่อไปยังตัวควบคุมการชาร์จ";
    private String textBattery2 = "ยกเลิกการเชื่อมต่อไปยังแบตเตอรี";
    private String textInverter2 = "ยกเลิกการเชื่อมต่อไปยังเครื่องแปลงกระแสไฟ";
    private String textDoor2 = "ยกเลิกการเชื่อมต่อไปยังสถานที่หลบภัย";

    private boolean stageFourClear;
    private boolean dialogCitizen2;

    private Label labelSolarCell1;
    private Label labelSolarCell2;
    private Label labelSolarCell3;
    private Label labelSolarCell4;

    private Button solarCellButton1;
    private Button solarCellButton2;
    private Button solarCellButton3;
    private Button solarCellButton4;

    private Label textChart1;
    private Label textChart2;
    private Label textChart3;
    private Label textChart4;
    private Label textChart5;
    private Label textChart6;
    private Label textChart7;

    private Label textSun;
    private Label textTemperature;
    private Label textLiking;
    private boolean stageTwoAfter;
    private Label textMission8;
    private Button buttonGuideWindow;
    private boolean guideShow;
    private boolean missionStart;
    private boolean guideStart;
    private boolean statusStart;

    public enum systemWindow {
        solarcell,
        chargecontroller,
        battery,
        inverter
    }

    private SolarState solarState;
    private systemWindow solarWindow;

    private Window guideWindow;

    private ArrayList<SolarState> link = new ArrayList<SolarState>();
    private ArrayList<SolarState> isComplete = new ArrayList<SolarState>();

    private TextureRegionDrawable imageLink1;
    private TextureRegionDrawable imageLink2;
    private TextureRegionDrawable imageLink3;
    private TextureRegionDrawable imageLink4;

    private Button buttonOption;
    private BitmapFont font;
    private Window optionsWindow;

    private Window solarCellWindow;

    private boolean animation_status = false;

    private Button buttonRule;

    private Window ruleWindow;
    private Window chartWindow;
    private Window statusWindow;

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

    private int countEnemy;

    private int trueLink = 0;

    private Dialog dialog;
    private Texture dialogStory;
    private int citizenCount = 0;

    private Window missionWindow;

    private String text =
            "\"จากข้อมูลที่ได้รับมา สถานที่หลบภัยต้องอยู่ภายในพื้นที่แถบนี้ รีบเร่งมือค้นหาทางเข้าภายในเวลาที่กำหนด\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเริ่มเกม)\"";

    private boolean dialogEnemy;
    private boolean dialogCitizen;
    private boolean dialogDoor1;
    private boolean dialogDoor2;
    private boolean dialogDoor3;
    private boolean dialogDoor4;
    private boolean dialogShow;

    private boolean stageTwoClear;
    private boolean stageThreeClear;

    private TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    private TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();

    private TextureRegionDrawable pauseUp;
    private TextureRegionDrawable toolUp;

    private TextButton.TextButtonStyle buttonToolStyle;
    private TextButton.TextButtonStyle buttonPauseStyle;

    public GameScreen(final Game game, final Window optionsWindow) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = Assets.instance.newFont;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.WHITE);

        dialogStory = new Texture("dialogStory.png");
        dialogStory.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        dialog = new Dialog(font, dialogStory, 65f, 120f);
        dialog.setPosition(
                SCENE_WIDTH / 2 - dialogStory.getWidth() * 0.5f,
                SCENE_HEIGHT / 4 - dialogStory.getHeight() * 0.5f);

        this.optionsWindow = optionsWindow;

        isComplete.add(solarState.StoC);
        isComplete.add(solarState.CtoB);
        isComplete.add(solarState.CtoI);
        isComplete.add(solarState.ItoD);

        createButton();
        batch = new SpriteBatch();
    }

    private void createButton() {

        buttonToolStyle = new TextButton.TextButtonStyle();
        toolUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_tools"));
        buttonToolStyle.up = toolUp;
        buttonToolStyle.down = toolUp.tint(Color.LIGHT_GRAY);
        buttonOption = new Button(buttonToolStyle);
        buttonOption.setPosition(SCENE_WIDTH - 50, SCENE_HEIGHT - 50);

        buttonPauseStyle = new TextButton.TextButtonStyle();
        pauseUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_pause"));
        buttonPauseStyle.up = pauseUp;
        buttonPauseStyle.down = pauseUp.tint(Color.LIGHT_GRAY);
        buttonPause = new Button(buttonPauseStyle);
        buttonPause.setPosition(SCENE_WIDTH - 50, SCENE_HEIGHT - 100);

        buttonPlayStyle = new TextButton.TextButtonStyle();
        playUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_play"));
        buttonPlayStyle.up = playUp;
        buttonPlayStyle.down = playUp.tint(Color.LIGHT_GRAY);
        buttonPlay = new Button(buttonPlayStyle);
        buttonPlay.setPosition(SCENE_WIDTH - 50, SCENE_HEIGHT - 100);

        buttonPlay.setVisible(false);

        TextButton.TextButtonStyle buttonControlStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable ControlUp = new TextureRegionDrawable(Assets.instance.iconControl);
        buttonControlStyle.up = ControlUp;
        buttonControlStyle.down = ControlUp.tint(Color.LIGHT_GRAY);
        buttonControl = new Button(buttonControlStyle);
        buttonControl.setPosition(SCENE_WIDTH - 48, SCENE_HEIGHT - 150);

        iconControl = new Button(buttonControlStyle);
        iconControl.setPosition(SCENE_WIDTH / 6 + 30, 145);

        iconControl.setVisible(false);

        TextButton.TextButtonStyle buttonMissionStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable missionUp = new TextureRegionDrawable(Assets.instance.iconMission);
        buttonMissionStyle.up = missionUp;
        buttonMissionStyle.down = missionUp.tint(Color.LIGHT_GRAY);
        buttonMission = new Button(buttonMissionStyle);
        buttonMission.setPosition(SCENE_WIDTH - 48, SCENE_HEIGHT - 200);

        iconMission = new Button(buttonMissionStyle);
        iconMission.setPosition(SCENE_WIDTH / 6 + 30, 145);
        iconMission.setVisible(false);

        TextButton.TextButtonStyle buttonGuideStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable GuideUp = new TextureRegionDrawable(Assets.instance.iconGuide);
        buttonGuideStyle.up = GuideUp;
        buttonGuideStyle.down = GuideUp.tint(Color.LIGHT_GRAY);
        buttonGuide = new Button(buttonGuideStyle);
        buttonGuide.setPosition(SCENE_WIDTH - 48, SCENE_HEIGHT - 250);

        iconGuide = new Button(buttonGuideStyle);
        iconGuide.setPosition(SCENE_WIDTH / 6 + 30, 145);
        iconGuide.setVisible(false);

        TextButton.TextButtonStyle buttonStatusStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable statusUp = new TextureRegionDrawable(Assets.instance.iconStatus);
        buttonStatusStyle.up = statusUp;
        buttonStatusStyle.down = statusUp.tint(Color.LIGHT_GRAY);
        buttonStatus = new Button(buttonStatusStyle);
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
        buttonGuideWindow.setPosition(SCENE_WIDTH/4, SCENE_HEIGHT/4-10);

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

        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        buttonStyle2.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        buttonStyle2.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        buttonStyle2.font = font;

        buttonAgree = new TextButton("ตกลง", buttonStyle);
        buttonAgree.setWidth(50);
        buttonAgree.setHeight(25);
        buttonAgree.setPosition(SCENE_WIDTH / 6 + 20, 120);

        buttonAgree.setVisible(false);

        buttonRefuse = new TextButton("ปฎิเสธ", buttonStyle2);
        buttonRefuse.setWidth(50);
        buttonRefuse.setHeight(25);
        buttonRefuse.setPosition(SCENE_WIDTH / 4, 120);

        buttonRefuse.setVisible(false);

        chartWindow = createChartWindow();
        chartWindow.setVisible(false);

        statusWindow = createStatusWindow();
        statusWindow.setVisible(false);

        guideWindow = createGuideWindow();
        guideWindow.setVisible(false);

        solarCellWindow = createSolarCellWindow();
        solarCellWindow.setVisible(false);

        missionWindow = createMissionWindow();
        missionWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - missionWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - missionWindow.getHeight() / 2);
        missionWindow.setVisible(false);

        optionsWindow.setVisible(false);

        dialog.hide();

        stage.addActor(dialog);

        stage.addActor(buttonOption);
        stage.addActor(buttonAgree);
        stage.addActor(buttonRefuse);
        stage.addActor(buttonMission);
        stage.addActor(buttonControl);
        stage.addActor(buttonStatus);
        stage.addActor(buttonGuide);
        stage.addActor(buttonControlWindow);
        stage.addActor(iconHuman);
        stage.addActor(iconItem);
        stage.addActor(buttonPlay);
        stage.addActor(buttonPause);
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

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 - optionsWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - optionsWindow.getHeight() / 2);
                optionsWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        buttonPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonPause.setVisible(false);
                buttonPlay.setVisible(true);
                worldController.level.player.timeStop = true;
            }
        });

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonPlay.setVisible(false);
                buttonPause.setVisible(true);
                worldController.level.player.timeStop = false;
            }
        });

        buttonMission.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionStart = true;
                missionWindow.pack();
                missionWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 - missionWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - missionWindow.getHeight() / 2);
                missionWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
                worldController.level.player.timeStop = true;
            }
        });

        iconMission.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionStart = true;
                missionWindow.pack();
                missionWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 - missionWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - missionWindow.getHeight() / 2);
                missionWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
                worldController.level.player.timeStop = true;
            }
        });

        buttonGuide.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guideShow =true;
                if (guideShow) {
                    guideShow = false;
                    buttonGuideWindow.setVisible(false);
                } else {
                    guideShow = true;
                    buttonGuideWindow.setVisible(true);
                }
//                guideWindow.pack();
//                worldController.level.player.timeStop = true;
//                guideWindow.setPosition(
//                        Gdx.graphics.getWidth() / 2 - guideWindow.getWidth() / 2,
//                        Gdx.graphics.getHeight() / 2 - guideWindow.getHeight() / 2);
//                guideWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        iconGuide.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guideShow =true;
                if (guideShow) {
                    guideShow = false;
                    buttonGuideWindow.setVisible(false);
                } else {
                    guideShow = true;
                    buttonGuideWindow.setVisible(true);
                }
//                guideWindow.pack();
//                worldController.level.player.timeStop = true;
//                guideWindow.setPosition(
//                        Gdx.graphics.getWidth() / 2 - guideWindow.getWidth() / 2,
//                        Gdx.graphics.getHeight() / 2 - guideWindow.getHeight() / 2);
//                guideWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        buttonGuideWindow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guideShow =true;
                if (guideShow) {
                    guideShow = false;
                    buttonGuideWindow.setVisible(false);
                } else {
                    guideShow = true;
                    buttonGuideWindow.setVisible(true);
                }
//                guideWindow.pack();
//                worldController.level.player.timeStop = true;
//                guideWindow.setPosition(
//                        Gdx.graphics.getWidth() / 2 - guideWindow.getWidth() / 2,
//                        Gdx.graphics.getHeight() / 2 - guideWindow.getHeight() / 2);
//                guideWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
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
                status();
            }
        });

        iconStatus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statusStart = true;
                status();
            }
        });

        buttonAgree.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonAgree.setVisible(false);
                buttonRefuse.setVisible(false);
                dialog.hide();
                worldController.level.player.timeStop = false;
                dialogShow = false;
                MusicManager.instance.stop();
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new GameScreen2(game, optionsWindow));
                    }
                });
            }
        });

        buttonRefuse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonAgree.setVisible(false);
                buttonRefuse.setVisible(false);
                dialog.hide();
                worldController.level.player.timeStop = false;
                dialogShow = false;
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
        });


        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;


        TextButton.TextButtonStyle buttonSunStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconSun = new TextureRegionDrawable(Assets.instance.iconSun);
        buttonSunStyle.up = iconSun;
        buttonSunStyle.over = iconSun.tint(Color.LIME);
        Button iconSunButton = new Button(buttonSunStyle);
        iconSunButton.setPosition(125, SCENE_HEIGHT - 50);

        textSun = new Label("", skin);
        textSun.setColor(0, 0, 0, 1);
        textSun.setStyle(labelStyle);
        textSun.setFontScale(1f, 1f);
        textSun.setPosition(150, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonTemperatureStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTemperature = new TextureRegionDrawable(Assets.instance.iconTemperature);
        buttonTemperatureStyle.up = iconTemperature;
        buttonTemperatureStyle.over = iconTemperature.tint(Color.LIME);
        Button iconTemperatureButton = new Button(buttonTemperatureStyle);
        iconTemperatureButton.setPosition(225, SCENE_HEIGHT - 50);

        textTemperature = new Label("", skin);
        textTemperature.setColor(0, 0, 0, 1);
        textTemperature.setStyle(labelStyle);
        textTemperature.setFontScale(1f, 1f);
        textTemperature.setPosition(250, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonCircleStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconCircle = new TextureRegionDrawable(Assets.instance.iconCircle);
        buttonCircleStyle.up = iconCircle;
        buttonCircleStyle.over = iconCircle.tint(Color.LIME);
        Button iconCircleButton = new Button(buttonCircleStyle);
        iconCircleButton.setPosition(275, SCENE_HEIGHT - 40);

        TextButton.TextButtonStyle buttonSwordStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconSword = new TextureRegionDrawable(Assets.instance.iconSword);
        buttonSwordStyle.up = iconSword;
        buttonSwordStyle.over = iconSword.tint(Color.LIME);
        Button iconSwordButton = new Button(buttonSwordStyle);
        iconSwordButton.setPosition(300, SCENE_HEIGHT - 50);

        textBeam = new Label("", skin);
        textBeam.setColor(0, 0, 0, 1);
        textBeam.setStyle(labelStyle);
        textBeam.setFontScale(1.f, 1.f);
        textBeam.setPosition(325, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonTrapStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTrap = new TextureRegionDrawable(Assets.instance.iconTrap);
        buttonTrapStyle.up = iconTrap;
        buttonTrapStyle.over = iconTrap.tint(Color.LIME);
        Button iconTrapButton = new Button(buttonTrapStyle);
        iconTrapButton.setPosition(375, SCENE_HEIGHT - 50);

        textTrap = new Label("", skin);
        textTrap.setColor(0, 0, 0, 1);
        textTrap.setStyle(labelStyle);
        textTrap.setFontScale(1f, 1f);
        textTrap.setPosition(400, SCENE_HEIGHT - 42);

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

        energyLevel = new Label("", skin);
        energyLevel.setColor(0, 0, 0, 1);
        energyLevel.setStyle(labelStyle);
        energyLevel.setFontScale(1, 1f);
        energyLevel.setPosition(575, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonEnergyMinusStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconEnergyMinus = new TextureRegionDrawable(Assets.instance.iconEnergyMinus);
        buttonEnergyMinusStyle.up = iconEnergyMinus;
        buttonEnergyMinusStyle.over = iconEnergyMinus.tint(Color.LIME);
        Button iconEnergyMinusButton = new Button(buttonEnergyMinusStyle);
        iconEnergyMinusButton.setPosition(650, SCENE_HEIGHT - 50);

        energyLevel2 = new Label("", skin);
        energyLevel2.setColor(0, 0, 0, 1);
        energyLevel2.setStyle(labelStyle);
        energyLevel2.setFontScale(1, 1f);
        energyLevel2.setPosition(675, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonBatteryStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconBattery = new TextureRegionDrawable(Assets.instance.iconBattery);
        buttonBatteryStyle.up = iconBattery;
        buttonBatteryStyle.over = iconBattery.tint(Color.LIME);
        Button iconBatteryButton = new Button(buttonBatteryStyle);
        iconBatteryButton.setPosition(750, SCENE_HEIGHT - 50);

        energyLevel3 = new Label("", skin);
        energyLevel3.setColor(0, 0, 0, 1);
        energyLevel3.setStyle(labelStyle);
        energyLevel3.setFontScale(1, 1f);
        energyLevel3.setPosition(775, SCENE_HEIGHT - 42);

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

        //  stage.addActor(iconBowButton);
        stage.addActor(iconSwordButton);
        stage.addActor(iconTrapButton);
        stage.addActor(iconTimeButton);
        stage.addActor(iconEnergyPlusButton);
        stage.addActor(iconEnergyMinusButton);
        stage.addActor(iconBatteryButton);
        stage.addActor(iconLikingButton);

        stage.addActor(textSun);
        stage.addActor(textTemperature);
        //    stage.addActor(textBullet);
        stage.addActor(textBeam);
        stage.addActor(textTrap);
        stage.addActor(textTime);
        stage.addActor(energyLevel);
        stage.addActor(energyLevel2);
        stage.addActor(energyLevel3);
        stage.addActor(textLiking);
    }

    private Window createChartWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Button.ButtonStyle buttonChartStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonChartStyle.up = buttonRegion;
        buttonChartStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonChartStyle);

        textChart1 = new Label("สถิติ", skin);
        textChart2 = new Label("", skin);
        textChart3 = new Label("", skin);
        textChart4 = new Label("", skin);
        textChart5 = new Label("", skin);
        textChart6 = new Label("", skin);
        textChart7 = new Label("", skin);

        textChart1.setStyle(labelStyle);
        textChart2.setStyle(labelStyle);
        textChart3.setStyle(labelStyle);
        textChart4.setStyle(labelStyle);
        textChart5.setStyle(labelStyle);
        textChart6.setStyle(labelStyle);
        textChart7.setStyle(labelStyle);

        final Window chartWindow = new Window("ยินดีด้วย คุณได้รับชัยชนะ", style);
        chartWindow.setModal(true);
        chartWindow.padTop(45);
        chartWindow.padLeft(40);
        chartWindow.padRight(40);
        chartWindow.padBottom(20);
        chartWindow.getTitleLabel().setAlignment(Align.center);
        chartWindow.row().padTop(10);
        chartWindow.add(textChart1);
        chartWindow.row().padTop(10);
        chartWindow.add(textChart2);
        chartWindow.row().padTop(10);
        chartWindow.add(textChart3);
        chartWindow.row().padTop(10);
        chartWindow.add(textChart4);
        chartWindow.row().padTop(10);
        chartWindow.add(textChart5);
        chartWindow.row().padTop(10);
        chartWindow.add(textChart6);
        chartWindow.row().padTop(10);
        chartWindow.add(textChart7);
        chartWindow.row().padTop(10);
        chartWindow.add(closeButton).colspan(3);
        chartWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chartWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                stageFourClear = true;
                worldController.level.player.timeStop = true;
                String text =
                        "\"ยินดีต้อนรับสู่่สถานที่หลบภัย\" \n\" (กรุณากด Enter เพื่อไปยังด่านถัดไป หรือกด ESC เพื่อบันทึกและกลับไปหน้าเมนู)\"";
                dialog.show();
                dialog.clearPages();
                dialog.addWaitingPage(text);
                System.out.print(text);
            }
        });

        return chartWindow;
    }

    private Window createStatusWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Button.ButtonStyle buttonChartStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonChartStyle.up = buttonRegion;
        buttonChartStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonChartStyle);
        text1 = new Label("", skin);
        text2 = new Label("", skin);
        text3 = new Label("", skin);
        text4 = new Label("", skin);
        text5 = new Label("", skin);
        text6 = new Label("", skin);
        text7 = new Label("", skin);
        text8 = new Label("", skin);
        text9 = new Label("", skin);

        text1.setStyle(labelStyle);
        text2.setStyle(labelStyle);
        text3.setStyle(labelStyle);
        text4.setStyle(labelStyle);
        text5.setStyle(labelStyle);
        text6.setStyle(labelStyle);
        text7.setStyle(labelStyle);
        text8.setStyle(labelStyle);
        text9.setStyle(labelStyle);

        final Window statusWindow = new Window("ข้อมูลการใช้พลังงานไฟฟ้า", style);
        statusWindow.setModal(true);
        statusWindow.padTop(45);
        statusWindow.padLeft(40);
        statusWindow.padRight(40);
        statusWindow.padBottom(20);
        statusWindow.getTitleLabel().setAlignment(Align.center);
        statusWindow.row().padBottom(10).padTop(10);
        statusWindow.row().padTop(10);
        statusWindow.add(text1);
        statusWindow.row().padTop(10);
        statusWindow.add(text2);
        statusWindow.row().padTop(10);
        statusWindow.add(text3);
        statusWindow.row().padTop(10);
        statusWindow.add(text4);
        statusWindow.row().padTop(10);
        statusWindow.add(text5);
        statusWindow.row().padTop(10);
        statusWindow.add(text6);
        statusWindow.row().padTop(10);
        statusWindow.add(text7);
        statusWindow.row().padTop(10);
        statusWindow.add(text8);
        statusWindow.row().padTop(10);
        statusWindow.add(text9);
        statusWindow.row().padTop(10);
        statusWindow.add(closeButton).colspan(3);
        statusWindow.pack();

//        statusWindow.setSize(500, 500);
//        statusWindow.debugAll();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statusWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.statusEnergyWindow = false;
                worldController.level.player.timeStop = false;
            }
        });

        return statusWindow;
    }

    private Window createGuideWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
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
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Button.ButtonStyle buttonSolarStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonSolarStyle.up = buttonRegion;
        buttonSolarStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonSolarStyle);

        Button.ButtonStyle buttonImageLink1 = new Button.ButtonStyle();
        Button.ButtonStyle buttonImageLink2 = new Button.ButtonStyle();
        Button.ButtonStyle buttonImageLink3 = new Button.ButtonStyle();
        Button.ButtonStyle buttonImageLink4 = new Button.ButtonStyle();

        imageLink1 = new TextureRegionDrawable((Assets.instance.buttonChargeAdd));
        imageLink2 = new TextureRegionDrawable((Assets.instance.buttonBatteryAdd));
        imageLink3 = new TextureRegionDrawable((Assets.instance.buttonInverterAdd));
        imageLink4 = new TextureRegionDrawable((Assets.instance.buttonDoorAdd));

        buttonImageLink1.up = imageLink1;
        buttonImageLink1.down = imageLink1.tint(Color.LIGHT_GRAY);
        buttonImageLink2.up = imageLink2;
        buttonImageLink2.down = imageLink2.tint(Color.LIGHT_GRAY);
        buttonImageLink3.up = imageLink3;
        buttonImageLink3.down = imageLink3.tint(Color.LIGHT_GRAY);
        buttonImageLink4.up = imageLink4;
        buttonImageLink4.down = imageLink4.tint(Color.LIGHT_GRAY);

        solarCellButton1 = new Button(imageLink1);
        solarCellButton2 = new Button(imageLink2);
        solarCellButton3 = new Button(imageLink3);
        solarCellButton4 = new Button(imageLink4);

        labelSolarCell1 = new Label(textCharge, skin);
        labelSolarCell2 = new Label(textBattery, skin);
        labelSolarCell3 = new Label(textInverter, skin);
        labelSolarCell4 = new Label(textDoor, skin);

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

    private Window createMissionWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = font;
        labelStyle2.fontColor = Color.LIME;

        Button.ButtonStyle buttonRuleStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonRuleStyle.up = buttonRegion;
        buttonRuleStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonRuleStyle);

        textMission1 = new Label("ภารกิจแรก ตามหาประตูทางเข้าสถานที่หลบภัยให้พบ พร้อมทั้งกำจัดเหล่ามอนสเตอร์ทั้งหมด", skin);
        textMission2 = new Label("", skin);
        textMission3 = new Label("", skin);
        textMission4 = new Label("", skin);
        textMission5 = new Label("", skin);
        textMission6 = new Label("", skin);
        textMission7 = new Label("", skin);
        textMission8 = new Label("", skin);

        textMission1.setStyle(labelStyle);
        textMission2.setStyle(labelStyle);
        textMission3.setStyle(labelStyle);
        textMission4.setStyle(labelStyle);
        textMission5.setStyle(labelStyle);
        textMission6.setStyle(labelStyle);
        textMission7.setStyle(labelStyle);
        textMission8.setStyle(labelStyle);

        final Window missionWindow = new Window("รายชื่อภารกิจ", style);
        missionWindow.setModal(true);
        //missionWindow.setSkin(skin);
        missionWindow.padTop(45);
        missionWindow.padLeft(40);
        missionWindow.padRight(40);
        missionWindow.padBottom(20);
        missionWindow.getTitleLabel().setAlignment(Align.center);
        missionWindow.row().padTop(10);
        missionWindow.add(textMission1);
        missionWindow.row().padTop(10);
        missionWindow.add(textMission2);
        missionWindow.row().padTop(10);
        missionWindow.add(textMission3);
        missionWindow.row().padTop(10);
        missionWindow.add(textMission4);
        missionWindow.row().padTop(10);
        missionWindow.add(textMission5);
        missionWindow.row().padTop(10);
        missionWindow.add(textMission6);
        missionWindow.row().padTop(10);
        missionWindow.add(textMission7);
        missionWindow.row().padTop(10);
        missionWindow.add(textMission8);
        missionWindow.row().padTop(20);
        missionWindow.add(closeButton).colspan(3).center().bottom();
        missionWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                if (!dialogShow) {
                    worldController.level.player.timeStop = false;
                }
            }
        });

        return missionWindow;
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

    private void checkButton(final systemWindow solarWindow) {
        Level1 level1 = (Level1) worldController.level;
        if (level1.solarCell1.nearPlayer() || level1.solarCell4.nearPlayer() || level1.solarCell7.nearPlayer()){
            solarCellWindow.getTitleLabel().setText("ตัวเลือกการเชื่อมต่อของแผงโซล่าเซลล์");
        } else if (level1.charge.nearPlayer()){
            solarCellWindow.getTitleLabel().setText("ตัวเลือกการเชื่อมต่อของตัวควบคุมการชาร์จ");
        } else if (level1.battery.nearPlayer()){
            solarCellWindow.getTitleLabel().setText("ตัวเลือกการเชื่อมต่อของแบตเตอรี่");
        } else if (level1.inverter.nearPlayer()) {
            solarCellWindow.getTitleLabel().setText("ตัวเลือกการเชื่อมต่อของเครื่องแปลงกระแสไฟ");
        }
        if ((solarWindow == systemWindow.solarcell) && (!addedStoC)) {
            imageLink1.setRegion(Assets.instance.buttonChargeAdd);
            labelSolarCell1.setText(textCharge);
        } else if ((solarWindow == systemWindow.solarcell) && (addedStoC)) {
            imageLink1.setRegion(Assets.instance.buttonChargeDel);
            labelSolarCell1.setText(textCharge2);
        } else if (((solarWindow == systemWindow.chargecontroller) && (!addedStoC))
                || ((solarWindow == systemWindow.battery) && (!addedStoB))
                || ((solarWindow == systemWindow.inverter) && (!addedStoI))) {
            imageLink1.setRegion(Assets.instance.buttonSolarcellAdd);
            labelSolarCell1.setText(textSolarcell);
        } else {
            imageLink1.setRegion(Assets.instance.buttonSolarcellDel);
            labelSolarCell1.setText(textSolarcell2);
        }
        solarCellButton1.clearListeners();
        solarCellButton1.addListener(new ClickListener() {
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
                } else if ((solarWindow == systemWindow.solarcell) && (addedStoC)) {
                    deleteLink(solarState);
                } else if (((solarWindow == systemWindow.chargecontroller) && (!addedStoC))
                        || ((solarWindow == systemWindow.battery) && (!addedStoB))
                        || ((solarWindow == systemWindow.inverter) && (!addedStoI))) {
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

        if (((solarWindow == systemWindow.solarcell) && (!addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoB))) {
            imageLink2.setRegion(Assets.instance.buttonBatteryAdd);
            labelSolarCell2.setText(textBattery);
        } else if (((solarWindow == systemWindow.solarcell) && (addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoB))) {
            imageLink2.setRegion(Assets.instance.buttonBatteryDel);
            labelSolarCell2.setText(textBattery2);
        } else if (((solarWindow == systemWindow.battery) && (!addedCtoB)) || ((solarWindow == systemWindow.inverter) && (!addedCtoI))) {
            imageLink2.setRegion(Assets.instance.buttonChargeAdd);
            labelSolarCell2.setText(textCharge);
        } else {
            imageLink2.setRegion(Assets.instance.buttonChargeDel);
            labelSolarCell2.setText(textCharge2);
        }
        solarCellButton2.clearListeners();
        solarCellButton2.addListener(new ClickListener() {
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
                } else if (((solarWindow == systemWindow.solarcell) && (addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoB))) {
                    deleteLink(solarState);
                } else if (((solarWindow == systemWindow.battery) && (!addedCtoB)) || ((solarWindow == systemWindow.inverter) && (!addedCtoI))) {
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
        if (((solarWindow == systemWindow.solarcell) && (!addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoI))
                || ((solarWindow == systemWindow.battery) && (!addedBtoI))) {
            imageLink3.setRegion(Assets.instance.buttonInverterAdd);
            labelSolarCell3.setText(textInverter);
        } else if (((solarWindow == systemWindow.solarcell) && (addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoI))
                || ((solarWindow == systemWindow.battery) && (addedBtoI))) {
            imageLink3.setRegion(Assets.instance.buttonInverterDel);
            labelSolarCell3.setText(textInverter2);
        } else if ((solarWindow == systemWindow.inverter) && (!addedBtoI)) {
            imageLink3.setRegion(Assets.instance.buttonBatteryAdd);
            labelSolarCell3.setText(textBattery);
        } else {
            imageLink3.setRegion(Assets.instance.buttonBatteryDel);
            labelSolarCell3.setText(textBattery2);
        }
        solarCellButton3.clearListeners();
        solarCellButton3.addListener(new ClickListener() {
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
                } else if (((solarWindow == systemWindow.solarcell) && (addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoI))
                        || ((solarWindow == systemWindow.battery) && (addedBtoI))) {
                    deleteLink(solarState);
                } else if ((solarWindow == systemWindow.inverter) && (!addedBtoI)) {
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
        if (((solarWindow == systemWindow.solarcell) && (!addedStoD)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoD))
                || ((solarWindow == systemWindow.battery) && (!addedBtoD)) || ((solarWindow == systemWindow.inverter) && (!addedItoD))) {
            imageLink4.setRegion(Assets.instance.buttonDoorAdd);
            labelSolarCell4.setText(textDoor);
        } else {
            imageLink4.setRegion(Assets.instance.buttonDoorDel);
            labelSolarCell4.setText(textDoor2);

        }
        solarCellButton4.clearListeners();
        solarCellButton4.addListener(new ClickListener() {
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
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.solarCell1,
                    level1.charge,
                    worldController.level.links, solarState);
            textMission4.setStyle(labelStyle2);
        } else if (solarState == SolarState.StoB) {
            addedStoB = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.solarCell1,
                    level1.battery,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.StoI) {
            addedStoI = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.solarCell1,
                    level1.inverter,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.StoD) {
            addedStoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.solarCell1,
                    level1.door,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.CtoB) {
            addedCtoB = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.charge,
                    level1.battery,
                    worldController.level.links, solarState);
            textMission5.setStyle(labelStyle2);
        } else if (solarState == SolarState.CtoI) {
            addedCtoI = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.charge,
                    level1.inverter,
                    worldController.level.links, solarState);
            textMission6.setStyle(labelStyle2);
        } else if (solarState == SolarState.CtoD) {
            addedCtoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.charge,
                    level1.door,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.BtoI) {
            addedBtoI = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.battery,
                    level1.inverter,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.BtoD) {
            addedBtoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.battery,
                    level1.door,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.ItoD) {
            addedItoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.inverter,
                    level1.door,
                    worldController.level.links, solarState);
            textMission7.setStyle(labelStyle2);
        }
        //System.out.print("ขนาด" + itemLink.linkList.size() + "นะจ๊ะ");
    }

    private void removeGuiLink(SolarState solarState) {
        for (int i = 0; i < itemLink.linkList.size(); i++) {
            if (itemLink.linkList.get(i).solarState == solarState) {
                System.out.print(itemLink.linkList.get(i).solarState);
                itemLink.linkList.remove(i);
                i--;
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
            }
        }
    }

    private void chartStatus() {
        Player player = worldController.level.player;
        player.timeStop = true;
        player.timeClear = true;
        String textString1 = ("เวลาที่ใช้ : " + String.valueOf((player.getIntitalTime() - player.timeCount) + " วินาที"));
        String textString2 = ("มอนสเตอร์ที่ถูกกำจัด : " + String.valueOf((countEnemy) + " ตัว"));
        String textString3 = ("กำลังไฟฟ้าผลิต : " + String.valueOf((EnergyProducedBar.instance.energyProduced) + " วัตต์"));
        String textString4 = ("กำลังไฟฟ้าใช้งานรวม : " + String.valueOf(EnergyUsedBar.instance.energyUse) + " วัตต์");
        String textString5 = ("พลังงานไฟฟ้าที่ได้รับจากมอนสเตอร์ : " + String.valueOf((countEnemy * 1000) + " จูล"));
        String textString6 = ("ความพอใจของประชาชน : " + String.valueOf((LikingBar.instance.liking)));
        textChart2.setText(textString1);
        textChart3.setText(textString2);
        textChart4.setText(textString3);
        textChart5.setText(textString4);
        textChart6.setText(textString5);
        textChart7.setText(textString6);
        chartWindow.pack();
        chartWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - chartWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - chartWindow.getHeight() / 2);
        chartWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    private void status() {

        worldController.level.player.timeStop = true;

        if (EnergyProducedBar.instance.energyProduced == 0) {
            String textString1 = ("ยังไม่เริ่มการผลิตพลังงาน");
            text1.setText(textString1);
        } else {
            String textString1 = ("กำลังไฟฟ้าผลิต : " + String.valueOf((EnergyProducedBar.instance.energyProduced) + " วัตต์"));
            String textString2 = ("กำลังไฟฟ้าใช้งานรวม : " + String.valueOf(EnergyUsedBar.instance.energyUse) + " วัตต์");
            if (EnergyProducedBar.instance.energyProduced < EnergyUsedBar.instance.energyUse) {
                String textString3 = ("อีก : " + String.valueOf((int) (BatteryBar.instance.getBatteryStorage() / (((EnergyProducedBar.instance.energyProduced
                        * SunBar.instance.accelerateTime) - (EnergyUsedBar.instance.energyUse* SunBar.instance.accelerateTime)))) + " วินาทีพลังงานจะหมดลง"));
                text3.setText(textString3);
            } else {
                String textString3 = ("อีก : " + String.valueOf((int) (BatteryBar.instance.getBatteryStorageBlank() / (((EnergyProducedBar.instance.energyProduced
                        * SunBar.instance.accelerateTime) - (EnergyUsedBar.instance.energyUse* SunBar.instance.accelerateTime)))) + " วินาทีพลังงานจะเต็มแบตเตอรี่"));
                text3.setText(textString3);
            }
            String textString4 = ("กำลังไฟฟ้าที่ผลิตได้หลังจากหักลบแล้ว : " + String.valueOf((EnergyProducedBar.instance.energyProduced - EnergyUsedBar.instance.energyUse)) + " วัตต์");
            text1.setText(textString1);
            text2.setText(textString2);
            text4.setText(textString4);
        }
        statusWindow.pack();
        statusWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - statusWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - statusWindow.getHeight() / 2);
        statusWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
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
                    citizen.quest = true;
                    dialogCitizen = true;
                    citizen.runPlayer = true;
                    citizenCount += 1;
                    LikingBar.instance.liking +=1;
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
                }
            } else {
                dialog.tryToChangePage();
            }
        }
    }

    private void textIconDraw() {
        textSun.setText(String.format(" %d", (int) SunBar.instance.sunTime) + " นาฬิกา");
        textTemperature.setText(String.format(" %d", (int) TemperatureBar.instance.Temperature));
        //textBullet.setText(String.format(" %d", (int) ArrowBar.instance.energyArrow));
        textBeam.setText(String.format(" %d", (int) SwordWaveBar.instance.energySwordWave));
        textTrap.setText(String.format(" %d", (int) TrapBar.instance.energyTrap));
        textTime.setText(String.format(" %d", worldController.level.player.timeCount) + " วินาที");
        textLiking.setText(String.format(" %d", (int) LikingBar.instance.liking));

        if (animation_status) {
            EnergyProducedBar.instance.energyProduced = 2700;
            energyLevel.setText(String.format(" %d", (int) EnergyProducedBar.instance.energyProduced) + " วัตต์");
        } else {
            energyLevel.setText(String.format(" %d", 0) + " วัตต์");
        }
        energyLevel2.setText(String.format(" %d", (int) EnergyUsedBar.instance.energyUse) + " วัตต์");
        energyLevel3.setText(String.format(" %d", (int) BatteryBar.instance.getBatteryStorage()) + " จูล");
    }

    private void dialogDraw() {

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        if (!dialogStart) {
            dialogAll();
            dialog.addWaitingPage(text);
            dialogStart = true;
            delayMission();
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
                            "\"ยังตามหาประชาชนที่ซ่อนตัวอยู่ไม่ครบ กรุณาตามหาให้ครบก่อน\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นตอ)\"";
                    dialog.addWaitingPage(text);
                    delayMission();
                } else if (stageTwoClear && !stageThreeClear && !dialogDoor3) {
                    dialogDoor3 = true;
                    dialogAll();
                    String text =
                            "\"ไม่มีพลังงานขับเคลื่อนประตู กรุณาเชื่อมต่อระบบโซล่าเซลล์เพื่อผลิตพลังงานเข้าสู่สถานที่หลบภัย\" \n\"(กด     เพื่ออ่านการทำงานของโซล่าเซลล์ หรือกด Enter เพื่อเล่นตอ)\"";
                    dialog.addWaitingPage(text);
                    stageTwoAfter = true;
                    delayGuide();
                    textMission2.setStyle(labelStyle2);
                    textMission3.setText("ภารกิจที่สาม เชื่อมต่อระบบโซล่าเซลล์เพื่อผลิตพลังงานให้กับสถานที่หลบภัย");
                    textMission4.setText("ภารกิจที่สาม - หนึ่ง เชื่อมต่อโซล่าเซลล์กับตัวควบคุมการชาร์จ");
                    textMission5.setText("ภารกิจที่สาม - สอง เชื่อมต่อตัวควบคุมการชาร์จกับแบตเตอรี่");
                    textMission6.setText("ภารกิจที่สาม - สาม เชื่อมต่อตัวควบคุมการชาร์จกับเครื่องแปลงกระแสไฟ");
                    textMission7.setText("ภารกิจที่สาม - สี่ เชื่อมต่อเครื่องแปลงกระแสไฟกับสถานที่หลบภัย");
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
            dialogCitizen = true;
            dialogAll();
            String text =
                    "\"กำจัดมอนสเตอร์หมดแล้ว กรุณาตามหาประชาชนแล้วพาไปยังสถานที่หลบภัย\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นตอ)\"";
            dialog.addWaitingPage(text);
            textMission1.setStyle(labelStyle2);
            textMission2.setText("ภารกิจที่สอง ตามหาประชาชนและพามายังสถานที่หลบภัย");
            delayMission();
        }

        if (player.timeCount <= 299 && !missionStart){
            missionStart = true;
            missionWindow.setVisible(true);
            player.timeStop = true;
        }

        if (player.timeCount <= 299 && !guideStart && stageTwoAfter){
            guideStart = true;
            guideWindow.setVisible(true);
            player.timeStop = true;
        }

        if (player.timeCount <= 299 && !statusStart && stageThreeClear){
            statusStart = true;
            statusWindow.setVisible(true);
            player.timeStop = true;
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

        if (player.stageOneClear) {
            for (int i = 0; i < worldController.level.citizens.size(); i++) {
                Citizen citizen = worldController.level.citizens.get(i);
                if (citizen.overlapPlayer && !citizen.runPlayer) {
                    if (player.status_find) {
                        citizen.quest =true;
                        dialogCitizen = true;
                        dialogAll();
                        String text =
                                "\"โปรดตามเรามา เราจะพาท่านไปยังสถานที่ปลอดภัย\" \n\"(กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
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
            String text =
                    "\"รวบรวมประชาชนได้ครบแล้ว ลองไปตรวจสอบที่ประตูทางเข้าสถานที่หลบภัยอีกรอบ\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นตอ)\"";
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
            String text =
                    "\"ยอดเยี่ยม ประตูทางเข้าที่หลบภัยได้เปิดขึ้นแล้ว รีบพาประชาชนเข้าไปสถานที่หลบภัยกันเถอะ\" \n\"(กด     เพื่อดูข้อมูลการใช้พลังงาน หรือกด Enter เพื่อเล่นตอ)\"";
            dialog.addWaitingPage(text);
            textMission3.setStyle(labelStyle2);
            textMission4.setStyle(labelStyle2);
            textMission5.setStyle(labelStyle2);
            textMission6.setStyle(labelStyle2);
            textMission7.setStyle(labelStyle2);
            textMission8.setText("ยินดีด้วยคุณทำภารกิจทั้งหมดเสร็จสิ้น สามารถเข้าไปยังพื้นที่ที่หลบภัยได้แล้ว");
            delayStatus();
        }

    }

    private void checkStageAndCount() {

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;
        if (countEnemy == worldController.level.enemies.size() || worldController.level.enemies.size() == 0) {
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
                countEnemy += 1;
            }
        }
    }

    private void checkWindow() {

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        solarCellWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - solarCellWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - solarCellWindow.getHeight() / 2);
        if (!animation_status && player.status_find && stageTwoAfter) {
            if (level1.solarCell1.nearPlayer()) {
                solarWindow = systemWindow.solarcell;
                checkButton(solarWindow);
                solarCellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.charge.nearPlayer()) {
                solarWindow = systemWindow.chargecontroller;
                checkButton(solarWindow);
                solarCellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.battery.nearPlayer()) {
                solarWindow = systemWindow.battery;
                checkButton(solarWindow);
                solarCellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.inverter.nearPlayer()) {
                solarWindow = systemWindow.inverter;
                checkButton(solarWindow);
                solarCellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else {
                player.status_find = false;
                player.status_windows_link = false;
                solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        }

        if (animation_status && stageThreeClear && !dialogDoor4 && level1.door.nearPlayer() && player.status_find) {
            chartStatus();
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
            if (player.bounds.overlaps(citizen.bounds) && !citizen.quest) {
                iconHuman.setVisible(true);
            }
        }

        if (!noItem) {
            if(stageTwoAfter || level1.door.nearPlayer()){
                iconItem.setVisible(true);
            }
        }

        if(player.energyLess){
            iconEnergyLess.setVisible(true);
            delay();
        }

        if (noItem && noCitizen) {
            iconHuman.setVisible(false);
            iconItem.setVisible(false);
            player.status_find = false;
            player.status_windows_link = false;
            solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
        }
    }

    private void dialogAll(){
        dialog.show();
        dialog.clearPages();
        dialogShow =true;
        worldController.level.player.timeStop = true;
    }

    public void delay(){
        float delay = 0.3f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                iconEnergyLess.setVisible(false);
                worldController.level.player.energyLess = false;
            }
        }, delay);
    }

    private void delayMission(){
        float delay = 2f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                iconMission.setVisible(true);
            }
        }, delay);
    }

    private void delayStatus(){
        float delay = 1.5f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                iconStatus.setVisible(true);
            }
        }, delay);
    }

    private void delayGuide(){
        float delay = 1.5f; // seconds
        Timer.schedule(new Timer.Task(){
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

        if(!player.timeStop && !player.timeClear){
            SunBar.instance.timeCount += 1*deltaTime;
        }

        if(SunBar.instance.timeCount >= 60){
            SunBar.instance.sunTime += 1;
            SunBar.instance.timeCount = 0;
        }

        if(!dialogShow){
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
        Gdx.input.setInputProcessor(stage);

        MusicManager.instance.stop();
        MusicManager.instance.play(MusicManager.Musics.MUSIC_2, true);
    }

    @Override
    public void hide() {
        batch.dispose();
        stage.dispose();
        dialogStory.dispose();
        worldRenderer.dispose();
        //font.dispose();
        bg.dispose();
    }

    @Override
    public void pause() {
    }

}
