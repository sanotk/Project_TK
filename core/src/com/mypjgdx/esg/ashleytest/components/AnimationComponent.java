package com.mypjgdx.esg.ashleytest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class AnimationComponent<E> implements Component {

    public ObjectMap<E, Animation<TextureRegion>> animations;
    public E currentAnimation;

    public Array<TextureAtlas.AtlasRegion> regions;
    public TextureRegion currentRegion;

    public float animationTime;
    public boolean freeze;
}
