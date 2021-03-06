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
import com.mypjgdx.esg.utils.MusicManager;
import com.mypjgdx.esg.utils.QuestState;

import java.util.ArrayList;

public class GameScreen extends AbstractGameScreen {

    private Dialog dialog;
    private TextButton buttonAgree;
    private TextButton buttonRefuse;
    private WorldController worldController;
    private WorldRenderer worldRenderer;

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

    private boolean missionStart;
    private boolean guideStart;
    private boolean statusStart;

    SpriteBatch batch;
    public Texture bg;

    private boolean controlShow = true;

    private Stage stage;
    private Skin skin;

    private String titleQuest;

    private Label textSun;
    private Label textTemperature;
    private Label textLiking;

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ

    private Window guideWindow;

    private Label textBullet;
    private Label textBeam;
    private Label textTrap;
    private Label textTime;
    private Label energyLevel;
    private Label energyLevel2;
    private Label energyLevel3;
    private Label textRule;

    private Label textChart1;
    private Label textChart2;
    private Label textChart3;
    private Label textChart4;
    private Label textChart5;
    private Label textChart6;
    private Label textChart7;

    public systemWindow citizenQuest = null;
    private boolean dialogStage4fail;
    private TextureRegionDrawable iconRegion;

    private Label textItem1;
    private Label textItem2;
    private Label textItem3;
    private Label textItem4;
    private Label textItem5;
    private Label textItem6;
    private Label textItem7;
    private Label textItem8;

    private TextureRegionDrawable buttonItem1;
    private TextureRegionDrawable buttonItem2;
    private TextureRegionDrawable buttonItem3;
    private TextureRegionDrawable buttonItem4;
    private TextureRegionDrawable buttonItem5;
    private TextureRegionDrawable buttonItem6;
    private TextureRegionDrawable buttonItem7;
    private TextureRegionDrawable buttonItem8;

    private boolean dialogWarning;
    private int timeEvent;
    private boolean trapShow;
    private boolean swordShow;
    private boolean dialogTrap;
    private boolean dialogSwordWave;
    private Button buttonGuideWindow;
    private boolean guideShow;
    private boolean lose;
    private boolean dialogItem;
    private Item item;
    private Label textBattery;
    private Label textPower;
    private TextButton.TextButtonStyle buttonFoodStyle;
    private TextButton.TextButtonStyle buttonWaterStyle;
    private TextButton.TextButtonStyle buttonTempStyle;
    private TextureRegionDrawable foodUp;
    private TextureRegionDrawable waterUp;
    private TextureRegionDrawable tempUp;
    private Button buttonFood;
    private Button buttonWater;
    private Button buttonTemp;
    private boolean Focus1;
    private boolean tutorFinish;
    private boolean tutorFirst;
    private boolean tutorSecond;
    private boolean tutorThird;
    private boolean tutorFourth;
    private boolean tutorFifth;
    private boolean tutorSixth;
    private boolean tutorSeventh;
    private boolean tutorEighth;

    public enum systemWindow {
        citizen1,
        citizen2,
        citizen3,
        citizen4,
        citizen5,
        citizen6
    }

    private Label textMission1;
    private Label textMission2;
    private Label textMission3;
    private Label textMission4;

    private boolean dialogShow;

    private Texture dialogStory;

    private String text =
            "\"ตามแผนที่ทางเข้าสถานที่หลบภัยน่าจะอยู่ข้างหน้านี้แหละ\" \n\"(กด Enter เพื่อเริ่มเกม)\"";

    public QuestState questState = null;

    private ArrayList<QuestState> isComplete = new ArrayList<QuestState>();
    private ArrayList<QuestState> addRequest = new ArrayList<QuestState>();

    private TextButton buttonLink1;
    private TextButton buttonLink2;

    private Button buttonOption;
    private BitmapFont font;
    private Window optionsWindow;

    private Window chartWindow;

    private boolean animation_status = false;

    private int trueLink = 0;

    private int countEnemy;

    public int enemyDeadCount = 0;
    public boolean stringDraw;

    private Label text1;
    private Label text2;
    private Label text3;
    private Label text4;
    private Label text5;
    private Label text6;

    private Window statusWindow;
    private Window missionWindow;

    private int questCount;
    private float Countdown;

    private boolean dialogEnemy;
    private boolean dialogCitizen;
    private boolean dialogStage1;
    private boolean dialogStage2;
    private boolean dialogStage3;
    private boolean dialogStage4;

    private boolean dialogDoor1;
    private boolean dialogDoor2;
    private boolean dialogDoor3;
    private boolean dialogDoor4;

    private boolean stageTwoClear;
    private boolean stageThreeClear;
    private boolean stageFourClear;

    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelStyle2;

    private TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    private TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();

    private TextureRegionDrawable pauseUp;
    private TextureRegionDrawable toolUp;

    private TextButton.TextButtonStyle buttonToolStyle;
    private TextButton.TextButtonStyle buttonPauseStyle;

    private boolean dialogStart;

    private PlayerTouchPad touchPad;
    private SwordAttackButton swordAttackButton;
    private SwordWaveAttackButton swordWaveAttackButton;
    private TrapAttackButton trapAttackButton;
    private TalkButton talkButton;


    private PowerGauge powerGauge;
    private BatteryGauge batteryGauge;

    public GameScreen(final Game game, final Window optionsWindow) {
        super(game);

        stage = new Stage();

        BatteryBar.instance.batteryStorage = 7000000;

        isComplete.add(QuestState.quest1no);
        isComplete.add(QuestState.quest2no);
        isComplete.add(QuestState.quest3no);
        isComplete.add(QuestState.quest4yes);
        isComplete.add(QuestState.quest5yes);
        isComplete.add(QuestState.quest6no);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = Assets.instance.newFont;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.WHITE);

        touchPad = new PlayerTouchPad();
        stage.addActor(touchPad);
        touchPad.setPosition(20, 20);

        swordAttackButton = new SwordAttackButton();
        stage.addActor(swordAttackButton);
        swordAttackButton.setPosition(stage.getWidth() - swordAttackButton.getWidth() -120, 10);

        swordWaveAttackButton = new SwordWaveAttackButton();
        stage.addActor(swordWaveAttackButton);
        swordWaveAttackButton.setPosition(stage.getWidth() - swordWaveAttackButton.getWidth() - 110, 110);

        trapAttackButton = new TrapAttackButton();
        stage.addActor(trapAttackButton);
        trapAttackButton.setPosition(stage.getWidth() - trapAttackButton.getWidth()-10, 130);

        talkButton = new TalkButton();
        stage.addActor(talkButton);
        talkButton.setPosition(stage.getWidth() - talkButton.getWidth() - 10, 20);

        EnergyProducedBar.instance.energyProduced = 2700;
        LikingBar.instance.liking = 6;
        TemperatureBar.instance.Temperature = 25;

        this.optionsWindow = optionsWindow;

        optionsWindow.setVisible(false);

        dialogStory = new Texture("dialogStory.png");
        dialogStory.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        dialog = new Dialog(font, dialogStory, 150f, 120f);
        dialog.setPosition(
                SCENE_WIDTH / 2 - dialogStory.getWidth() * 0.5f,
                SCENE_HEIGHT / 4 - dialogStory.getHeight() * 0.5f);

        stage.addActor(dialog);

        createButton();
        batch = new SpriteBatch();

        GameSaveManager.instance.gameScreen = this;
    }

    private void createButton() {

        buttonFoodStyle = new TextButton.TextButtonStyle();
        foodUp = new TextureRegionDrawable(Assets.instance.icon_food_off);
        buttonFoodStyle.up = foodUp;
        buttonFoodStyle.down = foodUp.tint(Color.LIME);
        buttonFood = new Button(buttonFoodStyle);
        buttonFood.setPosition(SCENE_WIDTH - SCENE_WIDTH/2.5f, SCENE_HEIGHT - SCENE_HEIGHT/10);

        buttonFood.setVisible(false);

        buttonTempStyle = new TextButton.TextButtonStyle();
        tempUp = new TextureRegionDrawable(Assets.instance.icon_temp_off);
        buttonTempStyle.up = tempUp;
        buttonTempStyle.down = tempUp.tint(Color.LIME);
        buttonTemp = new Button(buttonTempStyle);
        buttonTemp.setPosition(SCENE_WIDTH - SCENE_WIDTH/3f, SCENE_HEIGHT - SCENE_HEIGHT/10);

        buttonTemp.setVisible(false);

        buttonWaterStyle = new TextButton.TextButtonStyle();
        waterUp = new TextureRegionDrawable(Assets.instance.icon_water_off);
        buttonWaterStyle.up = waterUp;
        buttonWaterStyle.down = waterUp.tint(Color.LIME);
        buttonWater = new Button(buttonWaterStyle);
        buttonWater.setPosition(SCENE_WIDTH - SCENE_WIDTH/3.5f, SCENE_HEIGHT - SCENE_HEIGHT/10);

        buttonWater.setVisible(false);

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
        buttonControlWindow.setVisible(false);

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

        buttonStyle.up = new TextureRegionDrawable(Assets.instance.buttonGreen1);
        buttonStyle.down = new TextureRegionDrawable(Assets.instance.buttonGreen2);
        buttonStyle.font = font;

        buttonStyle2.up = new TextureRegionDrawable(Assets.instance.buttonRed1);
        buttonStyle2.down = new TextureRegionDrawable(Assets.instance.buttonRed2);
        buttonStyle2.font = font;

        buttonAgree = new TextButton("ตกลง", buttonStyle);
        buttonAgree.setWidth(50);
        buttonAgree.setHeight(25);
        buttonAgree.setPosition(SCENE_WIDTH - SCENE_WIDTH / 4.2f, SCENE_HEIGHT/3.9f);

        buttonAgree.setVisible(false);

        buttonRefuse = new TextButton("ปฎิเสธ", buttonStyle2);
        buttonRefuse.setWidth(50);
        buttonRefuse.setHeight(25);
        buttonRefuse.setPosition(SCENE_WIDTH - SCENE_WIDTH / 4.2f, SCENE_HEIGHT/5.2f);

        buttonRefuse.setVisible(false);

        chartWindow = createChartWindow();
        chartWindow.setVisible(false);

        statusWindow = createStatusWindow();
        statusWindow.setVisible(false);

        guideWindow = createGuideWindow();
        guideWindow.setVisible(false);

        missionWindow = createMissionWindow();
        missionWindow.setPosition(
                stage.getWidth() / 2 - missionWindow.getWidth() / 2,
                stage.getHeight() / 2 - missionWindow.getHeight() / 2);
        missionWindow.setVisible(false);

        optionsWindow.setVisible(false);

        dialog.hide();

        stage.addActor(dialog);

        stage.addActor(buttonWater);
        stage.addActor(buttonTemp);
        stage.addActor(buttonFood);
        stage.addActor(buttonOption);
        stage.addActor(buttonAgree);
        stage.addActor(buttonRefuse);
//        stage.addActor(buttonMission);
//        stage.addActor(buttonControl);
//        stage.addActor(buttonStatus);
//        stage.addActor(buttonGuide);
        stage.addActor(buttonControlWindow);
        stage.addActor(iconHuman);
        stage.addActor(iconItem);
        stage.addActor(buttonPlay);
        stage.addActor(buttonPause);
        stage.addActor(iconEnergyLess);
//        stage.addActor(iconGuide);
//        stage.addActor(iconControl);
//        stage.addActor(iconMission);
//        stage.addActor(iconStatus);
        stage.addActor(buttonGuideWindow);

        stage.addActor(optionsWindow);
        stage.addActor(chartWindow);
        stage.addActor(statusWindow);
        stage.addActor(guideWindow);
        stage.addActor(missionWindow);

        powerGauge = new PowerGauge();
        stage.addActor(powerGauge);
        powerGauge.setPosition(stage.getWidth()/8f, stage.getHeight()/1.075f, Align.center);
        powerGauge.setPosition((int)powerGauge.getX(), (int)powerGauge.getY());

        batteryGauge = new BatteryGauge();
        stage.addActor(batteryGauge);
        batteryGauge.setPosition(stage.getWidth()/3.5f, stage.getHeight()/1.075f, Align.center);
        batteryGauge.setPosition((int)batteryGauge.getX(), (int)batteryGauge.getY());

        textBattery = new Label("", skin);
        textBattery.setColor(0, 0, 0, 1);
        textBattery.setStyle(labelStyle);
        textBattery.setFontScale(1f, 1f);
        textBattery.setPosition(stage.getWidth()/4f, stage.getHeight()/1.075f);
        textBattery.setPosition((int)textBattery.getX(), (int)textBattery.getY());

        textPower = new Label("", skin);
        textPower.setColor(0, 0, 0, 1);
        textPower.setStyle(labelStyle);
        textPower.setFontScale(1f, 1f);
        textPower.setPosition(stage.getWidth()/9f, stage.getHeight()/1.075f);
        textPower.setPosition((int)textPower.getX(), (int)textPower.getY());

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsWindow.setPosition(
                        stage.getWidth() / 2 - optionsWindow.getWidth() / 2,
                        stage.getHeight() / 2 - optionsWindow.getHeight() / 2);
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
                        stage.getWidth() / 2 - missionWindow.getWidth() / 2,
                        stage.getHeight() / 2 - missionWindow.getHeight() / 2);
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
                        stage.getWidth() / 2 - missionWindow.getWidth() / 2,
                        stage.getHeight() / 2 - missionWindow.getHeight() / 2);
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
                if (guideShow) {
                    guideShow = false;
                    buttonGuideWindow.setVisible(false);
                    worldController.level.player.timeStop = false;
                } else {
                    guideShow = true;
                    buttonGuideWindow.setVisible(true);
                    worldController.level.player.timeStop = true;
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
                if (guideShow) {
                    guideShow = false;
                    buttonGuideWindow.setVisible(false);
                    worldController.level.player.timeStop = false;
                } else {
                    guideShow = true;
                    buttonGuideWindow.setVisible(true);
                    worldController.level.player.timeStop = true;
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
                if (trapShow) {
                    worldController.level.player.acceptTrap = true;
                    worldController.level.player.requestTrap = false;
                    dialogTrap = true;
                } else if (swordShow) {
                    worldController.level.player.acceptSwordWave = true;
                    worldController.level.player.requestSwordWave = false;
                    dialogSwordWave = true;
                } else if (dialogItem) {
                    item.questAccept = true;
                    item.quest = true;
                    item.state = Item.ItemState.OFF;
                    EnergyUsedBar.instance.energyUse -= item.getEnergyBurn();
                    LikingBar.instance.liking -= 6;
                } else if (stageFourClear) {
                    dialogShow = false;
                    dialogDoor4 = false;
                    GameSaveManager.instance.save();
                    MusicManager.instance.stop();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new GameScreen3(game, optionsWindow));
                        }
                    });
                } else {
                    if (citizenQuest == systemWindow.citizen1) {
                        questState = QuestState.quest1yes;
                        LikingBar.instance.liking += 1;
                        worldController.level.player.quest1IsAccept = true;
                        worldController.level.player.quest_window_1 = true;
                    } else if (citizenQuest == systemWindow.citizen2) {
                        questState = QuestState.quest2yes;
                        LikingBar.instance.liking += 1;
                        worldController.level.player.quest_window_2 = true;
                        worldController.level.player.quest2IsAccept = true;
                    } else if (citizenQuest == systemWindow.citizen3) {
                        questState = QuestState.quest3yes;
                        LikingBar.instance.liking += 1;
                        worldController.level.player.quest_window_3 = true;
                        worldController.level.player.quest3IsAccept = true;
                    } else if (citizenQuest == systemWindow.citizen4) {
                        questState = QuestState.quest4yes;
                        LikingBar.instance.liking += 6;
                        worldController.level.player.quest_window_4 = true;
                        worldController.level.player.quest4IsAccept = true;
                    } else if (citizenQuest == systemWindow.citizen5) {
                        questState = QuestState.quest5yes;
                        LikingBar.instance.liking += 6;
                        worldController.level.player.quest_window_5 = true;
                        worldController.level.player.quest5IsAccept = true;
                    } else if (citizenQuest == systemWindow.citizen6) {
                        questState = QuestState.quest6yes;
                        LikingBar.instance.liking += 1;
                        worldController.level.player.quest_window_6 = true;
                        worldController.level.player.quest6IsAccept = true;
                    }
                    addRequest.add(questState);
                    System.out.println(questState);
                    questState = null;
                    worldController.level.player.questScreen1 = false;
                    worldController.level.player.questScreen2 = false;
                    worldController.level.player.questScreen3 = false;
                    worldController.level.player.questScreen4 = false;
                    worldController.level.player.questScreen5 = false;
                    worldController.level.player.questScreen6 = false;
                }
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                dialog.hide();
                worldController.level.player.timeStop = false;
                dialogShow = false;
                trapShow = false;
                swordShow = false;
                dialogItem = false;
            }
        });

        buttonRefuse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonAgree.setVisible(false);
                buttonRefuse.setVisible(false);
                if (trapShow) {
                    worldController.level.player.acceptTrap = false;
                    worldController.level.player.requestTrap = false;
                } else if (swordShow) {
                    worldController.level.player.acceptSwordWave = false;
                    worldController.level.player.requestSwordWave = false;
                } else if (citizenQuest == systemWindow.citizen1) {
                    worldController.level.player.quest1Cancel = true;
                    worldController.level.player.quest_window_1 = true;
                    LikingBar.instance.liking -= 1;
                    questState = QuestState.quest1no;
                } else if (citizenQuest == systemWindow.citizen2) {
                    worldController.level.player.quest2Cancel = true;
                    worldController.level.player.quest_window_2 = true;
                    LikingBar.instance.liking -= 1;
                    questState = QuestState.quest2no;
                } else if (citizenQuest == systemWindow.citizen3) {
                    worldController.level.player.quest3Cancel = true;
                    worldController.level.player.quest_window_3 = true;
                    LikingBar.instance.liking -= 1;
                    questState = QuestState.quest3no;
                } else if (citizenQuest == systemWindow.citizen4) {
                    worldController.level.player.quest4Cancel = true;
                    worldController.level.player.quest_window_4 = true;
                    LikingBar.instance.liking -= 6;
                    questState = QuestState.quest4no;
                } else if (citizenQuest == systemWindow.citizen5) {
                    worldController.level.player.quest5Cancel = true;
                    worldController.level.player.quest_window_5 = true;
                    questState = QuestState.quest5no;
                    LikingBar.instance.liking -= 6;
                } else if (citizenQuest == systemWindow.citizen6) {
                    worldController.level.player.quest6Cancel = true;
                    worldController.level.player.quest_window_6 = true;
                    questState = QuestState.quest6no;
                    LikingBar.instance.liking -= 1;
                }
                questCount += 1;
                addRequest.add(questState);
                System.out.println(questState);
                questState = null;
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                worldController.level.player.questScreen1 = false;
                worldController.level.player.questScreen2 = false;
                worldController.level.player.questScreen3 = false;
                worldController.level.player.questScreen4 = false;
                worldController.level.player.questScreen5 = false;
                worldController.level.player.questScreen6 = false;
                dialog.hide();
                worldController.level.player.timeStop = false;
                dialogShow = false;
                trapShow = false;
                swordShow = false;
                dialogItem = false;
            }
        });

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        buttonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        buttonStyle.font = font;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        TextButton.TextButtonStyle buttonTimeStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTime = new TextureRegionDrawable(Assets.instance.iconTime);
        buttonTimeStyle.up = iconTime;
        buttonTimeStyle.over = iconTime.tint(Color.LIME);
        Button iconTimeButton = new Button(buttonTimeStyle);
        iconTimeButton.setPosition(SCENE_WIDTH/2.5f, SCENE_HEIGHT - SCENE_HEIGHT / 10);

        textTime = new Label("", skin);
        textTime.setColor(Color.BLUE);
        textTime.setStyle(labelStyle);
        textTime.setFontScale(1f, 1f);
        textTime.setPosition(SCENE_WIDTH/2.3f, SCENE_HEIGHT - SCENE_HEIGHT /14);

        stage.addActor(iconTimeButton);

        stage.addActor(textTime);
        stage.addActor(textBattery);
        stage.addActor(textPower);
    }

    private Window createGuideWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.window);
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

        Label text1 = new Label("การตอบรับคำขอของประชาชน", skin);
        Label text2 = new Label("เมื่อสอบถามประชาชนจะได้รับคำร้องขอใช้เครื่องใช้ไฟฟ้าทำกิจกรรมต่างๆ", skin);
        Label text3 = new Label("โดยจะให้ผู้เล่นตัดสินใจว่าจะตอบรับหรือปฎิเสธคำร้องขอนั้นตามความเหมาะสม ซึ่งสามารถตอบได้ครั้งเดียวเท่านั้น", skin);
        Label text4 = new Label("หากตอบรับประชาชนจะเดินไปยังเครื่องใช้ไฟฟ้าและเปิดใช้งาน พร้อมทั้งค่าความพอใจจะขึ้นตามความสำคัญของกิจกรรมนั้น", skin);
        Label text5 = new Label("หากปฎิเสธประชาชนจะยืนอยู่กับที่ พร้อมทั้งค่าความพอใจจะลดลงตามความสำคัญของกิจกรรมนั้น", skin);
        Label text6 = new Label("ถ้าตอบรับคำขอมากเกินไปพลังงานจะไม่พอใช้ เมื่อพลังงานลดต่ำกว่าเกณฑ์ผู้เล่นจะพ่ายแพ้", skin);
        Label text7 = new Label("ถ้าปฎิเสธคำร้องขอจนความพอใจไม่เหลือก็จะพ่ายแพ้เช่นกัน", skin);
        Label text8 = new Label("ผู้เล่นสามารถแก้ตัวได้ด้วยการปิดหรือเปิดเครื่องใช้ไฟฟ้าด้วยตัวเอง ซึ่งค่าความพึงพอใจจะเพิ่มหรือลดครึ่งหนึ่งจากที่เสียไป", skin);
        Label text9 = new Label("และเมื่อค่าความพึงพอใจและพลังงานเหลือเพียงพอ ผู้เล่นจะได้รับชัยชนะ", skin);

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
        chartWindow.padTop(50);
        chartWindow.padLeft(40);
        chartWindow.padRight(40);
        chartWindow.padBottom(20);
        chartWindow.getTitleLabel().setAlignment(Align.center);
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
                        "\"ต้องการเข้าประตูไปยังห้องถัดไปหรือไม่\" \n\"(กดปุ่มตกลงเพื่อเข้าไปยังห้องถัดไป หรือกดปุ่มปฎิเสธเพื่อบันทึกและออกไปหน้าเมนู)\"";
                buttonAgree.setVisible(true);
                buttonRefuse.setVisible(true);
                dialog.show();
                dialog.setText(text);
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

        Button.ButtonStyle buttonItem1Style1 = new Button.ButtonStyle();
        buttonItem1 = new TextureRegionDrawable(Assets.instance.comIconOff);
        buttonItem1Style1.up = buttonItem1;
        buttonItem1Style1.over = buttonItem1.tint(Color.FIREBRICK);
        Button itemIcon1 = new Button(buttonItem1Style1);

        Button.ButtonStyle buttonItem1Style2 = new Button.ButtonStyle();
        buttonItem2 = new TextureRegionDrawable(Assets.instance.refrigeratorIconOff);
        buttonItem1Style2.up = buttonItem2;
        buttonItem1Style2.over = buttonItem2.tint(Color.FIREBRICK);
        Button itemIcon2 = new Button(buttonItem1Style2);

        Button.ButtonStyle buttonItem1Style3 = new Button.ButtonStyle();
        buttonItem3 = new TextureRegionDrawable(Assets.instance.fanIconOff);
        buttonItem1Style3.up = buttonItem3;
        buttonItem1Style3.over = buttonItem3.tint(Color.FIREBRICK);
        Button itemIcon3 = new Button(buttonItem1Style3);

        Button.ButtonStyle buttonItem1Style4 = new Button.ButtonStyle();
        buttonItem4 = new TextureRegionDrawable(Assets.instance.microwaveIconOff);
        buttonItem1Style4.up = buttonItem4;
        buttonItem1Style4.over = buttonItem4.tint(Color.FIREBRICK);
        Button itemIcon4 = new Button(buttonItem1Style4);

        Button.ButtonStyle buttonItem1Style5 = new Button.ButtonStyle();
        buttonItem5 = new TextureRegionDrawable(Assets.instance.ricecookerIconOff);
        buttonItem1Style5.up = buttonItem5;
        buttonItem1Style5.over = buttonItem5.tint(Color.FIREBRICK);
        Button itemIcon5 = new Button(buttonItem1Style5);

        Button.ButtonStyle buttonItem1Style6 = new Button.ButtonStyle();
        buttonItem6 = new TextureRegionDrawable(Assets.instance.tvIconOff);
        buttonItem1Style6.up = buttonItem6;
        buttonItem1Style6.over = buttonItem6.tint(Color.FIREBRICK);
        Button itemIcon6 = new Button(buttonItem1Style6);

        Button.ButtonStyle buttonItem1Style7 = new Button.ButtonStyle();
        buttonItem7 = new TextureRegionDrawable(Assets.instance.waterpumpIconOff);
        buttonItem1Style7.up = buttonItem7;
        buttonItem1Style7.over = buttonItem7.tint(Color.FIREBRICK);
        Button itemIcon7 = new Button(buttonItem1Style7);

        Button.ButtonStyle buttonItem1Style8 = new Button.ButtonStyle();
        buttonItem8 = new TextureRegionDrawable(Assets.instance.airIconOff);
        buttonItem1Style8.up = buttonItem8;
        buttonItem1Style8.over = buttonItem8.tint(Color.FIREBRICK);
        Button itemIcon8 = new Button(buttonItem1Style8);

        text1 = new Label("", skin);
        text2 = new Label("", skin);
        text3 = new Label("", skin);
        text4 = new Label("", skin);

        textItem1 = new Label("", skin);
        textItem2 = new Label("", skin);
        textItem3 = new Label("", skin);
        textItem4 = new Label("", skin);
        textItem5 = new Label("", skin);
        textItem6 = new Label("", skin);
        textItem7 = new Label("", skin);
        textItem8 = new Label("", skin);

        text1.setStyle(labelStyle);
        text2.setStyle(labelStyle);
        text3.setStyle(labelStyle);
        text4.setStyle(labelStyle);

        textItem1.setStyle(labelStyle);
        textItem2.setStyle(labelStyle);
        textItem3.setStyle(labelStyle);
        textItem4.setStyle(labelStyle);
        textItem5.setStyle(labelStyle);
        textItem6.setStyle(labelStyle);
        textItem7.setStyle(labelStyle);
        textItem8.setStyle(labelStyle);

        final Window statusWindow = new Window("ข้อมูลการใช้พลังงานไฟฟ้า", style);
        statusWindow.setModal(true);
        statusWindow.padTop(60);
        statusWindow.padLeft(40);
        statusWindow.padRight(40);
        statusWindow.padBottom(20);
        statusWindow.getTitleLabel().setAlignment(Align.center);
        statusWindow.row().padBottom(10).padTop(10);
        statusWindow.row().padTop(10);
        statusWindow.add(text1).colspan(4);
        statusWindow.row().padTop(10);
        statusWindow.add(text2).colspan(4);
        statusWindow.row().padTop(10);
        statusWindow.add(text3).colspan(4);
        statusWindow.row().padTop(10);
        statusWindow.add(text4).colspan(4);
        statusWindow.row().padTop(10);
        statusWindow.add(itemIcon1);
        statusWindow.add(itemIcon2);
        statusWindow.add(itemIcon3);
        statusWindow.add(itemIcon4);
        statusWindow.row().padTop(10);
        statusWindow.add(textItem1);
        statusWindow.add(textItem2);
        statusWindow.add(textItem3);
        statusWindow.add(textItem4);
        statusWindow.row().padTop(10);
        statusWindow.add(itemIcon5);
        statusWindow.add(itemIcon6);
        statusWindow.add(itemIcon7);
        statusWindow.add(itemIcon8);
        statusWindow.row().padTop(10);
        statusWindow.add(textItem5);
        statusWindow.add(textItem6);
        statusWindow.add(textItem7);
        statusWindow.add(textItem8);
        statusWindow.row().padTop(10);
        statusWindow.add(closeButton).colspan(4);
        statusWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statusWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.statusEnergyWindow = false;
                if (!dialogShow) {
                    worldController.level.player.timeStop = false;
                }
            }
        });

        return statusWindow;
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

        textMission1 = new Label("ภารกิจแรก ตามหาคัตเอาท์และสับคันโยก พร้อมทั้งกำจัดเหล่ามอนสเตอร์ทั้งหมด", skin);
        textMission2 = new Label("", skin);
        textMission3 = new Label("", skin);
        textMission4 = new Label("", skin);

        Label textMission5 = new Label("", skin);
        Label textMission6 = new Label("ต", skin);
        Label textMission7 = new Label("เ", skin);

        textMission1.setStyle(labelStyle);
        textMission2.setStyle(labelStyle);
        textMission3.setStyle(labelStyle);
        textMission4.setStyle(labelStyle);

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

    private void controlAndDebug() {

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            for (Enemy enemy : worldController.level.enemies) {
                enemy.getStateMachine().changeState(EnemyState.DIE);
            }
        }

        if (dialogShow){
            touchPad.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.visible(false)));
            swordAttackButton.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.visible(false)));
            swordWaveAttackButton.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.visible(false)));
            trapAttackButton.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.visible(false)));
            talkButton.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.visible(false)));
        } else {
            touchPad.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.5f)));
            swordAttackButton.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.5f)));
            swordWaveAttackButton.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.5f)));
            trapAttackButton.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.5f)));
            talkButton.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.5f)));
        }

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

        if (!worldController.level.player.isAlive()) {
            MusicManager.instance.stop();
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

        if (LikingBar.instance.liking < 0) {
            MusicManager.instance.stop();
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
            MusicManager.instance.stop();
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

        if (player.isSwitch && BatteryBar.instance.getBatteryStorage() < 1000) {
            MusicManager.instance.stop();
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

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            dialog.hide();
            worldController.level.player.timeStop = false;
            citizenQuest = null;
            dialogShow = false;
            player.status_find = false;
            buttonAgree.setVisible(false);
            buttonRefuse.setVisible(false);
            dialogDoor1 = false;
            dialogDoor2 = false;
            dialogDoor3 = false;
            dialogDoor4 = false;
            trapShow = false;
            swordShow = false;
            dialogItem = false;
            if(lose){
                MusicManager.instance.stop();
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
        } else {
            dialog.speedUp();
        }
    }

    private void textIconDraw() {
//        textSun.setText(String.format(" %d", (int) SunBar.instance.sunTime) + " นาฬิกา");
//        textTemperature.setText(String.format(" %d", (int) TemperatureBar.instance.Temperature));
//        //textBullet.setText(String.format(" %d", (int) ArrowBar.instance.energyArrow));
//        textBeam.setText(String.format(" %d", (int) SwordWaveBar.instance.energySwordWave) + " วัตต์");
//        textTrap.setText(String.format(" %d", (int) TrapBar.instance.energyTrap) + " วัตต์");
        textTime.setText(String.format(" %d", worldController.level.player.timeCount) + " วินาที");
//        textBattery.setText(String.format(" %d", (int)BatteryBar.instance.batteryStorage/1000) + " / " + String.format(" %d", (int)BatteryBar.instance.BATTERY_MAX/1000) + "กิโลจูล");
//        textPower.setText(String.format(" %d", (int)EnergyUsedBar.instance.energyUse) + " / " + String.format(" %d", (int)EnergyProducedBar.instance.energyProduced) + "วัตต์");
//        textLiking.setText(String.format(" %d", (int) LikingBar.instance.liking));
//        energyLevel.setText(String.format(" %d", (int) EnergyProducedBar.instance.energyProduced) + " วัตต์");
//        energyLevel2.setText(String.format(" %d", (int) EnergyUsedBar.instance.energyUse) + " วัตต์");
//        energyLevel3.setText(String.format(" %d", (int) BatteryBar.instance.getBatteryStorage()) + " จูล");
    }

    private void dialogDraw() {

        Player player = worldController.level.player;
        Level1 level1= (Level1) worldController.level;

        if (!dialogStart) {
            dialogAll();
            dialog.setText(text);
            dialogStart = true;
            delayMission();
            timeEvent = player.timeCount - 1;
        }

//        if(dialogStart && !tutorFirst && player.timeCount <= 299){
//            dialogAll();
//            String text =
//                    "\"ลองเคลื่อนทีตัวละครด้วยปุ่มลูกศรดูสิ \" \n\"(กด Enter เพื่อเล่นต่อ)\"";
//            dialog.setText(text);
//            tutorFirst = true;
//            //tutorFinish = true;
//            timeEvent = player.timeCount - 1;
//        }

//        if(!Focus1 && player.timeCount <= timeEvent){
//            dialogAll();
//            String text =
//                    "\"วิสัยทัศน์ค่อนข้างมองเห็นลำบาก เหมือนเห็นแสงสว่างจากที่ไกลๆ ลองเดินไปสำรวจกันเถอะ \" \n\"(กด Enter เพื่อเล่นต่อ)\"";
//            dialog.setText(text);
//            Focus1 = true;
//            player.focusCamera.setFocus1(Focus1);
//            timeEvent = player.timeCount - 1;
//        }

        if (player.requestTrap && !dialogTrap) {
            player.requestTrap = false;
            trapShow = true;
            dialogAll();
            String text =
                    "\"ต้องการวางกับดักหรือไม่ กับดัก 1 อันใช้กำลังไฟฟ้า 100 วัตต์ เมื่อกับดักถูกทำลายถึงจะได้กำลังไฟฟ้าที่ใช้อยู่คืน\" \n\"(กดปุ่มตกลงเพื่อวางกับดัก หรือกดปุ่มปฎิเสธเมื่อไม่ต้องการวางกับดัก)\"";
            buttonAgree.setVisible(true);
            buttonRefuse.setVisible(true);
            dialog.setText(text);
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
                    "\"ต้องการใช้ท่าคลื่นดาบหรือไม่ ใช้ 1 ครั้ง เสียกำลังไฟฟ้า 1000 วัตต์ เป็นเวลา 10 วินาที\" \n\"(กดปุ่มตกลงเพื่อใช้ท่าคลื่นดาบ หรือกดปุ่มปฎิเสธเมื่อไม่ต้องการใช้)\"";
            buttonAgree.setVisible(true);
            buttonRefuse.setVisible(true);
            dialog.setText(text);
        } else if (player.requestSwordWave && dialogSwordWave) {
            player.requestSwordWave = false;
            player.acceptSwordWave = true;
            swordShow = false;
        }



        if (!dialogEnemy && player.timeCount <= 298) {
            for (int i = 0; i < worldController.level.enemies.size(); i++) {
                Enemy enemy = worldController.level.enemies.get(i);
                if (enemy.stateMachine.getCurrentState() == EnemyState.RUN_TO_PLAYER && !enemy.count) {
                    dialogEnemy = true;
                    dialogAll();
                    String text =
                            "\"ได้ยินเสียงของอะไรบางอย่างกำลังเคลื่อนไหวใกล้เข้ามา\" \n\"โปรดระวังตัว(กด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.setText(text);
                    iconMission.setVisible(false);
                }
            }
        }

        if (player.stageOneClear && !dialogCitizen && player.isSwitch) {
            level1.enemies.clear();
            dialogCitizen = true;
            dialogAll();
            String text =
                    "\"ดูเหมือนจะไม่มีอันตรายแล้ว ลองสอบถามประชาชนที่เข้ามาอาศัย (สอบถามได้โดยกดปุ่มคุยกับประชาชน)\" \n\"(กด     เพื่อตรวจสอบภารกิจ หรือกด Enter เพื่อเล่นต่อ)\"";
            dialog.setText(text);
            timeEvent = player.timeCount - 1;
            missionStart = false;
            System.out.print(player.timeCount);
            textMission1.setStyle(labelStyle2);
            textMission2.setText("ภารกิจที่สอง สอบถามประชาชนที่เข้ามาอาศัยในที่หลบภัย (สอบถามได้โดยกดปุ่มคุยกับประชาชน)");
            delayMission();
        }


        if (EnergyProducedBar.instance.energyProduced < EnergyUsedBar.instance.energyUse && !dialogWarning) {
            dialogWarning = true;
            dialogAll();
            String text =
                    "\"อันตราย! กำลังไฟฟ้าที่ใช้มากกว่ากำลังไฟฟ้าที่ผลิต\" \n\"(กด Enter เพื่อเล่นเกมต่อ)\"";
            dialog.setText(text);
        }


        }

//        if (player.timeCount <= timeEvent && !missionStart) {
//            missionStart = true;
//            missionWindow.pack();
//            missionWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
//            player.timeStop = true;
//       }




    private void dialogAll() {
        dialog.show();
        dialogShow = true;
        worldController.level.player.timeStop = true;
    }

    private void delayMission() {
        float delay = 1.5f; // seconds
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

    private void dialogCitizenDetail() {
        Player player = worldController.level.player;
        player.timeStop = true;
        player.status_find = false;
        dialog.show();
        buttonAgree.setVisible(true);
        buttonRefuse.setVisible(true);
        dialogShow = true;
    }

    private void checkStageAndCount() {

        Player player = worldController.level.player;

        for (int i = 0; i < worldController.level.enemies.size(); i++) {
            Enemy enemy = worldController.level.enemies.get(i);
            if (enemy.stateMachine.getCurrentState() == EnemyState.DIE && !enemy.count) {
                BatteryBar.instance.addEnergy(1000);
                enemy.count = true;
                countEnemy += 1;
            }
        }

        if (countEnemy == worldController.level.enemies.size() && player.isSwitch) {
            player.stageOneClear = true;
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

        if ((player.status_find)) {
            for (Item item : level1.items) {
                if (item.nearPlayer() && item.state == Item.ItemState.ONLOOP && !item.questAccept && !item.quest && stageTwoClear) {
                    dialogItem = true;
                    String text =
                            "\"ต้องการปิด\"" + item.name + "\"หรือไม่\""
                                    + "\n\"( " + item.name + "\"ใช้กำลังไฟฟ้า\"" + item.getEnergyBurn() + " วัตต์ )\" ";
                    dialogCitizenDetail();
                    dialog.setText(text);
                    player.status_find = false;
                    this.item = item;
                }
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
            if (player.bounds.overlaps(citizen.bounds) && !citizen.questIsAccept && player.isSwitch) {
                iconHuman.setVisible(true);
            }
        }

        if (!noItem && stageThreeClear) {
            iconItem.setVisible(true);
        }

        if (player.energyLess) {
            iconEnergyLess.setVisible(true);
            delay();
        }

        if (noItem && noCitizen) {
            iconHuman.setVisible(false);
            iconItem.setVisible(false);
            player.status_find = false;
            player.status_windows_link = false;
        }

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
                stage.getWidth() / 2 - chartWindow.getWidth() / 2,
                stage.getHeight() / 2 - chartWindow.getHeight() / 2);
        chartWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    private void status() {
        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        player.timeStop = true;
        if (EnergyProducedBar.instance.energyProduced == 0) {
            String textString1 = ("ยังไม่เริ่มการผลิตพลังงาน");
            text1.setText(textString1);
        } else {
            String textString1 = ("กำลังไฟฟ้าผลิต : " + String.valueOf((EnergyProducedBar.instance.energyProduced) + " วัตต์"));
            String textString2 = ("กำลังไฟฟ้าใช้งานรวม : " + String.valueOf(EnergyUsedBar.instance.energyUse) + " วัตต์");
            if (EnergyProducedBar.instance.energyProduced < EnergyUsedBar.instance.energyUse) {
                String textString3 = ("อีก : " + String.valueOf((int) (BatteryBar.instance.getBatteryStorage() / (((EnergyProducedBar.instance.energyProduced *
                        SunBar.instance.accelerateTime) - (EnergyUsedBar.instance.energyUse * SunBar.instance.accelerateTime)))) + " วินาทีพลังงานจะหมดลง"));
                text3.setText(textString3);
            } else {
                String textString3 = ("อีก : " + String.valueOf((int) (BatteryBar.instance.getBatteryStorageBlank() / (((EnergyProducedBar.instance.energyProduced *
                        SunBar.instance.accelerateTime) - (EnergyUsedBar.instance.energyUse * SunBar.instance.accelerateTime)))) + " วินาทีพลังงานจะเต็มแบตเตอรี่"));
                text3.setText(textString3);
            }
            String textString4 = ("กำลังไฟฟ้าที่ผลิตได้หลังจากหักลบแล้ว : " + String.valueOf((EnergyProducedBar.instance.energyProduced - EnergyUsedBar.instance.energyUse)) + " วัตต์");
            text1.setText(textString1);
            text2.setText(textString2);
            text4.setText(textString4);
        }


        statusWindow.pack();
        statusWindow.setPosition(
                stage.getWidth() / 2 - statusWindow.getWidth() / 2,
                stage.getHeight() / 2 - statusWindow.getHeight() / 2);
        statusWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(Assets.instance.bggame, 0, 0);
        batch.end();

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        controlAndDebug();
        textIconDraw();
        dialogDraw();
        checkStageAndCount();
        checkObject();

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

        if (!player.timeStop && !player.timeClear) {
            BatteryBar.instance.update(deltaTime);
        }

        for (Citizen citizen : level1.citizens) {
            if (citizen.itemOn) {
                if (!citizen.getGoalItem().count) {
                    citizen.getGoalItem().state = Item.ItemState.ONLOOP;
                    EnergyUsedBar.instance.energyUse += citizen.getGoalItem().getEnergyBurn();
                    citizen.getGoalItem().count = true;
                    questCount += 1;
                    System.out.print(questCount);
                    System.out.print(addRequest.size());
                } else if (citizen.getGoalItem().count && citizen.getGoalItem().state != Item.ItemState.OFF && !player.timeStop) {
                    citizen.getGoalItem().timeCount -= deltaTime;
                    if (citizen.getGoalItem().timeCount <= 0) {
                        citizen.getGoalItem().state = Item.ItemState.OFF; // ทำงาน
                        EnergyUsedBar.instance.energyUse -= citizen.getGoalItem().getEnergyBurn();
                        citizen.itemOn = false;
                    }
                }
            }
        }

        worldController.update(Gdx.graphics.getDeltaTime()); //อัพเดท Game World
        worldRenderer.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw(); //การทำงา
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
//        json.writeValue("dialog", dialog);
//        json.writeValue("buttonAgree", buttonAgree);
//        json.writeValue("buttonRefuse", buttonRefuse);
//        json.writeValue("worldController", worldController);
//        json.writeValue("worldRenderer", worldRenderer);
//        json.writeValue("iconEnergyLess", iconEnergyLess);
//        json.writeValue("buttonPlayStyle", buttonPlayStyle);
//        json.writeValue("playUp", playUp);
//        json.writeValue("buttonPlay", buttonPlay);
//        json.writeValue("buttonPause", buttonPause);
//        json.writeValue("iconHuman", iconHuman);
//        json.writeValue("iconItem", iconItem);
//        json.writeValue("buttonControlWindow", buttonControlWindow);
//        json.writeValue("buttonControl", buttonControl);
//        json.writeValue("iconControl", iconControl);
//        json.writeValue("buttonMission", buttonMission);
//        json.writeValue("iconMission", iconMission);
//        json.writeValue("buttonGuide", buttonGuide);
//        json.writeValue("iconGuide", iconGuide);
//        json.writeValue("buttonStatus", buttonStatus);
//        json.writeValue("iconStatus", iconStatus);
        json.writeValue("missionStart", missionStart);
        json.writeValue("guideStart", guideStart);
        json.writeValue("statusStart", statusStart);
//        json.writeValue("batch", batch);
//        json.writeValue("bg", bg);
        json.writeValue("controlShow", controlShow);
//        json.writeValue("stage", stage);
//        json.writeValue("skin", skin);
        json.writeValue("titleQuest", titleQuest);
//        json.writeValue("textSun", textSun);
//        json.writeValue("textTemperature", textTemperature);
//        json.writeValue("textLiking", textLiking);
//        json.writeValue("guideWindow", guideWindow);
//        json.writeValue("textBullet", textBullet);
//        json.writeValue("textBeam", textBeam);
//        json.writeValue("textTrap", textTrap);
//        json.writeValue("textTime", textTime);
//        json.writeValue("energyLevel", energyLevel);
//        json.writeValue("energyLevel2", energyLevel2);
//        json.writeValue("energyLevel3", energyLevel3);
//        json.writeValue("textRule", textRule);
//        json.writeValue("textChart1", textChart1);
//        json.writeValue("textChart2", textChart2);
//        json.writeValue("textChart3", textChart3);
//        json.writeValue("textChart4", textChart4);
//        json.writeValue("textChart5", textChart5);
//        json.writeValue("textChart6", textChart6);
//        json.writeValue("textChart7", textChart7);
//        json.writeValue("citizenQuest", citizenQuest);
        json.writeValue("dialogStage4fail", dialogStage4fail);
//        json.writeValue("iconRegion", iconRegion);
//        json.writeValue("textItem1", textItem1);
//        json.writeValue("textItem2", textItem2);
//        json.writeValue("textItem3", textItem3);
//        json.writeValue("textItem4", textItem4);
//        json.writeValue("textItem5", textItem5);
//        json.writeValue("textItem6", textItem6);
//        json.writeValue("textItem7", textItem7);
//        json.writeValue("textItem8", textItem8);
//        json.writeValue("buttonItem1", buttonItem1);
//        json.writeValue("buttonItem2", buttonItem2);
//        json.writeValue("buttonItem3", buttonItem3);
//        json.writeValue("buttonItem4", buttonItem4);
//        json.writeValue("buttonItem5", buttonItem5);
//        json.writeValue("buttonItem6", buttonItem6);
//        json.writeValue("buttonItem7", buttonItem7);
//        json.writeValue("buttonItem8", buttonItem8);
        json.writeValue("dialogWarning", dialogWarning);
        json.writeValue("timeEvent", timeEvent);
        json.writeValue("trapShow", trapShow);
        json.writeValue("swordShow", swordShow);
        json.writeValue("dialogTrap", dialogTrap);
        json.writeValue("dialogSwordWave", dialogSwordWave);
//        json.writeValue("buttonGuideWindow", buttonGuideWindow);
        json.writeValue("guideShow", guideShow);
//        json.writeValue("textMission1", textMission1);
//        json.writeValue("textMission2", textMission2);
//        json.writeValue("textMission3", textMission3);
//        json.writeValue("textMission4", textMission4);
        json.writeValue("dialogShow", dialogShow);
//        json.writeValue("dialogStory", dialogStory);
        json.writeValue("text", text);
//        json.writeValue("questState", questState);
//        json.writeValue("isComplete", isComplete);
//        json.writeValue("addRequest", addRequest);
//        json.writeValue("buttonLink1", buttonLink1);
//        json.writeValue("buttonLink2", buttonLink2);
//        json.writeValue("buttonOption", buttonOption);
//        json.writeValue("font", font);
//        json.writeValue("optionsWindow", optionsWindow);
//        json.writeValue("chartWindow", chartWindow);
        json.writeValue("animation_status", animation_status);
        json.writeValue("trueLink", trueLink);
        json.writeValue("countEnemy", countEnemy);
        json.writeValue("enemyDeadCount", enemyDeadCount);
        json.writeValue("stringDraw", stringDraw);
//        json.writeValue("text1", text1);
//        json.writeValue("text2", text2);
//        json.writeValue("text3", text3);
//        json.writeValue("text4", text4);
//        json.writeValue("text5", text5);
//        json.writeValue("text6", text6);
//        json.writeValue("statusWindow", statusWindow);
//        json.writeValue("missionWindow", missionWindow);
        json.writeValue("questCount", questCount);
        json.writeValue("Countdown", Countdown);
        json.writeValue("dialogEnemy", dialogEnemy);
        json.writeValue("dialogCitizen", dialogCitizen);
        json.writeValue("dialogStage1", dialogStage1);
        json.writeValue("dialogStage2", dialogStage2);
        json.writeValue("dialogStage3", dialogStage3);
        json.writeValue("dialogStage4", dialogStage4);
        json.writeValue("dialogDoor1", dialogDoor1);
        json.writeValue("dialogDoor2", dialogDoor2);
        json.writeValue("dialogDoor3", dialogDoor3);
        json.writeValue("dialogDoor4", dialogDoor4);
        json.writeValue("stageTwoClear", stageTwoClear);
        json.writeValue("stageThreeClear", stageThreeClear);
        json.writeValue("stageFourClear", stageFourClear);
//        json.writeValue("labelStyle", labelStyle);
//        json.writeValue("labelStyle2", labelStyle2);
//        json.writeValue("buttonStyle", buttonStyle);
//        json.writeValue("buttonStyle2", buttonStyle2);
//        json.writeValue("pauseUp", pauseUp);
//        json.writeValue("toolUp", toolUp);
//        json.writeValue("buttonToolStyle", buttonToolStyle);
//        json.writeValue("buttonPauseStyle", buttonPauseStyle);
        json.writeValue("dialogStart", dialogStart);
//        json.writeValue("touchPad", touchPad);
//        json.writeValue("swordAttackButton", swordAttackButton);
//        json.writeValue("swordWaveAttackButton", swordWaveAttackButton);
//        json.writeValue("trapAttackButton", trapAttackButton);
//        json.writeValue("talkButton", talkButton);
//        json.writeValue("game", game);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
//        dialog = jsonData.get("dialog");
//        buttonAgree = jsonData.get("buttonAgree");
//        buttonRefuse = jsonData.get("buttonRefuse");
//        worldController = jsonData.get("worldController");
//        worldRenderer = jsonData.get("worldRenderer");
//        iconEnergyLess = jsonData.get("iconEnergyLess");
//        buttonPlayStyle = jsonData.get("buttonPlayStyle");
//        playUp = jsonData.get("playUp");
//        buttonPlay = jsonData.get("buttonPlay");
//        buttonPause = jsonData.get("buttonPause");
//        iconHuman = jsonData.get("iconHuman");
//        iconItem = jsonData.get("iconItem");
//        buttonControlWindow = jsonData.get("buttonControlWindow");
//        buttonControl = jsonData.get("buttonControl");
//        iconControl = jsonData.get("iconControl");
//        buttonMission = jsonData.get("buttonMission");
//        iconMission = jsonData.get("iconMission");
//        buttonGuide = jsonData.get("buttonGuide");
//        iconGuide = jsonData.get("iconGuide");
//        buttonStatus = jsonData.get("buttonStatus");
//        iconStatus = jsonData.get("iconStatus");
        missionStart = jsonData.getBoolean("missionStart");
        guideStart = jsonData.getBoolean("guideStart");
        statusStart = jsonData.getBoolean("statusStart");
//        batch = jsonData.get("batch");
//        bg = jsonData.get("bg");
        controlShow = jsonData.getBoolean("controlShow");
//        stage = jsonData.get("stage");
//        skin = jsonData.get("skin");
        titleQuest = jsonData.getString("titleQuest");
//        textSun = jsonData.get("textSun");
//        textTemperature = jsonData.get("textTemperature");
//        textLiking = jsonData.get("textLiking");
//        guideWindow = jsonData.get("guideWindow");
//        textBullet = jsonData.get("textBullet");
//        textBeam = jsonData.get("textBeam");
//        textTrap = jsonData.get("textTrap");
//        textTime = jsonData.get("textTime");
//        energyLevel = jsonData.get("energyLevel");
//        energyLevel2 = jsonData.get("energyLevel2");
//        energyLevel3 = jsonData.get("energyLevel3");
//        textRule = jsonData.get("textRule");
//        textChart1 = jsonData.get("textChart1");
//        textChart2 = jsonData.get("textChart2");
//        textChart3 = jsonData.get("textChart3");
//        textChart4 = jsonData.get("textChart4");
//        textChart5 = jsonData.get("textChart5");
//        textChart6 = jsonData.get("textChart6");
//        textChart7 = jsonData.get("textChart7");
//        citizenQuest = jsonData.get("citizenQuest");
        dialogStage4fail = jsonData.getBoolean("dialogStage4fail");
//        iconRegion = jsonData.get("iconRegion");
//        textItem1 = jsonData.get("textItem1");
//        textItem2 = jsonData.get("textItem2");
//        textItem3 = jsonData.get("textItem3");
//        textItem4 = jsonData.get("textItem4");
//        textItem5 = jsonData.get("textItem5");
//        textItem6 = jsonData.get("textItem6");
//        textItem7 = jsonData.get("textItem7");
//        textItem8 = jsonData.get("textItem8");
//        buttonItem1 = jsonData.get("buttonItem1");
//        buttonItem2 = jsonData.get("buttonItem2");
//        buttonItem3 = jsonData.get("buttonItem3");
//        buttonItem4 = jsonData.get("buttonItem4");
//        buttonItem5 = jsonData.get("buttonItem5");
//        buttonItem6 = jsonData.get("buttonItem6");
//        buttonItem7 = jsonData.get("buttonItem7");
//        buttonItem8 = jsonData.get("buttonItem8");
        dialogWarning = jsonData.getBoolean("dialogWarning");
        timeEvent = jsonData.getInt("timeEvent");
        trapShow = jsonData.getBoolean("trapShow");
        swordShow = jsonData.getBoolean("swordShow");
        dialogTrap = jsonData.getBoolean("dialogTrap");
        dialogSwordWave = jsonData.getBoolean("dialogSwordWave");
//        buttonGuideWindow = jsonData.get("buttonGuideWindow");
        guideShow = jsonData.getBoolean("guideShow");
//        textMission1 = jsonData.get("textMission1");
//        textMission2 = jsonData.get("textMission2");
//        textMission3 = jsonData.get("textMission3");
//        textMission4 = jsonData.get("textMission4");
        dialogShow = jsonData.getBoolean("dialogShow");
//        dialogStory = jsonData.get("dialogStory");
        text = jsonData.getString("text");
//        questState = jsonData.get("questState");
//        isComplete = jsonData.get("isComplete");
//        addRequest = jsonData.get("addRequest");
//        buttonLink1 = jsonData.get("buttonLink1");
//        buttonLink2 = jsonData.get("buttonLink2");
//        buttonOption = jsonData.get("buttonOption");
//        font = jsonData.get("font");
//        optionsWindow = jsonData.get("optionsWindow");
//        chartWindow = jsonData.get("chartWindow");
        animation_status = jsonData.getBoolean("animation_status");
        trueLink = jsonData.getInt("trueLink");
        countEnemy = jsonData.getInt("countEnemy");
        enemyDeadCount = jsonData.getInt("enemyDeadCount");
        stringDraw = jsonData.getBoolean("stringDraw");
//        text1 = jsonData.get("text1");
//        text2 = jsonData.get("text2");
//        text3 = jsonData.get("text3");
//        text4 = jsonData.get("text4");
//        text5 = jsonData.get("text5");
//        text6 = jsonData.get("text6");
//        statusWindow = jsonData.get("statusWindow");
//        missionWindow = jsonData.get("missionWindow");
        questCount = jsonData.getInt("questCount");
        Countdown = jsonData.getFloat("Countdown");
        dialogEnemy = jsonData.getBoolean("dialogEnemy");
        dialogCitizen = jsonData.getBoolean("dialogCitizen");
        dialogStage1 = jsonData.getBoolean("dialogStage1");
        dialogStage2 = jsonData.getBoolean("dialogStage2");
        dialogStage3 = jsonData.getBoolean("dialogStage3");
        dialogStage4 = jsonData.getBoolean("dialogStage4");
        dialogDoor1 = jsonData.getBoolean("dialogDoor1");
        dialogDoor2 = jsonData.getBoolean("dialogDoor2");
        dialogDoor3 = jsonData.getBoolean("dialogDoor3");
        dialogDoor4 = jsonData.getBoolean("dialogDoor4");
        stageTwoClear = jsonData.getBoolean("stageTwoClear");
        stageThreeClear = jsonData.getBoolean("stageThreeClear");
        stageFourClear = jsonData.getBoolean("stageFourClear");
//        labelStyle = jsonData.get("labelStyle");
//        labelStyle2 = jsonData.get("labelStyle2");
//        buttonStyle = jsonData.get("buttonStyle");
//        buttonStyle2 = jsonData.get("buttonStyle2");
//        pauseUp = jsonData.get("pauseUp");
//        toolUp = jsonData.get("toolUp");
//        buttonToolStyle = jsonData.get("buttonToolStyle");
//        buttonPauseStyle = jsonData.get("buttonPauseStyle");
        dialogStart = jsonData.getBoolean("dialogStart");
//        touchPad = jsonData.get("touchPad");
//        swordAttackButton = jsonData.get("swordAttackButton");
//        swordWaveAttackButton = jsonData.get("swordWaveAttackButton");
//        trapAttackButton = jsonData.get("trapAttackButton");
//        talkButton = jsonData.get("talkButton");
//        game = jsonData.get("game");
    }
}
