package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mypjgdx.esg.game.Assets;

public enum  EnemyBuilder {
    PEPO(Assets.instance.pepoAtlas, 5, 60),
    PEPO_KNIGHT(Assets.instance.pepoKnightAtlas, 8, 50),
    PEPO_DEVIL(Assets.instance.pepoDevilAtlas, 15, 80);

    private final TextureAtlas atlas;
    private float scale = 1.0f;
    private final int maxHealth;
    private final float movingSpeed;

    EnemyBuilder(TextureAtlas atlas, int maxHealth, float movingSpeed) {
        this.atlas = atlas;
        this.maxHealth = maxHealth;
        this.movingSpeed = movingSpeed;
    }

    public Enemy getNew() {
        return new Enemy(atlas, scale, maxHealth, movingSpeed);
    }
}
