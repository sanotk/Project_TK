package com.mypjgdx.esg.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.game.objects.AbstractGameObject;

public class CameraHelper {

    private static final float MAX_ZOOM_IN = 0.75f;
    private static final float MAX_ZOOM_OUT = 1.25f;

    private Vector2 position;
    private float zoom;
    private TiledMapTileLayer map;
    private AbstractGameObject target;

    public CameraHelper() {
        position = new Vector2();
        zoom = 1;
    }

    public void setPosition(float x, float y) { //เซ็ตตำแหน่ง
        this.position.set(x, y);
    }

    public void addPosition(float x, float y) { //เพิ่มตำแหน่ง
        this.position.add(x, y);
    }

    public Vector2 getPosition() { // เรียกค่า position ปัจจุบัน
        return position;
    }

    public void addZoom(float amount) { //รับค่า amount แล้วเซ็ตเพิ่มค่าซูมเป็นเท่านั้น
        setZoom(zoom + amount);
    }

    public void setZoom(float zoom) { //เซ็ตค่าซูม
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public float getZoom() { // เรียกค่า zoom ในปัจจุบัน
        return zoom;
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }

    public void setTarget(AbstractGameObject target) { // กำหนดเป้าหมายที่จะติดตาม
        this.target = target;
    }

    public void setMap(TiledMapTileLayer map) { // กำหนดเป้าหมายที่จะติดตาม
        this.map = map;
    }

    public AbstractGameObject getTarget() {    // หาเป้าหมายที่ติดตามอยู่
        return target;
    }

    public boolean hasTarget() {  // มีเป้าหมายติดตามอยู่หรือไม่?
        return target != null;
    }

    public boolean hasTarget(AbstractGameObject target) { // เป้าหมายที่ติดตามอยู่เป็น object นี้หรือใหม่
        return hasTarget() && this.target.equals(target);
    }

    public void update() {
        if (!hasTarget()) return;

        position.x = target.getPositionX() + target.origin.x;
        position.y = target.getPositionY() + target.origin.y;

        float leftEdge = map.getTileWidth() * 1;
        float rightEdge = (map.getWidth() - 1) * map.getTileWidth();
        float lowerEdge = map.getTileHeight() * 1;
        float upperEdge = (map.getHeight() - 1) * map.getTileHeight();
        float halfCameraWidth = zoom * 1024 / 2;
        float halfCameraHeight = zoom * 576 / 2;

        if (position.y + halfCameraHeight > upperEdge)
            position.y = upperEdge - halfCameraHeight;
        if (position.y - halfCameraHeight < lowerEdge)
            position.y = lowerEdge + halfCameraHeight;
        if (position.x + halfCameraWidth > rightEdge)
            position.x = rightEdge - halfCameraWidth;
        if (position.x - halfCameraWidth < leftEdge)
            position.x = leftEdge + halfCameraWidth;

    }
}
