package com.mypjgdx.esg.ashleytest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.ashleytest.ecs.components.*;
import com.mypjgdx.esg.ashleytest.ecs.systems.AnimatorSystem;
import com.mypjgdx.esg.collision.check.TiledCollisionCheck;
import com.mypjgdx.esg.collision.respone.BlockCollisionResponse;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.Pathfinding;

/**
 *
 * Created by Bill on 8/8/2560.
 */
public class EntityBuilder {

    private EntityBuilder() {
    }

    public static Entity buildPlayer(TiledMapTileLayer mapLayer, float x, float y) {
        final float playerFriction = 500f;
        final float playerMovingSpeed = 120f;
        final float playerFrameDuration = 1.0f / 16.0f;

        Entity playerEntity = new Entity();

        TransformComponent transform = new TransformComponent();
        PhysicsComponent physics = new PhysicsComponent();
        SpriteComponent sprite = new SpriteComponent();
        AnimatorComponent animator = new AnimatorComponent();
        CharacterComponent character = new CharacterComponent();
        PlayerComponent player = new PlayerComponent();

        playerEntity.add(transform);
        playerEntity.add(physics);
        playerEntity.add(sprite);
        playerEntity.add(animator);
        playerEntity.add(character);
        playerEntity.add(player);

        transform.position.set(x, y);

        physics.friction = new Vector2(playerFriction, playerFriction);
        physics.bounds.width = Assets.instance.playerAltas.getRegions().get(0).getRegionWidth();
        physics.bounds.height = Assets.instance.playerAltas.getRegions().get(0).getRegionHeight();
        physics.collisionCheck = new TiledCollisionCheck(physics.bounds, mapLayer);
        physics.collisionResponse = new BlockCollisionResponse();

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

        animator.currentAnimation = Player.PlayerAnimation.STAND_UP;

        character.movingSpeed = playerMovingSpeed;
        character.viewDirection = Direction.UP;

        player.state = Player.PlayerState.STANDING;

        return playerEntity;
    }

    public static Entity buildPepo(TiledMapTileLayer mapLayer, Entity player, float x, float y) {
        final float pepoFriction = 500f;
        final float pepoMovingSpeed = 120f;
        final float pepoFrameDuration = 1.0f / 8.0f;
        final float pepoFindingRange = 400f;

        Entity pepo = new Entity();

        TransformComponent transform = new TransformComponent();
        PhysicsComponent physics = new PhysicsComponent();
        SpriteComponent sprite = new SpriteComponent();
        AnimatorComponent animator = new AnimatorComponent();
        CharacterComponent character = new CharacterComponent();
        StatusComponent status = new StatusComponent();
        EnemyComponent enemy = new EnemyComponent();

        transform.position.set(x, y);

        physics.friction = new Vector2(pepoFriction, pepoFriction);
        physics.bounds.width = Assets.instance.pepoAltas.getRegions().get(0).getRegionWidth();
        physics.bounds.height = Assets.instance.pepoAltas.getRegions().get(0).getRegionHeight();
        physics.collisionCheck = new TiledCollisionCheck(physics.bounds, mapLayer);
        physics.collisionResponse = new BlockCollisionResponse();

        pepo.add(transform);
        pepo.add(physics);
        pepo.add(sprite);
        pepo.add(animator);
        pepo.add(character);
        pepo.add(status);
        pepo.add(enemy);

        AnimatorSystem.setAtlasTo(animator, Assets.instance.pepoAltas);
        AnimatorSystem.addLoopTo(animator, Enemy.EnemyAnimation.WALK_UP, pepoFrameDuration, 0, 3);
        AnimatorSystem.addLoopTo(animator, Enemy.EnemyAnimation.WALK_DOWN, pepoFrameDuration, 3, 3);
        AnimatorSystem.addLoopTo(animator, Enemy.EnemyAnimation.WALK_LEFT, pepoFrameDuration, 6, 3);
        AnimatorSystem.addLoopTo(animator, Enemy.EnemyAnimation.WALK_RIGHT, pepoFrameDuration, 9, 3);

        animator.currentAnimation = Enemy.EnemyAnimation.WALK_DOWN;

        character.movingSpeed = pepoMovingSpeed;
        character.viewDirection = Direction.DOWN;

        enemy.player = player;
        enemy.pathFinding = new Pathfinding(mapLayer);
        enemy.findingRange = pepoFindingRange;

        return pepo;
    }

    public static Entity buildCameraHelper(OrthographicCamera camera, TiledMapTileLayer mapLayer, Entity target) {
        Entity cameraHelperEntity = new Entity();
        cameraHelperEntity.add(new TransformComponent());

        CameraHelperComponent cameraHelper = new CameraHelperComponent();
        cameraHelperEntity.add(cameraHelper);

        cameraHelper.speed = 0.05f;
        cameraHelper.target = target;
        cameraHelper.camera = camera;
        cameraHelper.leftMost = mapLayer.getTileWidth() * 1;
        cameraHelper.rightMost = (mapLayer.getWidth() - 1) * mapLayer.getTileWidth();
        cameraHelper.bottomMost = mapLayer.getTileHeight() * 1;
        cameraHelper.topMost = (mapLayer.getHeight() - 1) * mapLayer.getTileHeight();

        return cameraHelperEntity;
    }

    public static Entity buildMap(TiledMap tiledMap) {
        Entity map = new Entity();
        TiledMapComponent tiledMapComponent = new TiledMapComponent();
        tiledMapComponent.map = tiledMap;
        map.add(tiledMapComponent);

        return map;
    }
}
