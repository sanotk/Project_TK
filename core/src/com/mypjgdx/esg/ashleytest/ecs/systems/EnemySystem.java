package com.mypjgdx.esg.ashleytest.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.ecs.components.*;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.Pathfinding;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class EnemySystem extends IteratingSystem {

    public EnemySystem() {
        super(Family.all(EnemyComponent.class,
                PhysicsComponent.class,
                AnimatorComponent.class,
                CharacterComponent.class,
                StatusComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsComponent physics = Mappers.physics.get(entity);
        AnimatorComponent animator = Mappers.animator.get(entity);
        CharacterComponent character = Mappers.character.get(entity);
        EnemyComponent enemy = Mappers.enemy.get(entity);
        StatusComponent status = Mappers.status.get(entity);

        setAnimation(physics, character, animator);

        PhysicsComponent playerPhysics = Mappers.physics.get(enemy.player);
        final float enemyX = physics.bounds.x + physics.bounds.width / 2;
        final float enemyY = physics.bounds.y + physics.bounds.height / 2;
        final float playerX = playerPhysics.bounds.x + playerPhysics.bounds.width / 2;
        final float playerY = playerPhysics.bounds.y + playerPhysics.bounds.height / 2;

        if (!status.stun && !status.knockback && isCanSeePlayer(enemyX, enemyY, playerX, playerY, enemy)) {
            runToPlayer(entity, enemyX, enemyY, playerX, playerY, enemy, character);
        }
    }

    private void setAnimation(PhysicsComponent physics, CharacterComponent character, AnimatorComponent animator) {
        animator.freeze = false;
        switch (character.viewDirection) {
            case DOWN:
                animator.currentAnimation = Enemy.EnemyAnimation.WALK_DOWN;
                break;
            case LEFT:
                animator.currentAnimation = Enemy.EnemyAnimation.WALK_LEFT;
                break;
            case RIGHT:
                animator.currentAnimation = Enemy.EnemyAnimation.WALK_RIGHT;
                break;
            case UP:
                animator.currentAnimation = Enemy.EnemyAnimation.WALK_UP;
                break;
        }
        if (physics.velocity.isZero()) {
            animator.freeze = true;
            animator.animationTime = 0;
        }
    }

    private boolean isCanSeePlayer(float enemyX, float enemyY, float playerX, float playerY, EnemyComponent enemy) {
        final float xDiff = enemyX - playerX;
        final float yDiff = enemyY - playerY;
        final double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        return distance <= enemy.findingRange;
    }

    private void runToPlayer(Entity enemyEntity, float enemyX, float enemyY, float playerX, float playerY,
                             EnemyComponent enemy, CharacterComponent character) {
        Array<Pathfinding.Node> list = enemy.pathFinding.findPath(enemyX, enemyY, playerX, playerY);
        if (list.size > 0) {
            Pathfinding.Node nextNode = list.get(0);

            float xDistanceToNextNode = nextNode.getPositionX() - enemyX;
            float yDistanceToNextNode = nextNode.getPositionY() - enemyY;

            final float minMovingDistance = character.movingSpeed / 8;
            final CharacterSystem characterSystem = getEngine().getSystem(CharacterSystem.class);

            if (yDistanceToNextNode > minMovingDistance) {
                characterSystem.move(enemyEntity, Direction.UP);
            } else if (yDistanceToNextNode < -minMovingDistance) {
                characterSystem.move(enemyEntity, Direction.DOWN);
            }

            if (xDistanceToNextNode > minMovingDistance) {
                characterSystem.move(enemyEntity, Direction.RIGHT);
            } else if (xDistanceToNextNode < -minMovingDistance) {
                characterSystem.move(enemyEntity, Direction.LEFT);
            }
        }
    }
}
