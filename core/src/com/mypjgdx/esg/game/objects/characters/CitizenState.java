package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum CitizenState implements State<Citizen> {
    WANDER {
        @Override
        public void enter(Citizen entity) {
            Gdx.app.log("enter", "WANDER");
            entity.setColor();
        }

        @Override
        public void update(Citizen entity) {
            if (entity.quest)
                entity.getStateMachine().changeState(RUN_TO_ITEM);
        }

        @Override
        public void exit(Citizen entity) {
            Gdx.app.log("exit", "WANDER");
        }
    },
    RUN_TO_ITEM {
        @Override
        public void enter(Citizen entity) {
            Gdx.app.log("enter", "RUN_TO_ITEM");
        }

        @Override
        public void update(Citizen entity) {
            entity.runToItem();
//            if(entity.itemOn){
//                entity.getStateMachine().changeState(ITEM_ON);
//            }
        }

        @Override
        public void exit(Citizen entity) {
            Gdx.app.log("exit", "RUN_TO_ITEM");
        }
    },
    RUN_TO_GOAL {
        @Override
        public void enter(Citizen entity) {

        }

        @Override
        public void update(Citizen entity) {
            entity.runToGoal();
            if(entity.toGoal){
                entity.getStateMachine().changeState(WANDER);
            }
        }

        @Override
        public void exit(Citizen entity) {
            Gdx.app.log("exit", "RUN_TO_GOAL");
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
