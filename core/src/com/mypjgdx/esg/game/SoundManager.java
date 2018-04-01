package com.mypjgdx.esg.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntMap;

public class SoundManager {
    public static final SoundManager instance = new SoundManager();
    private float volume = 0.5f;

    public static class Sounds {
        public static final int BULLET = 0;
        public static final int BEAM = 1;
        public static final int TRAP = 2;
        public static final int ENEMY_BALL = 3;
    }

    private IntMap<Sound> sounds;

    public SoundManager() {
        sounds = new IntMap<Sound>();
        sounds.put(Sounds.BULLET, Assets.instance.bulletSound);
        sounds.put(Sounds.BEAM, Assets.instance.beamSound);
        sounds.put(Sounds.TRAP, Assets.instance.trapSound);
        sounds.put(Sounds.ENEMY_BALL, Assets.instance.enemyBallSound);
    }

    public void play(int id) {
        sounds.get(id).play(volume);
    }

    public void setVolume(float volume) {
        this.volume = MathUtils.clamp(volume, 0, 1);
    }
}
