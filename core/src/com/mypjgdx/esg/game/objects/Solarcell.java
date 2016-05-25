package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;


public class Solarcell extends AbstractGameObject{

	    private static final float SCALE = 1f;

	    private Player player;

	    public Solarcell(TiledMapTileLayer mapLayer ,Player player) {
	        this.player = player;
	        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);
	        init();
	    }

	    public void init() {

	        // กำหนดขนาดสเกลของ ดาบ
	        scale.set(SCALE, SCALE);

	        setDimension(
	                Assets.instance.solarcell.getRegionWidth(),
	                Assets.instance.solarcell.getRegionHeight());

	        setPosition(
	                player.getPositionX() + player.origin.x - origin.x,
	        		player.getPositionY() + player.origin.y - 4);

	    }

	    @Override
	    public void update(float deltaTime) {
	        updateMotionX(deltaTime);
	        updateMotionY(deltaTime);

	        setPosition(
	                getPositionX() + velocity.x * deltaTime,
	                getPositionY() + velocity.y * deltaTime);

	    }

	    @Override
	    public void render(SpriteBatch batch) {
	        render(batch, Assets.instance.solarcell);
	    }
}
