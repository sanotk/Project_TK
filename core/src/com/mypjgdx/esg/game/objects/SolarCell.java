package com.mypjgdx.esg.game.objects;

public class SolarCell extends Item{
    // อัตราการขยายภาพ enemy
    private static final float SCALE = 0.5f;

	public Player player;

	@Override
	ItemType tellMeTheType() {
		type = ItemType.solarcell;
		return type;
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
}
