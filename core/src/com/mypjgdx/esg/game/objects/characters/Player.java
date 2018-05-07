package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;
import com.mypjgdx.esg.collision.CollisionCheck;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.SoundManager;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player.PlayerAnimation;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.*;
import com.mypjgdx.esg.game.objects.weapons.Weapon.WeaponType;
import com.mypjgdx.esg.utils.Direction;

import java.util.List;

public class Player extends AnimatedObject<PlayerAnimation> implements Damageable, Json.Serializable {

    // กำหนดจำนวนวินาทีที่แต่ละเฟรมจะถูกแสดง เป็น 1/8 วินาทีต่อเฟรม หรือ 8 เฟรมต่อวินาที (FPS)
    private static final float FRAME_DURATION = 1.0f / 16.0f;

    // อัตราการขยายภาพ player
    private static final float SCALE = 0.6f;

    private static final float INITIAL_FRICTION = 500f;           // ค่าแรงเสียดทานเริ่มต้น
    private static final float INITIAL_MOVING_SPEED = 120f;

    private static final int INTITAL_HEALTH = 10;
    private static final int INTITAL_TRAP = 5;
    private static final int INTITAL_TIME = 300;
    private static final int INTITAL_ARROW = 100;
    private static final int INTITAL_SWORDWAVE = 3;

    public boolean status_find = false;
    public boolean status_windows_link = false;
    private float Countdown;

    public boolean isSwitch = false;
    public boolean stageoneclear = false;

    private Rectangle walkingBounds = new Rectangle();

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
        WALK_UP,
        ITEM_LEFT,
        ITEM_RIGHT,
        ITEM_DOWN,
        ITEM_UP,
        ITEM_STAND_LEFT,
        ITEM_STAND_RIGHT,
        ITEM_STAND_DOWN,
        ITEM_STAND_UP
    }

    public enum PlayerState {
        STAND, ATTACK
    }

    private Item item;

    private PlayerState state;
    private int health;
    public int trapCount;
    public int arrowCount;
    public int swordWaveCount;
    public int timeCount;
    private boolean dead;
    private boolean invulnerable;
    private boolean knockback;

    private long lastInvulnerableTime;
    private long invulnerableTime;
    private float movingSpeed;

    private TiledMapTileLayer mapLayer;
    private Direction viewDirection;

    public boolean status_solarcell = false;
    public boolean status_inverter = false;
    public boolean status_ccontroller = false;
    public boolean status_battery = false;
    public boolean status_door = false;
    public boolean status_tv = false;
    public boolean status_com = false;
    public boolean status_microwave = false;
    public boolean status_air = false;
    public boolean status_refrigerator = false;
    public boolean status_switch = false;
    public boolean status_cooker = false;
    public boolean status_fan = false;
    public boolean status_pump = false;
    public boolean status_citizen = false;

    protected CollisionCheck solarcellCheck;
    protected CollisionCheck inverterCheck;
    protected CollisionCheck ccontrollerCheck;
    protected CollisionCheck batteryCheck;
    protected CollisionCheck doorCheck;
    protected CollisionCheck microwaveCheck;
    protected CollisionCheck tvCheck;
    protected CollisionCheck waterpumpCheck;
    protected CollisionCheck fanCheck;
    protected CollisionCheck cookerCheck;
    protected CollisionCheck airCheck;
    protected CollisionCheck comCheck;
    protected CollisionCheck switchCheck;
    protected CollisionCheck refrigeratorCheck;

    public boolean questScreen1 = false;
    public boolean questScreen2 = false;
    public boolean questScreen3 = false;
    public boolean questScreen4 = false;
    public boolean questScreen5 = false;
    public boolean questScreen6 = false;

    public boolean quest_window_1;
    public boolean quest_window_2;
    public boolean quest_window_3;
    public boolean quest_window_4;
    public boolean quest_window_5;
    public boolean quest_window_6;

    public boolean quest1 = false;
    public boolean quest2 = false;
    public boolean quest3 = false;
    public boolean quest4 = false;
    public boolean quest5 = false;
    public boolean quest6 = false;

    public boolean timeStop = true;
    public boolean timeClear;

    public boolean swordHit = true;

    public Player(TiledMapTileLayer mapLayer, float positionX, float positionY) {
        super(Assets.instance.playerAltas);

        addLoopAnimation(PlayerAnimation.STAND_UP, FRAME_DURATION, 120, 8);
        addLoopAnimation(PlayerAnimation.STAND_DOWN, FRAME_DURATION, 112, 8);
        addLoopAnimation(PlayerAnimation.STAND_LEFT, FRAME_DURATION, 128, 8);
        addLoopAnimation(PlayerAnimation.STAND_RIGHT, FRAME_DURATION, 136, 8);
        addNormalAnimation(PlayerAnimation.ATK_LEFT, FRAME_DURATION, 32, 8);
        addNormalAnimation(PlayerAnimation.ATK_RIGHT, FRAME_DURATION, 40, 8);
        addNormalAnimation(PlayerAnimation.ATK_UP, FRAME_DURATION, 24, 8);
        addNormalAnimation(PlayerAnimation.ATK_DOWN, FRAME_DURATION, 16, 8);
        addLoopAnimation(PlayerAnimation.WALK_UP, FRAME_DURATION, 8, 8);
        addLoopAnimation(PlayerAnimation.WALK_DOWN, FRAME_DURATION, 0, 8);
        addLoopAnimation(PlayerAnimation.WALK_LEFT, FRAME_DURATION, 144, 8);
        addLoopAnimation(PlayerAnimation.WALK_RIGHT, FRAME_DURATION, 152, 8);
        addLoopAnimation(PlayerAnimation.ITEM_UP, FRAME_DURATION, 56, 8);
        addLoopAnimation(PlayerAnimation.ITEM_DOWN, FRAME_DURATION, 48, 8);
        addLoopAnimation(PlayerAnimation.ITEM_LEFT, FRAME_DURATION, 64, 8);
        addLoopAnimation(PlayerAnimation.ITEM_RIGHT, FRAME_DURATION, 72, 8);
        addLoopAnimation(PlayerAnimation.ITEM_STAND_UP, FRAME_DURATION, 88, 8);
        addLoopAnimation(PlayerAnimation.ITEM_STAND_DOWN, FRAME_DURATION, 80, 8);
        addLoopAnimation(PlayerAnimation.ITEM_STAND_LEFT, FRAME_DURATION, 96, 8);
        addLoopAnimation(PlayerAnimation.ITEM_STAND_RIGHT, FRAME_DURATION, 104, 8);

        scale.set(SCALE, SCALE);
        movingSpeed = INITIAL_MOVING_SPEED;
        friction.set(INITIAL_FRICTION, INITIAL_FRICTION);

        init(mapLayer, positionX, positionY);
    }


    public void init(TiledMapTileLayer mapLayer, float positionX, float positionY) {
        this.mapLayer = mapLayer;
        CollisionCheck();

        state = PlayerState.STAND;
        setCurrentAnimation(PlayerAnimation.STAND_DOWN);
        viewDirection = Direction.DOWN;

        health = INTITAL_HEALTH;
        trapCount = INTITAL_TRAP;
        arrowCount = INTITAL_ARROW;
        timeCount = INTITAL_TIME;
        swordWaveCount = INTITAL_SWORDWAVE;

        dead = false;
        invulnerable = false;
        lastInvulnerableTime = 0;
        invulnerableTime = 0;
        setPosition(positionX, positionY);
    }

    public void CollisionCheck() {
        collisionCheck = new TiledCollisionCheck(walkingBounds, mapLayer);

        solarcellCheck = new TiledCollisionCheck(bounds, mapLayer, "solarcell");
        batteryCheck = new TiledCollisionCheck(bounds, mapLayer, "battery");
        inverterCheck = new TiledCollisionCheck(bounds, mapLayer, "inverter");
        ccontrollerCheck = new TiledCollisionCheck(bounds, mapLayer, "ccontroller");
        doorCheck = new TiledCollisionCheck(bounds, mapLayer, "door");
        microwaveCheck = new TiledCollisionCheck(bounds, mapLayer, "microwave");
        tvCheck = new TiledCollisionCheck(bounds, mapLayer, "tv");
        waterpumpCheck = new TiledCollisionCheck(bounds, mapLayer, "pump");
        fanCheck = new TiledCollisionCheck(bounds, mapLayer, "fan");
        cookerCheck = new TiledCollisionCheck(bounds, mapLayer, "cooker");
        airCheck = new TiledCollisionCheck(bounds, mapLayer, "air");
        comCheck = new TiledCollisionCheck(bounds, mapLayer, "com");
        switchCheck = new TiledCollisionCheck(bounds, mapLayer, "switch");
        refrigeratorCheck = new TiledCollisionCheck(bounds, mapLayer, "refrigerator");
    }

    public void update(float deltaTime, List<Weapon> weapons, List<Citizen> citizens) {
        super.update(deltaTime);
        statusUpdate();
        if (item != null) // ถ้ามีไอเทม
            item.setPosition(
                    getPositionX() + origin.x - item.origin.x,
                    getPositionY() + origin.y - item.origin.y);
        for (Weapon w : weapons) {
            if (bounds.overlaps(w.bounds) && !w.isDestroyed() && w.type == WeaponType.ENEMYBALL) {
                w.attack(this);
            }
        }
        if (stageoneclear) {

            questScreen1 = false;
            questScreen2 = false;
            questScreen3 = false;
            questScreen4 = false;
            questScreen5 = false;
            questScreen6 = false;

            for (Citizen citizen : citizens) {
                if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_1)) {
                    questScreen1 = true;
                    if (quest1 == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_2)) {
                    questScreen2 = true;
                    if (quest2 == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_3)) {
                    questScreen3 = true;
                    if (quest3 == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_4)) {
                    questScreen4 = true;
                    if (quest4 == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_5)) {
                    questScreen5 = true;
                    if (quest5 == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_6)) {
                    questScreen6 = true;
                    if (quest6 == true) {
                        citizen.quest = true;
                    }
                }

            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            System.out.println("\n" + questScreen1);
            System.out.println("" + questScreen2);
            System.out.println("" + questScreen3);
            System.out.println("" + questScreen4);
            System.out.println("" + questScreen5);
            System.out.println("" + questScreen6);
        }

        Countdown += deltaTime;
        if ((!timeStop) && (Countdown >= 1) && !timeClear) {
            timeCount--;
            Countdown = 0;
        }

        checkCollide();
    }

    private void checkCollide() {
        if (solarcellCheck.isCollidesBottom() || solarcellCheck.isCollidesLeft() ||
                solarcellCheck.isCollidesRight() || solarcellCheck.isCollidesTop()) {
            status_solarcell = true;
        } else {
            status_solarcell = false;
        }

        if (batteryCheck.isCollidesBottom() || batteryCheck.isCollidesLeft() ||
                batteryCheck.isCollidesRight() || batteryCheck.isCollidesTop()) {
            status_battery = true;
        } else {
            status_battery = false;
        }

        if (ccontrollerCheck.isCollidesBottom() || ccontrollerCheck.isCollidesLeft() ||
                ccontrollerCheck.isCollidesRight() || ccontrollerCheck.isCollidesTop()) {
            status_ccontroller = true;
        } else {
            status_ccontroller = false;
        }

        if (inverterCheck.isCollidesBottom() || inverterCheck.isCollidesLeft() ||
                inverterCheck.isCollidesRight() || inverterCheck.isCollidesTop()) {
            status_inverter = true;
        } else {
            status_inverter = false;
        }
        if (doorCheck.isCollidesBottom() || doorCheck.isCollidesLeft() ||
                doorCheck.isCollidesRight() || doorCheck.isCollidesTop()) {
            status_door = true;
        } else {
            status_door = false;
        }
        if (comCheck.isCollidesBottom() || comCheck.isCollidesLeft() ||
                comCheck.isCollidesRight() || comCheck.isCollidesTop()) {
            status_com = true;
        } else {
            status_com = false;
        }
        if (fanCheck.isCollidesBottom() || fanCheck.isCollidesLeft() ||
                fanCheck.isCollidesRight() || fanCheck.isCollidesTop()) {
            status_fan = true;
        } else {
            status_fan = false;
        }
        if (refrigeratorCheck.isCollidesBottom() || refrigeratorCheck.isCollidesLeft() ||
                refrigeratorCheck.isCollidesRight() || refrigeratorCheck.isCollidesTop()) {
            status_refrigerator = true;
        } else {
            status_refrigerator = false;
        }
        if (cookerCheck.isCollidesBottom() || cookerCheck.isCollidesLeft() ||
                cookerCheck.isCollidesRight() || cookerCheck.isCollidesTop()) {
            status_cooker = true;
        } else {
            status_cooker = false;
        }
        if (waterpumpCheck.isCollidesBottom() || waterpumpCheck.isCollidesLeft() ||
                waterpumpCheck.isCollidesRight() || waterpumpCheck.isCollidesTop()) {
            status_pump = true;
        } else {
            status_pump = false;
        }
        if (airCheck.isCollidesBottom() || airCheck.isCollidesLeft() ||
                airCheck.isCollidesRight() || airCheck.isCollidesTop()) {
            status_air = true;
        } else {
            status_air = false;
        }
        if (switchCheck.isCollidesBottom() || switchCheck.isCollidesLeft() ||
                switchCheck.isCollidesRight() || switchCheck.isCollidesTop()) {
            status_switch = true;
        } else {
            status_switch = false;
        }
        if (microwaveCheck.isCollidesBottom() || microwaveCheck.isCollidesLeft() ||
                microwaveCheck.isCollidesRight() || microwaveCheck.isCollidesTop()) {
            status_microwave = true;
        } else {
            status_microwave = false;
        }
        if (tvCheck.isCollidesBottom() || tvCheck.isCollidesLeft() ||
                tvCheck.isCollidesRight() || tvCheck.isCollidesTop()) {
            status_tv = true;
        } else {
            status_tv = false;
        }
        /*
        if (citizenCheck.isCollidesBottom() || citizenCheck.isCollidesLeft() ||
                citizenCheck.isCollidesRight() || citizenCheck.isCollidesTop()) {
            status_citizen = true;
        } else {
            status_citizen = false;
        }
        */
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
    protected void setAnimation() {
        if (state == PlayerState.STAND && velocity.x == 0 && velocity.y == 0) {
            unFreezeAnimation();
            if (item == null) {
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
            } else {
                switch (viewDirection) {
                    case DOWN:
                        setCurrentAnimation(PlayerAnimation.ITEM_STAND_DOWN);
                        break;
                    case LEFT:
                        setCurrentAnimation(PlayerAnimation.ITEM_STAND_LEFT);
                        break;
                    case RIGHT:
                        setCurrentAnimation(PlayerAnimation.ITEM_STAND_RIGHT);
                        break;
                    case UP:
                        setCurrentAnimation(PlayerAnimation.ITEM_STAND_UP);
                        break;
                    default:
                        break;
                }
            }
        } else if (state == PlayerState.ATTACK && item == null) {
            unFreezeAnimation();
            switch (viewDirection) {
                case DOWN:
                    setCurrentAnimation(PlayerAnimation.ATK_DOWN);
                    break;
                case LEFT:
                    setCurrentAnimation(PlayerAnimation.ATK_LEFT);
                    break;
                case RIGHT:
                    setCurrentAnimation(PlayerAnimation.ATK_RIGHT);
                    break;
                case UP:
                    setCurrentAnimation(PlayerAnimation.ATK_UP);
                    break;
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
        } else if (item != null) {
            unFreezeAnimation();
            switch (viewDirection) {
                case DOWN:
                    setCurrentAnimation(PlayerAnimation.ITEM_DOWN);
                    break;
                case LEFT:
                    setCurrentAnimation(PlayerAnimation.ITEM_LEFT);
                    break;
                case RIGHT:
                    setCurrentAnimation(PlayerAnimation.ITEM_RIGHT);
                    break;
                case UP:
                    setCurrentAnimation(PlayerAnimation.ITEM_UP);
                    break;
                default:
                    break;
            }
            if (velocity.x == 0 && velocity.y == 0) {
                freezeAnimation();
                resetAnimation();
            }
        } else {
            unFreezeAnimation();
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

    public void trapAttack(List<Weapon> weapons) {
        if (state != PlayerState.ATTACK && item == null) {
            state = PlayerState.ATTACK;
            resetAnimation();
            if (trapCount != 0) {
                weapons.add(new Trap(mapLayer, this));
                Assets.instance.bulletSound.play();
                SoundManager.instance.play(SoundManager.Sounds.BULLET);
                trapCount--;
            }
        }
    }

    public void swordAttack(List<Weapon> weapons, List<Sword> swords) {
        if (state != PlayerState.ATTACK) {
            state = PlayerState.ATTACK;
            for (Sword sword : swords) {
                sword.resetAnimation();
                sword.state = Sword.SwordState.HIT;
                if (swordWaveCount != 0) {
                    weapons.add(new SwordWave(mapLayer, this));
                    swordWaveCount--;
                }
                weapons.add(new SwordHit(mapLayer, this));
                SoundManager.instance.play(SoundManager.Sounds.BEAM);
                resetAnimation();
            }
        }
    }

    public void bowAttack(List<Weapon> weapons, List<Bow> bows) {
        if (state != PlayerState.ATTACK) {
            state = PlayerState.ATTACK;
            for (Bow bow : bows) {
                bow.resetAnimation();
                bow.state = Bow.Bowstate.SHOT;
                if (arrowCount != 0) {
                    weapons.add(new Arrow(mapLayer, this));
                    arrowCount--;
                }
                SoundManager.instance.play(SoundManager.Sounds.BEAM);
                resetAnimation();
            }
        }
    }

    public void statusUpdate() {
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

    public boolean isAlive() {
        return !dead;
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

    public void findItem() {
        if (status_windows_link == true) {
            status_windows_link = false;
            status_find = false;
        } else {
            status_windows_link = true;
            status_find = true;
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    public void debug(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        renderer.setColor(Color.BLUE);
        renderer.rect(walkingBounds.x, walkingBounds.y, walkingBounds.width, walkingBounds.height);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        walkingBounds.set(position.x, position.y, dimension.x, dimension.y - 50);
    }

    @Override
    public void write(Json json) {
        json.writeValue("hp", health);
        json.writeValue("position", position);
        json.writeValue("arrowCount", arrowCount);
        json.writeValue("trapCount", trapCount);
        json.writeValue("swordWaveCount", swordWaveCount);
        json.writeValue("timeCount", timeCount);

        json.writeValue("questScreen1", questScreen1);
        json.writeValue("questScreen2", questScreen2);
        json.writeValue("questScreen3", questScreen3);
        json.writeValue("questScreen4", questScreen4);
        json.writeValue("questScreen5", questScreen5);
        json.writeValue("questScreen6", questScreen6);

        json.writeValue("quest_window_1", quest_window_1);
        json.writeValue("quest_window_2", quest_window_2);
        json.writeValue("quest_window_3", quest_window_3);
        json.writeValue("quest_window_4", quest_window_4);
        json.writeValue("quest_window_5", quest_window_5);
        json.writeValue("quest_window_6", quest_window_6);

        json.writeValue("quest1", quest1);
        json.writeValue("quest2", quest2);
        json.writeValue("quest3", quest3);
        json.writeValue("quest4", quest4);
        json.writeValue("quest5", quest5);
        json.writeValue("quest6", quest6);

        json.writeValue("dead", dead);
        json.writeValue("invulnerable", invulnerable);
        json.writeValue("knockback", knockback);

        json.writeValue("lastInvulnerableTime", lastInvulnerableTime);
        json.writeValue("invulnerableTime", invulnerableTime);
        json.writeValue("movingSpeed", movingSpeed);

        json.writeValue("viewDirection", viewDirection);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue player = jsonData.get("player");
        JsonValue positionJson = player.get("position");

        setPosition(positionJson.getFloat("x"), positionJson.getFloat("y"));

        arrowCount = player.getInt("arrowCount");
        trapCount = player.getInt("trapCount");
        swordWaveCount = player.getInt("swordWaveCount");
        timeCount = player.getInt("timeCount");

        questScreen1 = player.getBoolean("questScreen1");
        questScreen2 = player.getBoolean("questScreen2");
        questScreen3 = player.getBoolean("questScreen3");
        questScreen4 = player.getBoolean("questScreen4");
        questScreen5 = player.getBoolean("questScreen5");
        questScreen6 = player.getBoolean("questScreen6");
        quest_window_1 = player.getBoolean("quest_window_1");
        quest_window_2 = player.getBoolean("quest_window_2");
        quest_window_3 = player.getBoolean("quest_window_3");
        quest_window_4 = player.getBoolean("quest_window_4");
        quest_window_5 = player.getBoolean("quest_window_5");
        quest_window_6 = player.getBoolean("quest_window_6");
        quest1 = player.getBoolean("quest1");
        quest2 = player.getBoolean("quest2");
        quest3 = player.getBoolean("quest3");
        quest4 = player.getBoolean("quest4");
        quest5 = player.getBoolean("quest5");
        quest6 = player.getBoolean("quest6");

        invulnerable = player.getBoolean("invulnerable");
        knockback = player.getBoolean("knockback");
        lastInvulnerableTime = player.getLong("lastInvulnerableTime");
        invulnerableTime = player.getLong("invulnerableTime");
        movingSpeed = player.getFloat("movingSpeed");

        viewDirection = Direction.valueOf(player.getString("viewDirection"));

    }

    public int getIntitalHealth(){
        return INTITAL_HEALTH;
    }

    public int getIntitalTrap(){
        return INTITAL_TRAP;
    }

    public int getIntitalTime(){
        return INTITAL_TIME;
    }

    public int getIntitalArrow(){
        return INTITAL_ARROW;
    }

    public int getIntitalSwordwave(){
        return INTITAL_SWORDWAVE;
    }

}
