package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Airconditioner extends Item{

    public static final float SCALE = 1f;
    public static final float P_X = 300f;
    public static final float P_Y = 700f;

	public Airconditioner(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.airAltas, SCALE, SCALE , P_X , P_Y);
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
