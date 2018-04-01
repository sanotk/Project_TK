package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.MusicManager;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.SoundManager;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level1;
import com.mypjgdx.esg.game.levels.Level1Generator;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.*;

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
        StoD
    }

    public enum chargecontrollercState {
        CtoS,
        CtoB,
        CtoI,
        CtoD
    }

    public enum batteryState {
        BtoS,
        BtoC,
        BtoI,
        BtoD
    }

    public enum inverterState {
        ItoS,
        ItoC,
        ItoB,
        ItoD
    }

    public solarcellState solarState = null;
    public chargecontrollercState ccState = null;
    public batteryState batState = null;
    public inverterState inverState = null;

    private TextButton buttonStoC;
    private TextButton buttonStoB;
    private TextButton buttonStoI;
    private TextButton buttonStoD;

    private TextButton buttonCtoS;
    private TextButton buttonCtoB;
    private TextButton buttonCtoI;
    private TextButton buttonCtoD;

    private TextButton buttonItoS;
    private TextButton buttonItoC;
    private TextButton buttonItoB;
    private TextButton buttonItoD;

    private TextButton buttonBtoS;
    private TextButton buttonBtoC;
    private TextButton buttonBtoI;
    private TextButton buttonBtoD;

    private Button buttonOption;
    private BitmapFont font;
    private Window window;

    private boolean animation_status = false;

    public GameScreen(Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = new BitmapFont();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable toolUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_tools"));
        buttonStyle.up = toolUp;
        buttonStyle.down = toolUp.tint(Color.LIGHT_GRAY);
        buttonOption = new Button(buttonStyle);
        buttonOption.setPosition(SCENE_WIDTH - 50, SCENE_HEIGHT - 50);

        window = createOptionsWindow();
        window.setVisible(false);

        stage.addActor(buttonOption);
        stage.addActor(window);

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.setPosition(
                        Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - window.getHeight() / 2);
                window.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        createbutton();
        batch = new SpriteBatch();
    }

    private Window createOptionsWindow() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
//        style.background = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("window_01"));
        style.titleFont = font;
        style.titleFontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("slider_back_hor"));
        TextureRegionDrawable knobRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("knob_03"));
        sliderStyle.knob = knobRegion;
        sliderStyle.knobDown = knobRegion.tint(Color.LIGHT_GRAY);

        final Slider musicSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        musicSlider.setValue(0.5f);

        final Slider soundSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        soundSlider.setValue(0.5f);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        buttonStyle.up = buttonRegion;
        buttonStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(buttonStyle);

        final Window window = new Window("Options", style);
        window.setModal(true);
        window.padTop(40);
        window.padLeft(40);
        window.padRight(40);
        window.padBottom(20);
        window.getTitleLabel().setAlignment(Align.center);
        window.row().padBottom(10).padTop(10);
        window.add(new Label("Volume", labelStyle)).colspan(3);
        window.row();
        window.add(new Image(Assets.instance.uiBlue.findRegion("icon_music"))).padRight(10);
        window.add(new Label("Music", labelStyle)).padRight(10);
        window.add(musicSlider).width(250);
        window.row().padTop(10);
        window.add(new Image(Assets.instance.uiBlue.findRegion("icon_sound_on"))).padRight(10);
        window.add(new Label("Sound Fx", labelStyle)).padRight(10);
        window.add(soundSlider).width(250);
        window.row().padTop(20);
        window.add(closeButton).colspan(3);
        window.pack();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
            }
        });

        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MusicManager.instance.setVolume(musicSlider.getValue());
            }
        });

        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundManager.instance.setVolume(soundSlider.getValue());
            }
        });

        return window;
    }


    public  void createbutton() {

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

        int btn_w = 200;
        int btn_h = 50;

        buttonStoC = new TextButton("link to charge controler", skin);
        buttonStoC.setWidth(btn_w);
        buttonStoC.setHeight(btn_h);
        buttonStoC.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 450);

        buttonStoB = new TextButton("link to battery", skin);
        buttonStoB.setWidth(btn_w);
        buttonStoB.setHeight(btn_h);
        buttonStoB.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 380);

        buttonStoI = new TextButton("link to inverter", skin);
        buttonStoI.setWidth(btn_w);
        buttonStoI.setHeight(btn_h);
        buttonStoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 310);

        buttonStoD = new TextButton("link to door", skin);
        buttonStoD.setWidth(btn_w);
        buttonStoD.setHeight(btn_h);
        buttonStoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        stage.addActor(buttonStoC);
        stage.addActor(buttonStoB);
        stage.addActor(buttonStoI);
        stage.addActor(buttonStoD);

        buttonStoC.setVisible(false);
        buttonStoB.setVisible(false);
        buttonStoI.setVisible(false);
        buttonStoD.setVisible(false);

        buttonStoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarcellState.StoC;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;

            }
        });

        buttonStoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarcellState.StoB;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonStoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarcellState.StoI;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonStoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                solarState = solarcellState.StoD;
                System.out.print(solarState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoS = new TextButton("link to solarcell", skin);
        buttonItoS.setWidth(btn_w);
        buttonItoS.setHeight(btn_h);
        buttonItoS.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 350);

        buttonItoC = new TextButton("link to charge controler", skin);
        buttonItoC.setWidth(btn_w);
        buttonItoC.setHeight(btn_h);
        buttonItoC.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 280);

        buttonItoB = new TextButton("link to battery", skin);
        buttonItoB.setWidth(btn_w);
        buttonItoB.setHeight(btn_h);
        buttonItoB.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 210);

        buttonItoD = new TextButton("link to door", skin);
        buttonItoD.setWidth(btn_w);
        buttonItoD.setHeight(btn_h);
        buttonItoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        stage.addActor(buttonItoS);
        stage.addActor(buttonItoC);
        stage.addActor(buttonItoB);
        stage.addActor(buttonItoD);

        buttonItoS.setVisible(false);
        buttonItoC.setVisible(false);
        buttonItoB.setVisible(false);
        buttonItoD.setVisible(false);

        buttonItoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverState = inverterState.ItoS;
                System.out.print(inverState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverState = inverterState.ItoC;
                System.out.print(inverState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverState = inverterState.ItoB;
                System.out.print(inverState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonItoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inverState = inverterState.ItoD;
                System.out.print(inverState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoS = new TextButton("link to solarcell", skin);
        buttonCtoS.setWidth(btn_w);
        buttonCtoS.setHeight(btn_h);
        buttonCtoS.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 350);

        buttonCtoB = new TextButton("link to battery", skin);
        buttonCtoB.setWidth(btn_w);
        buttonCtoB.setHeight(btn_h);
        buttonCtoB.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 280);

        buttonCtoI = new TextButton("link to inverter", skin);
        buttonCtoI.setWidth(btn_w);
        buttonCtoI.setHeight(btn_h);
        buttonCtoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 210);

        buttonCtoD = new TextButton("link to door", skin);
        buttonCtoD.setWidth(btn_w);
        buttonCtoD.setHeight(btn_h);
        buttonCtoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        stage.addActor(buttonCtoS);
        stage.addActor(buttonCtoB);
        stage.addActor(buttonCtoI);
        stage.addActor(buttonCtoD);

        buttonCtoS.setVisible(false);
        buttonCtoB.setVisible(false);
        buttonCtoI.setVisible(false);
        buttonCtoD.setVisible(false);

        buttonCtoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ccState = chargecontrollercState.CtoS;
                System.out.print(ccState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ccState = chargecontrollercState.CtoB;
                System.out.print(ccState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ccState = chargecontrollercState.CtoI;
                System.out.print(ccState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonCtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ccState = chargecontrollercState.CtoD;
                System.out.print(ccState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoS = new TextButton("link to solarcell", skin);
        buttonBtoS.setWidth(btn_w);
        buttonBtoS.setHeight(btn_h);
        buttonBtoS.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 350);

        buttonBtoC = new TextButton("link to charge controler", skin);
        buttonBtoC.setWidth(btn_w);
        buttonBtoC.setHeight(btn_h);
        buttonBtoC.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 280);

        buttonBtoI = new TextButton("link to inverter", skin);
        buttonBtoI.setWidth(btn_w);
        buttonBtoI.setHeight(btn_h);
        buttonBtoI.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 210);

        buttonBtoD = new TextButton("link to door", skin);
        buttonBtoD.setWidth(btn_w);
        buttonBtoD.setHeight(btn_h);
        buttonBtoD.setPosition(SCENE_WIDTH / 2 - btn_w / 2, 140);

        stage.addActor(buttonBtoS);
        stage.addActor(buttonBtoC);
        stage.addActor(buttonBtoI);
        stage.addActor(buttonBtoD);

        buttonBtoS.setVisible(false);
        buttonBtoC.setVisible(false);
        buttonBtoI.setVisible(false);
        buttonBtoD.setVisible(false);

        buttonBtoS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batState = batteryState.BtoS;
                System.out.print(batState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batState = batteryState.BtoC;
                System.out.print(batState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batState = batteryState.BtoI;
                System.out.print(batState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });

        buttonBtoD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batState = batteryState.BtoD;
                System.out.print(batState);
                worldController.level.player.status_find = false;
                worldController.level.player.status_windows_link = false;
            }
        });
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
            buttonStoC.setVisible(true);
            buttonStoB.setVisible(true);
            buttonStoI.setVisible(true);
            buttonStoD.setVisible(true);
        }else{
            buttonStoC.setVisible(false);
            buttonStoB.setVisible(false);
            buttonStoI.setVisible(false);
            buttonStoD.setVisible(false);
        }

        if((worldController.level.player.status_battery==true)&&(worldController.level.player.status_find == true)){
            buttonBtoC.setVisible(true);
            buttonBtoS.setVisible(true);
            buttonBtoI.setVisible(true);
            buttonBtoD.setVisible(true);
        }else{
            buttonBtoC.setVisible(false);
            buttonBtoS.setVisible(false);
            buttonBtoI.setVisible(false);
            buttonBtoD.setVisible(false);
        }

        if((worldController.level.player.status_inverter==true)&&(worldController.level.player.status_find == true)){
            buttonItoC.setVisible(true);
            buttonItoB.setVisible(true);
            buttonItoS.setVisible(true);
            buttonItoD.setVisible(true);
        }else{
            buttonItoC.setVisible(false);
            buttonItoB.setVisible(false);
            buttonItoS.setVisible(false);
            buttonItoD.setVisible(false);
        }

        if((worldController.level.player.status_ccontroller==true)&&(worldController.level.player.status_find == true)){
            buttonCtoS.setVisible(true);
            buttonCtoB.setVisible(true);
            buttonCtoI.setVisible(true);
            buttonCtoD.setVisible(true);
        }else{
            buttonCtoS.setVisible(false);
            buttonCtoB.setVisible(false);
            buttonCtoI.setVisible(false);
            buttonCtoD.setVisible(false);
        }

        if((solarState == solarcellState.StoC)&&(ccState == chargecontrollercState.CtoB)&&(batState == batteryState.BtoC)
                &&(inverState==inverterState.ItoC)){
            status_finish =true;
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

        boolean stageOneIsFinish = solarState==solarcellState.StoC
                && ccState==chargecontrollercState.CtoI
                && batState==batteryState.BtoC
                && inverState==inverterState.ItoD;
        if ((stageOneIsFinish)&&(!animation_status)){
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
            worldController.level.energyTube.energy += 100;
        }

        if((findItem(Door.class).state == Item.ItemState.ON)&&(player.status_door==true)){
            game.setScreen(new GameScreen(game));
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
