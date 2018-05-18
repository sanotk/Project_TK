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
import com.mypjgdx.esg.game.levels.Level3;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.EnemyState;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.*;
import com.mypjgdx.esg.ui.*;
import com.mypjgdx.esg.ui.Dialog;
import com.mypjgdx.esg.utils.QuestState;

import java.util.ArrayList;

public class GameScreen3 extends AbstractGameScreen {

    private final Dialog dialog;
    private TextButton buttonAgree;
    private TextButton buttonRefuse;
    private WorldController worldController;
    private WorldRenderer worldRenderer;

    SpriteBatch batch;
    public Texture bg;

    private Label textSun;
    private Label textTemperature;

    private Stage stage;
    private Skin skin;
    private String titleQuest;

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
    private Window missionWindow;

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
    private boolean itemStart;

    public enum systemWindow {
        citizen1,
        citizen2,
        citizen3,
        citizen4,
        citizen5,
        citizen6
    }

    private boolean dialogShow;

    private Texture dialogStory;

    private String text =
            "\"ทุกคนรออยู่ตรงนี้ก่อน จนกว่าเราจะตรวจสอบแล้วว่าไม่มีอันตราย\" \n\"(กด Enter เพื่อเริ่มเกม)\"";

    public QuestState questState = null;

    private ArrayList<QuestState> isComplete = new ArrayList<QuestState>();
    private ArrayList<QuestState> addRequest = new ArrayList<QuestState>();

    private TextButton buttonLink1;
    private TextButton buttonLink2;

    private Button buttonOption;
    private BitmapFont font;
    private Window optionsWindow;

    private Button buttonRule;
    private Button buttonMission;
    private Window ruleWindow;
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

    private boolean dialogStart;

    private boolean stageTwoClear;
    private boolean stageThreeClear;
    private boolean stageFourClear;

    private Label textLiking;

    private TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    private TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();

    public GameScreen3(Game game, final Window optionsWindow) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = new BitmapFont(Gdx.files.internal("thai24.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.WHITE);

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
        buttonRule.setPosition(SCENE_WIDTH - 50, SCENE_HEIGHT - 100);

        TextButton.TextButtonStyle buttonMissionStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable missionUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_pause"));
        buttonMissionStyle.up = missionUp;
        buttonMissionStyle.down = missionUp.tint(Color.LIGHT_GRAY);
        buttonMission = new Button(buttonMissionStyle);
        buttonMission.setPosition(SCENE_WIDTH - 50, SCENE_HEIGHT - 150);

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

        ruleWindow = createRuleWindow();
        ruleWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - ruleWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - ruleWindow.getHeight() / 2);
        ruleWindow.addAction(Actions.sequence(Actions.visible(false), Actions.fadeIn(0.2f)));

        chartWindow = createChartWindow();
        chartWindow.setVisible(false);

        statusWindow = createStatusWindow();
        statusWindow.setVisible(false);

        missionWindow = createMissionWindow();
        missionWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - missionWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - missionWindow.getHeight() / 2);

        optionsWindow.setVisible(false);

        dialogStory = new Texture("dialogStory.png");
        dialogStory.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        dialog = new Dialog(font, dialogStory, 65f, 120f);
        dialog.setPosition(
                SCENE_WIDTH / 2 - dialogStory.getWidth() * 0.5f,
                SCENE_HEIGHT / 4 - dialogStory.getHeight() * 0.5f);

        dialog.clearPages();
        dialog.addWaitingPage(text);

        stage.addActor(dialog);

        stage.addActor(buttonOption);
        stage.addActor(buttonRule);
        stage.addActor(buttonAgree);
        stage.addActor(buttonRefuse);

        stage.addActor(optionsWindow);
        stage.addActor(ruleWindow);
        stage.addActor(chartWindow);
        stage.addActor(statusWindow);
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

        buttonMission.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 - buttonMission.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - buttonMission.getHeight() / 2);
                missionWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
                worldController.level.player.timeStop = true;
            }
        });

        buttonAgree.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonAgree.setVisible(false);
                buttonRefuse.setVisible(false);
                if (citizenQuest == systemWindow.citizen1) {
                    questState = QuestState.quest1yes;
                    worldController.level.player.quest1IsAccept = true;
                    worldController.level.player.quest_window_1 = true;
                } else if (citizenQuest == systemWindow.citizen2) {
                    questState = QuestState.quest2yes;
                    worldController.level.player.quest_window_2 = true;
                    worldController.level.player.quest2IsAccept = true;
                } else if (citizenQuest == systemWindow.citizen3) {
                    questState = QuestState.quest3yes;
                    worldController.level.player.quest_window_3 = true;
                    worldController.level.player.quest3IsAccept = true;
                } else if (citizenQuest == systemWindow.citizen4) {
                    questState = QuestState.quest4yes;
                    worldController.level.player.quest_window_4 = true;
                    worldController.level.player.quest4IsAccept = true;
                } else if (citizenQuest == systemWindow.citizen5) {
                    questState = QuestState.quest5yes;
                    worldController.level.player.quest_window_5 = true;
                    worldController.level.player.quest5IsAccept = true;
                } else if (citizenQuest == systemWindow.citizen6) {
                    questState = QuestState.quest6yes;
                    worldController.level.player.quest_window_6 = true;
                    worldController.level.player.quest6IsAccept = true;
                }
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
            }
        });

        buttonRefuse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonAgree.setVisible(false);
                buttonRefuse.setVisible(false);
                if (citizenQuest == systemWindow.citizen1) {
                    worldController.level.player.quest_window_1 = true;
                    questState = QuestState.quest1no;
                } else if (citizenQuest == systemWindow.citizen2) {
                    worldController.level.player.quest_window_2 = true;
                    questState = QuestState.quest2no;
                } else if (citizenQuest == systemWindow.citizen3) {
                    worldController.level.player.quest_window_3 = true;
                    questState = QuestState.quest3no;
                } else if (citizenQuest == systemWindow.citizen4) {
                    worldController.level.player.quest_window_4 = true;
                    questState = QuestState.quest4no;
                } else if (citizenQuest == systemWindow.citizen5) {
                    worldController.level.player.quest_window_5 = true;
                    questState = QuestState.quest5no;
                } else if (citizenQuest == systemWindow.citizen6) {
                    worldController.level.player.quest_window_6 = true;
                    questState = QuestState.quest6no;
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
            }
        });

        createButton();
        batch = new SpriteBatch();
    }

    private void createButton() {

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
        iconSunButton.setPosition(50, SCENE_HEIGHT - 50);

        textSun = new Label("", skin);
        textSun.setColor(0, 0, 0, 1);
        textSun.setStyle(labelStyle);
        textSun.setFontScale(1f, 1f);
        textSun.setPosition(75, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonTemperatureStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTemperature = new TextureRegionDrawable(Assets.instance.iconTemperature);
        buttonTemperatureStyle.up = iconTemperature;
        buttonTemperatureStyle.over = iconTemperature.tint(Color.LIME);
        Button iconTemperatureButton = new Button(buttonTemperatureStyle);
        iconTemperatureButton.setPosition(150, SCENE_HEIGHT - 50);

        textTemperature = new Label("", skin);
        textTemperature.setColor(0, 0, 0, 1);
        textTemperature.setStyle(labelStyle);
        textTemperature.setFontScale(1f, 1f);
        textTemperature.setPosition(175, SCENE_HEIGHT - 42);

        TextButton.TextButtonStyle buttonCircleStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconCircle = new TextureRegionDrawable(Assets.instance.iconCircle);
        buttonCircleStyle.up = iconCircle;
        buttonCircleStyle.over = iconCircle.tint(Color.LIME);
        Button iconCircleButton = new Button(buttonCircleStyle);
        iconCircleButton.setPosition(195, SCENE_HEIGHT - 40);

        TextButton.TextButtonStyle buttonBowStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconBow = new TextureRegionDrawable(Assets.instance.iconBow);
        buttonBowStyle.up = iconBow;
        buttonBowStyle.over = iconBow.tint(Color.LIME);
        Button iconBowButton = new Button(buttonBowStyle);
        iconBowButton.setPosition(225, SCENE_HEIGHT - 50);

        textBullet = new Label("", skin);
        textBullet.setColor(0, 0, 0, 1);
        textBullet.setStyle(labelStyle);
        textBullet.setFontScale(1f, 1f);
        textBullet.setPosition(250, SCENE_HEIGHT - 42);

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

        stage.addActor(iconBowButton);
        stage.addActor(iconSwordButton);
        stage.addActor(iconTrapButton);
        stage.addActor(iconTimeButton);
        stage.addActor(iconEnergyPlusButton);
        stage.addActor(iconEnergyMinusButton);
        stage.addActor(iconBatteryButton);
        stage.addActor(iconLikingButton);

        stage.addActor(textSun);
        stage.addActor(textTemperature);
        stage.addActor(textBullet);
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
        style.background = new TextureRegionDrawable(Assets.instance.window);
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
        style.background = new TextureRegionDrawable(Assets.instance.window);
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

    private Window createRuleWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new TextureRegionDrawable(Assets.instance.window);
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Button.ButtonStyle buttonRuleStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonRuleStyle.up = buttonRegion;
        buttonRuleStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonRuleStyle);
//        Button ruleIcon = new Button(buttonPauseStyle);
//        Button toolIcon = new Button(buttonToolStyle);

        Label text1 = new Label("กด c เพื่อฟัน", skin);
        Label text2 = new Label("กด x เพื่อยิงธนู (ยิงธนู 1 ดอกใช้พลังงานไฟฟ้า 200 จูล)", skin);
        Label text3 = new Label("กด Z เพื่อวางกับดักสปริง เมื่อมอนสเตอร์เดินมาชนจะกระเด็นถอยหลัง (วางกับดัก 1 ครั้งใช้พลังงานไฟฟ้า 1000 จูล)", skin);
        Label text4 = new Label("กด W เพื่อฟันคลื่นดาบพลังสูง (ฟัน 1 ครั้งใช้พลังงานไฟฟ้า 3000 จูล)", skin);
        Label text5 = new Label("กด A เพื่อกระทำกับวัตถุ หรือคุยกับประชาชน", skin);
        Label text6 = new Label("กด S เพื่อดูผังการใช้พลังงานแบบละเอียด", skin);
        Label text7 = new Label("กด D เพื่ออ่านวิธีการทำงานของโซล่าเซลล์", skin);
        //  Label text8 = new Label("เพื่อหยุดเกม และอ่านวิธีควบคุม", skin);
        // Label text9 = new Label("เพื่อเปิดเมนูปรับแต่ง", skin);
        Label text8 = new Label("กดปุ่มลูกศรบนแป้นพิมพ์เพื่อเคลื่อนที่ตัวละคร", skin);

        text1.setStyle(labelStyle);
        text2.setStyle(labelStyle);
        text3.setStyle(labelStyle);
        text4.setStyle(labelStyle);
        text5.setStyle(labelStyle);
        text6.setStyle(labelStyle);
        text7.setStyle(labelStyle);
        //   text8.setStyle(labelStyle);
        //   text9.setStyle(labelStyle);
        text8.setStyle(labelStyle);

        final Window ruleWindow = new Window("การควบคุม", style);
        ruleWindow.setModal(true);
        ruleWindow.setSkin(skin);
        ruleWindow.padTop(60);
        ruleWindow.padLeft(40);
        ruleWindow.padRight(40);
        ruleWindow.padBottom(20);
        ruleWindow.getTitleLabel().setAlignment(Align.center);
        ruleWindow.add(text1);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text2);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text3);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text4);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text5);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text6);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text7);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text8);
        //   ruleWindow.add(ruleIcon).right();
        //  ruleWindow.add(text8).left();
        //   ruleWindow.row().padTop(10);
        //   ruleWindow.add(toolIcon).right();
        //   ruleWindow.add(text9).left();
        ruleWindow.row().padTop(20);
        ruleWindow.add(closeButton).colspan(3);
        ruleWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ruleWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                if (!dialogShow) {
                    worldController.level.player.timeStop = false;
                }
            }
        });

        return ruleWindow;
    }

    private Window createMissionWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new TextureRegionDrawable(Assets.instance.window);
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Button.ButtonStyle buttonRuleStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonRuleStyle.up = buttonRegion;
        buttonRuleStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonRuleStyle);
//        Button ruleIcon = new Button(buttonPauseStyle);
//        Button toolIcon = new Button(buttonToolStyle);

        Label text1 = new Label("ภารกิจแรก กำจัดมอนสเตอร์ในแผนที่ให้ครบทุกตัวซึ่งจะได้รับพลังงานเพื่อเริ่มต้นการทำงานของโซล่าเซลล์", skin);
        Label text2 = new Label("ภารกิจที่สอง หลังจากเสร็จสิ้นภารกิจแรกประชาชนจะปรากฎตัวออกมา ให้ค้นหาประชาชนในแผนที่ให้ครบ", skin);
        Label text3 = new Label("ภารกิจที่สาม หลังจากเสร็จสิ้นภารกิจที่สองให้พาประชาชนไปยังที่หลบภัย", skin);
        Label text4 = new Label("ภารกิจสุดท้าย เชื่อมต่อระบบโซล่าเซลล์ให้ถูกต้อง", skin);

        text1.setStyle(labelStyle);
        text2.setStyle(labelStyle);
        text3.setStyle(labelStyle);
        text4.setStyle(labelStyle);

        final Window missionWindow = new Window("ภารกิจที่ต้องทำให้สำเร็จ", style);
        missionWindow.setModal(true);
        missionWindow.setSkin(skin);
        missionWindow.padTop(60);
        missionWindow.padLeft(40);
        missionWindow.padRight(40);
        missionWindow.padBottom(20);
        missionWindow.getTitleLabel().setAlignment(Align.center);
        missionWindow.add(text1);
        missionWindow.row().padTop(10);
        missionWindow.add(text2);
        missionWindow.row().padTop(10);
        missionWindow.add(text3);
        missionWindow.row().padTop(10);
        missionWindow.add(text4);
        //   ruleWindow.add(ruleIcon).right();
        //  ruleWindow.add(text8).left();
        //   ruleWindow.row().padTop(10);
        //   ruleWindow.add(toolIcon).right();
        //   ruleWindow.add(text9).left();
        missionWindow.row().padTop(20);
        missionWindow.add(closeButton).colspan(3);
        missionWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                if (!dialogStart) {
                    ruleWindow.addAction(Actions.sequence(Actions.fadeIn(0.2f), Actions.visible(true)));
                }else if(!dialogShow){
                    worldController.level.player.timeStop = false;
                }
            }
        });

        return missionWindow;
    }

    private void controlAndDebug() {

        Player player = worldController.level.player;
        Level3 level3 = (Level3) worldController.level;

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            for (Enemy enemy : worldController.level.enemies) {
                enemy.getStateMachine().changeState(EnemyState.DIE);
            }
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            level3.switchItem.state = Item.ItemState.ON;
            level3.switchItem.resetAnimation();
            player.isSwitch = true;
            player.status_find = false;
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
        } else {
            dialog.tryToChangePage();
        }

    }

    private void textIconDraw() {
        textSun.setText(String.format(" %d", (int) SunBar.instance.sunTime)+ " นาฬิกา");
        textTemperature.setText(String.format(" %d", (int) TemperatureBar.instance.Temperature));
        textBullet.setText(String.format(" %d", (int) ArrowBar.instance.energyArrow));
        textBeam.setText(String.format(" %d", (int) SwordWaveBar.instance.energySwordWave));
        textTrap.setText(String.format(" %d", (int) TrapBar.instance.energyTrap));
        textTime.setText(String.format(" %d", worldController.level.player.timeCount) + " วินาที");
        energyLevel.setText(String.format(" %d", (int) EnergyProducedBar.instance.energyProduced) + " วัตต์");
        energyLevel2.setText(String.format(" %d", (int) EnergyUsedBar.instance.energyUse) + " วัตต์");
        energyLevel3.setText(String.format(" %d", (int) BatteryBar.instance.getBatteryStorage()) + " จูล");
        textLiking.setText(String.format(" %d", (int) LikingBar.instance.liking));
    }

    private void dialogDraw() {

        Player player = worldController.level.player;
        Level3 level3 = (Level3) worldController.level;

        if ((level3.gate.nearPlayer()) && (player.status_find)) {
            if (!animation_status && stageTwoClear && !stageThreeClear && !dialogDoor3) {
                dialogDoor3 = true;
                String text =
                        "\"กำลังไฟฟ้าที่ผลิตต่ำกว่ากำลังไฟฟ้าที่ใช้ รีบปิดเครื่องใช้ไฟฟ้าเร็วเข้า\" \n\" (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                dialogDetail();
                dialog.addWaitingPage(text);
            } else if (animation_status && stageThreeClear && !dialogDoor4) {
                dialogDoor4 = true;
                player.timeStop = true;
                player.timeClear = true;
                chartStatus();
            }
        }

        if (!dialogEnemy) {
            for (int i = 0; i < worldController.level.enemies.size(); i++) {
                Enemy enemy = worldController.level.enemies.get(i);
                if (enemy.stateMachine.getCurrentState() == EnemyState.RUN_TO_PLAYER && !enemy.count) {
                    dialogEnemy = true;
                    String text =
                            "\"ได้ยินเสียงของอะไรบางอย่างกำลังเคลื่อนไหวใกล้เข้ามา\" \n\"โปรดระวังตัว (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialogDetail();
                    dialog.addWaitingPage(text);
                }
            }
        }

        if (player.stageOneClear && !dialogCitizen) {
            dialogCitizen = true;
            String text =
                    "\"กำจัดมอนสเตอร์หมดแล้ว ลองสอบถามประชาชนที่เข้ามาอาศัยดีกว่า\" \n\"(กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
            dialogDetail();
            dialog.addWaitingPage(text);
        }

        if (questCount == 6 && !animation_status) {
            if (BatteryBar.instance.getBatteryStorage()>=1000 && !dialogStage4) {
                dialogStage4 = true;
                stageTwoClear = true;
                stageThreeClear = true;
                animation_status = true;
                player.timeStop = true;
                level3.gate.state = Item.ItemState.ON;
                String text =
                        "\"ทำได้ดีมาก สถานที่หลบภัยกลับมาอยู่ในสภาพปกติเรียบร้อยแล้ว\" \n\"(กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                dialogDetail();
                dialog.addWaitingPage(text);
            }
        }

        if (EnergyProducedBar.instance.energyProduced < EnergyUsedBar.instance.energyUse && !dialogWarning) {
            dialogWarning = true;
            String text =
                    "\"อันตราย! กำลังไฟฟ้าที่ใช้มากกว่ากำลังไฟฟ้าที่ผลิต หากพลังงานไฟฟ้าภายในแบตเตอรี่ลดต่ำลงกว่า 1000 จูล ทุกคนจะขาดอากาศหายใจ รีบปิดเครื่องใช้ไฟฟ้าที่ไม่จำเป็นทั้งหมดเร็วเข้า\" \n\"(กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
            dialogDetail();
            dialog.addWaitingPage(text);
        }

    }

    private void dialogDetail(){
        Player player = worldController.level.player;
        player.timeStop = true;
        player.status_find = false;
        dialog.show();
        dialog.clearPages();
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

        if (countEnemy == worldController.level.enemies.size()) {
            player.stageOneClear = true;
        }


    }

    private void checkObject() {

        Player player = worldController.level.player;
        Level3 level3 = (Level3) worldController.level;

        boolean noItem = true;

        for (Item item : level3.items) {
            if (item.nearPlayer()) {
                noItem = false;
                break;
            }
        }

        if(player.status_find && player.isSwitch){
            for (Item item : level3.items) {
                if (item.nearPlayer() && item.state == Item.ItemState.ONLOOP) {
                    item.state = Item.ItemState.OFF;
                    EnergyUsedBar.instance.energyUse -= item.getEnergyBurn();
                    player.status_find = false;
                } else if ((player.status_find) && item.nearPlayer() && item.state == Item.ItemState.OFF) {
                    if(item instanceof Gate || item instanceof Switch){

                    }else{
                        item.state = Item.ItemState.ONLOOP;
                        EnergyUsedBar.instance.energyUse += item.getEnergyBurn();
                    }
                    player.status_find = false;
                }
            }
        }

        if (player.status_find && noItem) {
            player.status_find = false;
            player.status_windows_link = false;
        }

        if ((!player.isSwitch) && (player.status_find) && (level3.switchItem.nearPlayer())) {
            level3.switchItem.state = Item.ItemState.ON;
            level3.switchItem.resetAnimation();
            player.isSwitch = true;
            player.status_find = false;
        }

        if(player.isSwitch && !itemStart){
            itemStart = true;
            for (Item item : level3.items){
                if(item instanceof Computer || item instanceof Microwave || item instanceof Gate){
                    item.state = Item.ItemState.OFF;
                    questCount += 1;
                }else if(item instanceof Switch){
                    item.state = Item.ItemState.ON;
                }else{
                    item.state = Item.ItemState.ONLOOP;
                    EnergyUsedBar.instance.energyUse += item.getEnergyBurn();
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
        chartWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - chartWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - chartWindow.getHeight() / 2);
        chartWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    private void status() {
        Player player = worldController.level.player;
        Level3 level3 = (Level3) worldController.level;

        player.timeStop = true;
        if(EnergyProducedBar.instance.energyProduced == 0){
            String textString1 = ("ยังไม่เริ่มการผลิตพลังงาน");
            text1.setText(textString1);
        }else{
            String textString1 = ("กำลังไฟฟ้าผลิต : " + String.valueOf((EnergyProducedBar.instance.energyProduced) + " วัตต์"));
            String textString2 = ("กำลังไฟฟ้าใช้งานรวม : " + String.valueOf(EnergyUsedBar.instance.energyUse) + " วัตต์");
            if (EnergyProducedBar.instance.energyProduced < EnergyUsedBar.instance.energyUse) {
                String textString3 = ("อีก : " + String.valueOf((int) (BatteryBar.instance.getBatteryStorage() / (((EnergyProducedBar.instance.energyProduced*60) - (EnergyUsedBar.instance.energyUse*60)))) + " วินาทีพลังงานจะหมดลง"));
                text3.setText(textString3);
            } else {
                String textString3 = ("อีก : " + String.valueOf((int) (BatteryBar.instance.getBatteryStorageBlank() / (((EnergyProducedBar.instance.energyProduced*60) - (EnergyUsedBar.instance.energyUse*60)))) + " วินาทีพลังงานจะเต็มแบตเตอรี่"));
                text3.setText(textString3);
            }
            String textString4 = ("กำลังไฟฟ้าผลิตที่ผลิตได้หลังจากหักลบแล้ว : " + String.valueOf((EnergyProducedBar.instance.energyProduced - EnergyUsedBar.instance.energyUse)) + " วัตต์");
            text1.setText(textString1);
            text2.setText(textString2);
            text4.setText(textString4);
        }

        if (level3.computer.state == Item.ItemState.ONLOOP) {
            buttonItem1.setRegion(Assets.instance.comIconOn);
            textItem1.setText(String.valueOf(level3.television.getEnergyBurn()));
        } else {
            buttonItem1.setRegion(Assets.instance.comIconOff);
            textItem1.clear();
        }

        if (level3.refrigerator.state == Item.ItemState.ONLOOP) {
            buttonItem2.setRegion(Assets.instance.refrigeratorIconOn);
            textItem2.setText(String.valueOf(level3.refrigerator.getEnergyBurn()));
        } else {
            buttonItem2.setRegion(Assets.instance.refrigeratorIconOff);
            textItem2.clear();
        }

        if (level3.fan1.state == Item.ItemState.ONLOOP) {
            buttonItem3.setRegion(Assets.instance.fanIconOn);
            if (level3.fan2.state == Item.ItemState.ONLOOP) {
                textItem3.setText(String.valueOf(level3.fan1.getEnergyBurn()) + level3.fan2.getEnergyBurn());
            } else {
                textItem3.setText(String.valueOf(level3.fan1.getEnergyBurn()));
            }
        } else {
            buttonItem3.setRegion(Assets.instance.fanIconOff);
            textItem3.clear();
        }

        if (level3.microwave.state == Item.ItemState.ONLOOP) {
            buttonItem4.setRegion(Assets.instance.microwaveIconOn);
            textItem4.setText(String.valueOf(level3.microwave.getEnergyBurn()));
        } else {
            buttonItem4.setRegion(Assets.instance.microwaveIconOff);
            textItem4.clear();
        }

        if (level3.riceCooker.state == Item.ItemState.ONLOOP) {
            buttonItem5.setRegion(Assets.instance.ricecookerIconOn);
            textItem5.setText(String.valueOf(level3.riceCooker.getEnergyBurn()));
        } else {
            buttonItem5.setRegion(Assets.instance.ricecookerIconOff);
            textItem5.clear();
        }

        if (level3.television.state == Item.ItemState.ONLOOP) {
            buttonItem6.setRegion(Assets.instance.tvIconOn);
            textItem6.setText(String.valueOf(level3.television.getEnergyBurn()));
        } else {
            buttonItem6.setRegion(Assets.instance.tvIconOff);
            textItem6.clear();
        }

        if (level3.waterPump.state == Item.ItemState.ONLOOP) {
            buttonItem7.setRegion(Assets.instance.waterpumpIconOn);
            textItem7.setText(String.valueOf(level3.waterPump.getEnergyBurn()));
        } else {
            buttonItem7.setRegion(Assets.instance.waterpumpIconOff);
            textItem7.clear();
        }

        if (level3.airConditioner.state == Item.ItemState.ONLOOP) {
            buttonItem8.setRegion(Assets.instance.airIconOn);
            textItem8.setText(String.valueOf(level3.airConditioner.getEnergyBurn()));
        } else {
            buttonItem8.setRegion(Assets.instance.airIconOff);
            textItem8.clear();
        }

        statusWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - statusWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - statusWindow.getHeight() / 2);
        statusWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Player player = worldController.level.player;

        controlAndDebug();
        textIconDraw();
        dialogDraw();
        checkStageAndCount();
        checkObject();

        if (!player.timeStop && !player.timeClear) {
            BatteryBar.instance.update(deltaTime);
        }

        if (player.statusEnergyWindow) {
            status();
        } else {
            statusWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
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
        worldController = new WorldController(new Level3());
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
        font.dispose();
        bg.dispose();
    }

    @Override
    public void pause() {
    }

}
