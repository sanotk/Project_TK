package com.mypjgdx.esg.game.objects.weapons;

public enum WeaponSpawner {
    SWORD_WAVE,
    TRAP;

    public Weapon spawn() {
        switch (this) {
            case SWORD_WAVE:
                return new SwordWave();
            case TRAP:
                return new Trap();
            default:
                throw new IllegalStateException("Weapon Spawner Error!");
        }
    }
}
