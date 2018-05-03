package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item.ItemAnimation;

public abstract class Item extends AnimatedObject<ItemAnimation> {

    protected static float FRAME_DURATION = 1.0f / 2.0f;

    public float p_x;
    public float p_y;
    public float energyBurn;

    public enum ItemAnimation {
        ON,
        ONLOOP,
        OFF
    }

    public enum ItemState {
        ON,
        ONLOOP,
        OFF
    }

    public ItemState state;

    public Item(TextureAtlas atlas, float scaleX, float scaleY, float P_X, float P_Y) {
        super(atlas);

        addLoopAnimation(ItemAnimation.OFF, FRAME_DURATION, 0, 1);
        addNormalAnimation(ItemAnimation.ON, FRAME_DURATION, 1, 3);
        addLoopAnimation(ItemAnimation.ONLOOP, FRAME_DURATION, 1, 3);


        p_x = P_X;
        p_y = P_Y;

        scale.set(scaleX, scaleY);
    }

    public void init(TiledMapTileLayer mapLayer, Player player) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        state = ItemState.OFF;
        setCurrentAnimation(ItemAnimation.OFF);
        setPosition(mapLayer, player);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void setAnimation() {
        unFreezeAnimation();
        switch (state) {
            case ON:
                setCurrentAnimation(ItemAnimation.ON);
                break;
            case OFF:
                setCurrentAnimation(ItemAnimation.OFF);
                break;
            case ONLOOP:
                setCurrentAnimation(ItemAnimation.ONLOOP);
                break;
            default:
                break;
        }
    }

    private void setPosition(TiledMapTileLayer mapLayer, Player player) {
        updateBounds();
        setPosition(p_x, p_y);
    }

    public void debug(ShapeRenderer renderer) {
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public abstract void activate();

    public void setEnergyBurn(float energyBurn) {
        this.energyBurn = energyBurn;
    }

    public float getGoalX() {
        return 0;
    }

    public float getGoalY() {
        return 0;
    }
}
