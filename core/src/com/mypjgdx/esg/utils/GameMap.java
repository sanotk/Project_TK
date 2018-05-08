package com.mypjgdx.esg.utils;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.game.objects.items.Item;

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

    private void createNode() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                nodes[i][j] = new Node(this, i, j, mapLayer.getCell(i, j).getTile().getProperties().containsKey("blocked"));
            }
        }
    }

    public void updateNeighbors() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                nodes[i][j].updateConnections(this);
            }
        }
    }

    public void updateNeighbors(Item item1, Item item2) {
        setItemBlocked(item1, false);
        setItemBlocked(item2, false);

        updateNeighbors();

        setItemBlocked(item1, true);
        setItemBlocked(item2, true);
    }

    private void setItemBlocked(Item item, boolean blocked) {
        int tiledCountX = Math.round(item.bounds.width / mapLayer.getTileWidth());
        int tiledCountY = Math.round(item.bounds.height / mapLayer.getTileHeight());
        for (int i = 0; i < tiledCountX; i++) {
            for (int j = 0; j < tiledCountY ; j++) {
                float x = item.bounds.x + i *  mapLayer.getTileWidth();
                float y = item.bounds.y + j *  mapLayer.getTileHeight();
                Node node = getNode(x, y);
                node.blocked = blocked;
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
        final int nodeX = (int) (x / mapLayer.getTileWidth());
        final int nodeY = (int) (y / mapLayer.getTileHeight());
        return nodes[nodeX][nodeY];
    }

    public TiledMapTileLayer getMapLayer() {
        return mapLayer;
    }
}
