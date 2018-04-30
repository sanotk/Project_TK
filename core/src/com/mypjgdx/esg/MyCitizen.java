package com.mypjgdx.esg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

public class MyCitizen implements Telegraph {

    DefaultStateMachine<MyCitizen, MyCitizenState> stateMachine;

    public MyCitizen() {
        stateMachine = new DefaultStateMachine<MyCitizen, MyCitizenState>(this);
        stateMachine.setInitialState(MyCitizenState.WANDER);
    }

    public void update() {
        stateMachine.update();
    }

    public DefaultStateMachine<MyCitizen, MyCitizenState> getStateMachine() {
        return stateMachine;
    }

    public void wander() {
        Gdx.app.log("wander", "");
    }

    public void runToItem() {
        Gdx.app.log("runToItem", "");
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
