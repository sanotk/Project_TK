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

    public Etc(TextureRegion etcTexture, float scaleX, float scaleY) {
        this.etcTexture = etcTexture;

        scale.set(scaleX, scaleY);
        setDimension(
                etcTexture.getRegionWidth(),
                etcTexture.getRegionHeight());

        destroyed = false;
    }

    public void init(TiledMapTileLayer mapLayer, float positionX, float positionY, int direction) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        TellMeByType();
        spawn(positionX, positionY ,direction);
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
