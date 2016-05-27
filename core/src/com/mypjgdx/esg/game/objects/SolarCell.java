package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SolarCell extends Item{
    // อัตราการขยายภาพ enemy
    private static final float SCALE = 0.5f;

    private static final float INITIAL_X_POSITION = 100f;         // ตำแหน่งแกน X
    private static final float INITIAL_Y_POSITION = 100f;

	public Player player;

	@Override
	ItemType tellMeTheType() {
		return ItemType.solarcell;
	}

	public SolarCell(){

	}

	public SolarCell(Player player){
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


	public void update(float deltaTime) {

	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

}
