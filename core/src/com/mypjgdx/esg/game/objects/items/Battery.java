package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Battery extends Item{

    private static final float SCALE = 1f;
    public static final float P_X = 1700f;
    public static final float P_Y = 950f;

	public Battery(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.batAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player);

        playerCheck = new TiledCollisionCheck(player.bounds, mapLayer, "battery");
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
