package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum EnemyState implements State<Enemy> {
    WANDER {
        @Override
        public void update(Enemy entity) {
            if (entity.isPlayerInRange())
                entity.getStateMachine().changeState(RUN_TO_PLAYER);
        }
    },
    RUN_TO_PLAYER {
        @Override
        public void update(Enemy entity) {
            entity.runToPlayer();
        }
    },
    DIE {
        @Override
        public void enter(Enemy entity) {
            entity.die();
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
