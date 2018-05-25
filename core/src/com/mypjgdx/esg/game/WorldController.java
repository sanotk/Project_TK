package com.mypjgdx.esg.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.EnemySpawner;
import com.mypjgdx.esg.game.objects.etcs.Link;
import com.mypjgdx.esg.ui.BatteryBar;
import com.mypjgdx.esg.ui.EnergyProducedBar;
import com.mypjgdx.esg.ui.EnergyUsedBar;
import com.mypjgdx.esg.utils.CameraHelper;
import com.mypjgdx.esg.utils.Direction;

public class WorldController extends InputAdapter {

    private static final float CAMERA_SPEED = 200.0f; //กำหนดความเร็วในการเคลื่อนที่ของกล้อง
    private static final float CAMERA_ZOOM_SPEED = 1.0f; //ความเร็วของการซูม

    public CameraHelper cameraHelper;
    public Level level;

    public WorldController(Level level) {
        init(level);
    }

    public void init(Level level) {
        this.level = level;
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(this.level.player);
        cameraHelper.setMap(this.level.mapLayer);
        Gdx.input.setInputProcessor(this);
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
        level.update(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleInput(float deltaTime) {
        handleCameraInput(deltaTime);
        handleplayerInput();
    }

    private void handleCameraInput(float deltaTime) {
        if (cameraHelper.hasTarget()) return; // มุมกล้องติดตาม player อยู่จะใช้ Input เลื่อนมุมกล้องเองไม่ได้

        if (Gdx.input.isKeyPressed(Keys.UP)) cameraHelper.addPostion(0, CAMERA_SPEED * deltaTime); //กดลูกศรขึ้น
        if (Gdx.input.isKeyPressed(Keys.DOWN)) cameraHelper.addPostion(0, -CAMERA_SPEED * deltaTime);//กดลูกศรลง
        if (Gdx.input.isKeyPressed(Keys.LEFT)) cameraHelper.addPostion(-CAMERA_SPEED * deltaTime, 0);//กดลูกศรซ้าย
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) cameraHelper.addPostion(CAMERA_SPEED * deltaTime, 0);//กดลูกศรขวา
        if (Gdx.input.isKeyPressed(Keys.Z)) cameraHelper.addZoom(CAMERA_ZOOM_SPEED * deltaTime); //กด z
        if (Gdx.input.isKeyPressed(Keys.X)) cameraHelper.addZoom(-CAMERA_ZOOM_SPEED * deltaTime); // กด x
    }

    private void handleplayerInput() { // ควบคุม player
        if (!cameraHelper.hasTarget()) return; // มุมกล้องติดตาม player อยู่ถึงจะควมคุม player ได้
        if (level.player.timeStop) return;

        final int screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();
        final float SCREEN_MOVE_EGDE = 0.4f;

        if (Gdx.app.getType() == ApplicationType.Android && Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float filppedY = screenHeight - Gdx.input.getY();
            if (x > screenWidth * (1.0f - SCREEN_MOVE_EGDE)) level.player.move(Direction.RIGHT);
            else if (x < screenWidth * SCREEN_MOVE_EGDE) level.player.move(Direction.LEFT);

            if (filppedY > screenHeight * (1.0f - SCREEN_MOVE_EGDE)) level.player.move(Direction.UP);
            else if (filppedY < screenHeight * SCREEN_MOVE_EGDE) level.player.move(Direction.DOWN);
        } else {
            if (Gdx.input.isKeyPressed(Keys.UP)) level.player.move(Direction.UP);
            if (Gdx.input.isKeyPressed(Keys.DOWN)) level.player.move(Direction.DOWN);
            if (Gdx.input.isKeyPressed(Keys.LEFT)) level.player.move(Direction.LEFT);
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) level.player.move(Direction.RIGHT);
            if (Gdx.input.isKeyJustPressed(Keys.Z)) level.player.trapAttack(level.weapons);
            if (Gdx.input.isKeyPressed(Keys.C)) level.player.swordAttack(level.weapons, level.swords);
            if (Gdx.input.isKeyJustPressed(Keys.X)) level.player.swordWaveAttack(level.weapons, level.swords);
            if (Gdx.input.isKeyJustPressed(Keys.A)) level.player.findItem();
            if (Gdx.input.isKeyJustPressed(Keys.B)) {
//                Json json = new Json();
//                String choice = json.toJson(level);
//                String energyProduced = json.toJson(EnergyProducedBar.instance.energyProduced);
                FileHandle file = Gdx.files.absolute("C:\\Data\\save.txt"); // TODO
//                file.delete();
//                file.writeString(choice, true);         // True means append, false means overwrite.

                Json json = new Json(JsonWriter.OutputType.json);
                System.out.print(json.prettyPrint(level));
                json.toJson(level, file);
            }
            ;
            if (Gdx.input.isKeyJustPressed(Keys.L)) {
                FileHandle file = Gdx.files.absolute("C:\\Data\\save.txt");

                Json json = new Json(JsonWriter.OutputType.json);

                JsonReader reader = new JsonReader();
                JsonValue saveData = reader.parse(file);
                loadPlayer(saveData);
                EnergyProducedBar.instance.read(null, saveData);
                EnergyUsedBar.instance.read(null, saveData);
                BatteryBar.instance.read(null, saveData);
                loadCitizens(saveData);
                loadItems(saveData);
                loadLinks(saveData);
                loadEnemys(saveData);
            }
        }
    }

    private void loadEnemys(JsonValue saveData) {
        JsonValue enemies = saveData.get("enemies");
        if (enemies.isArray()) {
            level.enemies.clear();
            for (int i = 0; i < enemies.size; i++) {
                Enemy enemy = EnemySpawner.valueOf(enemies.get(i).getString("type")).spawn();
                enemy.setPlayer(level.player);
                enemy.init(level.mapLayer);
                enemy.read(null, enemies.get(i));
                level.enemies.add(enemy);
            }
        }
    }

    private void loadLinks(JsonValue saveData) {
        JsonValue links = saveData.get("links");
        if (links.isArray()) {
            level.links.clear();
            for (int i = 0; i < links.size; i++) {
                Link link = new Link();
                link.read(null, links.get(i));
                link.init(level.mapLayer);
                level.links.add(link);
            }
        }
    }

    private void loadCitizens(JsonValue saveData) {
        JsonValue citizens = saveData.get("citizens");
        if (citizens.isArray()) {
            for (int i = 0; i < citizens.size; i++) {
                level.citizens.get(i).read(null, citizens.get(i));
            }
        }
    }


    private void loadItems(JsonValue saveData) {
        JsonValue items = saveData.get("items");
        if (items.isArray()) {
            for (int i = 0; i < items.size; i++) {
                level.items.get(i).read(null, items.get(i));
            }
        }
    }

    private void loadPlayer(JsonValue saveData) {
        level.player.read(null, saveData);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.SPACE: // กด Spacebar เพื่อให้มุมกล้องติดตาม/เลิกติดตาม player
                if (!cameraHelper.hasTarget()) cameraHelper.setTarget(level.player);
                else cameraHelper.setTarget(null);
                break;
            case Keys.NUMPAD_1:
                level.init();
                init(level);
                break;
            case Keys.NUMPAD_2:
                level.init();
                init(level);
                break;
            case Keys.NUMPAD_3:
                level.init();
                init(level);
                break;
            case Keys.NUMPAD_4:
                level.init();
                init(level);
                break;
        }


        return true;
    }

}
