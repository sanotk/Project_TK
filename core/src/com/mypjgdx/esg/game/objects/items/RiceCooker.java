package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class RiceCooker extends Item{

    public static final float SCALE = 1f;
    public static final float P_X = 350f;
    public static final float P_Y = 1050f;

	public RiceCooker(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.ricecookerAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player );

        playerCheck = new TiledCollisionCheck(player.bounds, mapLayer, "cooker");
	}

    public RiceCooker(TiledMapTileLayer mapLayer, Player player, float P_X, float P_Y){
        super(Assets.instance.ricecookerAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player );

        playerCheck = new TiledCollisionCheck(player.bounds, mapLayer, "cooker");
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

    @Override
    public void setTimeCount() {
        timeCount = 9999;
    }

    @Override
    public void setName() {
        name ="หม้อหุงข้าว";
    }
}
