package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

import java.util.List;

public class Trap extends Weapon implements Damageable {

    private static final float SCALE = 0.75f;

    private static final float INTITAL_FRICTION = 50f;
    private static final float INTITIAL_SPEED = 50f;
    private int damageCount = 0;
    private List<Weapon> weapons;

    Trap() {
        super(Assets.instance.trap, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
    }

    public Trap(TiledMapTileLayer mapLayer, Player player, List<Weapon> weapons) {
        super(Assets.instance.trap, SCALE, SCALE, INTITAL_FRICTION, INTITAL_FRICTION);
        this.weapons = weapons;
        init(mapLayer, player);
        spawn(player.getViewDirection());
    }

    @Override
    protected void spawn(Direction direction) {
        this.direction = direction;

        setPosition(
                player.getPositionX() + player.origin.x - origin.x,
                player.getPositionY() + player.origin.y - 9);

        switch (direction) {
            case DOWN:
                rotation = 90;
                velocity.set(0, -INTITIAL_SPEED);
                break;
            case LEFT:
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
        float knockbackSpeed = 500f;
        switch (damageable.getViewDirection()) {
            case DOWN:
                damageable.takeDamage(1, knockbackSpeed, 90);
                break;
            case LEFT:
                damageable.takeDamage(1, knockbackSpeed, 0);
                break;
            case RIGHT:
                damageable.takeDamage(1, knockbackSpeed, 180);
                break;
            case UP:
                damageable.takeDamage(1, knockbackSpeed, 270);
                break;
            default:
                break;
        }
        addDamageCount();
    }

    private void addDamageCount() {
        this.damageCount += 1;
        if (damageCount == 3) {
            destroy();
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (player.getSword().attackAreaOverlaps(bounds)) {
            player.getSword().damageTo(this);
        }
    }

    @Override
    public boolean takeDamage(float damage, float knockbackSpeed, float knockbackAngle) {
        addDamageCount();
        return true;
    }

    @Override
    public Direction getViewDirection() {
        return player.getViewDirection();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("damageCount", damageCount);
        json.writeValue("type", WeaponSpawner.TRAP);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        damageCount = jsonData.getInt("damageCount");
    }
}
