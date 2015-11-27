package com.mypjgdx.esg.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mypjgdx.esg.game.Assets;

public enum Tiles { //กำหนดไอดีให้ตรงกับภาพในโปรแกรม Tiled

    MAP_1("map01", 38),
    MAP_2("map02", 0),
    MAP_3("map03", 39),
    MAP_4("map04", 1),
    MAP_5("map05", 40),
    MAP_6("map06", 2),
    MAP_7("map07", 41),
    MAP_8("map08", 3),
    MAP_9("map09", 42),
    MAP_10("map10", 4),
    MAP_11("map11", 43),
    MAP_12("map12", 5),
    MAP_13("map13", 44),
    MAP_14("map14", 6),
    MAP_15("map15", 45),
    MAP_16("map16", 7),
    MAP_17("map17", 46),
    MAP_18("map18", 8),
    MAP_19("map19", 47),
    MAP_20("map20", 9),
    MAP_21("map21", 48),
    MAP_22("map22", 10),
    MAP_23("map23", 49),
    MAP_24("map24", 11),
    MAP_25("map25", 50),
    MAP_26("map26", 12),
    MAP_27("map27", 51),
    MAP_28("map28", 13),
    MAP_29("map29", 52),
    MAP_30("map30", 14),
    MAP_31("map31", 53),
    MAP_32("map32", 15),
    MAP_33("map33", 54),
    MAP_34("map34", 16),
    MAP_35("map35", 55),
    MAP_36("map36", 17),
    MAP_37("map37", 56),
    MAP_38("map38", 18),
    MAP_39("map39", 57),
    MAP_40("map40", 19),
    MAP_41("map41", 58),
    MAP_42("map42", 20),
    MAP_43("map43", 59),

    EMPTY(-1);

    public static final int TILE_WIDTH = 25; //ขนาดของภาพ 25px * 25px
    public static final int TILE_HEIGHT = 25;

    private String regionName;
    private int id;

    public static Tiles get(int id) { //รับไอดีมา
        for (Tiles t : Tiles.values()) {
            if(t.getId() == id) return t;
        }
        return EMPTY;
    }

    private Tiles(int id) {
        this("", id);
    }

    private Tiles(String regionName, int id) {
        this.regionName = regionName;
        this.id = id;
    }

    private int getId() {
        return id;
    }

    public void render(SpriteBatch batch, int x, int y) {
        TextureRegion region = Assets.instance.levelAtlas.findRegion(regionName);
        if (region != null) batch.draw(region, x, y);
    }

}
