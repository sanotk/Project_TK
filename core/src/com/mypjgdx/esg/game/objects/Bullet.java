package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.utils.Direction;


public class Bullet extends AbstractGameObject{

	    private static final float SCALE = 1f;

	    private static final float INTITAL_FRICTION = 50f;  // ค่าแรงเสียดทานเริ่มต้น
	    private static final float INTITIAL_SPEED = 400f; // ค่าความเร็วต้น

	    private Player player;
	    private boolean despawned;

	    private Direction direction;

	    public Bullet(TiledMapTileLayer mapLayer ,Player player) {
	        this.player = player;
	        init();
	    }

	    public void init() {
	    	despawned = false;

	        // กำหนดค่าทางฟิสิกส์
	        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
	        acceleration.set(0.0f, 0.0f);

	        // กำหนดขนาดสเกลของ ดาบ
	        scale.set(SCALE, SCALE);

	        setDimension(
	                Assets.instance.bullet.getRegionWidth(),
	                Assets.instance.bullet.getRegionHeight());

	        setPosition(
	                player.getPositionX() + player.origin.x - origin.x,
	        		player.getPositionY() + player.origin.y - 4);

	        direction = player.getViewDirection();

	    	switch(direction){
			case DOWN:
			    rotation = 90;
			    velocity.set(0,-INTITIAL_SPEED);
			    break;
			case LEFT:
			    velocity.set(-INTITIAL_SPEED,0);
			    break;
			case RIGHT:
			    velocity.set(INTITIAL_SPEED,0);
			    break;
			case UP:
			    rotation = 90;
			    velocity.set(0, INTITIAL_SPEED);
			    break;
			default:
			    break;
	    	}
	    }

	    @Override
	    public void update(float deltaTime) {
	        updateMotionX(deltaTime);
	        updateMotionY(deltaTime);

	        setPosition(
	                getPositionX() + velocity.x * deltaTime,
	                getPositionY() + velocity.y * deltaTime);

	        if (collisionCheck.isCollidesLeft()
	                || collisionCheck.isCollidesRight()
	                || collisionCheck.isCollidesTop()
	                || collisionCheck.isCollidesBottom()) {
	        	despawned = true;
	        }
	    }

	    public boolean isDespawned(){
	    	return despawned;
	    }

	    public void despawn(){
	    	despawned = true;
	    }

	    public Direction getDirection() {
	        return direction;
	    }

	    @Override
	    public void render(SpriteBatch batch) {
	        render(batch, Assets.instance.bullet);
	    }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return null;
        }


}
