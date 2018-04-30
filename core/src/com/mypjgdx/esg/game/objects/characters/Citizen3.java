package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class Citizen3 extends Citizen {

	public static final int MAX_SPEED = 60;
	public static final float SCALE = 0.5f;

    public Citizen3(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.citizenAltas, SCALE, SCALE, mapLayer);

        this.player = player;
        this.movingSpeed = MAX_SPEED;
        init(mapLayer);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public void TellMeByType() {
		type = CitizenType.CITIZEN_3;
	}

}
