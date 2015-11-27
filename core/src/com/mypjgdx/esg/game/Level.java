package com.mypjgdx.esg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mypjgdx.esg.game.objects.Sano;
import com.mypjgdx.esg.game.objects.Tiles;

public class Level {

    private TileMap backTileMap; // ตัวแปรเก็บแผนที่ด้านหลัง
    private TileMap frontTileMap; // ตัวแปรเก็บแผนที่ด้านหน้า

    public Sano sano; // ตัวละครที่ผู้เล่นจะได้ควบคุม

    public Level () {
        init();
    }

    private void init () {
        backTileMap =  Assets.instance.backTileMap1;
        frontTileMap = Assets.instance.frontTileMap1;

        sano = new Sano();
    }

    public void render(SpriteBatch batch) {
        renderLevel(backTileMap , batch);
        renderLevel(frontTileMap, batch);

        sano.render(batch);
    }

    private void renderLevel(TileMap tilemap, SpriteBatch batch) {
        int x = 0; //พิกัด x
        int y = 0; //พิกัด y

        for (int row = tilemap.row -1; row >= 0; --row) { //เรนเดอร์แมพให้ตรงกับไอดี
            for (int column = 0; column < tilemap.column ; ++column) {
                int tileId= tilemap.getId(row, column);
                Tiles.get(tileId).render(batch, x, y);
                x += Tiles.TILE_WIDTH;
            }
            x = 0;
            y += Tiles.TILE_HEIGHT;
        }
    }
}
