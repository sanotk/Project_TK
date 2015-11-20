package com.mypjgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Tiled {
    EMPTY(-1),

    MAP_1(1), MAP_2(2), MAP_3(3), MAP_4(4), MAP_5(5),
    MAP_6(6), MAP_7(7), MAP_8(8), MAP_9(9), MAP_10(10),
    MAP_11(11), MAP_12(12), MAP_13(13), MAP_14(14), MAP_15(15),
    MAP_16(16), MAP_17(17), MAP_18(18), MAP_19(19), MAP_20(20),
    MAP_21(21), MAP_22(22), MAP_23(23), MAP_24(24), MAP_25(25),
    MAP_26(26), MAP_27(27), MAP_28(28), MAP_29(29), MAP_30(30),
    MAP_31(31), MAP_32(32), MAP_33(33), MAP_34(34), MAP_35(35),
    MAP_36(36), MAP_37(37), MAP_38(38), MAP_39(39), MAP_40(40),
    MAP_41(41), MAP_42(42), MAP_43(43), MAP_44(44), MAP_45(45),
    MAP_46(46), MAP_47(47), MAP_48(48), MAP_49(49);

    public static final int TILE_WIDTH = 25;
    public static final int TILE_HEIGHT = 25;

    private static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("map_first.atlas")); //TODO atlas file
    private static final TextureRegion empty = new TextureRegion(new Texture(new Pixmap(0, 0, Format.Alpha)));

    private int number;

    public static Tiled get(int tileNum) {
        for (Tiled t : Tiled.values()) {
            if(t.getNumber() == tileNum) return t;
        }
        return EMPTY;
    }

    private Tiled(int number) { this.number = number; }

    public int getNumber() { return number; }

    public TextureRegion getTexture() {
        if (number == -1) return empty;
        else {
            TextureRegion region = atlas.findRegion("map" + Integer.toString(number+1));
            if (region != null) return region;
        }
        return empty;
    }
}
