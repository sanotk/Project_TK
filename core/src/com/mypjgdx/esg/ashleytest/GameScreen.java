package com.mypjgdx.esg.ashleytest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.ashleytest.systems.*;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.screens.AbstractGameScreen;
import com.mypjgdx.esg.utils.Direction;

/**
 * Created by Bill on 8/8/2560.
 */
public class GameScreen extends AbstractGameScreen {

    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ

    private OrthographicCamera camera = new OrthographicCamera();
    private FitViewport viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthogonalTiledMapRenderer tiledRenderer;

    private Stage stage;
    private Skin skin;
    private Label text;

    private Engine engine;
    private Entity player;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        tiledRenderer = new OrthogonalTiledMapRenderer(null, batch);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        text = new Label("Welcome to Ashley!", skin);

        text.setPosition(SCENE_WIDTH / 2f - text.getWidth() / 2, SCENE_HEIGHT - text.getHeight());

        engine = new Engine();
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new AnimatorSystem());

        CameraHelperSystem cameraHelperSystem = new CameraHelperSystem();
        cameraHelperSystem.priority = 1;
        engine.addSystem(cameraHelperSystem);

        TiledMapRenderingSystem tiledMapRenderingSystem = new TiledMapRenderingSystem(tiledRenderer, camera);
        tiledMapRenderingSystem.priority = 2;
        engine.addSystem(tiledMapRenderingSystem);

        RenderingSystem renderingSystem = new RenderingSystem(batch, camera);
        renderingSystem.priority = 3;
        engine.addSystem(renderingSystem);

        BoundRenderingSystem boundRenderingSystem = new BoundRenderingSystem(shapeRenderer, camera);
        boundRenderingSystem.priority = 4;
        engine.addSystem(boundRenderingSystem);

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) Assets.instance.testMap.getLayers().get(0);
        player = EntityBuilder.buildPlayer(mapLayer);
        Entity cameraHelper = EntityBuilder.buildCameraHelper(camera, mapLayer, player);

        engine.addEntity(player);
        engine.addEntity(cameraHelper);
        engine.addEntity(EntityBuilder.buildMap(Assets.instance.testMap));

        stage.addActor(text);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            engine.getSystem(PlayerSystem.class).move(player, Direction.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            engine.getSystem(PlayerSystem.class).move(player, Direction.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            engine.getSystem(PlayerSystem.class).move(player, Direction.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            engine.getSystem(PlayerSystem.class).move(player, Direction.DOWN);
        }

        engine.update(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        batch.dispose();
        tiledRenderer.dispose();
    }
}
