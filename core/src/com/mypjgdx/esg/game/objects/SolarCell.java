package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class SolarCell extends Item{

    private static final float SCALE = 0.3f;

	public SolarCell(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.solarCellAltas, SCALE, SCALE, mapLayer);
		this.player = player;

        init(mapLayer);
	}
}
