package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Computer extends Item{

    public static final float SCALE = 1f;
    public static final float P_X = 850f;
    public static final float P_Y = 1050f;

	public Computer(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.comAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player );

        playerCheck = new TiledCollisionCheck(player.bounds, mapLayer, "com");
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


    @Override
    public float getGoalX() {
        return bounds.x + bounds.width / 2;
    }

    @Override
    public float getGoalY() {
        return bounds.y + bounds.height / 2 - 100;
    }
}
