package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Waterpump extends Item{

    public static final float SCALE = 0.5f;
    public static final float P_X = 200f;
    public static final float P_Y = 1000f;

	public Waterpump(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.waterpumpAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player );
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
