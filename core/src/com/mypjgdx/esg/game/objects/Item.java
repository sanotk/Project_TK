package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Item {

		 private static final String ItemType = null;
		// ตำแหน่ง ขนาด จุดกำเนิด ระดับการขยาย องศาการหมุน
	    private Vector2 position;
	    protected Vector2 dimension;
	    public Vector2 origin;
	    public Vector2 scale;
	    public float rotation;

	    // ความเร็ว แรงเสียดทาน ความเร่ง กรอบวัตถุ
	    public Vector2 velocity;
	    public Vector2 friction;
	    public Vector2 acceleration;
	    public Rectangle bounds;

	    public enum ItemType{
	    	solarcell,
	    	battery,
	    	charge,
	    	inverter
	    }

	    abstract ItemType tellMeTheType();

	    public void render (SpriteBatch batch) {

		}

}
