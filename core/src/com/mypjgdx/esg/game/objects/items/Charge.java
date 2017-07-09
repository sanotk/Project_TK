package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Charge extends Item{

    private static final float SCALE = 0.3f;

	public Charge(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.solarCellAltas, SCALE, SCALE);
        init(mapLayer,  player);
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
