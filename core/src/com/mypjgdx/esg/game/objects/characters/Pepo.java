package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class Pepo extends Enemy {

	public static final int MAX_HEALTH = 10;
	public static final int MAX_SPEED = 60;
	public static final float SCALE = 1f;

    public Pepo() {
        super(Assets.instance.pepoAltas, SCALE, SCALE, null);
        this.movingSpeed = MAX_SPEED;
        this.maxHealth = MAX_HEALTH;
    }

    public Pepo(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.pepoAltas, SCALE, SCALE, mapLayer);

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
		type = EnemyType.PEPO;
	}



}
