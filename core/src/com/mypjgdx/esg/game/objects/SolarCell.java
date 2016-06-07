package com.mypjgdx.esg.game.objects;

import com.mypjgdx.esg.game.Assets;

public class SolarCell extends Item{

	public Player player;

	public SolarCell(Player player){
        super(Assets.instance.solarCellAltas);
		this.player = player;
		init();
	}
}
