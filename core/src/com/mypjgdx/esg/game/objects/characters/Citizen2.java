package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class Citizen2 extends Citizen {

	public static final int MAX_HEALTH = 5;
	public static final int MAX_SPEED = 60;
	public static final float SCALE = 0.5f;

    public Citizen2(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.playerAltas, SCALE, SCALE, mapLayer);

        this.player = player;
        this.movingSpeed = MAX_SPEED;
        this.maxHealth = MAX_HEALTH;

        init(mapLayer);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public void TellMeByType() {
		type = CitizenType.Citizen2;
	}


    @Override
    public boolean takeDamage(float damage, float knockbackSpeed, float knockbackAngle) {
        return false;
    }
}
