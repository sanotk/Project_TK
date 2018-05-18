package com.mypjgdx.esg.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Citizen;
import com.mypjgdx.esg.game.objects.characters.Citizen1;
import com.mypjgdx.esg.game.objects.characters.Pepo;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.Item;
import com.mypjgdx.esg.game.objects.items.Lamp;
import com.mypjgdx.esg.game.objects.items.Switch;
import com.mypjgdx.esg.game.objects.items.Television;
import com.mypjgdx.esg.game.objects.weapons.NormalBow;
import com.mypjgdx.esg.game.objects.weapons.NormalSword;

public class LevelTraining extends Level {

    public Citizen citizen1;

    public Item switchItem;
    public Item television;

    public LevelTraining() {
        map = Assets.instance.map1;
        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);

        player = new Player(mapLayer, 100, 1000);

        switchItem = new Switch(mapLayer, player);
        television = new Television(mapLayer, player);

        enemies.add(new Pepo(mapLayer, player));

        citizen1 = new Citizen1(mapLayer, player);

        citizens.add(citizen1);

        bows.add(new NormalBow(mapLayer, player));
        swords.add(new NormalSword(mapLayer, player));
    }

    @Override
    public void renderFbo(SpriteBatch batch, OrthographicCamera camera, FrameBuffer lightFbo) {
        batch.begin();
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batch.draw(lightFbo.getColorBufferTexture(),
                camera.position.x - camera.viewportWidth * camera.zoom / 2,
                camera.position.y - camera.viewportHeight * camera.zoom / 2,
                0, 0,
                lightFbo.getColorBufferTexture().getWidth(), lightFbo.getColorBufferTexture().getHeight(),
                1 * camera.zoom, 1 * camera.zoom,
                0,
                0, 0,
                lightFbo.getColorBufferTexture().getWidth(), lightFbo.getColorBufferTexture().getHeight(),
                false, true);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.end();
    }

    @Override
    public void createFbo(SpriteBatch batch, FrameBuffer lightFbo) {
        lightFbo.begin();

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(1, 1, 1, 1);
        batch.draw(Assets.instance.light,
                player.getPositionX() + player.origin.x
                        - Assets.instance.light.getWidth() / 2f,
                player.getPositionY() + player.origin.y
                        - Assets.instance.light.getHeight() / 2f);
        if(player.isSwitch){
            for (int i =0; i< items.size();i++){
                if(items.get(i) instanceof Lamp){
                    batch.draw(Assets.instance.light,
                            items.get(i).p_x + items.get(0).origin.x
                                    - Assets.instance.light.getWidth() / 2f,
                            items.get(i).p_y + items.get(0).origin.y
                                    - Assets.instance.light.getHeight() / 2f);
                }
            }
        }
        batch.end();
        FrameBuffer.unbind();
    }
}
