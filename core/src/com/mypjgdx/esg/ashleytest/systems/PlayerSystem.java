package com.mypjgdx.esg.ashleytest.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.components.AnimatorComponent;
import com.mypjgdx.esg.ashleytest.components.PhysicsComponent;
import com.mypjgdx.esg.ashleytest.components.PlayerComponent;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class PlayerSystem extends IteratingSystem {

    public PlayerSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = Mappers.player.get(entity);
        AnimatorComponent animator = Mappers.animator.get(entity);
        PhysicsComponent physics = Mappers.physics.get(entity);

        if (playerComponent.state == Player.PlayerState.STANDING && physics.velocity.isZero()) {
            if (playerComponent.item == null) {
                setStandAnimation(playerComponent, animator);
            } else {
                setCarryItemStandAnimation(playerComponent, animator);
            }
        } else if (playerComponent.state == Player.PlayerState.ATTACKING && playerComponent.item == null) {
            setAttackAnimation(playerComponent, animator);
        } else if (playerComponent.item != null) {
            setCarryItemAnimation(playerComponent, animator, physics);
        } else {
            setWalkAnimation(playerComponent, animator, physics);
        }
    }

    private void setWalkAnimation(PlayerComponent playerComponent, AnimatorComponent animator, PhysicsComponent physics) {
        switch (playerComponent.viewDirection) {
            case DOWN:
                animator.currentAnimation = Player.PlayerAnimation.WALK_DOWN;
                break;
            case LEFT:
                animator.currentAnimation = Player.PlayerAnimation.WALK_LEFT;
                break;
            case RIGHT:
                animator.currentAnimation = Player.PlayerAnimation.WALK_RIGHT;
                break;
            case UP:
                animator.currentAnimation = Player.PlayerAnimation.WALK_UP;
                break;
        }
        if (physics.velocity.isZero()) {
            playerComponent.state = Player.PlayerState.STANDING;
            animator.animationTime = 0;
        }
    }

    private void setCarryItemAnimation(PlayerComponent playerComponent, AnimatorComponent animator, PhysicsComponent physics) {
        switch (playerComponent.viewDirection) {
            case DOWN:
                animator.currentAnimation = Player.PlayerAnimation.ITEM_DOWN;
                break;
            case LEFT:
                animator.currentAnimation = Player.PlayerAnimation.ITEM_LEFT;
                break;
            case RIGHT:
                animator.currentAnimation = Player.PlayerAnimation.ITEM_RIGHT;
                break;
            case UP:
                animator.currentAnimation = Player.PlayerAnimation.ITEM_UP;
                break;
        }
        if (physics.velocity.isZero()) {
            animator.freeze = true;
            animator.animationTime = 0;
        }
    }

    private void setAttackAnimation(PlayerComponent playerComponent, AnimatorComponent animator) {
        switch (playerComponent.viewDirection) {
            case DOWN:
                animator.currentAnimation = Player.PlayerAnimation.ATK_DOWN;
                break;
            case LEFT:
                animator.currentAnimation = Player.PlayerAnimation.ATK_LEFT;
                break;
            case RIGHT:
                animator.currentAnimation = Player.PlayerAnimation.ATK_RIGHT;
                break;
            case UP:
                animator.currentAnimation = Player.PlayerAnimation.ATK_UP;
                break;
        }
        if (AnimatorSystem.isFinished(animator, Player.PlayerAnimation.ATK_LEFT)
                || AnimatorSystem.isFinished(animator, Player.PlayerAnimation.ATK_RIGHT)
                || AnimatorSystem.isFinished(animator, Player.PlayerAnimation.ATK_UP)
                || AnimatorSystem.isFinished(animator, Player.PlayerAnimation.ATK_DOWN)) {
            playerComponent.state = Player.PlayerState.STANDING;
            animator.animationTime = 0;
        }
    }

    private void setCarryItemStandAnimation(PlayerComponent playerComponent, AnimatorComponent animator) {
        switch (playerComponent.viewDirection) {
            case DOWN:
                animator.currentAnimation = Player.PlayerAnimation.ITEM_STAND_DOWN;
                break;
            case LEFT:
                animator.currentAnimation = Player.PlayerAnimation.ITEM_STAND_LEFT;
                break;
            case RIGHT:
                animator.currentAnimation = Player.PlayerAnimation.ITEM_STAND_RIGHT;
                break;
            case UP:
                animator.currentAnimation = Player.PlayerAnimation.ITEM_STAND_UP;
                break;
        }
    }

    private void setStandAnimation(PlayerComponent playerComponent, AnimatorComponent animator) {
        switch (playerComponent.viewDirection) {
            case DOWN:
                animator.currentAnimation = Player.PlayerAnimation.STAND_DOWN;
                break;
            case LEFT:
                animator.currentAnimation = Player.PlayerAnimation.STAND_LEFT;
                break;
            case RIGHT:
                animator.currentAnimation = Player.PlayerAnimation.STAND_RIGHT;
                break;
            case UP:
                animator.currentAnimation = Player.PlayerAnimation.STAND_UP;
                break;
        }
    }

    public void move(Entity entity, Direction direction) {
        PhysicsComponent physics = Mappers.physics.get(entity);
        PlayerComponent player = Mappers.player.get(entity);

        switch (direction) {
            case LEFT:
                physics.velocity.x = -player.movingSpeed;
                break;
            case RIGHT:
                physics.velocity.x = player.movingSpeed;
                break;
            case DOWN:
                physics.velocity.y = -player.movingSpeed;
                break;
            case UP:
                physics.velocity.y = player.movingSpeed;
                break;
            default:
                break;
        }
        player.viewDirection = direction;
        physics.velocity.setLength(player.movingSpeed);
    }
}
