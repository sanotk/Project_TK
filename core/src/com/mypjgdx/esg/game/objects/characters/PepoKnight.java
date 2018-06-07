package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.items.drop.DroppedItemType;

public class PepoKnight extends Enemy {

	public static final int MAX_HEALTH = 15;
	public static final int MAX_SPEED = 50;
	public static final float SCALE = 0.8f;

    public PepoKnight() {
        super(Assets.instance.pepoKnightAltas, SCALE, SCALE);
        this.movingSpeed = MAX_SPEED;
        this.maxHealth = MAX_HEALTH;
        setDroppedItemType(DroppedItemType.LINK);
    }

    public PepoKnight(TiledMapTileLayer mapLayer, Player player) {
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
		type = EnemyType.PEPO_KNIGHT;
	}
}
