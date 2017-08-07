package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Charge extends Item{

    private static final float SCALE = 1f;
    public static final float P_X = 1700f;
    public static final float P_Y = 1100f;

	public Charge(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.ccAltas, SCALE, SCALE , P_X , P_Y);
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
