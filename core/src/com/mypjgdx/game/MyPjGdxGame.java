package com.mypjgdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyPjGdxGame extends ApplicationAdapter  {
    public static final int SCENE_WIDTH = 1280;
    public static final int SCENE_HEIGHT = 720;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch batch;
    private WorldController worldController;

    private List<IntArray> backMapData;
    private List<IntArray> frontMapData;

    @Override
    public void create () {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        batch = new SpriteBatch();

        worldController = new WorldController(viewport);

        backMapData = LevelLoader.loadMap(Gdx.files.internal("mix_map_1.csv"));
        frontMapData = LevelLoader.loadMap(Gdx.files.internal("mix_map_2.csv"));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldController.handleInput(Gdx.graphics.getDeltaTime());

        worldController.getCameraHelper().applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        renderMap(backMapData);
        renderMap(frontMapData);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void renderMap(List<IntArray> mapData) {
        int x = 0;
        int y = 0;

        for (int row = mapData.size()-1; row >= 0; --row) {
            for (int column = 0; column < mapData.get(row).size; ++column) {
                int tileId= mapData.get(row).get(column);
                Tiled tile = Tiled.get(tileId);
                batch.draw(tile.getRegion(), x, y);
                x += Tiled.TILE_WIDTH;
            }
            x = 0;
            y += Tiled.TILE_HEIGHT;
        }
    }



}
