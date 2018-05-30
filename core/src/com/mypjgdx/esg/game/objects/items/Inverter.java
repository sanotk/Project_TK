package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Inverter extends Item{

    private static final float SCALE = 1f;
    public static final float P_X = 1700f;
    public static final float P_Y = 1250f;

	public Inverter(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.inverAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player);

        playerCheck = new TiledCollisionCheck(player.bounds, mapLayer, "inverter");
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
