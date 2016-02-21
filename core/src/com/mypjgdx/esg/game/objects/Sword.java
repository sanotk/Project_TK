package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;


public class Sword extends AbstractGameObject{

	    // จำนวนเฟรมต่อ 1 ทิศ
	    private static final int FRAME_PER_DIRECTION = 3;

	    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
	    private static final float FRAME_DURATION = 1.0f / 8.0f;

	    // อัตราการขยายภาพ player
	    private static final float SCALE = 0.2f;

	    private static final float INTITAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
	    private TextureRegion sword;

	    private TiledMapTileLayer mapLayer;
	    private Player player;
	    // เวลา Animation ที่ใช้หา KeyFrame
	    private float animationTime;

	    public Sword(TiledMapTileLayer mapLayer ,Player player) {
	        this.mapLayer = mapLayer;
	        this.player = player;
	        init();
	    }

	    public void init() {
	        sword = Assets.instance.sword;
	        // กำหนดค่าทางฟิสิกส์
	        friction.set(INTITAL_FRICTION, INTITAL_FRICTION);
	        acceleration.set(0.0f, 0.0f);

	        // กำหนดเวลา Animation เริ่มต้นเท่ากับ 0
	        animationTime = 0.0f;

	        // กำหนดขนาดสเกลของ player
	        scale.set(SCALE, SCALE);
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
	        if (cellX < mapLayer.getWidth() && cellX >= 0 && cellY <= mapLayer.getHeight() && cellY >= 0) {
	            return mapLayer.getCell(cellX, cellY).getTile().getProperties().containsKey("blocked");
	        }
	        return false;
	    }

	    @Override
	    public void render(SpriteBatch batch) {
	        // วาดตัวละคร ตามตำแหน่ง ขนาด และองศาตามที่กำหนด
	        render(batch, sword);
	    }
}
