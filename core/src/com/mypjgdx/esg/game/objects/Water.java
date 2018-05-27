package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mypjgdx.esg.game.Assets;

public class Water extends AbstractGameObject {

    private static final float FLOW_SPEED = 0.35f;

    private static float scrollY;
    private static TextureRegion region = new TextureRegion(Assets.instance.waterTexture);

    static {
        region.setRegionY(50);
    }

    public static void flow(float deltaTime) {
        final float deltaY = -FLOW_SPEED * deltaTime;
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
