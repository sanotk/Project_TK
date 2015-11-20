package com.mypjgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Tiled {
    BLUE(0),
    GREEN(1),
    RED(2),
    YELLOW(3);

    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    private static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("tile.atlas"));
    private static final TextureRegion empty = new TextureRegion(new Texture(new Pixmap(0, 0, Format.Alpha)));

    private int number;

    public static Tiled get(int tileNum) {
        for (Tiled t : Tiled.values()) {
            if(t.getNumber() == tileNum) return t;
        }
        return null;
    }

    private Tiled(int number) { this.number = number; }

    public int getNumber() { return number; }

    public TextureRegion getTexture() {
        switch(this) {
        case BLUE:   return atlas.findRegion("blue");
        case GREEN:  return atlas.findRegion("green");
        case RED:    return atlas.findRegion("red");
        case YELLOW: return atlas.findRegion("yellow");
        default:
            break;
        }
        return empty;
    }
}
