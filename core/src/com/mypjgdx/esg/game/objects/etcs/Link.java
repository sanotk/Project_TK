package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.utils.Direction;


public class Link extends Etc {

    private static final float SCALE = 1f;

    public Link(TiledMapTileLayer mapLayer, float positionX, float positionY, Direction direction) {
        super(Assets.instance.link, SCALE, SCALE);
        init(mapLayer, positionX, positionY, direction);
    }

    @Override
    protected void spawn(float positionX, float positionY, Direction direction) {
        setPosition(positionX, positionY);

        switch (direction) {
            case UP:
                rotation = 90;
                break;
            case RIGHT:
                rotation = 180;
                break;
            case DOWN:
                rotation = 270;
                break;
            case LEFT:
                rotation = 360;
                break;
            default:
                break;
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void TellMeByType() {
        // TODO Auto-generated method stub
        type = EtcType.Link;
    }

}
