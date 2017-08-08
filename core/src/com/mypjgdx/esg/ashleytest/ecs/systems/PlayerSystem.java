package com.mypjgdx.esg.ashleytest.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mypjgdx.esg.ashleytest.Mappers;
import com.mypjgdx.esg.ashleytest.ecs.components.AnimatorComponent;
import com.mypjgdx.esg.ashleytest.ecs.components.CharacterComponent;
import com.mypjgdx.esg.ashleytest.ecs.components.PhysicsComponent;
import com.mypjgdx.esg.ashleytest.ecs.components.PlayerComponent;
import com.mypjgdx.esg.game.objects.characters.Player;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class PlayerSystem extends IteratingSystem {

    public PlayerSystem() {
        super(Family.all(PlayerComponent.class,
                PhysicsComponent.class,
                AnimatorComponent.class,
                CharacterComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsComponent physics = Mappers.physics.get(entity);
        AnimatorComponent animator = Mappers.animator.get(entity);
        CharacterComponent character = Mappers.character.get(entity);
        PlayerComponent player = Mappers.player.get(entity);

        if (player.state == Player.PlayerState.STANDING && physics.velocity.isZero()) {
            if (player.item == null) {
                setStandAnimation(character, animator);
            } else {
                setCarryItemStandAnimation(character, animator);
            }
        } else if (player.state == Player.PlayerState.ATTACKING && player.item == null) {
            setAttackAnimation(player, character, animator);
        } else if (player.item != null) {
            setCarryItemAnimation(character, animator, physics);
        } else {
            setWalkAnimation(player, character, animator, physics);
        }
    }

    private void setWalkAnimation(PlayerComponent playerComponent, CharacterComponent characterComponent,
                                  AnimatorComponent animator, PhysicsComponent physics) {
        switch (characterComponent.viewDirection) {
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

    private void setCarryItemAnimation(CharacterComponent characterComponent,
                                       AnimatorComponent animator, PhysicsComponent physics) {
        switch (characterComponent.viewDirection) {
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

    private void setAttackAnimation(PlayerComponent playerComponent, CharacterComponent characterComponent, AnimatorComponent animator) {
        switch (characterComponent.viewDirection) {
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

    private void setCarryItemStandAnimation(CharacterComponent characterComponent, AnimatorComponent animator) {
        switch (characterComponent.viewDirection) {
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

    private void setStandAnimation(CharacterComponent characterComponent, AnimatorComponent animator) {
        switch (characterComponent.viewDirection) {
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
}
