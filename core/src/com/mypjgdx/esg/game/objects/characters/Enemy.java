package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Enemy.EnemyAnimation;
import com.mypjgdx.esg.game.objects.weapons.Weapon;
import com.mypjgdx.esg.ui.EnergyBar;
import com.mypjgdx.esg.utils.*;

public class Enemy extends AnimatedObject<EnemyAnimation> implements Damageable {

    private static final float FRAME_DURATION = 1.0f / 8.0f;

    private static final float INITIAL_FRICTION = 600f;
    private static final float INITIAL_FINDING_RANGE = 400f;

    public enum EnemyAnimation {
        WALK_LEFT,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_UP,
    }

    private Direction viewDirection;
    private boolean knockback;
    private int health;
    private final int maxHealth;
    private final float movingSpeed;
    private float findingRange;

    private GameMap gameMap;
    private Player player;

    private DefaultStateMachine<Enemy, EnemyState> stateMachine;

    private Array<Weapon> weapons;
    private Array<Enemy> enemies;

    public Enemy(TextureAtlas atlas, float scale, int maxHealth, float movingSpeed) {
        super(atlas);
        this.maxHealth = maxHealth;
        this.movingSpeed = movingSpeed;

        addLoopAnimation(EnemyAnimation.WALK_UP, FRAME_DURATION, 0, 3);
        addLoopAnimation(EnemyAnimation.WALK_DOWN, FRAME_DURATION, 3, 3);
        addLoopAnimation(EnemyAnimation.WALK_LEFT, FRAME_DURATION, 6, 3);
        addLoopAnimation(EnemyAnimation.WALK_RIGHT, FRAME_DURATION, 9, 3);

        findingRange = INITIAL_FINDING_RANGE;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        this.scale.set(scale, scale);

        stateMachine = new DefaultStateMachine<Enemy, EnemyState>(this);
    }

    public void init(TiledMapTileLayer mapLayer, Player player) {
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        gameMap = new GameMap(mapLayer);
        this.player = player;

        setCurrentAnimation(EnemyAnimation.WALK_DOWN);
        updateBounds();
        viewDirection = Direction.DOWN;

        health = maxHealth;
        randomPosition(mapLayer);
        stateMachine.setInitialState(EnemyState.WANDER);
    }

    @Override
    protected void updateAnimation() {
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


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateStatus();

        if (bounds.overlaps(player.bounds)) {
            attackPlayer();
        }
        for (int i = 0; i < weapons.size; i++) {
            Weapon weapon = weapons.get(i);
            if (bounds.overlaps(weapon.bounds)) {
                weapon.attack(this);
            }
        }
        if (player.getSword().attackAreaOverlaps(bounds)) {
            player.getSword().attack(this);
        }
        stateMachine.update();
    }

    public void move(Direction direction) {
        if (knockback) return;
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

    private void updateStatus() {
        if (knockback && velocity.isZero()) {
            knockback = false;
        }
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
        final float endX = player.bounds.x + player.bounds.width / 2;
        final float endY = player.bounds.y + player.bounds.height / 2;

        //TODO: need to refactor
        gameMap.updateNeighbors();
        Pathfinder.instance.setGameMap(gameMap);
        GraphPath<Node> nodePath = Pathfinder.instance.findNodePath(startX, startY, endX, endY);

        if (nodePath.getCount() > 1) {
            Node node = nodePath.get(1);

            float dx = node.getCenterPositionX() - bounds.x - bounds.width / 2;
            float dy = node.getCenterPositionY() - bounds.y - bounds.height / 2;

            final float minMovingDistance = movingSpeed / 8;

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

    public void showHp(ShapeRenderer shapeRenderer) {
        if (health <= 0) return;

        if (health != maxHealth) {
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(getPositionX(), getPositionY() - 10, bounds.width, 5);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(
                    getPositionX(), getPositionY() - 10,
                    bounds.width * ((float) health / maxHealth), 5);
        }
    }

    public void die() {
        enemies.removeValue(this, true);
        EnergyBar.instance.energy += 2;
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

    @Override
    public boolean takeDamage(float damage, float knockbackSpeed, float knockbackAngle) {
        if (!stateMachine.isInState(EnemyState.DIE)) {
            health -= damage;
            takeKnockback(knockbackSpeed, knockbackAngle);
            if (health <= 0) {
                health = 0;
                stateMachine.changeState(EnemyState.DIE);
            }
        }
        return true;
    }

    public Direction getViewDirection() {
        return viewDirection;
    }

    public void attackPlayer() {
        float dy = player.bounds.y + player.bounds.height / 2 - bounds.y - bounds.height / 2;
        float dx = player.bounds.x + player.bounds.width / 2 - bounds.x - bounds.width / 2;
        float angle = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
        float knockbackSpeed = 130 + movingSpeed * 1.2f;

        if (player.takeDamage(1, knockbackSpeed, angle)) {
            takeKnockback(100, angle + 180);
        }
    }

    public DefaultStateMachine<Enemy, EnemyState> getStateMachine() {
        return stateMachine;
    }

    public void setWeapons(Array<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void setEnemies(Array<Enemy> enemies) {
        this.enemies = enemies;
    }
}
