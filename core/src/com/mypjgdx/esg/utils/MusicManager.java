package com.mypjgdx.esg.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntMap;
import com.mypjgdx.esg.game.Assets;

public class MusicManager {

    public static final MusicManager instance = new MusicManager();

    public static class Musics {
        public static final int MUSIC_1 = 0;
        public static final int MUSIC_2 = 1;

    }

    private IntMap<Music> musics;
    private Music currentMusic;

    private MusicManager() {
        musics = new IntMap<Music>();
        musics.put(Musics.MUSIC_1, Assets.instance.music);
        musics.put(Musics.MUSIC_2, Assets.instance.introGame);
    }

    public void play(int id, boolean looping) {
        currentMusic = musics.get(id);
        currentMusic.setLooping(looping);
        currentMusic.play();
        currentMusic.setVolume(SettingManager.instance.musicVolume);
        Gdx.app.log("music play", "" + currentMusic.hashCode());
    }

    public void stop() {
        if (currentMusic != null) {
            currentMusic.stop();
            Gdx.app.log("music stop", "" + currentMusic.hashCode());
        }
    }

    public void setVolume(float volume) {
        SettingManager.instance.musicVolume = MathUtils.clamp(volume, 0, 1);
        currentMusic.setVolume(SettingManager.instance.musicVolume);
        SettingManager.instance.save();
    }
}
