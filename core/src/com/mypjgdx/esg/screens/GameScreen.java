package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.game.levels.Level1;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;

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

    public boolean status_finish = false;

    private Table buttonTable;
    private boolean showingButton;


    public GameScreen(Game game) {
        super(game);

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");

        setupGui();

        stage.setDebugAll(true);
    }

    private void setupGui() {
        textBullet = new Label("Punch Max : ", skin);
        textBullet.setColor(1, 1, 1, 1);
        textBullet.setFontScale(1f, 1f);
        textBullet.setPosition(50, 550);

        textBeam = new Label("Z-Punch Max : ", skin);
        textBeam.setColor(1, 1, 1, 1);
        textBeam.setFontScale(1.f, 1.f);
        textBeam.setPosition(200, 550);

        textTrap = new Label("Trap Max : ", skin);
        textTrap.setColor(1, 1, 1, 1);
        textTrap.setFontScale(1f, 1f);
        textTrap.setPosition(350, 550);

        textTime = new Label("Time : ", skin);
        textTime.setColor(1, 1, 1, 1);
        textTime.setFontScale(1f, 1f);
        textTime.setPosition(450, 500);

        energyLevel = new Label("Energy : ", skin);
        energyLevel.setColor(1, 1, 1, 1);
        energyLevel.setFontScale(1, 1f);
        energyLevel.setPosition(500, 550);

        energyLevel2 = new Label("Product Energy : ", skin);
        energyLevel2.setColor(1, 1, 1, 1);
        energyLevel2.setFontScale(1, 1f);
        energyLevel2.setPosition(650, 550);

        energyLevel3 = new Label("Battery : ", skin);
        energyLevel3.setColor(1, 1, 1, 1);
        energyLevel3.setFontScale(1, 1f);
        energyLevel3.setPosition(800, 550);

        buttonTable = new Table();

        stage.addActor(textBullet);
        stage.addActor(textBeam);
        stage.addActor(textTrap);
        stage.addActor(textTime);
        stage.addActor(energyLevel);
        stage.addActor(energyLevel2);
        stage.addActor(energyLevel3);
        stage.addActor(buttonTable);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        textBullet.setText(String.format("Punch Max : %d", worldController.level.player.bulletCount));
        textBeam.setText(String.format("Z-Punch Max : %d", worldController.level.player.beamCount));
        textTrap.setText(String.format("Trap Max : %d", worldController.level.player.trapCount));
        textTime.setText(String.format("Time limit : %d", worldController.level.player.timeCount));
        energyLevel.setText(String.format("Energy : %d", (int) worldController.level.energyTube.energy));
        energyLevel2.setText(String.format("Product Energy : %d", (int) worldController.level.energyTube.energy));
        energyLevel3.setText(String.format("Battery : %d", (int) worldController.level.energyTube.energy));
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

        handleButtonVisibility();

        Player player = worldController.level.player;
        if (player.status_find && player.getFrontItem() == null) {
            player.status_find = false;
            player.status_windows_link = false;
        }

        worldController.update(Gdx.graphics.getDeltaTime()); //อัพเดท Game World
        worldRenderer.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void handleButtonVisibility() {

        if (worldController.level.player.status_find
                && worldController.level.player.getFrontItem() != null
                && worldController.level.player.getFrontItem().isConnectable()) {
            if (!showingButton) {
                showingButton = true;
                generateButton(worldController.level.player.getFrontItem());
            }
        } else {
            showingButton = false;
            buttonTable.clear();
        }
    }

    private void generateButton(final Item item) {
        Array<Item> otherItems = new Array<Item>();

        for (Item levelItem : worldController.level.items)
            if (levelItem != item)
                otherItems.add(levelItem);

        Cell<TextButton> lastCell = null;
        for (int i = 0; i < otherItems.size; i++) {
            final Item otherItem = otherItems.get(i);
            if (otherItem.isConnectable()) {
                String itemName = otherItem.getName();

                TextButton button = new TextButton("Connect to " + itemName, skin);
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.app.log("Connected", "From " + item.getName() + " to " + otherItem.getName());
                        worldController.level.player.status_find = false;
                    }
                });

                buttonTable.row().width(260f).height(40f);
                lastCell = buttonTable.add(button);
                lastCell.padBottom(20f).fill();
            }
        }
        if (lastCell != null)
            lastCell.padBottom(0f);

        buttonTable.pack();
        buttonTable.setX((SCENE_WIDTH - buttonTable.getWidth()) * 0.5f);
        buttonTable.setY((SCENE_HEIGHT - buttonTable.getHeight()) * 0.5f);
    }


    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        worldController = new WorldController(new Level(new Level1()));
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
    }

    @Override
    public void pause() {
    }
}
