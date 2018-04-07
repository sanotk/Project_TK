package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum EnemyState implements State<Enemy> {
    WANDER {
        @Override
        public void enter(Enemy entity) {
            Gdx.app.log("enter", "WANDER");
        }

        @Override
        public void update(Enemy entity) {
            if (entity.isPlayerInRange())
                entity.getStateMachine().changeState(RUN_TO_PLAYER);
        }

        @Override
        public void exit(Enemy entity) {
            Gdx.app.log("exit", "WANDER");
        }
    },
    RUN_TO_PLAYER {
        @Override
        public void enter(Enemy entity) {
            Gdx.app.log("enter", "RUN_TO_PLAYER");
        }

        @Override
        public void update(Enemy entity) {
            entity.runToPlayer();
        }

        @Override
        public void exit(Enemy entity) {
            Gdx.app.log("exit", "RUN_TO_PLAYER");
        }
    },
    DIE {
        @Override
        public void enter(Enemy entity) {
            Gdx.app.log("enter", "DIE");
            entity.die();
        }

        @Override
        public void update(Enemy entity) {
        }

        @Override
        public void exit(Enemy entity) {
            Gdx.app.log("exit", "DIE");
        }
    };

    @Override
    public void enter(Enemy entity) {

    }

    @Override
    public void update(Enemy entity) {

    }

    @Override
    public void exit(Enemy entity) {

    }

    @Override
    public boolean onMessage(Enemy entity, Telegram telegram) {
        return false;
    }
}
