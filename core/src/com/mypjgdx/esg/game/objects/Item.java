package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class Item extends AnimatedObject{

    	// อัตราการขยายภาพ enemy
		private static final float SCALE = 0.5f;

		public Item(TextureAtlas atlas) {
			super(atlas);
		}

	    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
	    protected static final float FRAME_DURATION = 1.0f / 8.0f;
		// ตำแหน่ง ขนาด จุดกำเนิด ระดับการขยาย องศาการหมุน

	    public enum ItemState{
	    	OFF,
	    	ON
	    }

	    public ItemState state;

	    @Override
		public void render (SpriteBatch batch) {

		}

		public void init() {
	        addNormalAnimation(AnimationName.OFF, FRAME_DURATION, 3, 3);
	        addLoopAnimation(AnimationName.ON, FRAME_DURATION, 0, 3);
			// TODO Auto-generated method stub

	        // กำหนดค่าทางฟิสิกส์
	        acceleration.set(0.0f, 0.0f);

	        // กำหนดขนาดสเกลของ ดาบ
	        scale.set(SCALE, SCALE);

	        state = ItemState.OFF;

		}

		private void addPlayer(){

		}

		@Override
		public void update(float deltaTime) {
			super.update(deltaTime);
		}

		@Override
		protected void setAnimation() {
			// TODO Auto-generated method stub
	        switch (itemSwitch) {
	        case ON: setCurrentAnimation(AnimationName.ON); break;
	        case OFF: setCurrentAnimation(AnimationName.OFF); break;
	        default:
	            break;
	        }
		}

}
