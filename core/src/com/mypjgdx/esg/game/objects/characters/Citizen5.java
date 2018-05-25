package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class Citizen5 extends Citizen {

	public static final int MAX_SPEED = 100;
	public static final float SCALE = 0.5f;

    public static final float INITIAL_GOAL_X = 300;
    public static final float INITIAL_GOAL_Y = 950;

    public Citizen5(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.citizenAltas, SCALE, SCALE, mapLayer);

        this.player = player;
        this.movingSpeed = MAX_SPEED;
        setGoalPosition(INITIAL_GOAL_X,INITIAL_GOAL_Y);
        init(mapLayer);
    }

    public Citizen5(TiledMapTileLayer mapLayer, Player player,float P_X, float P_Y) {
        super(Assets.instance.citizenAltas, SCALE, SCALE,mapLayer,P_X,P_Y);

        this.player = player;
        this.movingSpeed = MAX_SPEED;
        setGoalPosition(INITIAL_GOAL_X,INITIAL_GOAL_Y);
        init(mapLayer);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public void TellMeByType() {
		type = CitizenType.CITIZEN_5;
	}
    public void setGoalPosition(float x,float y){
        positionGoalX = x;
        positionGoalY = y;
    }
}
