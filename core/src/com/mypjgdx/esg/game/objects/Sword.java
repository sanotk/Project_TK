package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;


public class Sword extends AbstractGameObject{

	    private static final float SCALE = 0.2f;

	    private static final float INTITAL_FRICTION = 100;  // ค่าแรงเสียดทานเริ่มต้น
	    private static final float INTITIAL_SPEED = 100; // ค่าความเร็วต้น
	    private TextureRegion sword;

	    private TiledMapTileLayer mapLayer;
	    private Player player;

	    public Sword(TiledMapTileLayer mapLayer ,Player player) {
	        this.mapLayer = mapLayer;
	        this.player = player;
	        init();
	    }

	    public void init() {

	        // กำหนดค่าทางฟิสิกส์
	        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
	        acceleration.set(0.0f, 0.0f);
	        // กำหนดขนาดสเกลของ ดาบ
	        scale.set(SCALE, SCALE);

	        setDimension(Assets.instance.sword.getRegionWidth(),Assets.instance.sword.getRegionHeight());
	        position.set(player.position);
	        updateBounds();

	    	switch(player.getViewDirection()){
			case DOWN: velocity.set(0,-INTITIAL_SPEED);
				break;
			case LEFT: velocity.set(-INTITIAL_SPEED,0);
				break;
			case RIGHT: velocity.set(INTITIAL_SPEED,0);
				break;
			case UP: velocity.set(0, INTITIAL_SPEED);
				break;
			default:
				break;
	    	}
	    }

	    @Override
	    public void update(float deltaTime) {
	        updateMotionX(deltaTime);
	        updateMotionY(deltaTime);

	        position.x += velocity.x * deltaTime;
	        updateBounds();
	        if (collidesLeft() || collidesRight()) {
	            updateBounds();
	        }

	        position.y += velocity.y * deltaTime;
	        updateBounds();
	        if (collidesTop() || collidesBottom()) {
	            updateBounds();
	        }
	    }

	    public boolean collidesRight() {
	        for (float step = 0; step < bounds.height; step += mapLayer.getTileHeight())
	            if (isCellBlocked (bounds.x + bounds.width, bounds.y + step))
	                return true;
	        return isCellBlocked (bounds.x + bounds.width, bounds.y + bounds.height);
	    }

	    public boolean collidesLeft() {
	        for (float step = 0; step < bounds.height; step += mapLayer.getTileHeight())
	            if (isCellBlocked (bounds.x, bounds.y + step))
	                return true;
	        return isCellBlocked (bounds.x, bounds.y + bounds.height);
	    }

	    public boolean collidesTop() {
	        for(float step = 0; step < bounds.width; step += mapLayer.getTileWidth())
	            if (isCellBlocked(bounds.x + step, bounds.y + bounds.height))
	                return true;
	        return isCellBlocked(bounds.x + bounds.width, bounds.y + bounds.height);
	    }

	    public boolean collidesBottom() {
	        for(float step = 0; step < bounds.width; step += mapLayer.getTileWidth())
	            if (isCellBlocked(bounds.x + step, bounds.y))
	                return true;
	        return isCellBlocked(bounds.x + bounds.width, bounds.y);
	    }

	    private boolean isCellBlocked(float x, float y) {
	        int cellX = (int) (x/ mapLayer.getTileWidth());
	        int cellY = (int) (y/ mapLayer.getTileHeight());
	        if (cellX < mapLayer.getWidth() && cellX >= 0 && cellY < mapLayer.getHeight() && cellY >= 0) {
	            return mapLayer.getCell(cellX, cellY).getTile().getProperties().containsKey("blocked");
	        }
	        return false;
	    }

	    @Override
	    public void render(SpriteBatch batch) {
	        // วาดตัวละคร ตามตำแหน่ง ขนาด และองศาตามที่กำหนด
	        render(batch, Assets.instance.sword);
	    }
}
