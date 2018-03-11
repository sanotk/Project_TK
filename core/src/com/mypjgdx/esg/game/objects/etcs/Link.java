package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;


public class Link extends Etc {

	    private static final float SCALE = 1f;

	    public Link(TiledMapTileLayer mapLayer, float positionX, float positionY, int direction ) {
	        super(Assets.instance.link, SCALE, SCALE);
	        init(mapLayer, positionX, positionY, direction);
	    }

	    @Override
	    protected void spawn(float positionX, float positionY ,int direction) {
	        setPosition(positionX, positionY);

	    	switch(direction){
			case 1:
			    rotation = 90;
			    break;
			case 2:
				rotation = 180;
			    break;
			case 3:
				rotation = 270;
			    break;
			case 4:
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
