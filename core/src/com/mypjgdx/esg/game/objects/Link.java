package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.utils.Direction;

public class Link extends AbstractGameObject {

    public Direction direction;
    private TextureRegion region;

    public Link(TiledMapTileLayer mapLayer, float positionX, float positionY, Direction direction) {
        region = Assets.instance.link;
        setDimension(region.getRegionWidth(), region.getRegionHeight());
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        final float TILE_SIZE = 50;
        switch (direction) {
            case UP:
                rotation = 0;
                break;
            case RIGHT:
                rotation = -90;
                positionX += TILE_SIZE / 2;
                positionY -= TILE_SIZE / 2;
                break;
            case DOWN:
                rotation = 180;
                positionY -= TILE_SIZE;
                break;
            case LEFT:
                rotation = 90;
                positionX -= TILE_SIZE / 2;
                positionY -= TILE_SIZE / 2;
                break;
            default:
                break;
        }
        setPosition(positionX, positionY);
    }

    @Override
    public void render(SpriteBatch batch) {
        render(batch, region);
    }

}
