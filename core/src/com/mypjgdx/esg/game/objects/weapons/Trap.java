package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;


public class Trap extends Weapon implements Json.Serializable{

	    private static final float SCALE = 0.75f;

	    private static final float INTITAL_FRICTION = 50f;
	    private static final float INTITIAL_SPEED = 100f;
	    private int damageCount = 0;

	    public Trap(TiledMapTileLayer mapLayer ,Player player) {
	        super(Assets.instance.trap, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
            init(mapLayer ,player, enemy);
	    }

        @Override
        protected void spawn() {
            setPosition(
                    player.getPositionX() + player.origin.x - origin.x,
                    player.getPositionY() + player.origin.y - 9);

            direction = player.getViewDirection();

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

        @Override
        public void attack(Damageable damageable) {
            float knockbackSpeed = 500f;
            switch(damageable.getViewDirection()) {
            case DOWN: damageable.takeDamage(1, knockbackSpeed, 90); break;
            case LEFT: damageable.takeDamage(1, knockbackSpeed, 0); break;
            case RIGHT: damageable.takeDamage(1, knockbackSpeed, 180); break;
            case UP:  damageable.takeDamage(1, knockbackSpeed, 270); break;
            default: break;
            }
            this.damageCount += 1;
            if(damageCount==3){
                destroy();
            }
        }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return null;
        }

    @Override
    public void write(Json json) {
	        json.writeValue("damageCount", damageCount);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        damageCount = (int) jsonData.get("Trap").getFloat("damageCount");
    }
}
