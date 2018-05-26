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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Enemy.EnemyAnimation;
import com.mypjgdx.esg.game.objects.weapons.Weapon;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.Distance;
import com.mypjgdx.esg.utils.GameMap;
import com.mypjgdx.esg.utils.Node;

import java.util.List;

public abstract class Enemy extends AnimatedObject<EnemyAnimation> implements Damageable, Json.Serializable {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 8.0f;

    private static final float INITIAL_FRICTION = 600f;
    private static final float INITIAL_FINDING_RANGE = 400f;
    public boolean link;

    protected Player player;

    public enum EnemyAnimation {
        WALK_LEFT,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_UP,
    }

    public enum EnemyType {
        PEPO,
        PEPO_KNIGHT,
        PEPO_DEVIL
    }

    private Rectangle walkingBounds = new Rectangle();

    public EnemyType type;
    private Direction viewDirection;

    public boolean dead;
    public boolean count = false;
    private boolean knockback;
    private boolean stun;
    private boolean attacktime;
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

    public DefaultStateMachine<Enemy, EnemyState> stateMachine;
    private Color color = Color.WHITE;

    private Node startNode;
    private Node endNode;
    private GraphPath<Node> path;

    public boolean running;

    public Enemy(TextureAtlas atlas, float scaleX, float scaleY, TiledMapTileLayer mapLayer) {
        super(atlas);

        addLoopAnimation(EnemyAnimation.WALK_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(EnemyAnimation.WALK_DOWN, FRAME_DURATION, 3, 3);
        addLoopAnimation(EnemyAnimation.WALK_LEFT, FRAME_DURATION, 6, 3);
        addLoopAnimation(EnemyAnimation.WALK_RIGHT, FRAME_DURATION, 9, 3);

        findingRange = INITIAL_FINDING_RANGE;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        scale.set(scaleX, scaleY);

        stateMachine = new DefaultStateMachine<Enemy, EnemyState>(this);
    }

    public void init(TiledMapTileLayer mapLayer) {
        collisionCheck = new TiledCollisionCheck(walkingBounds, mapLayer);
        gameMap = new GameMap(mapLayer);
        pathFinder = new IndexedAStarPathFinder<Node>(gameMap);

        this.mapLayer = mapLayer;

        setCurrentAnimation(EnemyAnimation.WALK_DOWN);
        viewDirection = Direction.DOWN;

        health = maxHealth;
        dead = false;
        knockback = false;
        stun = false;
        attacktime = false;
        TellMeByType();
        randomPosition(mapLayer);
        stateMachine.setInitialState(EnemyState.WANDER);
    }

    @Override
    protected void setAnimation() {
        unFreezeAnimation();
        switch (viewDirection) {
            case DOWN:
                setCurrentAnimation(EnemyAnimation.WALK_DOWN);
                break;
            case LEFT:
                setCurrentAnimation(EnemyAnimation.WALK_LEFT);
                break;
            case RIGHT:
                setCurrentAnimation(EnemyAnimation.WALK_RIGHT);
                break;
            case UP:
                setCurrentAnimation(EnemyAnimation.WALK_UP);
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
            attackPlayer();
        }
        for (Weapon w : weapons) {
            if (bounds.overlaps(w.bounds) && !w.isDestroyed()) {
                w.attack(this);
            }
        }
        if (!player.timeStop) {
            stateMachine.update();
        }
    }

    public boolean isAlive() {
        return !dead;
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

    public void takeKnockback(float knockbackSpeed, float knockbackAngle) {
        velocity.set(
                knockbackSpeed * MathUtils.cosDeg(knockbackAngle),
                knockbackSpeed * MathUtils.sinDeg(knockbackAngle));

        knockback = true;
    }

    public void takeStun(long duration) {
        stun = true;
        lastStunTime = TimeUtils.nanoTime();
        stunTime = TimeUtils.millisToNanos(duration);
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

    private void findPathPlayer() {
        final float startX = walkingBounds.x + walkingBounds.width / 2;
        final float startY = walkingBounds.y + walkingBounds.height / 2;
        final float goalX = player.walkingBounds.x + player.walkingBounds.width / 2;
        final float goalY = player.walkingBounds.y + player.walkingBounds.height / 2;

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
    }

    private boolean near(float value1, float value2) {
        return Math.abs(value1 - value2) < 1f;
    }


    public void runToPlayer() {
        if (path == null) {
            findPathPlayer();
            running = true;
        }
        if (running && !knockback) {
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
        }else {
            findPathPlayer();
            running = true;
        }
    }

    public void showHp(ShapeRenderer shapeRenderer) {
        if (stateMachine.getCurrentState() != EnemyState.DIE) {
            if (health != maxHealth) {
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.rect(getPositionX(), getPositionY() - 10, bounds.width, 5);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(
                        getPositionX(), getPositionY() - 10,
                        bounds.width * ((float) health / maxHealth), 5);
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void render(SpriteBatch batch) {
        Color oldColor = batch.getColor();
        batch.setColor(color);
        super.render(batch);
        batch.setColor(oldColor);
    }

    public void debug(ShapeRenderer renderer) {
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
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
    public boolean takeDamage(float damage, float knockbackSpeed, float knockbackAngle) {
        if ((health <= 0) && (!stateMachine.isInState(EnemyState.DIE))) {
            stateMachine.changeState(EnemyState.DIE);
            die();
            return true;
        } else if (stateMachine.isInState(EnemyState.DIE)) return true;
        health -= damage;
        takeKnockback(knockbackSpeed, knockbackAngle);
        return true;
    }

    @Override
    public Direction getViewDirection() {
        return viewDirection;
    }

    public void attackPlayer() {
        if (stateMachine.getCurrentState() == EnemyState.DIE) {
            return;
        }
        float ydiff = player.bounds.y + player.bounds.height / 2 - bounds.y - bounds.height / 2;
        float xdiff = player.bounds.x + player.bounds.width / 2 - bounds.x - bounds.width / 2;
        float angle = MathUtils.atan2(ydiff, xdiff) * MathUtils.radiansToDegrees;
        float knockbackSpeed = 130 + movingSpeed * 1.2f;

        if (player.takeDamage(1, knockbackSpeed, angle)) {
            takeKnockback(100, angle + 180);
        }
    }

    public DefaultStateMachine getStateMachine() {
        return stateMachine;
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(getPositionX(), getPositionY());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        walkingBounds.set(position.x, position.y, dimension.x, dimension.y - 50);
    }

    @Override
    public void write(Json json) {
        json.writeValue("position", position);
        json.writeValue("type", type);
        json.writeValue("viewDirection", viewDirection);
        json.writeValue("dead", dead);
        json.writeValue("count", count);
        json.writeValue("knockback", knockback);
        json.writeValue("stun", stun);
        json.writeValue("attacktime", attacktime);
        json.writeValue("stunTime", stunTime);
        json.writeValue("health", health);
        json.writeValue("maxHealth", maxHealth);
        json.writeValue("movingSpeed", movingSpeed);
        json.writeValue("findingRange", findingRange);
        json.writeValue("stateMachine", stateMachine.getCurrentState());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {

        JsonValue positionJson = jsonData.get("position");
        setPosition(positionJson.getFloat("x"), positionJson.getFloat("y"));

        type = EnemyType.valueOf(jsonData.getString("type"));
        viewDirection = Direction.valueOf(jsonData.getString("viewDirection"));
        dead = jsonData.getBoolean("dead");
        count = jsonData.getBoolean("count");
        knockback = jsonData.getBoolean("knockback");
        stun = jsonData.getBoolean("stun");
        attacktime = jsonData.getBoolean("attacktime");
        stunTime = jsonData.getInt("stunTime");
        health = jsonData.getInt("health");
        maxHealth = jsonData.getInt("maxHealth");
        movingSpeed = jsonData.getInt("movingSpeed" );
        findingRange = jsonData.getFloat("findingRange");
        stateMachine.changeState(EnemyState.valueOf(jsonData.getString("stateMachine")));
    }
}