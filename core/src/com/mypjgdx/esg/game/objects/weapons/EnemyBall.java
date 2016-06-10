package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;


public class EnemyBall extends Weapon {

	    private static final float SCALE = 0.5f;

	    private static final float INTITAL_FRICTION = 50f;
	    private static final float INTITIAL_SPEED = 400f;

	    public EnemyBall(TiledMapTileLayer mapLayer ,Player player ,Enemy enemy) {
	        super(Assets.instance.enemyBall, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
	        init(mapLayer ,player, enemy);
	    }

	    @Override
	    protected void spawn() {
	        setPosition(
	                enemy.getPositionX() + enemy.origin.x - 4,
	                enemy.getPositionY() + enemy.origin.y - 4);

	        direction = enemy.getViewDirection();

	    	switch(direction){
			case DOWN:
			    rotation = 90;
			    velocity.set(0,-INTITIAL_SPEED);
			    break;
			case LEFT:
			    velocity.set(-INTITIAL_SPEED,0);
			    break;
			case RIGHT:
			    velocity.set(INTITIAL_SPEED,0);
			    break;
			case UP:
			    rotation = 90;
			    velocity.set(0, INTITIAL_SPEED);
			    break;
			default:
			    break;
	    	}
	    }

	    public void attackPlayer(){
	    	attack(player);
	    }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return null;
        }

		@Override
		public void attack(Damageable damageable) {
			// TODO Auto-generated method stub

		}
}
