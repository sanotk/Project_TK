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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.ui.Range;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.GameMap;
import com.mypjgdx.esg.utils.Node;

public abstract class Citizen extends AnimatedObject<Citizen.CitizenAnimation> implements Json.Serializable {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    private static final float INITIAL_FRICTION = 600f;

    private static final float INITIAL_POSITION_X = 100;
    private static final float INITIAL_POSITION_Y = 1000;

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

    private TiledMapTileLayer mapLayer;

    abstract void TellMeByType();

    protected float movingSpeed;
    private IndexedAStarPathFinder<Node> pathFinder;
    private GameMap gameMap;

    private DefaultStateMachine<Citizen, CitizenState> stateMachine;

    private Node startNode;
    private Node endNode;
    protected GraphPath<Node> path;

    private boolean running;

    private float itemGoalX;
    private float itemGoalY;

    private Node walkingNode; //TODO

    protected Item goalItem;
    public boolean itemOn;
    public boolean toGoal;

    protected float positionGoalX;
    protected float positionGoalY;

    public boolean overlapPlayer;
    private boolean rangeAdd;
    private float range;
    public boolean runPlayer;

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
        setPosition(INITIAL_POSITION_X,INITIAL_POSITION_Y);
        //randomPosition(mapLayer);
        stateMachine.setInitialState(CitizenState.RUN_TO_GOAL);
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

        if (player.bounds.overlaps(bounds)){
            overlapPlayer = true;
        } else {
            overlapPlayer = false;
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

    private void findPathItem() {
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



    private void findPathGoal() {
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
        endNode = gameMap.getNode(positionGoalX, positionGoalY);

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);
        path = pathOutput;
    }

    private void findPathPlayer() {

        if(!rangeAdd){
            rangeAdd = true;
            Range.instance.rangeToPlayer+=50;
            range = Range.instance.rangeToPlayer;
        }

        final float startX = walkingBounds.x + walkingBounds.width / 2;
        final float startY = walkingBounds.y + walkingBounds.height / 2;
        final float goalX = player.walkingBounds.x + player.walkingBounds.width / 2;
        final float goalY = player.walkingBounds.y + player.walkingBounds.height / 2 - range;

        GraphPath<Node> pathOutput = new DefaultGraphPath<Node>();
        Heuristic<Node> heuristic = new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                return 1;
            }
        };

        gameMap.updateNeighbors();
        startNode = gameMap.getNode(startX, startY);
        endNode = gameMap.getNode(goalX, goalY);

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);
        path = pathOutput;
    }

    private boolean near(float value1, float value2) {
        return Math.abs(value1 - value2) < 1f;
    }

    public void runToItem() {
        if (path == null) {
            findPathItem();
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
            findPathItem();
            running = true;
        }
        if (path.getCount() <= 1) {
            itemOn = true;
        }
    }

    public void runToGoal() {
        if (path == null) {
            findPathGoal();
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
            findPathGoal();
            running = true;
        }
        if (path.getCount() <= 1) {
            toGoal = true;
        }
    }

    public void runToPlayer() {
        if (path == null) {
            findPathPlayer();
            running = true;
        }
        if (running) {
            if (path.getCount() > 1) {
                Node node = path.get(1);

                float xdiff = node.getCenterPositionX() - walkingBounds.x - walkingBounds.width / 2;
                float ydiff = node.getCenterPositionY() - walkingBounds.y - walkingBounds.height / 2;

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
                if (near(node.getCenterPositionX(), walkingBounds.x + walkingBounds.width / 2)
                        && near(node.getCenterPositionY(), walkingBounds.y + walkingBounds.height / 2)) {
                    running = false;
                }
            } else {
                running = false;
            }
        } else {
            findPathPlayer();
            running = true;
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

        path = null;
    }

    public Item getGoalItem() {
        return goalItem;
    }
}
