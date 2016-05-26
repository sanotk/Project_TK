package com.mypjgdx.esg.game.objects;

public class Solar_cell extends Item{

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    // อัตราการขยายภาพ enemy
    private static final float SCALE = 0.5f;


	public Player player;

	@Override
	ItemType tellMeTheType() {
		return ItemType.solarcell;
	}

	public Solar_cell(Player player){
		this.player = player;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

	}
}
