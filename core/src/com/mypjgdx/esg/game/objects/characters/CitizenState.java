package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum CitizenState implements State<Citizen> {

    IDLE,
    RUN_TO_ITEM {
        @Override
        public void update(Citizen entity) {
            entity.runToItem();
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
