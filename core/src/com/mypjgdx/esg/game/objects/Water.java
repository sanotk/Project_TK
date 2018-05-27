package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mypjgdx.esg.game.Assets;

public class Water extends AbstractGameObject {

    private float flowSpeed = 0.35f;
    private float scrollY;
    private TextureRegion region;

    public Water() {
        region = new TextureRegion(Assets.instance.waterTexture, 0, 50, 50, 50);
    }

    public void setFlowSpeed(float flowSpeed) {
        this.flowSpeed = flowSpeed;
    }

    public void resetFlow() {
        scrollY = 0;
        region.setRegion(0, 50, 50, 50);
    }

    @Override
    public void update(float deltaTime) {
        final float deltaY = -flowSpeed * deltaTime;
        scrollY += deltaY;
        region.scroll(0, deltaY);
        if (scrollY <= 0.5f) {
            scrollY += 0.5f;
            region.scroll(0, 0.5f);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(region, position.x, position.y);
    }

    @Override
    public String getName() {
        return "Water";
    }
}
