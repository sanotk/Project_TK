package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

public class Sword extends AnimatedObject {

    private static final float FRAME_DURATION = 0.1f / 2.0f;
    private static final float SCALE = 0.5f;
    private static final float INIT_POSITION_X = 1700f;
    private static final float INIT_POSITION_Y = 950f;

    private Player player;
    private Direction direction;

    public enum SwordAnimation {
        IDLE,
        ATTACK
    }

    public enum SwordState {
        IDLE,
        ATTACK
    }

    public SwordState state;

    public Sword(Player player) {
        super(Assets.instance.sword);

        addLoopAnimation(SwordAnimation.IDLE, FRAME_DURATION, 3, 1);
        addNormalAnimation(SwordAnimation.ATTACK, FRAME_DURATION, 0, 3);

        setPosition(INIT_POSITION_X, INIT_POSITION_Y);

        scale.set(SCALE, SCALE);

        state = SwordState.IDLE;
        setCurrentAnimation(SwordAnimation.IDLE);
        this.player = player;
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (state == SwordState.ATTACK && isAnimationFinished(SwordAnimation.ATTACK)) {
            state = SwordState.IDLE;
        }
        direction = player.getViewDirection();
        switch(direction){
            case DOWN:
                rotation = 0;
                setPosition(player.getPositionX()-player.bounds.width/2+3,player.getPositionY()-player.bounds.height/2+3);
                break;
            case LEFT:
                rotation = 270;
                setPosition(player.getPositionX()-player.bounds.width,player.getPositionY());
                break;
            case RIGHT:
                rotation = 90;
                setPosition(player.getPositionX()+player.bounds.width/2,player.getPositionY());
                break;
            case UP:
                rotation = 180;
                setPosition(player.getPositionX()-player.bounds.width/2,player.getPositionY()+player.bounds.height/2);
                break;
            default:
                break;
        }

    }

    @Override
    protected void setAnimation() {
        unFreezeAnimation();
        switch (state) {
            case IDLE: setCurrentAnimation(SwordAnimation.IDLE); break;
            case ATTACK: setCurrentAnimation(SwordAnimation.ATTACK); break;
            default:
                break;
        }
    }

    public void debug(ShapeRenderer renderer) {
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
