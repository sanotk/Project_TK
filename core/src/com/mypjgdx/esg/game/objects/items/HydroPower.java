package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class HydroPower extends Item{

    public static final float SCALE = 1f;
    public static final float P_X = 120f;
    public static final float P_Y = 700f;

	public HydroPower(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.hydroPowerAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player );

        playerCheck = new TiledCollisionCheck(player.bounds, mapLayer, "hydroPower");
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
