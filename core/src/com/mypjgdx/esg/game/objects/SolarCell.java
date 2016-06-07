package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class SolarCell extends Item{

	public SolarCell(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.solarCellAltas);
		this.player = player;
		init(mapLayer);
	}
}
