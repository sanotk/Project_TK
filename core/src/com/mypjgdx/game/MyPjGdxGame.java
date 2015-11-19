package com.mypjgdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntArray;

public class MyPjGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureAtlas tileAtlas;
	List<IntArray> mapData;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tileAtlas = new TextureAtlas(Gdx.files.internal("tile.atlas"));
		mapData = LevelLoader.loadMap(Gdx.files.internal("map.csv"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int x = 0, y = 0;
		batch.begin();
		for (int row = 0; row < mapData.size(); ++row) {
		    for (int column = 0; column < mapData.get(row).size; ++column) {
		        batch.draw(findTileRegion(mapData.get(row).get(column)), x, y);
		        x += 32;
		    }
		    x = 0;
		    y += 32;
		}
		batch.end();
	}

	public TextureRegion findTileRegion(int tileNum) {
	    switch(tileNum) {
	    case 0:
	        return tileAtlas.findRegion("blue");
	    case 1:
            return tileAtlas.findRegion("green");
	    case 2:
            return tileAtlas.findRegion("red");
	    case 3:
            return tileAtlas.findRegion("yellow");
	    default:
	        break;
	    }
	    return tileAtlas.findRegion("blue");
	}
}
