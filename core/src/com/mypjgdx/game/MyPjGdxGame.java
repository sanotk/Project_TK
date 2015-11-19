package com.mypjgdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntArray;

public class MyPjGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		List<IntArray> maplayer1 = LevelLoader.loadMap(Gdx.files.internal("mix_map1.csv"));
		List<IntArray> maplayer2 = LevelLoader.loadMap(Gdx.files.internal("mix_map2.csv"));
		System.out.println(maplayer1);
		System.out.println(maplayer2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
