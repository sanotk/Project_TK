package com.mypjgdx.esg.game.objects.weapons;

import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player;


public class Bow extends AnimatedObject<Bow.BowAnimation> {

    private static final float SCALE = 0.5f;
    private static final float FRAME_TIME = 0.1f / 2.0f;

    public enum BowState {
        IDLE,
        FIRE,
    }

    public enum BowAnimation {
        IDLE,
        FIRE,
    }

    private BowState state;
    private Player player;

    public Bow(Player player) {
        super(Assets.instance.bow);
        this.player = player;

        scale.set(SCALE, SCALE);

        addNormalAnimation(BowAnimation.FIRE, FRAME_TIME, 0, 3);
        addNormalAnimation(BowAnimation.IDLE, FRAME_TIME, 3, 1);

        setCurrentAnimation(BowAnimation.IDLE);
        state = BowState.IDLE;
        updateBounds();
    }


    @Override
    public void update(float deltaTime) {
        float positionX = player.getPositionX() + player.bounds.width / 2f;
        float positionY = player.getPositionY();

        switch (player.getViewDirection()) {
            case RIGHT:
                rotation = 90;
                break;
            case UP:
                rotation = 180;
                positionX -= bounds.width / 2f;
                positionY += player.bounds.height / 2f;
                break;
            case LEFT:
                rotation = -90;
                positionX -= bounds.width;
                break;
            case DOWN:
                rotation = 0;
                positionX -= bounds.width / 2f;
                positionY += player.bounds.height / 2f -  bounds.height;
                break;
            default:
        }
        setPosition(positionX, positionY);
        super.update(deltaTime);
    }

    @Override
    protected void updateAnimation() {
        switch (state) {
            case IDLE:
                setCurrentAnimation(BowAnimation.IDLE);
                break;
            case FIRE:
                setCurrentAnimation(BowAnimation.FIRE);
                if (isAnimationFinished(BowAnimation.FIRE)) {
                    state = BowState.IDLE;
                }
                break;
            default:
        }
    }

    public void fire() {
        resetAnimation();
        state = BowState.FIRE;
    }
}
