package com.mypjgdx.esg.game.objects;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimatedObject extends AbstractGameObject {

    private Animation walkLeft;
    private Animation walkRight;
    private Animation walkUp;
    private Animation walkDown;

    private TextureAtlas atlas;
    private TextureRegion region;

    private int framePerDirection;
    private float frameDuration;

    private float animationTime;

    public enum ViewDirection {
        LEFT, RIGHT, UP, DOWN
    }

    private ViewDirection viewDirection;

    public AnimatedObject(TextureAtlas atlas, int framePerDirection,  float frameDuration) {
        this.atlas = atlas;
        this.framePerDirection = framePerDirection;
        this.frameDuration = frameDuration;
        init();
    }

    private void init() {

        Array<AtlasRegion> regions = new Array<AtlasRegion>(atlas.getRegions());
        regions.sort(new regionComparator());

        Array<AtlasRegion> walkLeftRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> walkRightRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> walkDownRegions = new Array<AtlasRegion>();
        Array<AtlasRegion> walkUpRegions = new Array<AtlasRegion>();

        walkUpRegions.addAll(regions, 0 * framePerDirection,  framePerDirection);
        walkDownRegions.addAll(regions, 1 * framePerDirection, framePerDirection);
        walkLeftRegions.addAll(regions, 2 * framePerDirection, framePerDirection);
        walkRightRegions.addAll(regions, 3 * framePerDirection, framePerDirection);

        walkLeft = new Animation(frameDuration, walkLeftRegions, PlayMode.LOOP);
        walkRight = new Animation(frameDuration, walkRightRegions, PlayMode.LOOP);
        walkDown = new Animation(frameDuration, walkDownRegions, PlayMode.LOOP);
        walkUp = new Animation(frameDuration, walkUpRegions, PlayMode.LOOP);

        viewDirection = ViewDirection.DOWN;
        animationTime = 0.0f;
    }

    @Override
    public void render(SpriteBatch batch) {
        render(batch, region);
    }

    protected void updateViewDirection() {
        if (velocity.x != 0) {
            viewDirection = velocity.x < 0 ?  ViewDirection.LEFT : ViewDirection.RIGHT;
        }
        else if (velocity.y != 0) {
            viewDirection = velocity.y < 0 ?  ViewDirection.DOWN : ViewDirection.UP;
        }
    }

    protected void updateKeyFrame(float deltaTime) {

        if (velocity.x != 0 || velocity.y != 0) animationTime += deltaTime;
        else  animationTime = 0;

        switch(viewDirection) {
        case DOWN: region = walkDown.getKeyFrame(animationTime); break;
        case LEFT: region = walkLeft.getKeyFrame(animationTime); break;
        case RIGHT: region = walkRight.getKeyFrame(animationTime); break;
        case UP: region = walkUp.getKeyFrame(animationTime); break;
        default:
            break;
        }

        setDimension(region.getRegionWidth(), region.getRegionHeight());
    }

    private static class regionComparator implements Comparator<AtlasRegion> {
        @Override
        public int compare(AtlasRegion region1, AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

}
