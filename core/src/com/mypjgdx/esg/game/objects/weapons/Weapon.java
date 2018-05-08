package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

public abstract class Weapon extends AbstractGameObject {
    protected TextureRegion weaponTexture;

    protected Player player;
    protected Direction direction;

    private Array<Weapon> weapons;

    Weapon(TextureRegion weaponTexture, float scaleX, float scaleY, float frictionX, float frictionY) {
        this.weaponTexture = weaponTexture;

        scale.set(scaleX, scaleY);
        setDimension(weaponTexture.getRegionWidth(), weaponTexture.getRegionHeight());
        friction.set(frictionX, frictionY);
    }

    public void init(TiledMapTileLayer mapLayer ,Player player) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        this.player = player;
        spawn();
    }

    protected abstract void spawn();

    @Override
    public void render(SpriteBatch batch) {
        render(batch, weaponTexture);
    }

    public void remove(){
        weapons.removeValue(this, true);
    }

    public void setWeapons(Array<Weapon> weapons) {
        this.weapons = weapons;
    }

    public abstract void attack(Damageable damageable);
}
