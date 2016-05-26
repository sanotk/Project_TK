package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mypjgdx.esg.game.Assets;

public class Solar_cell extends Item{

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ enemy
    private static final float SCALE = 0.5f;

    private static final float INITIAL_X_POSITION = 100f;         // ตำแหน่งแกน X
    private static final float INITIAL_Y_POSITION = 100f;

	public Player player;

	@Override
	ItemType tellMeTheType() {
		return ItemType.solarcell;
	}

	public Solar_cell(){

	}

	public Solar_cell(Player player){
		this.player = player;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

        // กำหนดค่าทางฟิสิกส์
        acceleration.set(0.0f, 0.0f);

        // กำหนดขนาดสเกลของ ดาบ
        scale.set(SCALE, SCALE);

	}

	private void addPlayer(){

	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		render(batch, Assets.instance.solarcell);
	}

	public void update(float deltaTime) {
		// TODO Auto-generated method stub
        updateMotionX(deltaTime);
        updateMotionY(deltaTime);
	}

}
