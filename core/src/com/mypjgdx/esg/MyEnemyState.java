package com.mypjgdx.esg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum MyEnemyState implements State<MyEnemy> {
    WANDER {
        @Override
        public void enter(MyEnemy entity) {
            Gdx.app.log("enter", "WANDER");
        }

        @Override
        public void update(MyEnemy entity) {
            Gdx.app.log("update", "WANDER");
        }

        @Override
        public void exit(MyEnemy entity) {
            Gdx.app.log("exit", "WANDER");
        }
    },
    RUN_TO_PLAYER {
        @Override
        public void enter(MyEnemy entity) {
            Gdx.app.log("enter", "RUN_TO_PLAYER");
        }

        @Override
        public void update(MyEnemy entity) {
            Gdx.app.log("update", "RUN_TO_PLAYER");
        }

        @Override
        public void exit(MyEnemy entity) {
            Gdx.app.log("exit", "RUN_TO_PLAYER");
        }
    },
    DIE {
        @Override
        public void enter(MyEnemy entity) {
            Gdx.app.log("enter", "DIE");
        }

        @Override
        public void update(MyEnemy entity) {
            Gdx.app.log("update", "DIE");
        }

        @Override
        public void exit(MyEnemy entity) {
            Gdx.app.log("exit", "DIE");
        }
    };

    @Override
    public void enter(MyEnemy entity) {}

    @Override
    public void update(MyEnemy entity) {}

    @Override
    public void exit(MyEnemy entity) {}

    @Override
    public boolean onMessage(MyEnemy entity, Telegram telegram) {
        return false;
    }
}
