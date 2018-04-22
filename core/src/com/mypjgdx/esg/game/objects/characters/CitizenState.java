package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum CitizenState implements State<Citizen> {
    WANDER {
        @Override
        public void enter(Citizen entity) {
            Gdx.app.log("enter", "WANDER");
        }

        @Override
        public void update(Citizen entity) {
            if (entity.isPlayerInRange())
                entity.getStateMachine().changeState(RUN_TO_PLAYER);
        }

        @Override
        public void exit(Citizen entity) {
            Gdx.app.log("exit", "WANDER");
        }
    },
    RUN_TO_PLAYER {
        @Override
        public void enter(Citizen entity) {
            Gdx.app.log("enter", "RUN_TO_PLAYER");
        }

        @Override
        public void update(Citizen entity) {
            entity.runToPlayer();
        }

        @Override
        public void exit(Citizen entity) {
            Gdx.app.log("exit", "RUN_TO_PLAYER");
        }
    },
    DIE {
        @Override
        public void enter(Citizen entity) {
            Gdx.app.log("enter", "DIE");
            entity.die();
        }

        @Override
        public void update(Citizen entity) {
        }

        @Override
        public void exit(Citizen entity) {
            Gdx.app.log("exit", "DIE");
        }
    };

    @Override
    public void enter(Citizen entity) {

    }

    @Override
    public void update(Citizen entity) {

    }

    @Override
    public void exit(Citizen entity) {

    }

    @Override
    public boolean onMessage(Citizen entity, Telegram telegram) {
        return false;
    }
}
