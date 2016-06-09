package com.mypjgdx.esg.game.objects;

import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class SolarCell extends Item{

    private static final float SCALE = 0.3f;

	public SolarCell(TiledMapTileLayer mapLayer, Player player, List<Item> items){
        super(Assets.instance.solarCellAltas, SCALE, SCALE);
        init(mapLayer,  player, items);
	}

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub

    }
}
