package com.mypjgdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyPjGdxGame extends ApplicationAdapter {

    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;

    private static final float CAMERA_SPEED = 2.0f;
    private static final float CAMERA_MOVE_EDGE = 0.2f; // 20 %

    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch batch;
	private Vector3 touch;

	private List<IntArray> backMapData;
	private List<IntArray> frontMapData;

	@Override
	public void create () {
	    camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
		batch = new SpriteBatch();
		backMapData = LevelLoader.loadMap(Gdx.files.internal("")); //TODO map csv file
		frontMapData = LevelLoader.loadMap(Gdx.files.internal(""));
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float deltaTime = Gdx.graphics.getDeltaTime();

		float halfWidth = SCENE_WIDTH * 0.5f;
        float halfHeight = SCENE_HEIGHT * 0.5f;

        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
            camera.unproject(touch);
            touch.add(halfWidth - camera.position.x, halfHeight - camera.position.y, 0.0f);

            if (touch.x > SCENE_WIDTH * (1.0f - CAMERA_MOVE_EDGE)) {
                camera.position.x += CAMERA_SPEED * deltaTime;
            }
            else if (touch.x < SCENE_WIDTH * CAMERA_MOVE_EDGE) {
                camera.position.x -= CAMERA_SPEED * deltaTime;
            }

            if (touch.y > SCENE_HEIGHT * (1.0f - CAMERA_MOVE_EDGE)) {
                camera.position.y += CAMERA_SPEED * deltaTime;
            }
            else if (touch.y < SCENE_HEIGHT * CAMERA_MOVE_EDGE) {
                camera.position.y -= CAMERA_SPEED * deltaTime;
            }
        }
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		renderMap(backMapData);
		renderMap(frontMapData);

		batch.end();
	}

	public void renderMap(List<IntArray> mapData) {
	    int x = 0;
	    int y = 0;

	    for (int row = mapData.size()-1; row >= 0; --row) {
            for (int column = 0; column < mapData.get(row).size; ++column) {
                int tileNum = mapData.get(row).get(column);
                Tiled tile = Tiled.get(tileNum);
                batch.draw(tile.getTexture(), x, y);
                x += Tiled.TILE_WIDTH;
            }
            x = 0;
            y += Tiled.TILE_HEIGHT;
        }
	}

}
