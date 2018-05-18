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
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.SoundManager;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Player.PlayerAnimation;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.weapons.*;
import com.mypjgdx.esg.game.objects.weapons.Weapon.WeaponType;
import com.mypjgdx.esg.ui.ArrowBar;
import com.mypjgdx.esg.ui.BatteryBar;
import com.mypjgdx.esg.ui.SwordWaveBar;
import com.mypjgdx.esg.ui.TrapBar;
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
    private static final int INTITAL_TIME = 300;

    public boolean status_find;
    public boolean status_windows_link;
    private float Countdown;

    public boolean isSwitch = false;
    public boolean stageOneClear = false;

    public Rectangle walkingBounds = new Rectangle();
    public boolean statusEnergyWindow;
    public boolean solarCellGuideWindow;

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
    public int timeCount;
    private boolean dead;
    private boolean invulnerable;
    private boolean knockback;

    private long lastInvulnerableTime;
    private long invulnerableTime;
    private float movingSpeed;

    private TiledMapTileLayer mapLayer;
    private Direction viewDirection;

    public boolean status_citizen = false;


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

    public boolean quest1IsAccept = false;
    public boolean quest2IsAccept = false;
    public boolean quest3IsAccept = false;
    public boolean quest4IsAccept = false;
    public boolean quest5IsAccept = false;
    public boolean quest6IsAccept = false;

    public boolean timeStop = true;
    public boolean timeClear;

    public boolean swordHit = true;

    private PlayerStalkerPosition stalkerPosition;

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

        stalkerPosition = new PlayerStalkerPosition(this);
    }


    public void init(TiledMapTileLayer mapLayer, float positionX, float positionY) {
        this.mapLayer = mapLayer;
        CollisionCheck();

        state = PlayerState.STAND;
        setCurrentAnimation(PlayerAnimation.STAND_DOWN);
        viewDirection = Direction.UP;

        health = INTITAL_HEALTH;
        timeCount = INTITAL_TIME;

        dead = false;
        invulnerable = false;
        lastInvulnerableTime = 0;
        invulnerableTime = 0;
        setPosition(positionX, positionY);
    }

    public void CollisionCheck() {
        collisionCheck = new TiledCollisionCheck(walkingBounds, mapLayer);
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
        if (stageOneClear) {

            questScreen1 = false;
            questScreen2 = false;
            questScreen3 = false;
            questScreen4 = false;
            questScreen5 = false;
            questScreen6 = false;

            for (Citizen citizen : citizens) {
                if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_1) )  {
                    questScreen1 = true;
                    if (quest1IsAccept == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_2)) {
                    questScreen2 = true;
                    if (quest2IsAccept == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_3)) {
                    questScreen3 = true;
                    if (quest3IsAccept == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_4)) {
                    questScreen4 = true;
                    if (quest4IsAccept == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_5)) {
                    questScreen5 = true;
                    if (quest5IsAccept == true) {
                        citizen.quest = true;
                    }
                } else if (bounds.overlaps(citizen.bounds) && (citizen.type == Citizen.CitizenType.CITIZEN_6)) {
                    questScreen6 = true;
                    if (quest6IsAccept == true) {
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

        stalkerPosition.update();
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
            if (BatteryBar.instance.getBatteryStorage() >= TrapBar.instance.energyTrap) {
                weapons.add(new Trap(mapLayer, this));
                Assets.instance.bulletSound.play();
                SoundManager.instance.play(SoundManager.Sounds.BULLET);
                BatteryBar.instance.batteryStorage -= TrapBar.instance.energyTrap;
            }
        }
    }

    public void swordAttack(List<Weapon> weapons, List<Sword> swords) {
        if (state != PlayerState.ATTACK) {
            state = PlayerState.ATTACK;
            for (Sword sword : swords) {
                sword.resetAnimation();
                sword.state = Sword.SwordState.HIT;
                weapons.add(new SwordHit(mapLayer, this));
                SoundManager.instance.play(SoundManager.Sounds.BEAM);
                resetAnimation();
            }
        }
    }

    public void swordWaveAttack(List<Weapon> weapons, List<Sword> swords) {
        if (state != PlayerState.ATTACK) {
            state = PlayerState.ATTACK;
            for (Sword sword : swords) {
                sword.resetAnimation();
                sword.state = Sword.SwordState.HIT;
                if (BatteryBar.instance.getBatteryStorage() >= SwordWaveBar.instance.energySwordWave) {
                    weapons.add(new SwordWave(mapLayer, this));
                    BatteryBar.instance.batteryStorage -= SwordWaveBar.instance.energySwordWave;
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
                if (BatteryBar.instance.getBatteryStorage() >= ArrowBar.instance.energyArrow) {
                    weapons.add(new Arrow(mapLayer, this));
                    BatteryBar.instance.batteryStorage -= ArrowBar.instance.energyArrow;
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
        if (status_find) {
            status_windows_link = false;
            status_find = false;
        } else {
            status_windows_link = true;
            status_find = true;
        }
    }

    public void statusEnergy() {
        if (statusEnergyWindow == true) {
            statusEnergyWindow = false;
        } else {
            statusEnergyWindow = true;
        }
    }

    public void solarCellGuide() {
        if (solarCellGuideWindow == true) {
            solarCellGuideWindow = false;
        } else {
            solarCellGuideWindow = true;
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
        stalkerPosition.debug(renderer);
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

        json.writeValue("quest1IsAccept", quest1IsAccept);
        json.writeValue("quest2IsAccept", quest2IsAccept);
        json.writeValue("quest3IsAccept", quest3IsAccept);
        json.writeValue("quest4IsAccept", quest4IsAccept);
        json.writeValue("quest5IsAccept", quest5IsAccept);
        json.writeValue("quest6IsAccept", quest6IsAccept);

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
        quest1IsAccept = player.getBoolean("quest1IsAccept");
        quest2IsAccept = player.getBoolean("quest2IsAccept");
        quest3IsAccept = player.getBoolean("quest3IsAccept");
        quest4IsAccept = player.getBoolean("quest4IsAccept");
        quest5IsAccept = player.getBoolean("quest5IsAccept");
        quest6IsAccept = player.getBoolean("quest6IsAccept");

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

    public int getIntitalTime(){
        return INTITAL_TIME;
    }

}
