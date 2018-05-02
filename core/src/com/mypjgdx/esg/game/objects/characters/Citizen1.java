package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class Citizen1 extends Citizen {

	public static final int MAX_SPEED = 60;
	public static final float SCALE = 0.4f;

    public Citizen1(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.citizenAltas, SCALE, SCALE,mapLayer);

        this.player = player;
        this.movingSpeed = MAX_SPEED;

        init(mapLayer);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setColor() {
        color = Color.RED;
    }

	@Override
	public void TellMeByType() {
		type = CitizenType.CITIZEN_1;
	}


}
