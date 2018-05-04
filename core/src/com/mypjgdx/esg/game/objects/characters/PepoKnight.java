package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class PepoKnight extends Enemy {

	public static final int MAX_HEALTH = 8;
	public static final int MAX_SPEED = 50;
	public static final float SCALE = 0.8f;

    public PepoKnight() {
        super(Assets.instance.pepoKnightAltas, SCALE, SCALE, null);
        this.movingSpeed = MAX_SPEED;
        this.maxHealth = MAX_HEALTH;
    }

    public PepoKnight(TiledMapTileLayer mapLayer,Player player) {
        super(Assets.instance.pepoKnightAltas, SCALE ,SCALE, mapLayer);

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
		type = EnemyType.PEPO_KNIGHT;
	}
}
