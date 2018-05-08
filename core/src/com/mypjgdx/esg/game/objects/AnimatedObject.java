package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Comparator;

public abstract class AnimatedObject<E extends Enum<E>> extends AbstractGameObject {

    private static final float START_TIME = 0;

    private ObjectMap<E, Animation<TextureRegion>> animations;
    private E currentAnimation;

    private Array<AtlasRegion> regions;
    private TextureRegion currentRegion;

    private float animationTime;
    private boolean freeze;

    public AnimatedObject(TextureAtlas atlas) {
        animations = new ObjectMap<E, Animation<TextureRegion>>();

        regions = new Array<AtlasRegion>(atlas.getRegions());
        regions.sort(new RegionComparator());

        resetAnimation();
        unFreezeAnimation();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateAnimation();
        updateKeyFrame(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        render(batch, currentRegion);
    }

    protected void addLoopAnimation(E name, float frameTime, int regionStart, int size) {
        addAnimation(name, frameTime, regionStart, size, PlayMode.LOOP);
    }

    protected void addNormalAnimation(E name, float frameTime, int regionStart, int size) {
        addAnimation(name, frameTime, regionStart, size, PlayMode.NORMAL);
    }

    protected void addAnimation(E name, float frameTime, int regionStart, int size, PlayMode mode) {
        Array<AtlasRegion> animationRegions = new Array<AtlasRegion>();
        animationRegions.addAll(regions, regionStart, size);
        animations.put(name, new Animation<TextureRegion>(frameTime, animationRegions, mode));
    }

    protected abstract void updateAnimation();

    protected void freezeAnimation() {
        freeze = true;
    }

    protected void unFreezeAnimation() {
        freeze = false;
    }

    protected void resetAnimation() {
        animationTime = 0.0f;
    }

    protected void setCurrentAnimation(E name) {
        currentAnimation = name;
    }

    protected boolean isAnimationFinished(E name) {
        return animations.get(name).isAnimationFinished(animationTime);
    }

    protected int getKeyFrameIndex(E name) {
        return animations.get(name).getKeyFrameIndex(animationTime);
    }

    private void updateKeyFrame(float deltaTime) {
        if (!freeze) animationTime += deltaTime;

        currentRegion = animations.get(currentAnimation).getKeyFrame(animationTime);
        setDimension(currentRegion.getRegionWidth(), currentRegion.getRegionHeight());
    }

    protected void updateBounds() {
        updateKeyFrame(START_TIME);
        setPosition(getPositionX(), getPositionY());
    }

    private static class RegionComparator implements Comparator<AtlasRegion> {
        @Override
        public int compare(AtlasRegion region1, AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

}
