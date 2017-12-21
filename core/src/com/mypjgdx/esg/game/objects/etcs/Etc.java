package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

public abstract class Etc extends AbstractGameObject {

    public enum EtcType {
        Link
    }

    private TextureRegion etcTexture;
    private boolean destroyed;

    public EtcType type;
    protected Player player;
    public Direction direction;

    public abstract void TellMeByType();

    public Etc(TextureRegion weaponTexture, float scaleX, float scaleY, float frictionX, float frictionY) {
        this.etcTexture = weaponTexture;

        scale.set(scaleX, scaleY);
        setDimension(
                weaponTexture.getRegionWidth(),
                weaponTexture.getRegionHeight());

        destroyed = false;
        friction.set(frictionX, frictionY);
    }

    public void init(TiledMapTileLayer mapLayer ,Player player) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        this.player = player;
        TellMeByType();
        spawn();
    }

    protected abstract void spawn();

    @Override
    public void render(SpriteBatch batch) {
        render(batch, etcTexture);
    }

}
