package com.mypjgdx.esg.game.objects;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

// TODO
/** คลาสนี้อยู่ระหว่างการพัฒนา*/

public class AnimatedObject2  extends AbstractGameObject {


    private ObjectMap<String, Animation> animations;

    private TextureAtlas atlas;
    private TextureRegion currentRegion;
    private String currentAnimation;

    private Array<AtlasRegion> regions;

    private float animationTime;
    private boolean freeze;

    public AnimatedObject2(TextureAtlas atlas) {
        this.atlas = atlas;
        init();
    }

    private void init() {
        animations = new ObjectMap<String, Animation>();
        regions = new Array<AtlasRegion>(atlas.getRegions());
        regions.sort(new regionComparator());

        currentAnimation = "";
        resetAnimation();
        unFreezeAnimation();
    }

    public void addAnimation(String animationName, int regionStart, int size) {
        Array<AtlasRegion> animationRegions = new Array<AtlasRegion>();
        animationRegions.addAll(regions, regionStart, size);
    }

    @Override
    public void render(SpriteBatch batch) {
        render(batch, currentRegion);
    }

    protected void freezeAnimation() {
        freeze = true;
    }
    protected void unFreezeAnimation() {
        freeze = false;
    }

    protected void resetAnimation() {
        animationTime = 0.0f;
    }

    public void setAnimation(String animationName) {
        currentAnimation = animationName;
    }

    protected void updateKeyFrame(float deltaTime) {
        if (currentAnimation.equals("")) return;
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
