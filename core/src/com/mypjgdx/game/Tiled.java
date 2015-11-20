package com.mypjgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Tiled {
    MAP_1(0), MAP_2(1), MAP_3(2), MAP_4(3), MAP_5(4),
    MAP_6(5), MAP_7(6), MAP_8(7), MAP_9(8), MAP_10(9),
    MAP_11(10), MAP_12(11), MAP_13(12), MAP_14(13), MAP_15(14),
    MAP_16(15), MAP_17(16), MAP_18(17), MAP_19(18), MAP_20(19),
    MAP_21(20), MAP_22(21), MAP_23(22), MAP_24(23), MAP_25(24),
    MAP_26(25), MAP_27(26), MAP_28(27), MAP_29(28), MAP_30(29),
    MAP_31(30), MAP_32(31), MAP_33(32), MAP_34(33), MAP_35(34),
    MAP_36(35), MAP_37(36), MAP_38(37), MAP_39(38), MAP_40(39),
    MAP_41(40), MAP_42(41), MAP_43(42), MAP_44(43), MAP_45(44),
    MAP_46(45), MAP_47(46), MAP_48(47), MAP_49(48);

    public static final int TILE_WIDTH = 25;
    public static final int TILE_HEIGHT = 25;

    private static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("")); //TODO atlas file
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
        TextureRegion region = atlas.findRegion("map" + Integer.toString(number+1));
        if (region != null) return region;
        return empty;
    }
}
