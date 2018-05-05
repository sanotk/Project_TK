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
import com.mypjgdx.esg.ui.BatteryBar;
import com.mypjgdx.esg.ui.EnergyProducedBar;
import com.mypjgdx.esg.ui.EnergyUsedBar;
import com.mypjgdx.esg.utils.ItemLink;
import com.mypjgdx.esg.utils.SolarState;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GameScreen extends AbstractGameScreen {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private ItemLink itemLink;

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

    private Label text1;
    private Label text2;
    private Label text3;

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
    private Window doorWindow;

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

    public int enemyDeadCount = 0;

    public boolean stringDraw;

    private int trueLink = 0;

    private TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    private TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();

    public GameScreen(Game game, final Window optionsWindow) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = new BitmapFont(Gdx.files.internal("thai24.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(0.4f, 0, 0, 1);

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

        doorWindow = createDoorWindow();
        doorWindow.setVisible(false);

        optionsWindow.setVisible(false);

        stage.addActor(buttonOption);
        stage.addActor(buttonRule);

        stage.addActor(optionsWindow);
        stage.addActor(ruleWindow);
        stage.addActor(chartWindow);
        stage.addActor(solarcellWindow);
        stage.addActor(doorWindow);

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
        String textString = ("เวลาที่ใช้ : " + String.valueOf((1) + " วินาที"));
        text1 = new Label("สถิติ", skin);
        text2 = new Label(textString, skin);
        text3 = new Label("หากเดินไปยังประตูจะสามารถเข้าสถานที่หลบภัยได้แล้ว", skin);

        text1.setStyle(labelStyle);
        text2.setStyle(labelStyle);
        text3.setStyle(labelStyle);

        final Window chartWindow = new Window("ยินดีด้วย คุณได้รับชัยชนะ", style);
        chartWindow.setModal(true);
        chartWindow.padTop(40);
        chartWindow.padLeft(40);
        chartWindow.padRight(40);
        chartWindow.padBottom(20);
        chartWindow.getTitleLabel().setAlignment(Align.center);
        chartWindow.row().padBottom(10).padTop(10);
        chartWindow.row().padTop(10);
        chartWindow.add(text1);
        chartWindow.row().padTop(10);
        chartWindow.add(text2);
        chartWindow.row().padTop(10);
        chartWindow.add(text3);
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

    private Window createDoorWindow() {
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

        final Window doorWindow = new Window("Door", style);
        doorWindow.setModal(true);
        doorWindow.setSkin(skin);
        doorWindow.padTop(40);
        doorWindow.padLeft(40);
        doorWindow.padRight(40);
        doorWindow.padBottom(20);
        doorWindow.getTitleLabel().setAlignment(Align.center);
        doorWindow.row().padBottom(10).padTop(10);
        doorWindow.row().padTop(10);
        //doorWindow.add("ไม่สามารถเปิดประตูได้ กรุณาเชื่อมต่อระบบพลังงานแสงอาทิตย์เพื่อเติมเต็มพลังงานให้สถานที่หลบภัยให้เรียบร้อยก่อน");
        doorWindow.add("teeeeesst");
        doorWindow.row().padTop(10);
        doorWindow.add(closeButton).colspan(3);
        doorWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                doorWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.status_find = false;
            }
        });

        return doorWindow;
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

        buttonLink1 = new TextButton("เชื่อมต่อไปยังชาร์จคอนโทรลเลอร์", buttonStyle);
        buttonLink2 = new TextButton("เชื่อมต่อไปยังแบตเตอรี", buttonStyle);
        buttonLink3 = new TextButton("เชื่อมต่อไปยังอินเวอร์เตอร์", buttonStyle);
        buttonLink4 = new TextButton("เชื่อมต่อไปยังประตู", buttonStyle);

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
        Label text1 = new Label("ตามหาทางเข้าสถานที่หลบภัยให้พบ", skin);
        Label text2 = new Label("เชื่อมต่อแผงโซล่าเซลล์เพื่อเติมเต็มพลังงานให้สถานที่หลบภัย", skin);
        Label text3 = new Label("หลังจากเติมเต็มพลังงานได้แล้ว จะสามารถเข้าไปยังสถานที่หลบภัยได้", skin);

        text1.setStyle(labelStyle);
        text2.setStyle(labelStyle);
        text3.setStyle(labelStyle);

        final Window ruleWindow = new Window("กติกา", style);
        ruleWindow.setModal(true);
        ruleWindow.setSkin(skin);
        ruleWindow.padTop(40);
        ruleWindow.padLeft(40);
        ruleWindow.padRight(40);
        ruleWindow.padBottom(20);
        ruleWindow.getTitleLabel().setAlignment(Align.center);
        ruleWindow.row().padBottom(10).padTop(10);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text1);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text2);
        ruleWindow.row().padTop(10);
        ruleWindow.add(text3);
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

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        int btn_w = 200;
        int btn_h = 50;

        TextButton.TextButtonStyle buttonBowStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconBow = new TextureRegionDrawable(Assets.instance.iconBow);
        buttonBowStyle.up = iconBow;
        buttonBowStyle.over = iconBow.tint(Color.LIME);
        Button iconBowButton = new Button(buttonBowStyle);
        iconBowButton.setPosition(50, SCENE_HEIGHT - 50);

        textBullet = new Label("ลูกธนู : ", skin);
        textBullet.setColor(1, 1, 1, 1);
        textBullet.setStyle(labelStyle);
        textBullet.setFontScale(1f, 1f);
        textBullet.setPosition(50, SCENE_HEIGHT - 50);

        TextButton.TextButtonStyle buttonSwordStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconSword = new TextureRegionDrawable(Assets.instance.iconSword);
        buttonSwordStyle.up = iconSword;
        buttonSwordStyle.over = iconSword.tint(Color.LIME);
        Button iconSwordButton = new Button(buttonSwordStyle);
        iconSwordButton.setPosition(150, SCENE_HEIGHT - 50);

        textBeam = new Label("คลื่นดาบ : ", skin);
        textBeam.setColor(1, 1, 1, 1);
        textBeam.setStyle(labelStyle);
        textBeam.setFontScale(1.f, 1.f);
        textBeam.setPosition(150, SCENE_HEIGHT - 50);

        TextButton.TextButtonStyle buttonTrapStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTrap = new TextureRegionDrawable(Assets.instance.iconTrap);
        buttonTrapStyle.up = iconTrap;
        buttonTrapStyle.over = iconTrap.tint(Color.LIME);
        Button iconTrapButton = new Button(buttonTrapStyle);
        iconTrapButton.setPosition(250, SCENE_HEIGHT - 50);

        textTrap = new Label("กับดัก : ", skin);
        textTrap.setColor(1, 1, 1, 1);
        textTrap.setStyle(labelStyle);
        textTrap.setFontScale(1f, 1f);
        textTrap.setPosition(250, SCENE_HEIGHT - 50);

        TextButton.TextButtonStyle buttonTimeStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconTime = new TextureRegionDrawable(Assets.instance.iconTime);
        buttonTimeStyle.up = iconTime;
        buttonTimeStyle.over = iconTime.tint(Color.LIME);
        Button iconTimeButton = new Button(buttonTimeStyle);
        iconTimeButton.setPosition(450, SCENE_HEIGHT - 100);

        textTime = new Label("เวลาที่เหลือ : ", skin);
        textTime.setColor(1, 1, 1, 1);
        textTime.setStyle(labelStyle);
        textTime.setFontScale(1f, 1f);
        textTime.setPosition(450, SCENE_HEIGHT - 100);

        TextButton.TextButtonStyle buttonEnergyPlusStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconEnergyPlus = new TextureRegionDrawable(Assets.instance.iconEnergyPlus);
        buttonEnergyPlusStyle.up = iconEnergyPlus;
        buttonEnergyPlusStyle.over = iconEnergyPlus.tint(Color.LIME);
        Button iconEnergyPlusButton = new Button(buttonEnergyPlusStyle);
        iconEnergyPlusButton.setPosition(330, SCENE_HEIGHT - 50);

        energyLevel = new Label("พลังงานที่ผลิตต่อวินาที : ", skin);
        energyLevel.setColor(1, 1, 1, 1);
        energyLevel.setStyle(labelStyle);
        energyLevel.setFontScale(1, 1f);
        energyLevel.setPosition(330, SCENE_HEIGHT - 50);

        TextButton.TextButtonStyle buttonEnergyMinusStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconEnergyMinus = new TextureRegionDrawable(Assets.instance.iconEnergyMinus);
        buttonEnergyMinusStyle.up = iconEnergyMinus;
        buttonEnergyMinusStyle.over = iconEnergyMinus.tint(Color.LIME);
        Button iconEnergyMinusButton = new Button(buttonEnergyMinusStyle);
        iconEnergyMinusButton.setPosition(530, SCENE_HEIGHT - 50);

        energyLevel2 = new Label("พลังงานที่ใช้่อวินาที: ", skin);
        energyLevel2.setColor(1, 1, 1, 1);
        energyLevel2.setStyle(labelStyle);
        energyLevel2.setFontScale(1, 1f);
        energyLevel2.setPosition(530, SCENE_HEIGHT - 50);

        TextButton.TextButtonStyle buttonBatteryStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable iconBattery = new TextureRegionDrawable(Assets.instance.iconBattery);
        buttonBatteryStyle.up = iconBattery;
        buttonBatteryStyle.over = iconBattery.tint(Color.LIME);
        Button iconBatteryButton = new Button(buttonBatteryStyle);
        iconBatteryButton.setPosition(720, SCENE_HEIGHT - 50);

        energyLevel3 = new Label("พลังงานภายในแบตเตอรี่ : ", skin);
        energyLevel3.setColor(1, 1, 1, 1);
        energyLevel3.setStyle(labelStyle);
        energyLevel3.setFontScale(1, 1f);
        energyLevel3.setPosition(720, SCENE_HEIGHT - 50);

        stage.addActor(iconBowButton);
        stage.addActor(iconSwordButton);
        stage.addActor(iconTrapButton);
        stage.addActor(iconTimeButton);
        stage.addActor(iconEnergyPlusButton);
        stage.addActor(iconEnergyMinusButton);
        stage.addActor(iconBatteryButton);

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
        addGuiLink(solarState);
    }

    private void deleteLink(SolarState solarState) {
        if (link.size() != 0) {
            for (int i = 0; i < link.size(); i++) {
                if (link.get(i) == solarState) {
                    System.out.print("ลบ" + link.get(i) + "แล้ว");
                    link.remove(solarState);
                    checkDeledLink(solarState);
                    removeGuiLink(solarState);
                }
            }
        }
    }

    private void checkButton(final systemWindow solarWindow) {
        if ((solarWindow == systemWindow.solarcell) && (!addedStoC)) {
            buttonLink1.setText("เชื่อมต่อไปยังชาร์จคอนโทรลเลอร์");
            buttonLink1.setStyle(buttonStyle);
        } else if ((solarWindow == systemWindow.solarcell) && (addedStoC)) {
            buttonLink1.setText("ยกเลิกการเชื่อมต่อไปยังชาร์จคอนโทรลเลอร์");
            buttonLink1.setStyle(buttonStyle2);
        } else if (((solarWindow == systemWindow.chargecontroller) && (!addedStoC))
                || ((solarWindow == systemWindow.battery) && (!addedStoB))
                || ((solarWindow == systemWindow.inverter) && (!addedStoI))) {
            buttonLink1.setText("เชื่อมต่อไปยังโซล่าเซลล์");
            buttonLink1.setStyle(buttonStyle);
        } else {
            buttonLink1.setText("ยกเลิกการเชื่อมต่อไปยังโซล่าเซลล์");
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
            }
        });

        if (((solarWindow == systemWindow.solarcell) && (!addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoB))) {
            buttonLink2.setText("เชื่อมต่อไปยังแบตเตอรี");
            buttonLink2.setStyle(buttonStyle);
        } else if (((solarWindow == systemWindow.solarcell) && (addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoB))) {
            buttonLink2.setText("ยกเลิกการเชื่อมต่อไปยังแบตเตอรี");
            buttonLink2.setStyle(buttonStyle2);
        } else if (((solarWindow == systemWindow.battery) && (!addedCtoB)) || ((solarWindow == systemWindow.inverter) && (!addedCtoI))) {
            buttonLink2.setText("เชื่อมต่อไปยังชาร์จคอนโทรลเลอร์");
            buttonLink2.setStyle(buttonStyle);
        } else {
            buttonLink2.setText("ยกเลิกการเชื่อมต่อไปยังชาร์จคอนโทรลเลอร์");
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
            }
        });
        if (((solarWindow == systemWindow.solarcell) && (!addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoI))
                || ((solarWindow == systemWindow.battery) && (!addedBtoI))) {
            buttonLink3.setText("เชื่อมต่อไปยังอินเวอร์เตอร์");
            buttonLink3.setStyle(buttonStyle);
        } else if (((solarWindow == systemWindow.solarcell) && (addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoI))
                || ((solarWindow == systemWindow.battery) && (addedBtoI))) {
            buttonLink3.setText("ยกเลิกการเชื่อมต่อไปยังอินเวอร์เตอร์");
            buttonLink3.setStyle(buttonStyle2);
        } else if ((solarWindow == systemWindow.inverter) && (!addedBtoI)) {
            buttonLink3.setText("เชื่อมต่อไปยังแบตเตอรี่");
            buttonLink3.setStyle(buttonStyle);
        } else {
            buttonLink3.setText("ยกเลิกการเชื่อมต่อไปยังแบตเตอรี่");
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
            }
        });
        if (((solarWindow == systemWindow.solarcell) && (!addedStoD)) || ((solarWindow == systemWindow.chargecontroller) && (!addedCtoD))
                || ((solarWindow == systemWindow.battery) && (!addedBtoD)) || ((solarWindow == systemWindow.inverter) && (!addedItoD))) {
            buttonLink4.setText("เชื่อมต่อไปยังประตู");
            buttonLink4.setStyle(buttonStyle);
        } else {
            buttonLink4.setText("ยกเลิกการเชื่อมต่อไปยังประตู");
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
                } else {
                    deleteLink(solarState);
                }
                checkGameComplete();
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });
        solarcellWindow.pack();
    }

    private void checkDeledLink(SolarState solarState) {
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
        if (solarState == SolarState.StoC) {
            addedStoC = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(SolarCell.class),
                    findItem(Charge.class),
                    worldController.level.links, solarState);

        } else if (solarState == SolarState.StoB) {
            addedStoB = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(SolarCell.class),
                    findItem(Battery.class),
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.StoI) {
            addedStoI = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(SolarCell.class),
                    findItem(Inverter.class),
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.StoD) {
            addedStoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(SolarCell.class),
                    findItem(Door.class),
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.CtoB) {
            addedCtoB = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(Charge.class),
                    findItem(Battery.class),
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.CtoI) {
            addedCtoI = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(Charge.class),
                    findItem(Inverter.class),
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.CtoD) {
            addedCtoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(Charge.class),
                    findItem(Door.class),
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.BtoI) {
            addedBtoI = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(Battery.class),
                    findItem(Inverter.class),
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.BtoD) {
            addedBtoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(Battery.class),
                    findItem(Door.class),
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.ItoD) {
            addedItoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    findItem(Inverter.class),
                    findItem(Door.class),
                    worldController.level.links, solarState);
        }
        System.out.print("ขนาด" + itemLink.linkList.size() + "นะจ๊ะ");
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
                //if(x>trueLink) System.out.print(link.get(x-1) + "เป็นการเชื่อมต่อที่ไม่ถูกต้อง");
            }
        }
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!stringDraw) {
            textBullet.setText(String.format("ลูกธนู : %d", worldController.level.player.arrowCount) + " ดอก");
            textBeam.setText(String.format("คลื่นดาบ : %d", worldController.level.player.swordWaveCount) + " ครั้ง");
            textTrap.setText(String.format("กับดัก : %d", worldController.level.player.trapCount) + " ครั้ง");
            textTime.setText(String.format("เวลาที่เหลือ : %d", worldController.level.player.timeCount) + " วินาที");
            if (animation_status) {
                energyLevel.setText(String.format("พลังงานที่ผลิตต่อวินาที : %d", (int) EnergyProducedBar.instance.energyProduced) + " วัตต์");
            } else {
                energyLevel.setText(String.format("พลังงานที่ผลิตต่อวินาที : %d", 0) + " วัตต์");
            }
            energyLevel2.setText(String.format("พลังงานที่ใช้ต่อวินาที : %d", (int) EnergyUsedBar.instance.energyUse) + " วัตต์");
            energyLevel3.setText(String.format("พลังงานภายในแบตเตอรี : %d", (int) BatteryBar.instance.getBatteryStorage()) + " จูล");
        }
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

        Player player = worldController.level.player;
        boolean noItem = !player.status_battery
                && !player.status_ccontroller
                && !player.status_inverter
                && !player.status_solarcell
                && !player.status_door;

        if (player.status_find && noItem) {
            player.status_find = false;
            player.status_windows_link = false;
        }

        solarcellWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - solarcellWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - solarcellWindow.getHeight() / 2);
        if (!animation_status) {
            if ((player.status_solarcell == true) && (player.status_find == true)) {
                solarWindow = systemWindow.solarcell;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if ((player.status_ccontroller == true) && (player.status_find == true)) {
                solarWindow = systemWindow.chargecontroller;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if ((player.status_battery == true) && (player.status_find == true)) {
                solarWindow = systemWindow.battery;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if ((player.status_inverter == true) && (player.status_find == true)) {
                solarWindow = systemWindow.inverter;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else {
                solarcellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        }

        doorWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - doorWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - doorWindow.getHeight() / 2);
        if (!animation_status) {
            if ((player.status_door == true) && (player.status_find == true)) {
                doorWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else {
                doorWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        }

        if ((trueLink == 4) && (!animation_status)) {
            findItem(SolarCell.class).state = Item.ItemState.ONLOOP;
            findItem(SolarCell.class).resetAnimation();
            findItem(Inverter.class).state = Item.ItemState.ONLOOP;
            findItem(Inverter.class).resetAnimation();
            findItem(Battery.class).state = Item.ItemState.ONLOOP;
            findItem(Battery.class).resetAnimation();
            findItem(Charge.class).state = Item.ItemState.ONLOOP;
            findItem(Charge.class).resetAnimation();
            findItem(Door.class).state = Item.ItemState.ON;
            findItem(Door.class).resetAnimation();
            animation_status = true;
            player.timeStop = true;
            String textString = ("เวลาที่ใช้ : " + String.valueOf((player.getIntitalTime() - player.timeCount) + " วินาที"));
            text2.setText(textString);
            chartWindow.setPosition(
                    Gdx.graphics.getWidth() / 2 - chartWindow.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - chartWindow.getHeight() / 2);
            chartWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
        } else if (animation_status && BatteryBar.instance.getBatteryStorage() < BatteryBar.instance.BATTERY_MAX) {
            BatteryBar.instance.update(deltaTime);
            player.timeStop = true;
        }

        if ((findItem(Door.class).state == Item.ItemState.ON) && (player.status_door == true)) {
            game.setScreen(new GameScreen2(game, optionsWindow));
        }

        for (int i = 0; i < worldController.level.enemies.size(); i++) {
            Enemy enemy = worldController.level.enemies.get(i);
            if (enemy.dead && !enemy.count) {
                BatteryBar.instance.addEnergy(1000);
                enemy.count = true;
            }
        }

        if (worldController.level.enemies.size() == 0) {
            player.stageoneclear = true;
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
