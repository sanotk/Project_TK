package com.mypjgdx.esg.ashleytest;

import com.badlogic.ashley.core.ComponentMapper;
import com.mypjgdx.esg.ashleytest.components.*;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class Mappers {

    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<AnimatorComponent> animator = ComponentMapper.getFor(AnimatorComponent.class);
    public static final ComponentMapper<CameraHelperComponent> cameraHelper = ComponentMapper.getFor(CameraHelperComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<TiledMapComponent> tiledMap = ComponentMapper.getFor(TiledMapComponent.class);

    private Mappers() {
    }
}
