package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Item extends AnimatedObject{

		public Item(TextureAtlas atlas) {
			super(atlas);
		}

	    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
	    protected static final float FRAME_DURATION = 1.0f / 8.0f;
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

	    public Texture solarcell;
	    public Texture battery;
	    public Texture charge;
	    public Texture inverter;

	    public enum ItemType{
	    	solarcell,
	    	battery,
	    	charge,
	    	inverter
	    }

	    public enum ItemState{
	    	off,
	    	on
	    }

	    public ItemType type;
	    public ItemState state;

	    abstract ItemType tellMeTheType();

	    @Override
		public void render (SpriteBatch batch) {

		}

}
