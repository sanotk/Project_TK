package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

public abstract class Weapon extends AbstractGameObject {

    public enum WeaponType {
        BULLET,
        TRAP,
        BEAM,
        ENEMYBALL
    }

    private TextureRegion weaponTexture;
    private TextureAtlas weaponTextureAtlas;
    private boolean destroyed;

    public WeaponType type;
    protected Player player;
    protected Enemy enemy;
    protected float damage;
    public Direction direction;

    public abstract void TellMeByType();

    public Weapon(TextureRegion weaponTexture,  float scaleX, float scaleY, float frictionX, float frictionY) {
        this.weaponTexture = weaponTexture;

        scale.set(scaleX, scaleY);
        setDimension(
                weaponTexture.getRegionWidth(),
                weaponTexture.getRegionHeight());

        destroyed = false;
        friction.set(frictionX, frictionY);
    }

    public void init(TiledMapTileLayer mapLayer ,Player player ,Enemy enemy) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        this.player = player;
        this.enemy = enemy;
        TellMeByType();
        spawn();
    }

    protected abstract void spawn();

    @Override
    public void render(SpriteBatch batch) {
        render(batch, weaponTexture);
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void destroy(){
        destroyed = true;
    }

    public abstract void attack(Damageable damageable);
}
