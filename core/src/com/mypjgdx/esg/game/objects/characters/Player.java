package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.collision.TiledCollisionProperty;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.levels.Level;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player.PlayerAnimation;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.*;
import com.mypjgdx.esg.ui.LinkingWindow;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.SoundManager;

public class Player extends AnimatedObject<PlayerAnimation> implements Damageable {

    private static final float FRAME_TIME = 1.0f / 16.0f;

    private static final float SCALE = 0.6f;

    private static final float INITIAL_FRICTION = 500f;
    private static final float INITIAL_MOVING_SPEED = 120f;

    private static final int INTITAL_HEALTH = 10;
    private static final int INTITAL_TRAP = 5;
    private static final int INTITAL_ARROW = 100;
    private static final int INTITAL_SWORD_WAVE = 3;

    public enum PlayerAnimation {
        ATK_LEFT,
        ATK_RIGHT,
        ATK_DOWN,
        ATK_UP,
        STAND_LEFT,
        STAND_RIGHT,
        STAND_DOWN,
        STAND_UP,
        WALK_LEFT,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_UP
    }

    public enum PlayerState {
        STAND, ATTACK
    }

    private Item interactingItem;

    private PlayerState state;
    private int health;
    public int trapCount;
    public int arrowCount;
    public int swordWaveCount;

    private boolean dead;
    private boolean invulnerable;
    private boolean knockback;

    private long lastInvulnerableTime;
    private long invulnerableTime;
    private float movingSpeed;

    private TiledMapTileLayer mapLayer;
    private Direction viewDirection;

    private Level level;

    private Sword sword;
    private Bow bow;

    public Player() {
        super(Assets.instance.playerAtlas);

        addLoopAnimation(PlayerAnimation.STAND_UP, FRAME_TIME, 120, 8);
        addLoopAnimation(PlayerAnimation.STAND_DOWN, FRAME_TIME, 112, 8);
        addLoopAnimation(PlayerAnimation.STAND_LEFT, FRAME_TIME, 128, 8);
        addLoopAnimation(PlayerAnimation.STAND_RIGHT, FRAME_TIME, 136, 8);
        addNormalAnimation(PlayerAnimation.ATK_LEFT, FRAME_TIME, 32, 8);
        addNormalAnimation(PlayerAnimation.ATK_RIGHT, FRAME_TIME, 40, 8);
        addNormalAnimation(PlayerAnimation.ATK_UP, FRAME_TIME, 24, 8);
        addNormalAnimation(PlayerAnimation.ATK_DOWN, FRAME_TIME, 16, 8);
        addLoopAnimation(PlayerAnimation.WALK_UP, FRAME_TIME, 8, 8);
        addLoopAnimation(PlayerAnimation.WALK_DOWN, FRAME_TIME, 0, 8);
        addLoopAnimation(PlayerAnimation.WALK_LEFT, FRAME_TIME, 144, 8);
        addLoopAnimation(PlayerAnimation.WALK_RIGHT, FRAME_TIME, 152, 8);

        scale.set(SCALE, SCALE);
        movingSpeed = INITIAL_MOVING_SPEED;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        sword = new Sword(this);
        bow = new Bow(this);
    }

    public void init(TiledMapTileLayer mapLayer, float positionX, float positionY) {
        this.mapLayer = mapLayer;
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);

        state = PlayerState.STAND;
        setCurrentAnimation(PlayerAnimation.STAND_DOWN);
        updateBounds();
        viewDirection = Direction.DOWN;

        health = INTITAL_HEALTH;
        trapCount = INTITAL_TRAP;
        arrowCount = INTITAL_ARROW;
        swordWaveCount = INTITAL_SWORD_WAVE;

        dead = false;
        invulnerable = false;
        lastInvulnerableTime = 0;
        invulnerableTime = 0;
        setPosition(positionX, positionY);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        statusUpdate();
        if (interactingItem != null && !isCollide(interactingItem.getCollisionProperty())) {
            interactingItem.hideLinkingWindow();
            interactingItem = null;
        }
        sword.update(deltaTime);
        bow.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        sword.render(batch);
        bow.render(batch);
    }

    @Override
    public void debug(ShapeRenderer renderer) {
        super.debug(renderer);
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

    @Override
    public Direction getViewDirection() {
        return viewDirection;
    }

    @Override
    protected void updateAnimation() {
        if (state == PlayerState.STAND && velocity.x == 0 && velocity.y == 0) {
            switch (viewDirection) {
                case DOWN:
                    setCurrentAnimation(PlayerAnimation.STAND_DOWN);
                    break;
                case LEFT:
                    setCurrentAnimation(PlayerAnimation.STAND_LEFT);
                    break;
                case RIGHT:
                    setCurrentAnimation(PlayerAnimation.STAND_RIGHT);
                    break;
                case UP:
                    setCurrentAnimation(PlayerAnimation.STAND_UP);
                    break;
                default:
                    break;
            }
        } else if (state == PlayerState.ATTACK) {
            switch (viewDirection) {
            case DOWN: setCurrentAnimation(PlayerAnimation.ATK_DOWN); break;
            case LEFT: setCurrentAnimation(PlayerAnimation.ATK_LEFT); break;
            case RIGHT: setCurrentAnimation(PlayerAnimation.ATK_RIGHT); break;
            case UP:  setCurrentAnimation(PlayerAnimation.ATK_UP); break;
            default:
                break;
            }
            if (isAnimationFinished(PlayerAnimation.ATK_LEFT) || isAnimationFinished(PlayerAnimation.ATK_RIGHT)) {
                state = PlayerState.STAND;
                resetAnimation();
            }
            if (isAnimationFinished(PlayerAnimation.ATK_UP) || isAnimationFinished(PlayerAnimation.ATK_DOWN)) {
                state = PlayerState.STAND;
                resetAnimation();
            }
        } else {
            switch (viewDirection) {
                case DOWN:
                    setCurrentAnimation(PlayerAnimation.WALK_DOWN);
                    break;
                case LEFT:
                    setCurrentAnimation(PlayerAnimation.WALK_LEFT);
                    break;
                case RIGHT:
                    setCurrentAnimation(PlayerAnimation.WALK_RIGHT);
                    break;
                case UP:
                    setCurrentAnimation(PlayerAnimation.WALK_UP);
                    break;
                default:
                    break;
            }
            if (velocity.x == 0 && velocity.y == 0) {
                state = PlayerState.STAND;
            }
        }
    }

    private void statusUpdate() {
        if (invulnerable && TimeUtils.nanoTime() - lastInvulnerableTime > invulnerableTime)
            invulnerable = false;

        if (knockback && velocity.isZero()) {
            knockback = false;
        }
    }

    public void takeInvulnerable(long duration) {
        invulnerable = true;
        lastInvulnerableTime = TimeUtils.nanoTime();
        invulnerableTime = TimeUtils.millisToNanos(duration);
    }

    public void takeKnockback(float knockbackSpeed, float knockbackAngle) {
        velocity.set(
                knockbackSpeed * MathUtils.cosDeg(knockbackAngle),
                knockbackSpeed * MathUtils.sinDeg(knockbackAngle));
        knockback = true;
    }

    public void trapAttack() {
        if (state != PlayerState.ATTACK) {
            state = PlayerState.ATTACK;
            resetAnimation();
            if (trapCount != 0) {
                level.addWeapon(new Trap());
                SoundManager.instance.play(SoundManager.Sounds.ARROW);
                trapCount--;
            }
        }
    }

    public void bowAttack() {
        if (state != PlayerState.ATTACK) {
            state = PlayerState.ATTACK;
            bow.fire();
            if (arrowCount != 0) {
                level.addWeapon(new Arrow());
                SoundManager.instance.play(SoundManager.Sounds.ARROW);
                arrowCount--;
            }
            resetAnimation();
        }
    }

    public void swordAttack() {
        if (state != PlayerState.ATTACK) {
            state = PlayerState.ATTACK;
            sword.swing();
            if (swordWaveCount != 0) {
                level.addWeapon(new SwordWave());
                swordWaveCount--;
            }
            SoundManager.instance.play(SoundManager.Sounds.BEAM);
            resetAnimation();
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void showHp(ShapeRenderer shapeRenderer) {
        if (health != INTITAL_HEALTH) {
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(getPositionX(), getPositionY() - 10, bounds.width, 5);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(
                    getPositionX(), getPositionY() - 10,
                    bounds.width * ((float) health / INTITAL_HEALTH), 5);
        }
    }

    @Override
    public boolean takeDamage(float damage, float knockbackSpeed, float knockbackAngle) {
        if (!invulnerable) {
            health -= damage;
            if (health <= 0) {
                dead = true;
                return true;
            }
            takeInvulnerable(500);
            takeKnockback(knockbackSpeed, knockbackAngle);
            return true;
        }
        return false;
    }

    public void findItem(Stage stage, LinkingWindow linkingWindow, Array<Item> items) {
        if (interactingItem == null) {
            for (TiledCollisionProperty collisionProperty : TiledCollisionProperty.values()) {
                if (isCollide(collisionProperty)) {
                    interactingItem = findItem(collisionProperty, items);
                    if (interactingItem != null) {
                        break;
                    }
                }
            }
        } else if (linkingWindow.hasParent()) {
            interactingItem.hideLinkingWindow();
            interactingItem = null;
            return;
        }
        if (interactingItem != null) {
            interactingItem.trigger();
            if (!interactingItem.showLinkingWindow(stage, linkingWindow, items)) {
                interactingItem = null;
            }
        }
    }

    public boolean isCollide(TiledCollisionProperty property) {
        TiledCollisionCheck check = TiledCollisionCheck.getPool().obtain();
        check.init(bounds, mapLayer, property);
        boolean collide = check.isCollidesBottom()
                || check.isCollidesTop()
                || check.isCollidesLeft()
                || check.isCollidesRight();
        TiledCollisionCheck.getPool().free(check);
        return collide;
    }

    private Item findItem(TiledCollisionProperty property, Array<Item> items) {
        for (int i = 0; i < items.size; i++) {
            if (items.get(i).getCollisionProperty() == property)
                return items.get(i);
        }
        return null;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Sword getSword() {
        return sword;
    }
}
