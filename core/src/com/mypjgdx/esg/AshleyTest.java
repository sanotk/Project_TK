package com.mypjgdx.esg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.ashleytest.components.PhysicsComponent;
import com.mypjgdx.esg.ashleytest.components.SpriteComponent;
import com.mypjgdx.esg.ashleytest.components.TransformComponent;
import com.mypjgdx.esg.ashleytest.systems.MovementSystem;
import com.mypjgdx.esg.ashleytest.systems.RenderingSystem;
import com.mypjgdx.esg.game.Assets;

/**
 * Created by Bill on 7/8/2560.
 */
public class AshleyTest extends ApplicationAdapter {


    public static final int SCENE_WIDTH = 1024; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 576; //เซตค่าความสูงของจอ


    private OrthographicCamera camera = new OrthographicCamera();
    private FitViewport viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Stage stage;
    private Skin skin;
    private Label text;

    private Engine ashleyEngine;
    private Entity testEntity;


    @Override
    public void create() {
        Assets.instance.init();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        text = new Label("Welcome to Ashley!", skin);

        text.setPosition(SCENE_WIDTH / 2 - text.getWidth() / 2, SCENE_HEIGHT / 2 - text.getHeight() / 2);

        ashleyEngine = new Engine();
        ashleyEngine.addSystem(new MovementSystem());
        ashleyEngine.addSystem(new RenderingSystem(batch, camera));

        testEntity = new Entity();
        testEntity.add(new TransformComponent());
        testEntity.add(new PhysicsComponent());
        testEntity.add(new SpriteComponent());

        SpriteComponent entitySprite = testEntity.getComponent(SpriteComponent.class);
        entitySprite.region = Assets.instance.playerAltas.findRegion("5");

        testEntity.getComponent(TransformComponent.class).dimension =
                new Vector2(entitySprite.region.getRegionWidth(), entitySprite.region.getRegionHeight());

        testEntity.getComponent(PhysicsComponent.class).friction = new Vector2(500f, 500f);



        ashleyEngine.addEntity(testEntity);

        stage.addActor(text);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float deltaTime =  Gdx.graphics.getDeltaTime();
        PhysicsComponent testPhysicsComponent = testEntity.getComponent(PhysicsComponent.class);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            testPhysicsComponent.velocity.x = 120f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            testPhysicsComponent.velocity.x = -120f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            testPhysicsComponent.velocity.y = 120f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            testPhysicsComponent.velocity.y = -120f;
        }
        ashleyEngine.update(deltaTime);

        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.instance.dispose();
    }
}
