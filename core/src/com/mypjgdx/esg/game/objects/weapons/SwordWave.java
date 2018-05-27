package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;


public class SwordWave extends Weapon {

    private static final float SCALE = 0.5f;

    private static final float INTITAL_FRICTION = 50f;
    private static final float INTITIAL_SPEED = 400f;

    private Damageable target;
    private boolean damaged;
    private Vector2 positionToTarget;
    private int damageCount;

    public SwordWave(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.wave, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
        init(mapLayer, player, enemy);
    }

    @Override
    protected void spawn() {

        direction = player.getViewDirection();

        switch (direction) {
            case LEFT:
            case RIGHT:
                setPosition(
                        player.getPositionX() ,
                        player.getPositionY() );
                break;
            case DOWN:
                setPosition(
                        player.getPositionX() + player.origin.x - origin.x,
                        player.getPositionY() + player.origin.y - player.bounds.height/2);
                break;
            case UP:
                setPosition(
                        player.getPositionX() + player.origin.x - origin.x,
                        player.getPositionY() + player.origin.y + player.bounds.height/2);
                break;

        }

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
        delay();
    }

    @Override
    public void update(float deltaTime) {
        //super.update(deltaTime);
        updateMotionX(deltaTime);
        updateMotionY(deltaTime);
        setPositionX(position.x + velocity.x * deltaTime);
        setPositionY(position.y + velocity.y * deltaTime);
        if(target != null){
            float x = target.getPosition().x + positionToTarget.x;
            float y = target.getPosition().y + positionToTarget.y;
            switch (direction) {
                case LEFT:
                    x -= 20;
                    break;
                case RIGHT:
                    x += 20;
                    break;
                case DOWN:
                    y -= 20;
                    break;
                case UP:
                    y += 20;
                    break;
            }
            setPosition(x, y);
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
        this.damageCount += 1;
        if(damageCount==10){
            destroy();
        }
    }

    private void delay(){
        float delay = 10f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                destroy();
            }
        }, delay);
    }

    @Override
    public void setPosition(float x, float y) {
        position.set(x, y);

        switch (direction) {
            case LEFT:
            case RIGHT:
                bounds.set(position.x, position.y, dimension.x, dimension.y);
                break;
            case DOWN:
            case UP:
                float newX = position.x + dimension.x / 2 - dimension.y / 2;
                float newY = position.y - dimension.x / 2 + dimension.y / 2;
                bounds.set(newX, newY, dimension.y, dimension.x);
                break;
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
}
