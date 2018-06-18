package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.utils.AttackArea;

public class Sword extends AnimatedObject {

    private static final float FRAME_DURATION = 0.1f / 2.0f;
    private static final float SCALE = 0.5f;
    private static final float INIT_POSITION_X = 1700f;
    private static final float INIT_POSITION_Y = 950f;

    public enum SwordAnimation {
        IDLE,
        SWING
    }

    public enum SwordState {
        IDLE,
        SWING
    }

    public SwordState state;
    private Player player;

    private IntMap<AttackArea> attackAreas = new IntMap<AttackArea>();
    private AttackArea currentAttackArea = AttackArea.nullArea;
    private ObjectSet<Damageable> attackedObjects= new ObjectSet<Damageable>();

    public Sword(Player player) {
        super(Assets.instance.sword);

        addNormalAnimation(SwordAnimation.SWING, FRAME_DURATION, 0, 3);
        addNormalAnimation(SwordAnimation.IDLE, FRAME_DURATION, 3, 1);

        setPosition(INIT_POSITION_X, INIT_POSITION_Y);

        scale.set(SCALE, SCALE);

        state = SwordState.IDLE;
        setCurrentAnimation(SwordAnimation.IDLE);
        this.player = player;
        updateBounds();

        attackAreas.put(0, new AttackArea(new float[] {58, 75, 63, 77, 95, 33, 96, 17}, SCALE, bounds.width, bounds.height));
        attackAreas.put(1, new AttackArea(new float[] {46, 74, 51, 74, 55, 18, 46, 5}, SCALE, bounds.width, bounds.height));
        attackAreas.put(2, new AttackArea(new float[] {39, 74, 35, 77, 1, 16, 15, 25}, SCALE, bounds.width, bounds.height));
    }

    @Override
    public void update(float deltaTime) {
        float positionX = player.getPositionX() + player.bounds.width / 2f;
        float positionY = player.getPositionY();
        setFlip(false, false);

        switch (player.getViewDirection()) {
            case RIGHT:
                rotation = 90;
                break;
            case UP:
                rotation = 180;
                positionX -= bounds.width / 2f;
                positionY += player.bounds.height / 2f;
                break;
            case LEFT:
                setFlip(false, true);
                rotation = 90;
                positionX -= bounds.width;
                break;
            case DOWN:
                rotation = 0;
                positionX -= bounds.width / 2f;
                positionY += player.bounds.height / 2f -  bounds.height;
                break;
            default:
        }

        setPosition(positionX, positionY);
        super.update(deltaTime);
    }

    public void swing() {
        state = Sword.SwordState.SWING;
        resetAnimation();
        attackedObjects.clear();
    }

    public void damageTo(Damageable damageable) {
        if (isAttacked(damageable))
            return;

        float knockbackSpeed = 200f;
        switch (player.getViewDirection()) {
            case DOWN:
                damageable.takeDamage(1, knockbackSpeed, 270);
                break;
            case LEFT:
                damageable.takeDamage(1, knockbackSpeed, 180);
                break;
            case RIGHT:
                damageable.takeDamage(1, knockbackSpeed, 0);
                break;
            case UP:
                damageable.takeDamage(1, knockbackSpeed, 90);
                break;
            default:
                break;
        }
        attackedObjects.add(damageable);
    }

    public boolean isAttacked(Damageable damageable) {
        return attackedObjects.contains(damageable);
    }

    @Override
    protected void setAnimation() {
        unFreezeAnimation();
        switch (state) {
            case IDLE:
                setCurrentAnimation(SwordAnimation.IDLE);
                break;
            case SWING:
                setCurrentAnimation(SwordAnimation.SWING);
                int frameIndex = getKeyFrameIndex(SwordAnimation.SWING);
                currentAttackArea = attackAreas.get(frameIndex);
                currentAttackArea.setPosition(getPositionX(), getPositionY());
                currentAttackArea.setRotation(rotation);
                if (isAnimationFinished(SwordAnimation.SWING)) {
                    currentAttackArea = AttackArea.nullArea;
                    state = SwordState.IDLE;
                }
                break;
            default:
                break;
        }
    }

    private void setFlip(boolean flipX, boolean flipY) {
        Array<TextureAtlas.AtlasRegion> swordRegions = Assets.instance.sword.getRegions();
        for (TextureAtlas.AtlasRegion swordRegion : swordRegions) {
            swordRegion.flip(flipX != swordRegion.isFlipX(), flipY != swordRegion.isFlipY());
        }
        for (AttackArea attackArea : attackAreas.values()) {
            attackArea.setFlip(flipX, flipY);
        }
    }

    public boolean attackAreaOverlaps(Rectangle bounds) {
        return currentAttackArea.overlaps(bounds);
    }

    public void debug(ShapeRenderer renderer) {
        renderer.setColor(Color.BLUE);
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        currentAttackArea.debug(renderer);
    }
}
