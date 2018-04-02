package com.mypjgdx.esg;

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

    private float volume = 0.5f;

    private MusicManager() {
        musics = new IntMap<Music>();
        musics.put(Musics.MUSIC_1, Assets.instance.music);
        musics.put(Musics.MUSIC_2, Assets.instance.introGame);
    }

    public void play(int id, boolean looping) {
        currentMusic = musics.get(id);
        currentMusic.setLooping(looping);
        currentMusic.play();
        updateVolume();
        Gdx.app.log("music play", "" + currentMusic.hashCode());
    }

    public void stop() {
        if (currentMusic != null) {
            currentMusic.stop();
            Gdx.app.log("music stop", "" + currentMusic.hashCode());
        }
    }

    private void updateVolume() {
        currentMusic.setVolume(volume);
    }

    public void setVolume(float volume) {
        this.volume = MathUtils.clamp(volume, 0, 1);
        updateVolume();
    }
}