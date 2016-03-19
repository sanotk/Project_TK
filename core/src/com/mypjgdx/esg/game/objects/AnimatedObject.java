package com.mypjgdx.esg.game.objects;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

// TODO
/** คลาสนี้อยู่ระหว่างการพัฒนา*/

public abstract class AnimatedObject  extends AbstractGameObject {

    private ObjectMap<AnimationName, Animation> animations;

    private TextureAtlas atlas;
    private TextureRegion currentRegion;
    private AnimationName currentAnimation;

    private Array<AtlasRegion> regions;

    private float animationTime;
    private boolean freeze;

    public static enum AnimationName {
        WALK_LEFT,
        WALK_RIGHT,
        WALK_UP,
        WALK_DOWN,
        ATK_LEFT,
        ATK_RIGHT
    }

    public enum ViewDirection {
        LEFT, RIGHT, UP, DOWN
    }

    protected ViewDirection viewDirection;

    public AnimatedObject(TextureAtlas atlas) {
        this.atlas = atlas;
        init();
    }

    private void init() {
        animations = new ObjectMap<AnimationName, Animation>();
        regions = new Array<AtlasRegion>(atlas.getRegions());
        regions.sort(new regionComparator());

        currentAnimation = AnimationName.WALK_DOWN;
        viewDirection = ViewDirection.DOWN;

        resetAnimation();
        unFreezeAnimation();
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
        setAnimation();
        updateKeyFrame(deltaTime);
        updateViewDirection();
    }


    @Override
    public void render(SpriteBatch batch) {
        render(batch, currentRegion);
    }

    protected void updateViewDirection() { // update ทิศที่ player มองอยู่  โดยยึดการมองด้านแกน X  เป็นหลักหากมีการเดินเฉียง
        if (velocity.x != 0) {
            viewDirection = velocity.x < 0 ?  ViewDirection.LEFT : ViewDirection.RIGHT;
        }
        else if (velocity.y != 0) {
            viewDirection = velocity.y < 0 ?  ViewDirection.DOWN : ViewDirection.UP;
        }
    }

    public ViewDirection getViewDirection(){
        return viewDirection;
    }

    protected void addLoopAnimation(AnimationName name, float frameTime, int regionStart, int size) {
        addAnimation(name, frameTime, regionStart, size, PlayMode.LOOP);
    }

    protected void addNormalAnimation(AnimationName name, float frameTime, int regionStart, int size) {
        addAnimation(name, frameTime, regionStart, size, PlayMode.NORMAL);
    }

    protected void addAnimation(AnimationName name, float frameTime, int regionStart, int size, PlayMode mode) {
        Array<AtlasRegion> animationRegions = new Array<AtlasRegion>();
        animationRegions.addAll(regions, regionStart, size);
        animations.put(name, new Animation(frameTime, animationRegions, mode));
    }

    protected abstract void setAnimation ();

    protected void freezeAnimation() {
        freeze = true;
    }
    protected void unFreezeAnimation() {
        freeze = false;
    }

    protected void resetAnimation() {
        animationTime = 0.0f;
    }

    public void setCurrentAnimation(AnimationName name) {
        currentAnimation = name;
    }

    public boolean isAnimationFinished(AnimationName name) {
        return animations.get(name).isAnimationFinished(animationTime);
    }

    protected void updateKeyFrame(float deltaTime) {
        if (!freeze) animationTime += deltaTime;

        currentRegion = animations.get(currentAnimation).getKeyFrame(animationTime);
        setDimension(currentRegion.getRegionWidth(), currentRegion.getRegionHeight());
    }

    private static class regionComparator implements Comparator<AtlasRegion> {
        @Override
        public int compare(AtlasRegion region1, AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

}