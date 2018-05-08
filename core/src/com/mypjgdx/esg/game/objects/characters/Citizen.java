package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.utils.*;

public class Citizen extends AnimatedObject<Citizen.CitizenAnimation> {

    private static final float FRAME_DURATION = 1.0f / 8.0f;

    private static final float INITIAL_FRICTION = 600f;
    private static final float MOVING_SPEED = 60f;

    public enum CitizenAnimation {
        WALK_LEFT,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_UP,
    }

    private Direction viewDirection;
    private GameMap gameMap;

    private DefaultStateMachine<Citizen, CitizenState> stateMachine;

    private Item targetItem;
    private Player player;

    public Citizen(TextureAtlas atlas, float scale) {
        super(atlas);

        addLoopAnimation(CitizenAnimation.WALK_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(CitizenAnimation.WALK_DOWN, FRAME_DURATION, 3, 3);
        addLoopAnimation(CitizenAnimation.WALK_LEFT, FRAME_DURATION, 6, 3);
        addLoopAnimation(CitizenAnimation.WALK_RIGHT, FRAME_DURATION, 9, 3);

        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        this.scale.set(scale, scale);

        stateMachine = new DefaultStateMachine<Citizen, CitizenState>(this);
    }

    public void init(TiledMapTileLayer mapLayer, Player player) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        gameMap = new GameMap(mapLayer);

        setCurrentAnimation(CitizenAnimation.WALK_DOWN);
        updateBounds();
        viewDirection = Direction.DOWN;

        this.player = player;
        randomPosition(mapLayer);
        stateMachine.setInitialState(CitizenState.IDLE);
    }

    @Override
    protected void updateAnimation() {
        switch (viewDirection) {
            case DOWN:
                setCurrentAnimation(CitizenAnimation.WALK_DOWN);
                break;
            case LEFT:
                setCurrentAnimation(CitizenAnimation.WALK_LEFT);
                break;
            case RIGHT:
                setCurrentAnimation(CitizenAnimation.WALK_RIGHT);
                break;
            case UP:
                setCurrentAnimation(CitizenAnimation.WALK_UP);
                break;
            default:
                break;
        }
        if (velocity.x == 0 && velocity.y == 0) {
            resetAnimation();
        }
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        stateMachine.update();
    }

    public void move(Direction direction) {
        switch (direction) {
            case LEFT:
                velocity.x = -MOVING_SPEED;
                break;
            case RIGHT:
                velocity.x = MOVING_SPEED;
                break;
            case DOWN:
                velocity.y = -MOVING_SPEED;
                break;
            case UP:
                velocity.y = MOVING_SPEED;
                break;
            default:
                break;
        }
        viewDirection = direction;
        velocity.setLength(MOVING_SPEED);
    }

    public void runToItem() {
        final float startX = bounds.x + bounds.width / 2;
        final float startY = bounds.y + bounds.height / 2;
        final float endX = targetItem.bounds.x + targetItem.bounds.width / 2;
        final float endY = targetItem.bounds.y + targetItem.bounds.height / 2;

        //TODO: need to refactor
        gameMap.updateNeighbors();
        Pathfinder.instance.setGameMap(gameMap);
        GraphPath<Node> nodePath = Pathfinder.instance.findNodePath(startX, startY, endX, endY);

        if (nodePath.getCount() > 1) {
            Node node = nodePath.get(1);

            float dx = node.getCenterPositionX() - bounds.x - bounds.width / 2;
            float dy = node.getCenterPositionY() - bounds.y - bounds.height / 2;

            final float minMovingDistance = MOVING_SPEED / 8;

            if (dy > minMovingDistance) {
                move(Direction.UP);
            } else if (dy < -minMovingDistance) {
                move(Direction.DOWN);
            }

            if (dx > minMovingDistance) {
                move(Direction.RIGHT);
            } else if (dx < -minMovingDistance) {
                move(Direction.LEFT);
            }
        }
    }

    private void randomPosition(TiledMapTileLayer mapLayer) {
        updateBounds();

        float mapWidth = mapLayer.getTileWidth() * mapLayer.getWidth();
        float mapHeight = mapLayer.getTileHeight() * mapLayer.getHeight();

        final float MIN_DISTANCE = 200;
        do {
            setPosition(
                    MathUtils.random(MIN_DISTANCE, mapWidth - bounds.width),
                    MathUtils.random(MIN_DISTANCE, mapHeight - bounds.height));
        } while ((Distance.manhattan(this, player) < MIN_DISTANCE)
                || collisionCheck.isCollidesTop()
                || collisionCheck.isCollidesBottom()
                || collisionCheck.isCollidesRight()
                || collisionCheck.isCollidesLeft());
    }

    public Direction getViewDirection() {
        return viewDirection;
    }

    public DefaultStateMachine<Citizen, CitizenState> getStateMachine() {
        return stateMachine;
    }

    public void setTargetItem(Item targetItem) {
        this.targetItem = targetItem;
    }


}
