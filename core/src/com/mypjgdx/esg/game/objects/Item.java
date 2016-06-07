package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class Item extends AnimatedObject{

		public Item(TextureAtlas atlas) {
			super(atlas);
		}

	    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
	    protected static final float FRAME_DURATION = 1.0f / 8.0f;
		// ตำแหน่ง ขนาด จุดกำเนิด ระดับการขยาย องศาการหมุน

	    public enum ItemType{
	    	SOLARCELL,
	    	BATTERY,
	    	CHARGE,
	    	INVERTER
	    }

	    public enum ItemState{
	    	OFF,
	    	ON
	    }

	    public ItemType type;
	    public ItemState state;

	    abstract ItemType tellMeTheType();

	    @Override
		public void render (SpriteBatch batch) {

		}

}
