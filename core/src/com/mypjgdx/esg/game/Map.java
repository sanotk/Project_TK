package com.mypjgdx.esg.game;

import com.badlogic.gdx.maps.tiled.TiledMap;

public enum Map {

    MAP_01, MAP_02, MAP_03;

    private static Map[] vals = values();

    public static Map getMap(TiledMap tileMap) {
        for (Map map : vals) {
            if (map.getTiledMap().equals(tileMap))
                return map;
        }
        return null;
    }

    public TiledMap getTiledMap() {
        switch(this) {
        case MAP_01: return Assets.instance.map1;
        case MAP_02: return Assets.instance.map2;
        case MAP_03: return Assets.instance.map3;
        default:
            break;
        }
        return null;
    }

    public Map next() {
        return vals[(this.ordinal()+1) % vals.length];
    }

}
