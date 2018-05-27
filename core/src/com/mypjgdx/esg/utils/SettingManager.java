package com.mypjgdx.esg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Timer;

public class SettingManager {
    public static final SettingManager instance = new SettingManager();

    float soundVolume;
    float musicVolume;

    private Preferences preferences;

    private Timer.Task saveTask = new Timer.Task() {
        @Override
        public void run() {
            preferences.putFloat("soundVolume", soundVolume);
            preferences.putFloat("musicVolume", musicVolume);
            preferences.flush();
            Gdx.app.log("saved", "");
        }
    };

    private SettingManager() {
        preferences = Gdx.app.getPreferences("energy-saving-game-setting");
        preferences.flush();
    }

    public void load() {
        soundVolume = preferences.getFloat("soundVolume", 0.5f);
        musicVolume = preferences.getFloat("musicVolume", 0.5f);
    }

    public void save() {
        saveTask.cancel();
        Timer.schedule(saveTask, 1f);
    }
}
