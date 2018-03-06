package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;


public class Link extends Etc {

	    private static final float SCALE = 1f;

	    private static final float INTITAL_FRICTION = 50f;
	    private static final float INTITIAL_SPEED = 400f;

	    public Link(TiledMapTileLayer mapLayer, float x, float y, int d ) {
	        super(Assets.instance.beam, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
	        init(mapLayer ,x,y,d);
	        spawn(x, y ,d);
	    }

	    @Override
	    protected void spawn(float x, float y ,int d) {
	        setPosition(x, y);

	    	switch(d){
			case 1:
			    rotation = 90;
			    velocity.set(0,-INTITIAL_SPEED);
			    break;
			case 2:
			    velocity.set(-INTITIAL_SPEED,0);
			    break;
			case 3:
			    velocity.set(INTITIAL_SPEED,0);
			    break;
			case 4:
			    rotation = 90;
			    velocity.set(0, INTITIAL_SPEED);
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
