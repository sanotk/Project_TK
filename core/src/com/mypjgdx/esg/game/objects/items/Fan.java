package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Fan extends Item{

    public static final float SCALE = 1f;
    public static final float P_X = 950f;
    public static final float P_Y = 800f;

	public Fan(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.fanAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player );
	}

    public Fan(TiledMapTileLayer mapLayer, Player player, float P_X, float P_Y){
        super(Assets.instance.fanAltas, SCALE, SCALE , P_X , P_Y);
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
