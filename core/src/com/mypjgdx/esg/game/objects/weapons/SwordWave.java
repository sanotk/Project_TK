package com.mypjgdx.esg.game.objects.weapons;

import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.utils.Direction;


public class SwordWave extends Weapon {

    private static final float SCALE = 0.5f;

    private static final float INTITAL_FRICTION = 50f;
    private static final float INTITIAL_SPEED = 400f;

    public SwordWave() {
        super(Assets.instance.wave, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
    }

    @Override
    protected void spawn() {
        direction = player.getViewDirection();
        final float playerCenterX = player.getPositionX() + player.origin.x;
        final float playerCenterY = player.getPositionY() + player.origin.y;

        setPosition(playerCenterX - origin.x, playerCenterY - origin.y);

        switch (direction) {
            case DOWN:
                rotation = -90;
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
    protected void responseCollisionX(float oldPositionX) {
        remove();
    }

    @Override
    protected void responseCollisionY(float oldPositionY) {
        remove();
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
    public void setPosition(float x, float y) {
        super.setPosition(x, y);

        if (direction == Direction.DOWN || direction == Direction.UP) {
            final float newX = getPositionX() + dimension.x / 2 - dimension.y / 2;
            final float newY = getPositionY() - dimension.x / 2 + dimension.y / 2;
            bounds.set(newX, newY, dimension.y, dimension.x);
        }
    }

    @Override
    public String getName() {
        return "Sword Wave";
    }

}
