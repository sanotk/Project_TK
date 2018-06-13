package com.mypjgdx.esg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.utils.CameraHelper;

public class BlackScreen {

    public static final BlackScreen instance = new BlackScreen();

    private Sprite blackScreen;
    private float elapsedTime;
    private float fadeOutTime;

    private BlackScreen() {
        blackScreen = new Sprite(Assets.instance.white);
        blackScreen.setColor(Color.BLACK);
    }

    public void draw(SpriteBatch batch) {
        blackScreen.draw(batch);
    }

    public void update(float deltaTime, CameraHelper cameraHelper) {
        elapsedTime += deltaTime;

        float width = cameraHelper.rightEdge - cameraHelper.leftEdge;
        float height = cameraHelper.upperEdge - cameraHelper.lowerEdge;
        Vector2 cameraPosition = cameraHelper.getPosition();

        blackScreen.setPosition(
                cameraPosition.x - cameraHelper.halfCameraWidth,
                cameraPosition.y - cameraHelper.halfCameraHeight);
        blackScreen.setSize(width, height);
        blackScreen.setAlpha(Interpolation.fade.apply(Math.max(1 - elapsedTime / fadeOutTime, 0f)));
    }

    public void reset() {
        elapsedTime = 0;
    }

    public void setFadeOutTime(float fadeOutTime) {
        this.fadeOutTime = fadeOutTime;
    }
}
