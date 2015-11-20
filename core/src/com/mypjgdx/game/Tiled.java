package com.mypjgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Tiled {

    MAP_1(38), MAP_2(0), MAP_3(39), MAP_4(1), MAP_5(40),
    MAP_6(2), MAP_7(41), MAP_8(3), MAP_9(42), MAP_10(4),
    MAP_11(43), MAP_12(5), MAP_13(44), MAP_14(6), MAP_15(45),
    MAP_16(7), MAP_17(46), MAP_18(8), MAP_19(47), MAP_20(9),
    MAP_21(48), MAP_22(10), MAP_23(49), MAP_24(11), MAP_25(50),
    MAP_26(12), MAP_27(51), MAP_28(13), MAP_29(52), MAP_30(14),
    MAP_31(53), MAP_32(15), MAP_33(54), MAP_34(16), MAP_35(55),
    MAP_36(17), MAP_37(56), MAP_38(18), MAP_39(57), MAP_40(19),
    MAP_41(58), MAP_42(20), MAP_43(59),

    EMPTY(-1);

    public static final int TILE_WIDTH = 25;
    public static final int TILE_HEIGHT = 25;

    private static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("map_first.atlas")); //TODO atlas file
    private static final TextureRegion emptyRegion = new TextureRegion(new Texture(new Pixmap(0, 0, Format.Alpha)));

    private int number;

    public static Tiled get(int tileNum) {
        for (Tiled t : Tiled.values()) {
            if(t.getNumber() == tileNum) return t;
        }
        return EMPTY;
    }

    private Tiled(int number) { this.number = number; }

    public int getNumber() { return number; }

    public TextureRegion getRegion() {
        if (number == -1) return emptyRegion;
        else {
            TextureRegion region = atlas.findRegion("map" + Integer.toString(number+1));
            if (region != null) return region;
        }
        return emptyRegion;
    }
}
