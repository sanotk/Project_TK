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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.MusicManager;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level4;
import com.mypjgdx.esg.game.levels.Level4Generator;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.items.Item;

public class GameScreen4 extends AbstractGameScreen {

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

    private Button buttonOption;
    private BitmapFont font;
    private Window optionsWindow;

    private boolean animation_status = false;

    public GameScreen4(Game game , final Window optionsWindow) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");
        font = new BitmapFont();

        this.optionsWindow = optionsWindow;

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        TextureRegionDrawable toolUp = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("icon_tools"));
        buttonStyle.up = toolUp;
        buttonStyle.down = toolUp.tint(Color.LIGHT_GRAY);
        buttonOption = new Button(buttonStyle);
        buttonOption.setPosition(SCENE_WIDTH - 50, SCENE_HEIGHT - 50);

        optionsWindow.setVisible(false);

        stage.addActor(buttonOption);
        stage.addActor(optionsWindow);

        buttonOption.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsWindow.setPosition(
                        Gdx.graphics.getWidth() / 2 -  optionsWindow.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 -  optionsWindow.getHeight() / 2);
                optionsWindow.addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
            }
        });

        createbutton();
        batch = new SpriteBatch();
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
        worldController = new WorldController(new Level4(new Level4Generator()));
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
