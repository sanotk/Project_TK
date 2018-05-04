package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.Distance;
import com.mypjgdx.esg.utils.GameMap;
import com.mypjgdx.esg.utils.Node;

public abstract class Citizen extends AnimatedObject<Citizen.CitizenAnimation> implements Json.Serializable {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    private static final float INITIAL_FRICTION = 600f;

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
    private IndexedAStarPathFinder<Node> pathFinder;
    private GameMap gameMap;

    private DefaultStateMachine<Citizen, CitizenState> stateMachine;

    private Node startNode;
    private Node endNode;
    private GraphPath<Node> path;

    private boolean running;

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

    public void setColor() {
        // color = Color.RED;
    }

    private void findPath() {
        final float startX = bounds.x + bounds.width / 2;
        final float startY = bounds.y;

        GraphPath<Node> pathOutput = new DefaultGraphPath<Node>();
        Heuristic<Node> heuristic = new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                return 1;
            }
        };

        gameMap.updateNeighbors(); //TODO
        startNode = gameMap.getNode(startX, startY);
        endNode = gameMap.getNode(goalItem.getGoalX(), goalItem.getGoalY());

        itemGoalX = goalItem.getGoalX();
        itemGoalY = goalItem.getGoalY();

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);
        path = pathOutput;
    }

    private boolean near(float value1, float value2) {
        return Math.abs(value1 - value2) < 1f;
    }

    public void runToItem() {
        if (path == null) {
            findPath();
            running = true;
        }
        if (running) {
            if (path.getCount() > 1) {
                Node node = path.get(1);
                walkingNode = node;

                float xdiff = node.getCenterPositionX() - bounds.x - bounds.width / 2;
                float ydiff = node.getCenterPositionY() - bounds.y;

                final float minMovingDistance = 1f;

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

                if (near(path.get(1).getCenterPositionX(), bounds.x + bounds.width / 2)
                        && near(path.get(1).getCenterPositionY(), bounds.y) ) {
                    running = false;
                }
            }

        } else {
            findPath();
            running = true;
        }
        if (startNode == endNode) {
            itemOn = true;
        }
    }

    public void debug(ShapeRenderer renderer) {
        if (walkingNode != null) {
            renderer.setColor(Color.BLACK);
            renderer.circle(walkingNode.getCenterPositionX(), walkingNode.getCenterPositionY(), 5);
            renderer.setColor(Color.CYAN);
            renderer.circle(itemGoalX, itemGoalY, 5);
            renderer.setColor(Color.FOREST);
            if (path != null) {
                for (Node node : path) {
                    renderer.circle(node.getCenterPositionX(), node.getCenterPositionY(), 5);
                }
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

    @Override
    public void write(Json json) {
        json.writeValue("position", position);
        json.writeValue("running", running);
        json.writeValue("quest", quest);
        json.writeValue("type", type);
        json.writeValue("viewDirection", viewDirection);
        json.writeValue("movingSpeed", movingSpeed);
        json.writeValue("stateMachine", stateMachine.getCurrentState());
        json.writeValue("running", running);
//        json.writeValue("goalItem", goalItem); // TODO
        json.writeValue("itemOn", itemOn);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue positionJson = jsonData.get("position");
        setPosition(positionJson.getFloat("x"), positionJson.getFloat("y"));

        quest = jsonData.getBoolean("quest");
        type = CitizenType.valueOf(jsonData.getString("type"));
        viewDirection = Direction.valueOf(jsonData.getString("viewDirection"));
        movingSpeed = jsonData.getInt("movingSpeed");
        stateMachine.changeState(CitizenState.valueOf(jsonData.getString("stateMachine")));
        running = jsonData.getBoolean("running");
        itemOn = jsonData.getBoolean("itemOn");
    }
}
