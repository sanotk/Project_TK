package com.mypjgdx.esg.game.objects.etcs;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.weapons.Weapon;


public class Link extends Etc {

	    private static final float SCALE = 1f;

		public static final float P_X = 0f;
		public static final float P_Y = 0f;

	    public Link(TiledMapTileLayer mapLayer , Player player) {
	        super(Assets.instance.link, SCALE, SCALE, P_X, P_Y);
	        init(mapLayer ,player);
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
