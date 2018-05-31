package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.collision.NullCollsionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

public class SwordWave extends Weapon {

    private static final float SCALE = 0.5f;

    private static final float INTITAL_FRICTION = 50f;
    private static final float INTITIAL_SPEED = 400f;

    private int damageCount;
    private float lifeTime = 10;

    SwordWave() {
        super(Assets.instance.wave, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
    }

    public SwordWave(TiledMapTileLayer mapLayer, Player player) {
        super(Assets.instance.wave, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
        init(mapLayer, player);
        spawn(player.getViewDirection());
    }

    @Override
    public void init(TiledMapTileLayer mapLayer, Player player) {
        super.init(mapLayer, player);
        collisionCheck = new NullCollsionCheck();
    }

    @Override
    protected void spawn(Direction direction) {
        this.direction = direction;

        switch (direction) {
            case LEFT:
            case RIGHT:
                setPosition(
                        player.getPositionX(),
                        player.getPositionY());
                break;
            case DOWN:
                setPosition(
                        player.getPositionX() + player.origin.x - origin.x,
                        player.getPositionY() + player.origin.y - player.bounds.height / 2);
                break;
            case UP:
                setPosition(
                        player.getPositionX() + player.origin.x - origin.x,
                        player.getPositionY() + player.origin.y + player.bounds.height / 2);
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
    }


    @Override
    public void attack(Damageable damageable) {
        float knockbackSpeed = 100f;
        if (damageCount < 15) {
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
        this.damageCount += 1;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (lifeTime <= 0)
            destroy();
        lifeTime -= deltaTime;
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

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("damageCount", damageCount);
        json.writeValue("lifeTime", lifeTime);
        json.writeValue("type", WeaponSpawner.SWORD_WAVE);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        damageCount = jsonData.getInt("damageCount");
        lifeTime = jsonData.getFloat("lifeTime");
    }
}
