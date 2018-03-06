package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.utils.Direction;

public abstract class Etc extends AbstractGameObject {

    public enum EtcType {
        Link
    }

    private TextureRegion etcTexture;

    public EtcType type;
    public Direction direction;
    private boolean destroyed;
   //public int x,y,d;

    public abstract void TellMeByType();

    public Etc(TextureRegion etcTexture, float scaleX, float scaleY, float frictionX, float frictionY) {
        this.etcTexture = etcTexture;

        scale.set(scaleX, scaleY);
        setDimension(
                etcTexture.getRegionWidth(),
                etcTexture.getRegionHeight());

        destroyed = false;
        friction.set(frictionX, frictionY);
    }

    public void init(TiledMapTileLayer mapLayer, float x, float y, int d) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        TellMeByType();
        spawn(x, y ,d);
    }

    protected abstract void spawn(float x, float y ,int d);

    @Override
    public void render(SpriteBatch batch) {
        render(batch, etcTexture);
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void destroy(){
        destroyed = true;
    }

}
