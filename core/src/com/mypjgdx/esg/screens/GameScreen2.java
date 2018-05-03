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
import com.mypjgdx.esg.game.levels.Level2;
import com.mypjgdx.esg.game.levels.Level2Generator;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.items.Switch;
import com.mypjgdx.esg.ui.EnergyBar;
import com.mypjgdx.esg.utils.ItemLink;
import com.mypjgdx.esg.utils.QuestState;

import java.util.ArrayList;

public class GameScreen2 extends AbstractGameScreen {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private ItemLink itemLink;

    SpriteBatch batch;
    public Texture bg;

    private Stage stage;
    private Skin skin;
    ;
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

    public systemWindow citizenQuest = null;

    public enum systemWindow {
        citizen1,
        citizen2,
        citizen3,
        citizen4,
        citizen5,
        citizen6
    }

    public QuestState questState = null;

    private ArrayList<QuestState> isComplete = new ArrayList<QuestState>();
    private ArrayList<QuestState> addRequest = new ArrayList<QuestState>();

    private TextButton buttonLink1;
    private TextButton buttonLink2;
    private TextButton buttonLink3;
    private TextButton buttonLink4;

    private Label labelTitle;

    private Button buttonOption;
    private BitmapFont font;
    private Window optionsWindow;
    private boolean animation_status = false;

    private Button buttonRule;
    private Window ruleWindow;
    private Window chartWindow;
    private Window requestCitizenWindow;

    private float startX;
    private float startY;
    private float goalX;
    private float goalY;
    private float startWidth;
    private float startHeight;
    private float goalWidth;
    private float goalHeight;

    public int enemyDeadCount = 0;
    private int trueLink = 0;

    private TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    private TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();

    public GameScreen2(Game game, final Window optionsWindow) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = new BitmapFont();

        this.optionsWindow = optionsWindow;

        isComplete.add(questState.quest1yes);
        isComplete.add(questState.quest2yes);
        isComplete.add(questState.quest3no);
        isComplete.add(questState.quest4yes);
        isComplete.add(questState.quest5yes);
        isComplete.add(questState.quest6no);

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

        requestCitizenWindow = createRequestWindow();
        requestCitizenWindow.setVisible(false);

        optionsWindow.setVisible(false);

        stage.addActor(buttonOption);
        stage.addActor(buttonRule);

        stage.addActor(optionsWindow);
        stage.addActor(ruleWindow);
        stage.addActor(chartWindow);
        stage.addActor(requestCitizenWindow);

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

    private Window createRequestWindow() {
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

        buttonLink1 = new TextButton("YES", buttonStyle);
        buttonLink2 = new TextButton("NO", buttonStyle);
        labelTitle = new Label("Choice", labelStyle);

        final Window requestWindow = new Window("Choice", style);
        requestWindow.setModal(true);
        requestWindow.padTop(40);
        requestWindow.padLeft(40);
        requestWindow.padRight(40);
        requestWindow.padBottom(20);
        requestWindow.getTitleLabel().setAlignment(Align.center);
        requestWindow.row().padBottom(10).padTop(10);
        requestWindow.add(buttonLink1);
        requestWindow.add(buttonLink2).padLeft(10);
        requestWindow.row().padTop(10);
        requestWindow.add(closeButton).colspan(2);
        requestWindow.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                requestWindow.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                worldController.level.player.questScreen1 = false;
                worldController.level.player.questScreen2 = false;
                worldController.level.player.questScreen3 = false;
                worldController.level.player.questScreen4 = false;
                worldController.level.player.questScreen5 = false;
                worldController.level.player.questScreen6 = false;
            }
        });

        return requestWindow;
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

    private void checkButton(final systemWindow requestWindow) {
        buttonLink1.setText("YES");
        buttonLink1.clearListeners();
        buttonLink1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (citizenQuest == systemWindow.citizen1) {
                    questState = QuestState.quest1yes;
                    worldController.level.player.quest1 = true;
                    worldController.level.player.quest_window_1 = true;
                } else if (citizenQuest == systemWindow.citizen2) {
                    questState = QuestState.quest2yes;
                    worldController.level.player.quest_window_2 = true;
                    worldController.level.player.quest2 = true;
                } else if (citizenQuest == systemWindow.citizen3) {
                    questState = QuestState.quest3yes;
                    worldController.level.player.quest_window_3 = true;
                    worldController.level.player.quest3 = true;
                } else if (citizenQuest == systemWindow.citizen4) {
                    questState = QuestState.quest4yes;
                    worldController.level.player.quest_window_4 = true;
                    worldController.level.player.quest4 = true;
                } else if (citizenQuest == systemWindow.citizen5) {
                    questState = QuestState.quest5yes;
                    worldController.level.player.quest_window_5 = true;
                    worldController.level.player.quest5 = true;
                } else {
                    questState = QuestState.quest6yes;
                    worldController.level.player.quest_window_6 = true;
                    worldController.level.player.quest6 = true;
                }
                addRequest.add(questState);
                checkGameComplete();
                System.out.println(questState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                worldController.level.player.questScreen1 = false;
                worldController.level.player.questScreen2 = false;
                worldController.level.player.questScreen3 = false;
                worldController.level.player.questScreen4 = false;
                worldController.level.player.questScreen5 = false;
                worldController.level.player.questScreen6 = false;
            }
        });

        buttonLink2.setText("NO");
        buttonLink2.clearListeners();
        buttonLink2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
                } else {
                    worldController.level.player.quest_window_6 = true;
                    questState = QuestState.quest6no;
                }
                addRequest.add(questState);
                checkGameComplete();
                System.out.println(questState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
                worldController.level.player.questScreen1 = false;
                worldController.level.player.questScreen2 = false;
                worldController.level.player.questScreen3 = false;
                worldController.level.player.questScreen4 = false;
                worldController.level.player.questScreen5 = false;
                worldController.level.player.questScreen6 = false;

            }
        });

        requestCitizenWindow.pack();
    }

    private void checkGameComplete() {
        trueLink = 0;
        if (addRequest.size() == 6) {
            for (int i = 0; i < addRequest.size(); i++) {
                for (int j = 0; j < isComplete.size(); j++)
                    if (addRequest.get(i) == isComplete.get(j)) {
                        trueLink += 1;
                    }
            }
        }
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        textBullet.setText(String.format("Arrow : %d", worldController.level.player.bulletCount));
        textBeam.setText(String.format("SwordWave : %d", worldController.level.player.beamCount));
        textTrap.setText(String.format("Trap : %d", worldController.level.player.trapCount));
        textTime.setText(String.format("Time limit : %d", worldController.level.player.timeCount));
        energyLevel.setText(String.format("Energy : %d", (int) EnergyBar.instance.energy));
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

        boolean noCitizen = !player.questScreen1
                && !player.questScreen2
                && !player.questScreen3
                && !player.questScreen4
                && !player.questScreen5
                && !player.questScreen6
                && !player.status_switch;

        if (player.status_find && noCitizen) {
            player.status_find = false;
            player.status_windows_link = false;
        }

        if ((!player.isSwitch) && (player.status_find) && (player.status_switch)) {
            findItem(Switch.class).state = Item.ItemState.ON;
            findItem(Switch.class).resetAnimation();
            EnergyBar.instance.energy += 100;
            player.isSwitch = true;
            player.status_find = false;
        }

        for (int i = 0; i < worldController.level.enemies.size(); i++) {
            Enemy enemy = worldController.level.enemies.get(i);
            if (enemy.dead && !enemy.count) {
                EnergyBar.instance.energy += 2;
                enemy.count = true;
            }
        }

        if (worldController.level.enemies.size() == 0) {
            player.stageoneclear = true;
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            worldController.level.enemies.clear();
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            findItem(Switch.class).state = Item.ItemState.ON;
            findItem(Switch.class).resetAnimation();
            EnergyBar.instance.energy += 100;
            player.isSwitch = true;
            player.status_find = false;        }

        requestCitizenWindow.setPosition(
                Gdx.graphics.getWidth() / 2 - requestCitizenWindow.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - requestCitizenWindow.getHeight() / 2);

        if (player.stageoneclear && player.status_find && player.questScreen1 && !player.quest_window_1) {
            citizenQuest = systemWindow.citizen1;
            checkButton(citizenQuest);
            requestCitizenWindow.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.2f)));
        } else if (player.stageoneclear && player.status_find && player.questScreen2 && !player.quest_window_2) {
            citizenQuest = systemWindow.citizen2;
            checkButton(citizenQuest);
            requestCitizenWindow.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.2f)));
        } else if (player.stageoneclear && player.status_find && player.questScreen3 && !player.quest_window_3) {
            citizenQuest = systemWindow.citizen3;
            checkButton(citizenQuest);
            requestCitizenWindow.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.2f)));
        } else if (player.stageoneclear && player.status_find && player.questScreen4 && !player.quest_window_4) {
            citizenQuest = systemWindow.citizen4;
            checkButton(citizenQuest);
            requestCitizenWindow.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.2f)));
        } else if (player.stageoneclear && player.status_find && player.questScreen5 && !player.quest_window_5) {
            citizenQuest = systemWindow.citizen5;
            checkButton(citizenQuest);
            requestCitizenWindow.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.2f)));
        } else if (player.stageoneclear && player.status_find && player.questScreen6 && !player.quest_window_6) {
            citizenQuest = systemWindow.citizen6;
            checkButton(citizenQuest);
            requestCitizenWindow.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.2f)));
        } else {
            requestCitizenWindow.setVisible(false);
        }

        if (worldController.level.citizens.get(0).itemOn) {
            worldController.level.items.get(3).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(3).resetAnimation();
        }
        if (worldController.level.citizens.get(1).itemOn) {
            worldController.level.items.get(2).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(2).resetAnimation();
        }
        if (worldController.level.citizens.get(2).itemOn) {
            worldController.level.items.get(5).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(5).resetAnimation();
        }
        if (worldController.level.citizens.get(3).itemOn) {
            worldController.level.items.get(8).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(8).resetAnimation();
        }
        if (worldController.level.citizens.get(4).itemOn) {
            worldController.level.items.get(9).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(9).resetAnimation();
        }
        if (worldController.level.citizens.get(5).itemOn) {
            worldController.level.items.get(1).state = Item.ItemState.ONLOOP;
            worldController.level.items.get(1).resetAnimation();
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
        worldController = new WorldController(new Level2(new Level2Generator()));
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
