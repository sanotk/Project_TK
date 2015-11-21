package com.mypjgdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {

    private final float MAX_ZOOM_IN = 0.25f; //ตัวแปรอัตราการซูมเข้าสูงสุด
    private final float MAX_ZOOM_OUT = 5.0f; //ตัวแปรอัตราการซูมออกสูงสุด

    private Vector2 position; //ตำแหน่ง 2 มิติ
    private float zoom; //ตัวแปรซูม

    public CameraHelper () { //สร้าง instance ของ class Vector2 เพื่อใช้เก็บตำแหน่งตัวช่วยมุมกล้อง
        position = new Vector2();
        zoom = 1.0f;
    }

    public void setPosition (float x, float y) { //เซ็ตตำแหน่ง
        this.position.set(x, y);
    }

    public void addPostion(float x, float y) { //เพิ่มตำแหน่ง
        this.position.add(x, y);
    }

    public Vector2 getPosition () { // เรียกค่า position ปัจจุบัน
        return position;
    }

    public void addZoom (float amount) { //รับค่า amount แล้วเซ็ตเพิ่มค่าซูมเป็นเท่านั้น
        setZoom(zoom + amount);
    }

    public void setZoom (float zoom) { //เซ็ตค่าซูม
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public float getZoom () { // เรียกค่า zoom ในปัจจุบัน
        return zoom;
    }

    public void applyTo (OrthographicCamera camera) { //ปรับมุมกล้องจริงให้ตรงกับตัวช่วยมุมกล้อง
        camera.position.x = position.x; //
        camera.position.y = position.y; //
        camera.zoom = zoom; //
        camera.update(); //
    }

}
