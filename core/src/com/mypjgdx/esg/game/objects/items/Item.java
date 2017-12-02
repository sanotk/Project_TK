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

		protected static final float FRAME_DURATION = 1.0f / 2.0f;

		public float p_x;
		public float p_y;

		public enum ItemAnimation {
		    ON,
		    OFF
		}

		public enum ItemState {
	        ON,
	        OFF
	    }

		private ItemState state;

		public Item(TextureAtlas atlas, float scaleX, float scaleY , float P_X , float P_Y) {
            super(atlas);

            addLoopAnimation(ItemAnimation.OFF, FRAME_DURATION, 0, 3);
			addNormalAnimation(ItemAnimation.ON, FRAME_DURATION, 3, 3);

			p_x = P_X;
			p_y = P_Y;

            scale.set(scaleX, scaleY);
        }

		public void init(TiledMapTileLayer mapLayer, Player player) {
	        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
            state = ItemState.OFF;
            setCurrentAnimation(ItemAnimation.OFF);
	        setPosition(mapLayer, player);
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

		private void setPosition(TiledMapTileLayer mapLayer, Player player) {
			updateBounds();
			setPosition(p_x,p_y);
		}

	    private void randomPosition(TiledMapTileLayer mapLayer, Player player) {
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

	    public abstract void activate();
}
