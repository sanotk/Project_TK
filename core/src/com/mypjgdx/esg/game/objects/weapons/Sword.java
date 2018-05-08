package com.mypjgdx.esg.game.objects.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.characters.Damageable;
import com.mypjgdx.esg.game.objects.characters.Player;


public class Sword extends AnimatedObject<Sword.SwordAnimation> {

    private static final float SCALE = 0.5f;
    private static final float FRAME_TIME = 0.1f / 2.0f;
    private static final int SWING_FRAME_SIZE = 3;

    public enum SwordState {
        IDLE,
        SWING,
    }

    public enum SwordAnimation {
        IDLE,
        SWING,
    }

    // 2D Rectangular Polygon that contain 4 point ( 4 x 2 = 8 )
    private static final Polygon tmpPolygon = new Polygon(new float[8]);

    // 3 Frame attack area
    private Polygon[] allAttackArea = new Polygon[SWING_FRAME_SIZE];
    private boolean[] attackAreaFlipX = new boolean[SWING_FRAME_SIZE];
    private boolean[] attackAreaFlipY = new boolean[SWING_FRAME_SIZE];

    // Current attack area
    private Polygon attackArea;

    private SwordState state;
    private Player player;

    private boolean attacked;

    public Sword(Player player) {
        super(Assets.instance.sword);
        this.player = player;

        scale.set(SCALE, SCALE);

        addNormalAnimation(SwordAnimation.SWING, FRAME_TIME, 0, SWING_FRAME_SIZE);
        addNormalAnimation(SwordAnimation.IDLE, FRAME_TIME, SWING_FRAME_SIZE, 1);

        setCurrentAnimation(SwordAnimation.IDLE);
        state = SwordState.IDLE;
        updateBounds();

        allAttackArea[0] = newAttackArea(new float[]{58, 75, 63, 77, 95, 33, 96, 17});
        allAttackArea[1] = newAttackArea(new float[]{46, 74, 51, 74, 55, 18, 46, 5});
        allAttackArea[2] = newAttackArea(new float[]{39, 74, 35, 77, 1, 16, 15, 25});
    }

    private Polygon newAttackArea(float[] vertices) {
        Polygon area = new Polygon(vertices);
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] *= SCALE;
        }
        area.setOrigin(bounds.width / 2f, bounds.height / 2f);
        return area;
    }

    @Override
    public void update(float deltaTime) {
        float positionX = player.getPositionX() + player.bounds.width / 2f;
        float positionY = player.getPositionY();
        setFlip(false, false);

        switch (player.getViewDirection()) {
            case RIGHT:
                setRotation(90);
                break;
            case UP:
                setRotation(180);
                positionX -= bounds.width / 2f;
                positionY += player.bounds.height / 2f;
                break;
            case LEFT:
                setFlip(false, true);
                setRotation(90);
                positionX -= bounds.width;
                break;
            case DOWN:
                setRotation(0);
                positionX -= bounds.width / 2f;
                positionY += player.bounds.height / 2f -  bounds.height;
                break;
            default:
        }
        setPosition(positionX, positionY);
        super.update(deltaTime);
    }

    @Override
    protected void updateAnimation() {
        switch (state) {
            case IDLE:
                setCurrentAnimation(SwordAnimation.IDLE);
                attacked = false;
                break;
            case SWING:
                setCurrentAnimation(SwordAnimation.SWING);
                int frameIndex = getKeyFrameIndex(SwordAnimation.SWING);
                attackArea = allAttackArea[frameIndex];
                attackArea.setPosition(getPositionX(), getPositionY());
                attackArea.setRotation(rotation);
                if (isAnimationFinished(SwordAnimation.SWING)) {
                    attackArea = null;
                    state = SwordState.IDLE;
                }
                break;
            default:
        }
    }

    public void swing() {
        resetAnimation();
        state = SwordState.SWING;
    }

    public void attack(Damageable damageable) {
        if (attacked) return;

        float knockbackSpeed = 200f;
        switch (player.getViewDirection()) {
            case RIGHT:
                damageable.takeDamage(1, knockbackSpeed, 0);
                break;
            case UP:
                damageable.takeDamage(1, knockbackSpeed, 90);
                break;
            case LEFT:
                damageable.takeDamage(1, knockbackSpeed, 180);
                break;
            case DOWN:
                damageable.takeDamage(1, knockbackSpeed, 270);
                break;
            default:
        }
        attacked = true;
    }

    @Override
    public void debug(ShapeRenderer renderer) {
        super.debug(renderer);
        if (attackArea != null) {
            renderer.setColor(Color.CYAN);
            renderer.polygon(attackArea.getTransformedVertices());
        }
    }

    private void setRotation(float degrees) {
        for (Polygon polygon : allAttackArea) {
            polygon.setRotation(degrees);
        }
        rotation = degrees;
    }

    private void setFlip(boolean flipX, boolean flipY) {
        Array<TextureAtlas.AtlasRegion> swordRegions = Assets.instance.sword.getRegions();

        // Only swing region
        for (int i = 0; i < SWING_FRAME_SIZE ; i++) {
            final TextureRegion region = swordRegions.get(i);
            final Polygon area = allAttackArea[i];
            final float[] vertices = area.getVertices();

            region.flip(flipX != region.isFlipX(), flipY != region.isFlipY());

            if (attackAreaFlipX[i] != flipX) {
                for (int j = 0; j < vertices.length; j += 2) {
                    vertices[j] = area.getOriginX() * 2 - vertices[j];
                }
                attackAreaFlipX[i] = flipX;
            }
            if (attackAreaFlipY[i] != flipY) {
                for (int j = 1; j < vertices.length; j += 2) {
                    vertices[j] = area.getOriginY() * 2 - vertices[j];
                }
                attackAreaFlipY[i] = flipY;
            }
        }
    }

    public boolean attackAreaOverlaps(Rectangle bounds) {
        if (attackArea == null) return false;

        tmpPolygon.setPosition(bounds.x, bounds.y);
        float[] vertices = tmpPolygon.getVertices();

        vertices[0] = 0;
        vertices[1] = 0;

        vertices[2] = bounds.width;
        vertices[3] = 0;

        vertices[4] = bounds.width;
        vertices[5] = bounds.height;

        vertices[6] = 0;
        vertices[7] = bounds.height;

        return Intersector.overlapConvexPolygons(attackArea, tmpPolygon);
    }
}
