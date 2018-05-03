package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.Distance;
import com.mypjgdx.esg.utils.GameMap;
import com.mypjgdx.esg.utils.Node;

public abstract class Citizen extends AnimatedObject<Citizen.CitizenAnimation> {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    private static final float INITIAL_FRICTION = 600f;
    private static final float INITIAL_FINDING_RANGE = 400f;

    protected Player player;

    public enum CitizenAnimation {
        WALK_LEFT,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_UP,
    }

    public enum CitizenType {
        CITIZEN_1,
        CITIZEN_2,
        CITIZEN_3,
        CITIZEN_4,
        CITIZEN_5,
        CITIZEN_6
    }

    private Rectangle walkingBounds = new Rectangle();

    public boolean quest = false;

    public CitizenType type;
    private Direction viewDirection;

    private boolean knockback;
    private TiledMapTileLayer mapLayer;

    abstract void TellMeByType();

    protected float movingSpeed;
    private float findingRange;
    private IndexedAStarPathFinder<Node> pathFinder;
    private GameMap gameMap;

    private DefaultStateMachine<Citizen, CitizenState> stateMachine;
    protected Color color = Color.WHITE;

    private Node startNode;
    private Node endNode;
    private GraphPath<Node> path;


    private float itemGoalX;
    private float itemGoalY;

    private Node walkingNode; //TODO

    protected Item goalItem;
    public boolean itemOn;

    public Citizen(TextureAtlas atlas, float scaleX, float scaleY, TiledMapTileLayer mapLayer) {
        super(atlas);

        addLoopAnimation(CitizenAnimation.WALK_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(CitizenAnimation.WALK_DOWN, FRAME_DURATION, 3, 3);
        addLoopAnimation(CitizenAnimation.WALK_LEFT, FRAME_DURATION, 6, 3);
        addLoopAnimation(CitizenAnimation.WALK_RIGHT, FRAME_DURATION, 9, 3);

        findingRange = INITIAL_FINDING_RANGE;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        scale.set(scaleX, scaleY);

        stateMachine = new DefaultStateMachine<Citizen, CitizenState>(this);
    }

    public void init(TiledMapTileLayer mapLayer) {
        collisionCheck = new TiledCollisionCheck(walkingBounds, mapLayer);
        gameMap = new GameMap(mapLayer);
        pathFinder = new IndexedAStarPathFinder<Node>(gameMap);

        this.mapLayer = mapLayer;

        setCurrentAnimation(CitizenAnimation.WALK_DOWN);
        viewDirection = Direction.DOWN;

        TellMeByType();
        randomPosition(mapLayer);
        stateMachine.setInitialState(CitizenState.WANDER);
    }

    @Override
    protected void setAnimation() {
        unFreezeAnimation();
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
            freezeAnimation();
            resetAnimation();
        }
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        updateStatus();

        if (player.status_citizen) {
            //attackPlayer();
        }

        if (!player.timeStop) {
            stateMachine.update();
        }
    }

    public void move(Direction direction) {
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
    }

    public void die() {
        color = Color.GRAY;
    }

    public void setColor() {
       // color = Color.RED;
    }

    public void runToItem() {
        final float startX = bounds.x + bounds.width / 2;
        final float startY = bounds.y + bounds.height / 2;

        GraphPath<Node> pathOutput = new DefaultGraphPath<Node>();
        Heuristic<Node> heuristic = new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                return 1;
            }
        };

        gameMap.updateNeighbors(); //TODO
        startNode = gameMap.getNode(startX, startY);
        endNode =gameMap.getNode(goalItem.getGoalX(), goalItem.getGoalY());

        itemGoalX = goalItem.getGoalX();
        itemGoalY = goalItem.getGoalY();

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);
        path = pathOutput;

        if (pathOutput.getCount() > 1) {
            Node node = pathOutput.get(1);
            walkingNode = node;

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
        if(startNode == endNode){
            itemOn = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Color oldColor = batch.getColor();
        batch.setColor(color);
        super.render(batch);
        batch.setColor(oldColor);
    }

    public void debug(ShapeRenderer renderer) {
        if (walkingNode != null) {
            renderer.setColor(Color.BLACK);
            renderer.circle(walkingNode.getCenterPositionX(), walkingNode.getCenterPositionY(), 5);
            renderer.setColor(Color.CYAN);
            renderer.circle(itemGoalX, itemGoalY, 5);
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
        } while ((Distance.absoluteXY(this, player) < MIN_DISTANCE)
                || collisionCheck.isCollidesTop()
                || collisionCheck.isCollidesBottom()
                || collisionCheck.isCollidesRight()
                || collisionCheck.isCollidesLeft());
    }

    public Direction getViewDirection() {
        return viewDirection;
    }

    public DefaultStateMachine getStateMachine() {
        return stateMachine;
    }

    public Vector2 getPosition() {
        return new Vector2(getPositionX(), getPositionY());
    }

    public void setGoalItem(Item goalItem) {
        this.goalItem = goalItem;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        walkingBounds.set(position.x, position.y, dimension.x, dimension.y - 50);
    }

}
