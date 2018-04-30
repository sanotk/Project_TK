package com.mypjgdx.esg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum MyCitizenState implements State<MyCitizen> {
    WANDER {
        @Override
        public void enter(MyCitizen entity) {
            Gdx.app.log("enter", "WANDER");
        }

        @Override
        public void update(MyCitizen entity) {
            Gdx.app.log("update", "WANDER");
        }

        @Override
        public void exit(MyCitizen entity) {
            Gdx.app.log("exit", "WANDER");
        }

        @Override
        public boolean onMessage(MyCitizen entity, Telegram telegram) {
            return false;
        }
    },
    RUN_TO_ITEM {
        @Override
        public void enter(MyCitizen entity) {
            Gdx.app.log("enter", "RUN_TO_ITEM");
        }

        @Override
        public void update(MyCitizen entity) {
            Gdx.app.log("update", "RUN_TO_ITEM");
        }

        @Override
        public void exit(MyCitizen entity) {
            Gdx.app.log("exit", "RUN_TO_ITEM");
        }

        @Override
        public boolean onMessage(MyCitizen entity, Telegram telegram) {
            return false;
        }
    },
    DIE {
        @Override
        public void enter(MyCitizen entity) {
            Gdx.app.log("enter", "DIE");
        }

        @Override
        public void update(MyCitizen entity) {
            Gdx.app.log("update", "DIE");
        }

        @Override
        public void exit(MyCitizen entity) {
            Gdx.app.log("exit", "DIE");
        }

        @Override
        public boolean onMessage(MyCitizen entity, Telegram telegram) {
            return false;
        }
    };

}
