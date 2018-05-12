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
    private Label text4;
    private Label text5;
    private Label text6;
    private Label text7;
    private Label text8;

    private String textSolarcell = "แผงโซล่าเซลล์";
    private String textCharge = "ชาร์จคอนโทรลเลอร์";
    private String textBattery = "แบตเตอรี";
    private String textInverter = "อินเวอร์เตอร์";
    private String textDoor = "ประตูไฟฟ้า";

    private Label labelSolarCell;
    private Label labelBattery;
    private Label labelCharge;
    private Label labelInverter;
    private Label labelDoor;

    private boolean stageFourClear;
    private boolean dialogCitizen2;

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

    private ImageButton imageLink1;
    private ImageButton imageLink2;
    private ImageButton imageLink3;
    private ImageButton imageLink4;

    private TextButton buttonN1;
    private TextButton buttonN2;

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

    private int countEnemy;

    private int trueLink = 0;

    private Dialog dialog;
    private Texture dialogStory;
    private int citizenCount = 0;

    private String text =
            "\"จากข้อมูลที่ได้รับมา สถานที่หลบภัยต้องอยู่ภายในพื้นที่แถบนี้\" \n\"เอาล่ะ รีบเร่งมือกันเถอะ (กด Enter เพื่อเริ่มเกม)\"";

    private boolean dialogEnemy;
    private boolean dialogCitizen;
    private boolean dialogDoor1;
    private boolean dialogDoor2;
    private boolean dialogDoor3;
    private boolean dialogDoor4;

    private boolean stageTwoClear;
    private boolean stageThreeClear;

    private TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    private TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();

    public GameScreen(Game game, final Window optionsWindow) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = new BitmapFont(Gdx.files.internal("thai24.fnt"));
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
        ruleWindow.setVisible(false);

        chartWindow = createChartWindow();
        chartWindow.setVisible(false);

        solarcellWindow = createSolarcellWindow();
        solarcellWindow.setVisible(false);

        optionsWindow.setVisible(false);

        dialog.clearPages();
        dialog.addWaitingPage(text);

        stage.addActor(dialog);

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
        style.background = new TextureRegionDrawable(Assets.instance.window);
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.BLACK;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Button.ButtonStyle buttonChartStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonChartStyle.up = buttonRegion;
        buttonChartStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonChartStyle);
        String textString = ("เวลาที่ใช้ : " + String.valueOf((1) + " วินาที"));
        text1 = new Label("สถิติ", skin);
        text2 = new Label(textString, skin);
        text3 = new Label(textString, skin);
        text4 = new Label(textString, skin);
        text5 = new Label(textString, skin);
        text6 = new Label("หากเดินไปยังประตูจะสามารถเข้าสถานที่หลบภัยได้แล้ว", skin);

        text1.setStyle(labelStyle);
        text2.setStyle(labelStyle);
        text3.setStyle(labelStyle);
        text4.setStyle(labelStyle);
        text5.setStyle(labelStyle);
        text6.setStyle(labelStyle);

        final Window chartWindow = new Window("ยินดีด้วย คุณได้รับชัยชนะ", style);
        chartWindow.setModal(true);
        chartWindow.padTop(50);
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
        chartWindow.add(text4);
        chartWindow.row().padTop(10);
        chartWindow.add(text5);
        chartWindow.row().padTop(10);
        chartWindow.add(text6);
        chartWindow.row().padTop(10);
        chartWindow.add(closeButton).colspan(3);
        chartWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chartWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                dialogDoor4 = true;
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

    private Window createSolarcellWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new TextureRegionDrawable(Assets.instance.window);
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.BLACK;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Button.ButtonStyle buttonSolarStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonSolarStyle.up = buttonRegion;
        buttonSolarStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonSolarStyle);

        imageLink1 = new ImageButton(new TextureRegionDrawable(Assets.instance.buttonChargeAdd));
        imageLink2 = new ImageButton(new TextureRegionDrawable( Assets.instance.buttonBatteryAdd));
        imageLink3 = new ImageButton(new TextureRegionDrawable( Assets.instance.buttonInverterAdd));
        imageLink4 = new ImageButton(new TextureRegionDrawable( Assets.instance.buttonDoorAdd));

        labelSolarCell = new Label(textSolarcell, skin);
        labelBattery = new Label(textBattery, skin);
        labelCharge = new Label(textCharge, skin);
        labelInverter = new Label(textInverter, skin);
        labelDoor = new Label(textDoor, skin);

        final Window solarcellWindow = new Window("ตัวเลือกการเชื่อมต่อ", style);
        solarcellWindow.setModal(true);
        solarcellWindow.padTop(50);
        solarcellWindow.padLeft(40);
        solarcellWindow.padRight(40);
        solarcellWindow.padBottom(20);
        solarcellWindow.getTitleLabel().setAlignment(Align.center);
        solarcellWindow.row().padBottom(10).padTop(10);
        solarcellWindow.add(imageLink1);
        solarcellWindow.add(imageLink2).padLeft(20);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(labelCharge);
        solarcellWindow.add(labelBattery).padLeft(20);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(imageLink3);
        solarcellWindow.add(imageLink4).padLeft(20);
        solarcellWindow.row().padTop(10);
        solarcellWindow.add(labelInverter);
        solarcellWindow.add(labelDoor).padLeft(20);
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
        style.background = new TextureRegionDrawable(Assets.instance.window);
        style.titleFont = font;
        style.titleFontColor = Color.BLACK;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

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
        ruleWindow.padTop(50);
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
            imageLink1.setBackground(new TextureRegionDrawable( Assets.instance.buttonChargeAdd));
            solarCell.setText("");
        } else if ((solarWindow == systemWindow.solarcell) && (addedStoC)) {
            imageLink1.setBackground(new TextureRegionDrawable( Assets.instance.buttonChargeDel));
        } else if (((solarWindow == systemWindow.chargecontroller) && (!addedStoC))
                || ((solarWindow == systemWindow.battery) && (!addedStoB))
                || ((solarWindow == systemWindow.inverter) && (!addedStoI))) {
            imageLink1.setBackground(new TextureRegionDrawable( Assets.instance.buttonSolarcellAdd));
        } else {
            imageLink1.setBackground(new TextureRegionDrawable( Assets.instance.buttonSolarcellDel));
        }
        imageLink1.clearListeners();
        imageLink1.addListener(new ClickListener() {
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
            imageLink2.setBackground(new TextureRegionDrawable( Assets.instance.buttonBatteryAdd));
        } else if (((solarWindow == systemWindow.solarcell) && (addedStoB)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoB))) {
            imageLink2.setBackground(new TextureRegionDrawable( Assets.instance.buttonBatteryDel));
        } else if (((solarWindow == systemWindow.battery) && (!addedCtoB)) || ((solarWindow == systemWindow.inverter) && (!addedCtoI))) {
            imageLink2.setBackground(new TextureRegionDrawable( Assets.instance.buttonChargeAdd));
        } else {
            imageLink2.setBackground(new TextureRegionDrawable( Assets.instance.buttonChargeDel));
        }
        imageLink2.clearListeners();
        imageLink2.addListener(new ClickListener() {
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
            imageLink3.setBackground(new TextureRegionDrawable( Assets.instance.buttonInverterAdd));
        } else if (((solarWindow == systemWindow.solarcell) && (addedStoI)) || ((solarWindow == systemWindow.chargecontroller) && (addedCtoI))
                || ((solarWindow == systemWindow.battery) && (addedBtoI))) {
            imageLink3.setBackground(new TextureRegionDrawable( Assets.instance.buttonInverterDel));
        } else if ((solarWindow == systemWindow.inverter) && (!addedBtoI)) {
            imageLink3.setBackground(new TextureRegionDrawable( Assets.instance.buttonBatteryAdd));
        } else {
            imageLink3.setBackground(new TextureRegionDrawable( Assets.instance.buttonBatteryDel));
        }
        imageLink3.clearListeners();
        imageLink3.addListener(new ClickListener() {
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
            imageLink4.setBackground(new TextureRegionDrawable( Assets.instance.buttonDoorAdd));
        } else {
            imageLink4.setBackground(new TextureRegionDrawable( Assets.instance.buttonDoorDel));

        }
        imageLink4.clearListeners();
        imageLink4.addListener(new ClickListener() {
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

        Level1 level1 = (Level1) worldController.level;

        if (solarState == SolarState.StoC) {
            addedStoC = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.solarCell,
                    level1.charge,
                    worldController.level.links, solarState);

        } else if (solarState == SolarState.StoB) {
            addedStoB = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.solarCell,
                    level1.battery,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.StoI) {
            addedStoI = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.solarCell,
                    level1.inverter,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.StoD) {
            addedStoD = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.solarCell,
                    level1.door,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.CtoB) {
            addedCtoB = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.charge,
                    level1.battery,
                    worldController.level.links, solarState);
        } else if (solarState == SolarState.CtoI) {
            addedCtoI = true;
            itemLink = new ItemLink(worldController.level.mapLayer,
                    level1.charge,
                    level1.inverter,
                    worldController.level.links, solarState);
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
        player.timeClear = true;
        String textString5 = ("เวลาที่ใช้ : " + String.valueOf((player.getIntitalTime() - player.timeCount) + " วินาที"));
        String textString4 = ("มอนสเตอร์ที่ถูกกำจัด : " + String.valueOf((countEnemy) + " ตัว"));
        String textString3 = ("อัตราการผลิตพลั" +
                "งงาน : " + String.valueOf((EnergyProducedBar.instance.energyProduced) + " วัตต์ต่อวินาที"));
        String textString2 = ("อัตราการใช้พลังงาน : " + String.valueOf(EnergyUsedBar.instance.energyUse) + " วัตต์ต่อวินาที");
        String textString = ("พลังงานที่ได้รับจากมอนสเตอร์ : " + String.valueOf((BatteryBar.instance.getBatteryStorage()) + " จูล"));
        text2.setText(textString5);
        text3.setText(textString4);
        text4.setText(textString3);
        text5.setText(textString2);
        text6.setText(textString);
        chartWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - chartWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - chartWindow.getHeight() / 2);
        chartWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Player player = worldController.level.player;
        Level1 level1 = (Level1) worldController.level;

        if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                dialog.hide();
                player.timeStop = false;
                if (stageFourClear) {
                    game.setScreen(new GameScreen2(game, optionsWindow));
                }
            } else {
                dialog.tryToChangePage();
            }
        }

        textBullet.setText(String.format(" %d", (int) ArrowBar.instance.energyArrow));
        textBeam.setText(String.format(" %d", (int) SwordWaveBar.instance.energySwordWave));
        textTrap.setText(String.format(" %d", (int) TrapBar.instance.energyTrap));
        textTime.setText(String.format(" %d", worldController.level.player.timeCount) + " วินาที");

        if (animation_status) {
            energyLevel.setText(String.format(" %d", (int) EnergyProducedBar.instance.energyProduced) + " วัตต์");
        } else {
            energyLevel.setText(String.format(" %d", 0) + " วัตต์");
        }
        energyLevel2.setText(String.format(" %d", (int) EnergyUsedBar.instance.energyUse) + " วัตต์");
        energyLevel3.setText(String.format(" %d", (int) BatteryBar.instance.getBatteryStorage()) + " จูล");

        if (Gdx.input.isKeyJustPressed(Keys.M)) {
            game.setScreen(new MenuScreen(game));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            worldController.level.enemies.clear();
        }


        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            for (int i = 0; i < worldController.level.citizens.size(); i++) {
                Citizen citizen = worldController.level.citizens.get(i);
                if (!citizen.runPlayer) {
                    dialogCitizen = true;
                    citizen.runPlayer = true;
                    citizenCount += 1;
                }
            }
        }

        if (!worldController.level.player.isAlive()) {
            MusicManager.instance.stop();
            game.setScreen(new GameOverScreen(game));
            return;
        }

        if (worldController.level.player.timeCount <= 0) {
            MusicManager.instance.stop();
            game.setScreen(new GameOverScreen(game));
            return;
        }

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

        if (player.status_find && noItem && noCitizen) {
            player.status_find = false;
            player.status_windows_link = false;
        }

        solarcellWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - solarcellWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - solarcellWindow.getHeight() / 2);
        if (!animation_status && player.status_find && dialogDoor3) {
            if (level1.solarCell.nearPlayer()) {
                solarWindow = systemWindow.solarcell;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.charge.nearPlayer()) {
                solarWindow = systemWindow.chargecontroller;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.battery.nearPlayer()) {
                solarWindow = systemWindow.battery;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else if (level1.inverter.nearPlayer()) {
                solarWindow = systemWindow.inverter;
                checkButton(solarWindow);
                solarcellWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            } else {
                solarcellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        }

        if (!animation_status) {
            if ((level1.door.nearPlayer()) && (player.status_find)) {
                if (!player.stageOneClear && !dialogDoor1) {
                    dialogDoor1 = true;
                    player.timeStop = true;
                    String text =
                            "\"พื้นที่แห่งนี้ยังอันตรายอยู่ โปรดกำจัดมอนสเตอร์ให้หมด แล้วประชาชนที่ซ่อนอยู่จะปรากฎตัวออกมา\" \n\" (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.show();
                    dialog.clearPages();
                    dialog.addWaitingPage(text);
                } else if (player.stageOneClear && !stageTwoClear && !dialogDoor2) {
                    dialogDoor2 = true;
                    player.timeStop = true;
                    String text =
                            "\"ยังตามหาประชาชนที่ซ่อนตัวอยู่ไม่ครบ กรุณาตามหาให้ครบก่อน\" \n\"เ (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.show();
                    dialog.clearPages();
                    dialog.addWaitingPage(text);
                } else if (stageTwoClear && !stageThreeClear && !dialogDoor3) {
                    dialogDoor3 = true;
                    player.timeStop = true;
                    String text =
                            "\"เหมือนว่าพลังงานในสถานที่หลบภัยจะหมดลง กรุณาเชื่อมต่อระบบโซล่าเซลล์เพื่อผลิตพลังงาน\" \n\" (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.show();
                    dialog.clearPages();
                    dialog.addWaitingPage(text);
                }
            }
        }
        if (animation_status && stageThreeClear && !dialogDoor4 && level1.door.nearPlayer() && player.status_find) {
            chartStatus();
        }

        if (player.stageOneClear && !dialogCitizen) {
            dialogCitizen = true;
            player.timeStop = true;
            String text =
                    "\"กำจัดมอนสเตอร์หมดแล้ว กรุณาตามหาประชาชนแล้วพาไปยังสถานที่หลบภัยพร้อมกัน\" \n\"(กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
            dialog.show();
            dialog.clearPages();
            dialog.addWaitingPage(text);
        }

        if (!dialogEnemy) {
            for (int i = 0; i < worldController.level.enemies.size(); i++) {
                Enemy enemy = worldController.level.enemies.get(i);
                if (enemy.stateMachine.getCurrentState() == EnemyState.RUN_TO_PLAYER && !enemy.count) {
                    dialogEnemy = true;
                    player.timeStop = true;
                    String text =
                            "\"ได้ยินเสียงของอะไรบางอย่างกำลังเคลื่อนไหวใกล้เข้ามา\" \n\"โปรดระวังตัว (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.show();
                    dialog.clearPages();
                    dialog.addWaitingPage(text);
                }
            }
        }

        if (player.stageOneClear && player.status_find) {
            for (int i = 0; i < worldController.level.citizens.size(); i++) {
                Citizen citizen = worldController.level.citizens.get(i);
                if (citizen.overlapPlayer && !citizen.runPlayer) {
                    dialogCitizen = true;
                    player.timeStop = true;
                    String text =
                            "\"โปรดตามเรามา เราจะพาท่านไปยังสถานที่ปลอดภัย\" \n\" (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.show();
                    dialog.clearPages();
                    dialog.addWaitingPage(text);
                    citizen.runPlayer = true;
                    citizenCount += 1;
                }
            }
        }
        
        if(citizenCount == worldController.level.citizens.size() && !dialogCitizen2){
            dialogCitizen2 = true;
            player.timeStop = true;
            String text =
                    "\"ค้นพบประชาชนในพื้นที่แถบนี้ครบแล้ว ลองไปตรวจสอบที่ประตูทางเข้าสถานที่หลบภัยอีกรอบ\" \n\" (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
            dialog.show();
            dialog.clearPages();
            dialog.addWaitingPage(text);
        }

        if (player.stageOneClear && citizenCount == level1.citizens.size() && !stageTwoClear) {
            stageTwoClear = true;
        }

        if ((trueLink == 4) && (!animation_status)) {
            for (Item item : level1.items) {
                item.state = Item.ItemState.ONLOOP;
                item.resetAnimation();
            }
            stageThreeClear = true;
            level1.door.state = Item.ItemState.ON;
            animation_status = true;
            dialogCitizen = true;
            player.timeStop = true;
            String text =
                    "\"เยี่ยม ระบบผลิตพลังงานไฟฟ้าด้วยแสงอาทิตย์ทำงานแล้ว รีบพาประชาชนไปยังประตูกันเถอะ\" \n\" (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
            dialog.show();
            dialog.clearPages();
            dialog.addWaitingPage(text);

        }

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
        worldController = new WorldController(new Level1());
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setInputProcessor(stage);

        MusicManager.instance.stop();
        MusicManager.instance.play(MusicManager.Musics.MUSIC_2, true);
    }

    @Override
    public void hide() {
        dialogStory.dispose();
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
