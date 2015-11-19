package com.mypjgdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntArray;

public class MyPjGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	List<IntArray> mapData;

	@Override
	public void create () {
		batch = new SpriteBatch();
		mapData = LevelLoader.loadMap(Gdx.files.internal("map.csv"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		renderMap();
		batch.end();
	}

	public void renderMap() {
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
