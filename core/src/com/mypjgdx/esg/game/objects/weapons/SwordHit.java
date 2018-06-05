package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

public class SwordHit extends Weapon {

    private static final float SCALE = 0.85f;

    private static final float INTITAL_FRICTION = 0f;
    private static final float INTITIAL_SPEED = 0f;

    private int damageCount = 0;

    public SwordHit(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.box, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
        init(mapLayer, player);
        spawn(player.getViewDirection());
    }

    @Override
    protected void spawn(Direction direction) {
        this.direction = direction;

        float x = player.getPositionX();
        float y = player .getPositionY();

        switch (direction) {
            case DOWN:
                rotation = 270;
                x -= player.origin.x/2;
                y -= player.bounds.height/2;
                velocity.set(0, -INTITIAL_SPEED);
                break;
            case LEFT:
                rotation = 180;
                x -= player.bounds.width;
                y += 5;
                velocity.set(-INTITIAL_SPEED, 0);
                break;
            case RIGHT:
                x += player.bounds.width/2;
                y += 5;
                velocity.set(INTITIAL_SPEED, 0);
                break;
            case UP:
                rotation = 90;
                x -= player.origin.x/2;
                y += player.bounds.height/1.5;
                velocity.set(0, INTITIAL_SPEED);
                break;
            default:
                break;
        }
        setPosition(
                x,
                y);
        delay();
    }

    @Override
    public void attack(Damageable damageable) {
        float knockbackSpeed = 200f;
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
        if(damageCount==2){
            destroy();
        }
    }

    public void delay() {
        float delay = 0.1f; // seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                destroy();
            }
        }, delay);
    }

    public void debug(ShapeRenderer renderer) {
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void write(Json json) {
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
    }
}
