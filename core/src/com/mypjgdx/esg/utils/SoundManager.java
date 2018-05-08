package com.mypjgdx.esg.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntMap;
import com.mypjgdx.esg.game.Assets;

public class SoundManager {
    public static final SoundManager instance = new SoundManager();

    public static class Sounds {
        public static final int ARROW = 0;
        public static final int BEAM = 1;
        public static final int TRAP = 2;
        public static final int ENEMY_BALL = 3;

        private Sounds() {
        }
    }

    private float volume = 0.5f;
    private IntMap<Sound> sounds;

    private SoundManager() {
        sounds = new IntMap<Sound>();
        sounds.put(Sounds.ARROW, Assets.instance.arrowSound);
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
