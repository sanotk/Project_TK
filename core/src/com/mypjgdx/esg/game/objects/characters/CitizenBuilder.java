package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mypjgdx.esg.game.Assets;

public enum CitizenBuilder {

    //TODO: need to update atlas!
    CITIZEN_1(Assets.instance.citizenAtlas),
    CITIZEN_2(Assets.instance.citizenAtlas),
    CITIZEN_3(Assets.instance.citizenAtlas),
    CITIZEN_4(Assets.instance.citizenAtlas),
    CITIZEN_5(Assets.instance.citizenAtlas),
    CITIZEN_6(Assets.instance.citizenAtlas);

    private final TextureAtlas atlas;
    private float scale = 0.5f;

    CitizenBuilder(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public Citizen getNew() {
        return new Citizen(atlas, scale);
    }
}
