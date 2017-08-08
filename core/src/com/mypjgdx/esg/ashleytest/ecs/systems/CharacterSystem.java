package com.mypjgdx.esg.ashleytest.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.ecs.components.CharacterComponent;
import com.mypjgdx.esg.ashleytest.ecs.components.PhysicsComponent;
import com.mypjgdx.esg.utils.Direction;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class CharacterSystem extends IteratingSystem {

    public CharacterSystem() {
        super(Family.all(CharacterComponent.class, PhysicsComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void move(Entity entity, Direction direction) {
        PhysicsComponent physics = Mappers.physics.get(entity);
        CharacterComponent character = Mappers.character.get(entity);

        switch (direction) {
            case LEFT:
                physics.velocity.x = -character.movingSpeed;
                break;
            case RIGHT:
                physics.velocity.x = character.movingSpeed;
                break;
            case DOWN:
                physics.velocity.y = -character.movingSpeed;
                break;
            case UP:
                physics.velocity.y = character.movingSpeed;
                break;
            default:
                break;
        }
        character.viewDirection = direction;
        physics.velocity.setLength(character.movingSpeed);
    }
}
