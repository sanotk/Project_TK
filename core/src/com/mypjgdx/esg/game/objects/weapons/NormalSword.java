package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;

public class NormalSword extends Sword{

    public static final float SCALE = 0.5f;
    public static final float P_X = 950f;
    public static final float P_Y = 800f;

	public NormalSword(TiledMapTileLayer mapLayer, Player player){
        super(Assets.instance.sword, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player);
	}

    public NormalSword(TiledMapTileLayer mapLayer, Player player, float P_X, float P_Y){
        super(Assets.instance.sword, SCALE, SCALE , P_X , P_Y);
        init(mapLayer,  player);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void spawn() {
        setPosition(
                player.getPositionX() + player.origin.x - origin.x,
                player.getPositionY() + player.origin.y - 9);
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub
    }

    public void debug(ShapeRenderer renderer) {
	    renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void attack(Damageable damageable) {
        float knockbackSpeed = 200f;
        switch(damageable.getViewDirection()) {
            case DOWN: damageable.takeDamage(1, knockbackSpeed, 90); break;
            case LEFT: damageable.takeDamage(1, knockbackSpeed, 0); break;
            case RIGHT: damageable.takeDamage(1, knockbackSpeed, 180); break;
            case UP:  damageable.takeDamage(1, knockbackSpeed, 270); break;
            default: break;
        }
    }
}
