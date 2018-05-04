package com.mypjgdx.esg.game.objects.characters;

public enum EnemySpawner {

    PEPO,
    PEPO_KNIGHT,
    PEPO_DEVIL;

    public Enemy spawn() {
        switch (this) {
            case PEPO:
                return new Pepo();
            case PEPO_KNIGHT:
                return new PepoKnight();
            case PEPO_DEVIL:
                return new PepoDevil();
            default:
                throw new IllegalStateException("Enemy Spawner Error!");
        }
    }
}
