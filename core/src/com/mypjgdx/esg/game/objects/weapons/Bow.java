package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

public abstract class Bow extends AnimatedObject<Bow.BowAnimation>{

    protected static final float FRAME_DURATION = 0.1f / 2.0f;

    protected Player player;
    protected Enemy enemy;
    public Direction direction;
    private boolean destroyed;

    public enum BowAnimation {
        OFF,
        SHOT
    }

    public enum Bowstate {
        OFF,
        SHOT
    }

    public Bowstate state;

    public Bow(TextureAtlas atlas, float scaleX, float scaleY , float P_X , float P_Y) {
        super(atlas);

        addLoopAnimation(BowAnimation.OFF, FRAME_DURATION, 3, 1);
        addNormalAnimation(BowAnimation.SHOT, FRAME_DURATION, 0, 3);

        setPosition(P_X, P_Y);

        scale.set(scaleX, scaleY);
    }

    public void init(TiledMapTileLayer mapLayer, Player player) {
        state = Bowstate.OFF;
        setCurrentAnimation(BowAnimation.OFF);
        this.player = player;
        spawn();
    }

    protected abstract void spawn();

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(state == Bowstate.SHOT){
            if(isAnimationFinished(BowAnimation.SHOT)){
                state = Bowstate.OFF;
            }
        }
        direction = player.getViewDirection();
        switch(direction){
            case DOWN:
                rotation = 0;
                setPosition(player.getPositionX()-player.bounds.width/2+3,player.getPositionY()-player.bounds.height/2+3);
                break;
            case LEFT:
                rotation = 270;
                setPosition(player.getPositionX()-player.bounds.width * 1.3f,player.getPositionY());
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
            case OFF: setCurrentAnimation(BowAnimation.OFF); break;
            case SHOT: setCurrentAnimation(BowAnimation.SHOT); break;
            default:
                break;
        }
    }

    public void debug(ShapeRenderer renderer) {
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public abstract void activate();

    public abstract void attack(Damageable damageable);
}
