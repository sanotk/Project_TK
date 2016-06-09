package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item.ItemAnimation;
import com.mypjgdx.esg.utils.Distance;

public abstract class Item extends AnimatedObject<ItemAnimation>{

		protected static final float FRAME_DURATION = 1.0f / 8.0f;

		public Player player;

		private boolean addPlayer;

		public enum ItemAnimation {
		    ON,
		    OFF
		}

		public enum ItemState {
	        ON,
	        OFF
	    }

		private ItemState state;

		public Item(TextureAtlas atlas, float scaleX, float scaleY) {
            super(atlas);

            addLoopAnimation(ItemAnimation.OFF, FRAME_DURATION, 0, 3);
            addLoopAnimation(ItemAnimation.ON, FRAME_DURATION, 3, 3);

            scale.set(scaleX, scaleY);
        }

		public void init(TiledMapTileLayer mapLayer, Player player) {
	        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
            this.player = player;
            addPlayer = false;
            state = ItemState.OFF;
            setCurrentAnimation(ItemAnimation.OFF);
	        randomPosition(mapLayer);
		}

	    @Override
	    public void update(float deltaTime) {
	        super.update(deltaTime);

	        if(addPlayer==true) addItemToPlayer();
	    }

		private void addItemToPlayer() {
			// TODO Auto-generated method stub
			setPosition(player.getPositionX(),player.getPositionY());
		}

		@Override
		protected void setAnimation() {
			unFreezeAnimation();
	        switch (state) {
	        case ON: setCurrentAnimation(ItemAnimation.ON); break;
	        case OFF: setCurrentAnimation(ItemAnimation.OFF); break;
	        default:
	            break;
	        }
		}

	    private void randomPosition(TiledMapTileLayer mapLayer) {
            updateBounds();

	        float mapWidth = mapLayer.getTileWidth()*mapLayer.getWidth();
	        float mapHeight = mapLayer.getTileHeight()*mapLayer.getHeight();

	        final float MIN_DISTANCE = 100;
	        do{
	            setPosition(
	                    MathUtils.random(MIN_DISTANCE, mapWidth - bounds.width),
	                    MathUtils.random(MIN_DISTANCE, mapHeight - bounds.height));

                if (Distance.absoluteXY(this, player) < MIN_DISTANCE)
                    continue;

	        } while ( collisionCheck.isCollidesTop()
	                || collisionCheck.isCollidesBottom()
	                || collisionCheck.isCollidesRight()
	                || collisionCheck.isCollidesLeft());
	    }

	    public void addPlayer(){
	    	if(addPlayer == false)addPlayer = true;
	    	else addPlayer = false;
	    }

	    public abstract void activate();
}
