package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AbstractGameObject;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.utils.Direction;

public abstract class Etc extends AbstractGameObject {

    public enum EtcType {
        Link
    }

    public float p_x;
    public float p_y;

    private TextureRegion etcTexture;

    public EtcType type;
    protected Player player;
    public Direction direction;

    public abstract void TellMeByType();

    public Etc(TextureRegion weaponTexture, float scaleX, float scaleY, float P_X , float P_Y) {
        this.etcTexture = weaponTexture;

        p_x = P_X;
        p_y = P_Y;

        scale.set(scaleX, scaleY);

    }

    public void init(TiledMapTileLayer mapLayer ,Player player) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        this.player = player;
        TellMeByType();
        setPosition(mapLayer, player);
    }


    private void setPosition(TiledMapTileLayer mapLayer, Player player) {
        setPosition(p_x,p_y);
    }

    @Override
    public void render(SpriteBatch batch) {
        render(batch, etcTexture);
    }

}
