package com.mypjgdx.esg.ashleytest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.ashleytest.components.*;
import com.mypjgdx.esg.ashleytest.systems.AnimatorSystem;
import com.mypjgdx.esg.collision.check.TiledCollisionCheck;
import com.mypjgdx.esg.collision.respone.BlockCollisionResponse;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;

/**
 * Created by Bill on 8/8/2560.
 */
public class EntityBuilder {

    private EntityBuilder() {
    }

    public static Entity buildPlayer(TiledMapTileLayer mapLayer) {
        final float playerFriction = 500f;
        final float playerMovingSpeed = 120f;
        final float playerFrameDuration = 1.0f / 16.0f;

        Entity player = new Entity();

        TransformComponent transform = new TransformComponent();
        PhysicsComponent physics = new PhysicsComponent();
        SpriteComponent sprite = new SpriteComponent();
        PlayerComponent playerComponent = new PlayerComponent();
        AnimatorComponent animator = new AnimatorComponent();

        player.add(transform);
        player.add(physics);
        player.add(sprite);
        player.add(playerComponent);
        player.add(animator);

        transform.position.set(100, 100);

        physics.friction = new Vector2(playerFriction, playerFriction);
        physics.bounds.width = Assets.instance.playerAltas.getRegions().get(0).getRegionWidth();
        physics.bounds.height = Assets.instance.playerAltas.getRegions().get(0).getRegionHeight();
        physics.collisionCheck = new TiledCollisionCheck(player.getComponent(PhysicsComponent.class).bounds, mapLayer);
        physics.collisionResponse = new BlockCollisionResponse();

        playerComponent.movingSpeed = playerMovingSpeed;

        AnimatorSystem.setAtlasTo(animator, Assets.instance.playerAltas);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.STAND_UP, playerFrameDuration, 120, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.STAND_DOWN, playerFrameDuration, 112, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.STAND_LEFT, playerFrameDuration, 128, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.STAND_RIGHT, playerFrameDuration, 136, 8);
        AnimatorSystem.addNormalTo(animator, Player.PlayerAnimation.ATK_LEFT, playerFrameDuration, 32, 8);
        AnimatorSystem.addNormalTo(animator, Player.PlayerAnimation.ATK_RIGHT, playerFrameDuration, 40, 8);
        AnimatorSystem.addNormalTo(animator, Player.PlayerAnimation.ATK_UP, playerFrameDuration, 24, 8);
        AnimatorSystem.addNormalTo(animator, Player.PlayerAnimation.ATK_DOWN, playerFrameDuration, 16, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.WALK_UP, playerFrameDuration, 8, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.WALK_DOWN, playerFrameDuration, 0, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.WALK_LEFT, playerFrameDuration, 144, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.WALK_RIGHT, playerFrameDuration, 152, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.ITEM_UP, playerFrameDuration, 56, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.ITEM_DOWN, playerFrameDuration, 48, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.ITEM_LEFT, playerFrameDuration, 64, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.ITEM_RIGHT, playerFrameDuration, 72, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.ITEM_STAND_UP, playerFrameDuration, 88, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.ITEM_STAND_DOWN, playerFrameDuration, 80, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.ITEM_STAND_LEFT, playerFrameDuration, 96, 8);
        AnimatorSystem.addLoopTo(animator, Player.PlayerAnimation.ITEM_STAND_RIGHT, playerFrameDuration, 104, 8);

        playerComponent.state = Player.PlayerState.STANDING;
        animator.currentAnimation = Player.PlayerAnimation.STAND_UP;
        playerComponent.viewDirection = Direction.UP;

        return player;
    }

    public static Entity buildCameraHelper(OrthographicCamera camera, TiledMapTileLayer mapLayer, Entity target) {
        Entity cameraHelper = new Entity();
        cameraHelper.add(new TransformComponent());

        CameraHelperComponent cameraHelperComponent = new CameraHelperComponent();
        cameraHelper.add(cameraHelperComponent);

        cameraHelperComponent.target = target;
        cameraHelperComponent.camera = camera;
        cameraHelperComponent.leftMost = mapLayer.getTileWidth() * 1;
        cameraHelperComponent.rightMost = (mapLayer.getWidth() - 1) * mapLayer.getTileWidth();
        cameraHelperComponent.bottomMost = mapLayer.getTileHeight() * 1;
        cameraHelperComponent.topMost = (mapLayer.getHeight() - 1) * mapLayer.getTileHeight();

        return cameraHelper;
    }

    public static Entity buildMap(TiledMap tiledMap) {
        Entity map = new Entity();
        TiledMapComponent tiledMapComponent = new TiledMapComponent();
        tiledMapComponent.map = tiledMap;
        map.add(tiledMapComponent);

        return map;
    }
}
