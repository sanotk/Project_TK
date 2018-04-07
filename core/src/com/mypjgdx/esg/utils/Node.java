package com.mypjgdx.esg.utils;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.utils.Array;

public class Node {
    int x;
    int y;
    int index;
    boolean blocked;
    int cost;
    GameMap map;

    public final Array<Connection<Node>> neighbors;

    public Node(GameMap map, int x, int y, boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
        this.map = map;
        index = x * map.getHeight() + y;
        cost = 1;


        neighbors = new Array<Connection<Node>>();
    }

    public void updateConnections(GameMap map) {
        neighbors.clear();

        if (x > 0) {
            Node leftNode = map.getNodes()[x - 1][y];
            neighbors.add(new DefaultConnection<Node>(this, leftNode));
        }
        if (x < map.getWidth() - 1) {
            Node rightNode = map.getNodes()[x + 1][y];
            neighbors.add(new DefaultConnection<Node>(this, rightNode));
        }
        if (y > 0) {
            Node bottomNode = map.getNodes()[x][y - 1];
            neighbors.add(new DefaultConnection<Node>(this, bottomNode));
        }
        if (y < map.getHeight() - 1) {
            Node topNode = map.getNodes()[x][y + 1];
            neighbors.add(new DefaultConnection<Node>(this, topNode));
        }
    }

    public float getPositionX() {
        return x * map.getMapLayer().getTileWidth();
    }

    public float getPositionY() {
        return y * map.getMapLayer().getTileHeight();
    }
}
