package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;

public class NormalBow extends Bow{

    private static final float SCALE = 0.5f;
    public static final float P_X = 1700f;
    public static final float P_Y = 950f;

	public NormalBow(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.bow, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player);
	}

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void spawn() {

    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void attack(Damageable damageable) {

    }
}
