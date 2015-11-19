package com.mypjgdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntArray;

public class MyPjGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureAtlas tileAtlas;
	List<IntArray> mapData;

	private static TextureRegion emptyTile;

	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tileAtlas = new TextureAtlas(Gdx.files.internal("tile.atlas"));
		mapData = LevelLoader.loadMap(Gdx.files.internal("map.csv"));
		emptyTile = new TextureRegion(new Texture(new Pixmap(0, 0, Format.Alpha)));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int x = 0, y = 0;
		batch.begin();
		for (int row = mapData.size()-1; row >= 0; --row) {
		    for (int column = 0; column < mapData.get(row).size; ++column) {
		        batch.draw(findTileRegion(mapData.get(row).get(column)), x, y);
		        x += TILE_WIDTH;
		    }
		    x = 0;
		    y += TILE_HEIGHT;
		}
		batch.end();
	}

	public TextureRegion findTileRegion(int tileNum) {
	    switch(tileNum) {
	    case 0: return tileAtlas.findRegion("blue");
	    case 1: return tileAtlas.findRegion("green");
	    case 2: return tileAtlas.findRegion("red");
	    case 3: return tileAtlas.findRegion("yellow");
	    default:
	        break;
	    }
	    return emptyTile;
	}
}
