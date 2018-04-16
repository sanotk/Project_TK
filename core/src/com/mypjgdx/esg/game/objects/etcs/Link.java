package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.SolarState;


public class Link extends Etc {

    private static final float SCALE = 2f;

    public Link(TiledMapTileLayer mapLayer, float positionX, float positionY, Direction direction , SolarState solarState) {
        super(Assets.instance.link, SCALE, SCALE);
        init(mapLayer, positionX, positionY, direction, solarState);
    }

    @Override
    protected void spawn(float positionX, float positionY, Direction direction) {
        switch (direction) {
            case UP:
                rotation = 90;
                positionX += 25;
                break;
            case RIGHT:
                rotation = 180;
                positionY += 25;
                break;
            case DOWN:
                rotation = 270;
                positionX -= 25;
                break;
            case LEFT:
                rotation = 360;
                positionY -= 25;
                break;
            default:
                break;
        }
        setPosition(positionX, positionY);
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
