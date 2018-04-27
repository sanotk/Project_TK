package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;


public class SwordHit extends Weapon {

    private static final float SCALE = 0.5f;

    private static final float INTITAL_FRICTION = 0f;
    private static final float INTITIAL_SPEED = 0f;

    public SwordHit(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.box, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
        init(mapLayer, player, enemy);
    }

    @Override
    protected void spawn() {
        setPosition(
                player.getPositionX() + player.origin.x - origin.x,
                player.getPositionY() + player.origin.y - 4);

        direction = player.getViewDirection();

        switch (direction) {
            case DOWN:
                rotation = 270;
                velocity.set(0, -INTITIAL_SPEED);
                break;
            case LEFT:
                rotation = 180;
                velocity.set(-INTITIAL_SPEED, 0);
                break;
            case RIGHT:
                velocity.set(INTITIAL_SPEED, 0);
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
        float knockbackSpeed = 100f;
        switch (direction) {
            case DOWN:
                damageable.takeDamage(1, knockbackSpeed, 270);
                break;
            case LEFT:
                damageable.takeDamage(1, knockbackSpeed, 180);
                break;
            case RIGHT:
                damageable.takeDamage(1, knockbackSpeed, 0);
                break;
            case UP:
                damageable.takeDamage(1, knockbackSpeed, 90);
                break;
            default:
                break;
        }
    }

    @Override
    protected void responseCollisionX(float oldPositionX) {
        destroy();
    }

    @Override
    protected void responseCollisionY(float oldPositionY) {
        destroy();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void TellMeByType() {
        // TODO Auto-generated method stub
        type = WeaponType.BULLET;
    }
}
