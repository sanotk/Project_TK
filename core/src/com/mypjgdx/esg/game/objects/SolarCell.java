package com.mypjgdx.esg.game.objects;

import com.mypjgdx.esg.game.Assets;

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
        super(Assets.instance.solarcellAltas);
		this.player = player;
		init();
	}

	private void init() {
        addNormalAnimation(AnimationName.OFF, FRAME_DURATION, 3, 3);
        addLoopAnimation(AnimationName.ON, FRAME_DURATION, 0, 3);
		// TODO Auto-generated method stub

        // กำหนดค่าทางฟิสิกส์
        acceleration.set(0.0f, 0.0f);

        // กำหนดขนาดสเกลของ ดาบ
        scale.set(SCALE, SCALE);

        state = ItemState.off;

	}

	private void addPlayer(){

	}

	@Override
	public void update(float deltaTime) {

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
