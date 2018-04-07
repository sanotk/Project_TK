package com.mypjgdx.esg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

public class MyEnemy implements Telegraph {

    DefaultStateMachine<MyEnemy, MyEnemyState> stateMachine;

    public MyEnemy() {
        stateMachine = new DefaultStateMachine<MyEnemy, MyEnemyState>(this);
        stateMachine.setInitialState(MyEnemyState.WANDER);
    }

    public void update() {
        stateMachine.update();
    }

    public DefaultStateMachine<MyEnemy, MyEnemyState> getStateMachine() {
        return stateMachine;
    }

    public void wander() {
        Gdx.app.log("wander", "");
    }

    public void runToPlayer() {
        Gdx.app.log("runToPlayer", "");
    }

    public void die() {
        Gdx.app.log("die", "");
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        Gdx.app.log("handleMessage", "" + msg);
        Gdx.app.log("sender", "" + msg.sender);
        Gdx.app.log("receiver", "" + msg.receiver);
        Gdx.app.log("message", "" + msg.message);
        Gdx.app.log("extraInfo", "" + msg.extraInfo);
        Gdx.app.log("returnReceiptStatus", "" + msg.returnReceiptStatus);
        return false;
    }
}
