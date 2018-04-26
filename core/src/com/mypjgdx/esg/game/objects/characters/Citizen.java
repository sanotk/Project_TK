package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.weapons.Weapon;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.Distance;
import com.mypjgdx.esg.utils.GameMap;
import com.mypjgdx.esg.utils.Node;

import java.util.List;

public abstract class Citizen extends AnimatedObject<Citizen.CitizenAnimation> implements Damageable {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    private static final float INITIAL_FRICTION = 600f;
    private static final float INITIAL_FINDING_RANGE = 400f;

    protected Player player;

    public enum CitizenAnimation {
        WALK_LEFT,
        WALK_RIGHT,
        WALK_UP,
        STAND_LEFT,
        STAND_RIGHT,
        STAND_UP
    }

    public enum CitizenType {
        Citizen1,
        Citizen2,
        Citizen3,
        Citizen4,
        Citizen5,
        Citizen6
    }

    public CitizenType type;
    private Direction viewDirection;

    public boolean quest;
    public int questCount;
    private boolean knockback;
    private boolean stun;
    private TiledMapTileLayer mapLayer;

    abstract void TellMeByType();

    private long stunTime;
    private long lastStunTime;

    private int health;
    protected int maxHealth;
    protected float movingSpeed;
    private float findingRange;
    private IndexedAStarPathFinder<Node> pathFinder;
    private GameMap gameMap;

    private DefaultStateMachine<Citizen, CitizenState> stateMachine;
    private Color color = Color.WHITE;

    private Node startNode;
    private Node endNode;
    private GraphPath<Node> path;

    public Citizen(TextureAtlas atlas, float scaleX, float scaleY, TiledMapTileLayer mapLayer) {
        super(atlas);

        addLoopAnimation(CitizenAnimation.WALK_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(CitizenAnimation.WALK_LEFT, FRAME_DURATION, 3, 3);
        addLoopAnimation(CitizenAnimation.WALK_RIGHT, FRAME_DURATION, 6, 3);
        addLoopAnimation(CitizenAnimation.STAND_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(CitizenAnimation.STAND_LEFT, FRAME_DURATION, 3, 3);
        addLoopAnimation(CitizenAnimation.STAND_RIGHT, FRAME_DURATION, 6, 3);

        findingRange = INITIAL_FINDING_RANGE;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        scale.set(scaleX, scaleY);

        stateMachine = new DefaultStateMachine<Citizen, CitizenState>(this);
    }

    public void init(TiledMapTileLayer mapLayer) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        gameMap = new GameMap(mapLayer);
        pathFinder = new IndexedAStarPathFinder<Node>(gameMap);

        this.mapLayer = mapLayer;

        questCount = 0;
        setCurrentAnimation(CitizenAnimation.STAND_UP);
        viewDirection = Direction.DOWN;

        health = maxHealth;
        quest = false;
        knockback = false;
        stun = false;
        TellMeByType();
        randomPosition(mapLayer);
        stateMachine.setInitialState(CitizenState.WANDER);
    }

    @Override
    protected void setAnimation() {
        unFreezeAnimation();
        switch (viewDirection) {
            case DOWN:
                setCurrentAnimation(CitizenAnimation.WALK_UP);
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
            freezeAnimation();
            resetAnimation();
        }
    }


    public void update(float deltaTime, List<Weapon> weapons) {
        super.update(deltaTime);
        updateStatus();

        if (bounds.overlaps(player.bounds)) {
            quest = true;
        }

        if (type == CitizenType.Citizen1 && quest == true && questCount == 0) {
            runToPlayer();
            questCount = questCount + 1;
        } else if (type == CitizenType.Citizen2 && quest == true && questCount == 1) {
            runToPlayer();
            quest = false;
            questCount = questCount + 1;
        } else if (type == CitizenType.Citizen3 && quest == true && questCount == 2) {
            runToPlayer();
            quest = false;
            questCount = questCount + 1;
        } else if (type == CitizenType.Citizen4 && quest == true && questCount == 3) {
            runToPlayer();
            quest = false;
            questCount = questCount + 1;
        } else if (type == CitizenType.Citizen5 && quest == true && questCount == 4) {
            runToPlayer();
            quest = false;
            questCount = questCount + 1;
        } else if (type == CitizenType.Citizen6 && quest == true && questCount == 5) {
            runToPlayer();
            quest = false;
            questCount = questCount + 1;
        }

        if (!player.timeStop) {
            stateMachine.update();
        }
    }

    public void move(Direction direction) {
        if (knockback || stun) return;
        switch (direction) {
            case LEFT:
                velocity.x = -movingSpeed;
                break;
            case RIGHT:
                velocity.x = movingSpeed;
                break;
            case DOWN:
                velocity.y = -movingSpeed;
                break;
            case UP:
                velocity.y = movingSpeed;
                break;
            default:
                break;
        }
        viewDirection = direction;
        velocity.setLength(movingSpeed);
    }

    private void updateStatus() {
        if (knockback && velocity.isZero()) {
            knockback = false;
        }
        if (stun && TimeUtils.nanoTime() - lastStunTime > stunTime)
            stun = false;
    }

    public boolean isPlayerInRange() {
        final float startX = bounds.x + bounds.width / 2;
        final float startY = bounds.y + bounds.height / 2;
        final float goalX = player.bounds.x + player.bounds.width / 2;
        final float goalY = player.bounds.y + player.bounds.height / 2;
        final float xDiff = startX - goalX;
        final float yDiff = startY - goalY;
        final double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

        return distance <= findingRange;
    }

    public void runToPlayer() {
        final float startX = bounds.x + bounds.width / 2;
        final float startY = bounds.y + bounds.height / 2;
        final float goalX = player.bounds.x + player.bounds.width / 2;
        final float goalY = player.bounds.y + player.bounds.height / 2;

        GraphPath<Node> pathOutput = new DefaultGraphPath<Node>();
        Heuristic<Node> heuristic = new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                return 1;
            }
        };

        gameMap.updateNeighbors(); //TODO
        startNode = gameMap.getNode(startX, startY);
        endNode = gameMap.getNode(goalX, goalY);

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);
        path = pathOutput;

        if (pathOutput.getCount() > 1) {
            Node node = pathOutput.get(1);

            float xdiff = node.getCenterPositionX() - bounds.x - bounds.width / 2;
            float ydiff = node.getCenterPositionY() - bounds.y - bounds.height / 2;

            final float minMovingDistance = movingSpeed / 8;

            if (ydiff > minMovingDistance) {
                move(Direction.UP);
            } else if (ydiff < -minMovingDistance) {
                move(Direction.DOWN);
            }

            if (xdiff > minMovingDistance) {
                move(Direction.RIGHT);
            } else if (xdiff < -minMovingDistance) {
                move(Direction.LEFT);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Color oldColor = batch.getColor();
        batch.setColor(color);
        super.render(batch);
        //batch.setColor(oldColor);
    }

    public void die() {
        color = Color.GRAY;
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
        } while ((Distance.absoluteXY(this, player) < MIN_DISTANCE)
                || collisionCheck.isCollidesTop()
                || collisionCheck.isCollidesBottom()
                || collisionCheck.isCollidesRight()
                || collisionCheck.isCollidesLeft());
    }

    @Override
    public Direction getViewDirection() {
        return viewDirection;
    }

    public DefaultStateMachine getStateMachine() {
        return stateMachine;
    }
}
