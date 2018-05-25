package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;

public class Waterpump extends Item{

    public static final float SCALE = 0.5f;
    public static final float P_X = 200f;
    public static final float P_Y = 1000f;

	public Waterpump(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.waterpumpAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player );

        playerCheck = new TiledCollisionCheck(player.bounds, mapLayer, "pump");
	}

    public Waterpump(TiledMapTileLayer mapLayer, Player player, float P_X, float P_Y){
        super(Assets.instance.waterpumpAltas, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player );

        playerCheck = new TiledCollisionCheck(player.bounds, mapLayer, "pump");
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
        name ="เครื่องสูบน้ำ";
    }
}
