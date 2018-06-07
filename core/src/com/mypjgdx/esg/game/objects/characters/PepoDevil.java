package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;

public class PepoDevil extends Enemy{

	public static final int MAX_HEALTH = 100;
	public static final int MAX_SPEED = 80;
	public static final float SCALE = 1f;

    public PepoDevil() {
        super(Assets.instance.pepoDevilAltas, SCALE, SCALE);
        this.movingSpeed = MAX_SPEED;
        this.maxHealth = MAX_HEALTH;
    }

    public PepoDevil(TiledMapTileLayer mapLayer,Player player) {
        this();
        this.player = player;
        init(mapLayer);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public void TellMeByType() {
		type = EnemyType.PEPO_DEVIL;
	}

}
