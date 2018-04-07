package com.mypjgdx.esg.utils;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

public class GameMap implements IndexedGraph<Node> {

    private Node[][] nodes;

    private int width;
    private int height;

    private TiledMapTileLayer mapLayer;

    public GameMap(TiledMapTileLayer mapLayer) {
        this.width = mapLayer.getWidth();
        this.height = mapLayer.getHeight();
        this.mapLayer = mapLayer;
        nodes = new Node[width][height];
        createNode();
    }

    public void createNode() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                nodes[i][j] = new Node(this, i, j, mapLayer.getCell(i, j).getTile().getProperties().containsKey("blocked"));
            }
        }
    }

    public void updateNeibors() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                nodes[i][j].updateConnections(this);
            }
        }
    }

    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return width * height;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.neighbors;
    }

    public Node[][] getNodes() {
        return nodes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Node getNode(float x, float y) {
        return nodes
                [(int) (x / mapLayer.getTileWidth())]
                [(int) (y / mapLayer.getTileHeight())];
    }

    public TiledMapTileLayer getMapLayer() {
        return mapLayer;
    }
}
