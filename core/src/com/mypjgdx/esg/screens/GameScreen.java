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

    private TextButton buttonAgree;
    private TextButton buttonRefuse;

    private Label textBullet;
    private Label textBeam;
    private Label textTrap;
    private Label textTime;
    private Label energyLevel;
    private Label energyLevel2;
    private Label energyLevel3;

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
    private String textCharge = "เชื่อมต่อไปยังชาร์จคอนโทรลเลอร์";
    private String textBattery = "เชื่อมต่อไปยังแบตเตอรี";
    private String textInverter = "เชื่อมต่อไปยังอินเวอร์เตอร์";
    private String textDoor = "เชื่อมต่อไปยังประตูไฟฟ้า";

    private String textSolarcell2 = "ยกเลิกการเชื่อมต่อไปยังแผงโซล่าเซลล์";
    private String textCharge2 = "ยกเลิกการเชื่อมต่อไปยังชาร์จคอนโทรลเลอร์";
    private String textBattery2 = "ยกเลิกการเชื่อมต่อไปยังแบตเตอรี";
    private String textInverter2 = "ยกเลิกการเชื่อมต่อไปยังอินเวอร์เตอร์";
    private String textDoor2 = "ยกเลิกการเชื่อมต่อไปยังประตูไฟฟ้า";

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

    public enum systemWindow {
        solarcell,
        chargecontroller,
        battery,
        inverter
    }

    private SolarState solarState;
    private systemWindow solarWindow;

    private Window solarCellGuideWindow;

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

    private String text =
            "\"จากข้อมูลที่ได้รับมา สถานที่หลบภัยต้องอยู่ภายในพื้นที่แถบนี้ รีบเร่งมือค้นหาทางเข้าภายในเวลาที่กำหนด\" \n\"เ(กด Enter เพื่อเริ่มเกม)\"";

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
        buttonRule = new Button(buttonPauseStyle);
        buttonRule.setPosition(SCENE_WIDTH - 100, SCENE_HEIGHT - 50);

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

        chartWindow = createChartWindow();
        chartWindow.setVisible(false);

        statusWindow = createStatusWindow();
        statusWindow.setVisible(false);

        solarCellGuideWindow = createSolarCellGuideWindow();
        solarCellGuideWindow.setVisible(false);

        solarCellWindow = createSolarCellWindow();
        solarCellWindow.setVisible(false);

        optionsWindow.setVisible(false);

        dialog.hide();

        stage.addActor(dialog);

        stage.addActor(buttonOption);
        stage.addActor(buttonRule);
        stage.addActor(buttonAgree);
        stage.addActor(buttonRefuse);

        stage.addActor(optionsWindow);
        stage.addActor(ruleWindow);
        stage.addActor(chartWindow);
        stage.addActor(statusWindow);
        stage.addActor(solarCellGuideWindow);
        stage.addActor(solarCellWindow);

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
                        game.setScreen(new GameScreen2(game,optionsWindow));
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
                    }
                });
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

        textChart1.setStyle(labelStyle);
        textChart2.setStyle(labelStyle);
        textChart3.setStyle(labelStyle);
        textChart4.setStyle(labelStyle);
        textChart5.setStyle(labelStyle);
        textChart6.setStyle(labelStyle);

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
        statusWindow.padTop(60);
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

    private Window createSolarCellGuideWindow() {
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

        Label text1 = new Label("การทำงานของระบบโซล่าเซลล์", skin);
        Label text2 = new Label("ตัวแผงโซล่าเซลล์จะคอยดูดซับพลังงานแสงอาทิตย์เปลี่ยนเป็นพลังงานไฟฟ้า", skin);
        Label text3 = new Label("โดยจะมีชาร์จคอนโทรลเลอร์คอยคุมกระแสไฟฟ้าว่าจะนำส่งไปยังที่ใด", skin);
        Label text4 = new Label("พลังงานไฟฟ้าจะถูกจัดเก็บภายในแบตเตอรี่ก่อนเป็นอันดับแรก", skin);
        Label text5 = new Label("หากมีการใช้งานเครื่องใช้ไฟฟ้าจะมาดึงพลังงานจากแบตเตอรี่ผ่านชาร์จคอนโทรลเลอร์ไม่ใช่จากแผงโซล่าเซลล์โดยตรง", skin);
        Label text6 = new Label("ไฟฟ้าที่ผลิตได้จะเป็นไฟฟ้ากระแสตรง ต้องผ่านอินเวอร์เตอร์ก่อนถึงจะนำไปใช้กับเครื่องใช้ไฟฟ้าภายในที่หลบภัยได้", skin);
        Label text7 = new Label("เนื่องจากอินเวอร์เตอร์ และชาร์จคอนโทรลเลอร์ต้องใช้ไฟฟ้าในการทำงาน จำเป็นต้องมีพลังงานภายในแบตเตอรี่ก่อน", skin);
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

        final Window solarCellGuideWindow = new Window("ข้อมูลระบบโซล่าเซลล์", style);
        solarCellGuideWindow.setModal(true);
        solarCellGuideWindow.padTop(60);
        solarCellGuideWindow.padLeft(40);
        solarCellGuideWindow.padRight(40);
        solarCellGuideWindow.padBottom(20);
        solarCellGuideWindow.getTitleLabel().setAlignment(Align.center);
        solarCellGuideWindow.row().padBottom(10).padTop(10);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text1);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text2);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text3);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text4);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text5);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text6);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text7);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text8);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(text9);
        solarCellGuideWindow.row().padTop(10);
        solarCellGuideWindow.add(closeButton).colspan(3);
        solarCellGuideWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarCellGuideWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.solarCellGuideWindow = false;
                worldController.level.player.timeStop = false;
            }
        });

        return solarCellGuideWindow;
    }

    private Window createSolarCellWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new TextureRegionDrawable(Assets.instance.window);
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
        solarcellWindow.padTop(50);
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
        Label text5 = new Label("กด A เพื่อติดต่อกับวัตถุ หรือประชาชน", skin);
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
                if(!dialogShow){
                    worldController.level.player.timeStop = false;
                }
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
        player.timeStop = true;
        player.timeClear = true;
        String textString1 = ("เวลาที่ใช้ : " + String.valueOf((player.getIntitalTime() - player.timeCount) + " วินาที"));
        String textString2 = ("มอนสเตอร์ที่ถูกกำจัด : " + String.valueOf((countEnemy) + " ตัว"));
        String textString3 = ("อัตราการผลิตพลังงาน : " + String.valueOf((EnergyProducedBar.instance.energyProduced) + " วัตต์"));
        String textString4 = ("อัตราการใช้พลังงาน : " + String.valueOf(EnergyUsedBar.instance.energyUse) + " วัตต์");
        String textString5 = ("พลังงานที่ได้รับจากมอนสเตอร์ : " + String.valueOf((countEnemy*1000) + " จูล"));
        textChart2.setText(textString1);
        textChart3.setText(textString2);
        textChart4.setText(textString3);
        textChart5.setText(textString4);
        textChart6.setText(textString5);
        chartWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - chartWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - chartWindow.getHeight() / 2);
        chartWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    private void status() {

        worldController.level.player.timeStop = true;

        String textString1 = ("กำลังไฟฟ้าผลิต : " + String.valueOf((EnergyProducedBar.instance.energyProduced) + " วัตต์"));
        String textString2 = ("กำลังไฟฟ้าใช้งานรวม : " + String.valueOf(EnergyUsedBar.instance.energyUse) + " วัตต์");
        if (EnergyProducedBar.instance.energyProduced < EnergyUsedBar.instance.energyUse) {
            String textString3 = ("อีก : " + String.valueOf((int)(BatteryBar.instance.getBatteryStorage() / (((EnergyProducedBar.instance.energyProduced*30) - (EnergyUsedBar.instance.energyUse*30)))) + " วินาทีพลังงานจะหมดลง"));
            text3.setText(textString3);
        } else {
            String textString3 = ("อีก : " + String.valueOf((int)(BatteryBar.instance.getBatteryStorageBlank() / (((EnergyProducedBar.instance.energyProduced*30) - (EnergyUsedBar.instance.energyUse*30)))) + " วินาทีพลังงานจะเต็มแบตเตอรี่"));
            text3.setText(textString3);
        }

        String textString4 = ("กำลังไฟฟ้าผลิตที่ผลิตได้หลังจากหักลบแล้ว : " + String.valueOf((EnergyProducedBar.instance.energyProduced - EnergyUsedBar.instance.energyUse)) + " วัตต์");
        text1.setText(textString1);
        text2.setText(textString2);
        text4.setText(textString4);
        statusWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - statusWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - statusWindow.getHeight() / 2);
        statusWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    private void solarCellGuide() {
        worldController.level.player.timeStop = true;
        solarCellGuideWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - solarCellGuideWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - solarCellGuideWindow.getHeight() / 2);
        solarCellGuideWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }

    private void controlAndDebug() {

        Player player = worldController.level.player;

        if (Gdx.input.isKeyJustPressed(Keys.M)) {
            MusicManager.instance.stop();
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MenuScreen(game));
                }
            });
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            for(Enemy enemy : worldController.level.enemies){
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
                }
            }
        }

        if (!worldController.level.player.isAlive()) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new GameOverScreen(game));
                }
            });
        }

        if (worldController.level.player.timeCount <= 0) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new GameOverScreen(game));
                }
            });
        }

        if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                dialog.hide();
                player.timeStop = false;
                dialogShow = false;
            } else {
                dialog.tryToChangePage();
            }
        }
    }

    private void textIconDraw() {
        textBullet.setText(String.format(" %d", (int) ArrowBar.instance.energyArrow));
        textBeam.setText(String.format(" %d", (int) SwordWaveBar.instance.energySwordWave));
        textTrap.setText(String.format(" %d", (int) TrapBar.instance.energyTrap));
        textTime.setText(String.format(" %d", worldController.level.player.timeCount) + " วินาที");

        if (animation_status) {
            EnergyProducedBar.instance.energyProduced = 2500;
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

        if(player.timeCount == 299 && !dialogStart){
            player.timeStop = true;
            dialog.clearPages();
            dialog.addWaitingPage(text);
            dialog.show();
            dialogStart = true;
            dialogShow = true;
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
                    dialogShow = true;
                } else if (player.stageOneClear && !stageTwoClear && !dialogDoor2) {
                    dialogDoor2 = true;
                    player.timeStop = true;
                    String text =
                            "\"ยังตามหาประชาชนที่ซ่อนตัวอยู่ไม่ครบ กรุณาตามหาให้ครบก่อน\" \n\"เ (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.show();
                    dialog.clearPages();
                    dialog.addWaitingPage(text);
                    dialogShow = true;
                } else if (stageTwoClear && !stageThreeClear && !dialogDoor3) {
                    dialogDoor3 = true;
                    player.timeStop = true;
                    String text =
                            "\"เหมือนว่าพลังงานในสถานที่หลบภัยจะหมดลง กรุณาเชื่อมต่อระบบโซล่าเซลล์เพื่อผลิตพลังงาน\" \n\" (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                    dialog.show();
                    dialog.clearPages();
                    dialog.addWaitingPage(text);
                    dialogShow = true;
                }
            }
            if (player.stageOneClear && !dialogCitizen) {
                dialogCitizen = true;
                player.timeStop = true;
                String text =
                        "\"กำจัดมอนสเตอร์หมดแล้ว กรุณาตามหาประชาชนแล้วพาไปยังสถานที่หลบภัยพร้อมกัน\" \n\"(กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                dialog.show();
                dialog.clearPages();
                dialog.addWaitingPage(text);
                dialogShow = true;
            }
            if (player.timeCount <= 298 && !dialogEnemy && dialogStart) {
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
                        dialogShow = true;
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
                        citizen.runPlayer = true;
                        citizenCount += 1;
                        dialog.show();
                        dialog.clearPages();
                        dialog.addWaitingPage(text);
                        dialogShow = true;
                    }
                }
            }
            if (citizenCount == worldController.level.citizens.size() && !dialogCitizen2) {
                dialogCitizen2 = true;
                player.timeStop = true;
                String text =
                        "\"รวบรวมประชาชนได้ครบแล้ว ลองไปตรวจสอบที่ประตูทางเข้าสถานที่หลบภัยอีกรอบ\" \n\" (กรุณากด Enter เพื่อเล่นเกมต่อ)\"";
                dialog.show();
                dialog.clearPages();
                dialog.addWaitingPage(text);
                dialogShow = true;
            }
            if ((trueLink == 4)) {
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
                buttonAgree.setVisible(true);
                buttonRefuse.setVisible(true);
                dialog.clearPages();
                dialog.addWaitingPage(text);
                dialogShow = true;
            }
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

        if (player.statusEnergyWindow) {
            status();
        } else {
            statusWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
        }

        if (player.solarCellGuideWindow) {
            solarCellGuide();
        } else {
            solarCellGuideWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
        }

        solarCellWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - solarCellWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - solarCellWindow.getHeight() / 2);
        if (!animation_status && player.status_find && dialogDoor3) {
            if (level1.solarCell.nearPlayer()) {
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

        if (noItem == false) {
            solarCellWindow.setVisible(false);
        }

        boolean noCitizen = !player.questScreen1
                && !player.questScreen2
                && !player.questScreen3
                && !player.questScreen4
                && !player.questScreen5
                && !player.questScreen6;

        if (noItem && noCitizen) {
            player.status_find = false;
            player.status_windows_link = false;
            solarCellWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
        }
    }

    @Override
    public void render(float deltaTime) {

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkObject();
        controlAndDebug();
        textIconDraw();
        dialogDraw();
        checkStageAndCount();
        checkWindow();

        if(!worldController.level.player.timeStop && BatteryBar.instance.getBatteryStorage()<BatteryBar.BATTERY_MAX){
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
        font.dispose();
        bg.dispose();
        EnergyProducedBar.instance.energyProduced = 0;
        EnergyUsedBar.instance.energyUse = 0;
        BatteryBar.instance.batteryStorage = 0;
    }

    @Override
    public void pause() {
    }

}
