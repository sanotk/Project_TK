package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.Item.ItemAnimation;

public abstract class Item extends AnimatedObject<ItemAnimation>{

	    private static final float INITIAL_FRICTION = 600f;
		protected static final float FRAME_DURATION = 1.0f / 8.0f;

		public Player player;

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

            friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

            scale.set(scaleX, scaleY);
        }

		public void init(TiledMapTileLayer mapLayer, Player player) {
	        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
            this.player = player;

            state = ItemState.OFF;
            setCurrentAnimation(ItemAnimation.OFF);
	        randomPosition(mapLayer);
		}

	    @Override
	    public void update(float deltaTime) {
	        super.update(deltaTime);
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

	        final float MIN_DISTANCE = 200;
	        double distance;
	        do{
	            setPosition(
	                    MathUtils.random(MIN_DISTANCE, mapWidth - bounds.width),
	                    MathUtils.random(MIN_DISTANCE, mapHeight - bounds.height));

	            float xdiff = getPositionX() - player.getPositionX();
	            float ydiff = getPositionY() - player.getPositionY();

	            distance =  Math.sqrt(xdiff*xdiff + ydiff*ydiff);
	        } while ((distance < MIN_DISTANCE
	                || collisionCheck.isCollidesTop()
	                || collisionCheck.isCollidesBottom()
	                || collisionCheck.isCollidesRight()
	                || collisionCheck.isCollidesLeft()));
	    }
}
