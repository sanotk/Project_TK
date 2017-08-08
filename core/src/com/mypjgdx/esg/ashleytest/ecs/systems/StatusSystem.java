package com.mypjgdx.esg.ashleytest.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.ecs.components.PhysicsComponent;
import com.mypjgdx.esg.ashleytest.ecs.components.StatusComponent;

/**
 *
 * Created by Bill on 9/8/2560.
 */
public class StatusSystem extends IteratingSystem {

    public StatusSystem() {
        super(Family.all(StatusComponent.class, PhysicsComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        PhysicsComponent physics = Mappers.physics.get(entity);
        StatusComponent status = Mappers.status.get(entity);

        long currentTime = TimeUtils.nanoTime();

        if (status.invulnerable && currentTime >= status.invulnerableStopTime)
            status.invulnerable = false;

        if (status.stun && currentTime >= status.stunStopTime)
            status.stun = false;

        if (status.knockback && physics.velocity.isZero()) {
            status.knockback =  false;
        }
    }

    public void damageTo(Entity entity, float damage) {
        StatusComponent status = Mappers.status.get(entity);
        status.health -= damage;
        if (status.health <= 0) {
            status.dead = true;
        }
    }

    public void knockbackTo(Entity entity, float knockbackSpeed, float knockbackAngle) {
        StatusComponent status = Mappers.status.get(entity);
        PhysicsComponent physics = Mappers.physics.get(entity);

        physics.velocity.set(
                knockbackSpeed * MathUtils.cosDeg(knockbackAngle),
                knockbackSpeed * MathUtils.sinDeg(knockbackAngle));
        status.knockback = true;
    }

    public void stunTo(Entity entity, float duration) {
        StatusComponent status = Mappers.status.get(entity);

        final long nanosPerSecond = 1000000000;
        status.stun = true;
        status.stunStopTime = TimeUtils.nanoTime() + (long) (duration * nanosPerSecond);
    }

    public void makeInvulnerableTo(Entity entity, float duration) {
        StatusComponent status = Mappers.status.get(entity);

        final long nanosPerSecond = 1000000000;
        status.invulnerable = true;
        status.invulnerableStopTime = TimeUtils.nanoTime() + (long) (duration * nanosPerSecond);
    }
}
