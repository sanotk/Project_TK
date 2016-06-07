package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;

public abstract class Item extends AnimatedObject{

    	// อัตราการขยายภาพ enemy
		private static final float SCALE = 0.3f;
	    private static final float INITIAL_FRICTION = 600f;           // ค่าแรงเสียดทานเริ่มต้น
		protected static final float FRAME_DURATION = 1.0f / 8.0f;

		public Player player;

		public Item(TextureAtlas atlas) {
			super(atlas);
		}

	    public enum ItemState{
	    	OFF,
	    	ON
	    }

	    public ItemState state;

		public void init(TiledMapTileLayer mapLayer) {
	        addLoopAnimation(AnimationName.OFF, FRAME_DURATION, 0, 3);
	        addLoopAnimation(AnimationName.ON, FRAME_DURATION, 3, 3);
			// TODO Auto-generated method stub

	        // กำหนดค่าทางฟิสิกส์
	        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);
	        acceleration.set(0.0f, 0.0f);

	        scale.set(SCALE, SCALE);

	        state = ItemState.OFF;

	        setPosition(0, 0);

	        currentRegion = animations.get(AnimationName.OFF).getKeyFrame(0);
	        setDimension(currentRegion.getRegionWidth(),currentRegion.getRegionHeight());

	        collisionCheck = new TiledCollisionCheck(this.bounds, mapLayer);

	        randomPosition(mapLayer);
		}

		private void addPlayer(){

		}

	    @Override
	    public void update(float deltaTime) {
	        super.update(deltaTime);
	    }

		@Override
		protected void setAnimation() {
			unFreezeAnimation();
	        switch (itemSwitch) {
	        case ON: setCurrentAnimation(AnimationName.ON); break;
	        case OFF: setCurrentAnimation(AnimationName.OFF); break;
	        default:
	            break;
	        }
		}

	    private void randomPosition(TiledMapTileLayer mapLayer) {
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
