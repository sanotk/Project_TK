package com.mypjgdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyPjGdxGame extends ApplicationAdapter  {
    public static final int SCENE_WIDTH = 1280; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 720; //เซตค่าความสูงของจอ

    private OrthographicCamera camera;//สร้างตัวแปรกล้อง
    private FitViewport viewport; //พื้นที่การมอง
    private SpriteBatch batch; //ตัวแปรการวาด
    private WorldController worldController; //ส่วนควบคุมเกม


    private List<IntArray> backMapData;// ตัวแปรเก็บแผนที่ด้านหลัง
    private List<IntArray> frontMapData;// ตัวแปรเก็บแผนที่ด้านหน้า

    private Sano sano;  // ตัวละคร

    @Override
    public void create () {
        camera = new OrthographicCamera(); //สร้างออปเจ็คกล้องเก็บไว้ในตัวแปร camera
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera); //สร้างออปเจ็คการมองของกล้องเก็บไว้ในตัวแปร
        batch = new SpriteBatch();//สร้างออปเจ็คไว้วาดสิ่งต่างๆ
        sano = new Sano();  // SANO IS COMING !!!!

        worldController = new WorldController(sano); //สร้าง อินสแตนซ์ viewpoint

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("Breaktime_Silent_Film_Light.mp3"));//โหลดเสียง

        sound.play();
        sound.setVolume(0,1.0f);
        sound.pause();
        sound.stop();
        sound.play();
        //Gdx.app.log("SONG",Float.toString(sound.getPosition()));

        backMapData = LevelLoader.loadMap(Gdx.files.internal("mix_map_1.csv")); //โหลดแมพหลัง
        frontMapData = LevelLoader.loadMap(Gdx.files.internal("mix_map_2.csv")); //โหลดแมพหน้า
    }

    @Override
    public void resize(int width, int height) { //ปรับขนาด viewport ให้เหมาะสมกับขนาดจอที่เปลี่ยนไป
        viewport.update(width, height);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1); //เคลียหน้าจอ
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //เคลียหน้าจอ

        worldController.update(Gdx.graphics.getDeltaTime()); //อัพเดท Game World

        worldController.getCameraHelper().applyTo(camera); //อัพเดทมุมกล้อง
        batch.setProjectionMatrix(camera.combined); //เรนเดอร์ภาพให้สอดคล้องกับมุมกล้อง
        batch.begin(); //เริ่มวาด

        renderMap(backMapData); //เรนเดอร์แมพหลัง
        renderMap(frontMapData); //เรนเดอร์แมพหน้า
        sano.render(batch); // วาด Sano

        batch.end(); //สิ้นสุดการวาด
    }

    @Override
    public void dispose() {
        batch.dispose(); //คืนหน่วยความจำ
    }

    private void renderMap(List<IntArray> mapData) {
        int x = 0; //พิกัด x
        int y = 0; //พิกัด y

        for (int row = mapData.size()-1; row >= 0; --row) { //เรนเดอร์แมพให้ตรงกับไอดี
            for (int column = 0; column < mapData.get(row).size; ++column) {
                int tileId= mapData.get(row).get(column);
                Tiled tile = Tiled.get(tileId);
                batch.draw(tile.getRegion(), x, y);
                x += Tiled.TILE_WIDTH;
            }
            x = 0;
            y += Tiled.TILE_HEIGHT;
        }
    }



}
