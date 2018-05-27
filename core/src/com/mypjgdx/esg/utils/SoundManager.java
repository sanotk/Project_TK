package com.mypjgdx.esg.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntMap;
import com.mypjgdx.esg.game.Assets;

public class SoundManager {
    public static final SoundManager instance = new SoundManager();

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
        sounds.get(id).play(SettingManager.instance.soundVolume);
    }

    public void setVolume(float volume) {
        SettingManager.instance.soundVolume = MathUtils.clamp(volume, 0, 1);
        SettingManager.instance.save();
    }
}
