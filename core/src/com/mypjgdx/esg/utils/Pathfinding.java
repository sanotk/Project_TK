package com.mypjgdx.esg.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Pathfinding {

    private TiledMapTileLayer mapLayer;
    private Node start;
    private Node goal;

    private List<Node> frontiers;
    private List<Node> oldFrontiers;
    private Map<Node, Node> cameFrom;
    private List<Node> path;
    private List<Node> neighbors;

    private Node[][] nodes;

    public Pathfinding (TiledMapTileLayer mapLayer) {
        this.mapLayer = mapLayer;
        frontiers = new ArrayList<Node>();
        oldFrontiers = new ArrayList<Node>();
        cameFrom = new HashMap<Node, Node>();
        path = new ArrayList<Node>();
        neighbors = new ArrayList<Node>();
        nodes = new Node[mapLayer.getWidth()][mapLayer.getHeight()];

        createNode();
    }

    public void createNode() {
        for(int i = 0; i< mapLayer.getWidth() ;i++){
            for(int j = 0; j<mapLayer.getHeight(); j++) {
                nodes[i][j] = new Node(i, j, mapLayer.getCell(i, j).getTile().getProperties().containsKey("blocked"));
            }
        }
    }


    public List<Node> findPath() {

    	path.clear();

        frontiers.add(start);
        oldFrontiers.add(start);

        while (!frontiers.isEmpty()) {
            Node current = frontiers.remove(0);
            if (current.equals(goal))
                break;

            for (Node neighbor: getNeighbors(current)) {
                if(!oldFrontiers.contains(neighbor) && !neighbor.blocked) {
                    frontiers.add(neighbor);
                    oldFrontiers.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        Node current  = goal;
        path.add(current);

        while(!current.equals(start)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    public List<Node> getNeighbors(Node node) {

        neighbors.clear();

        int i = node.i;
        int j = node.j;

        if (i-1 >= 0 && !nodes[i-1][j].blocked) neighbors.add(nodes[i-1][j]);
        if (j-1 >= 0 && !nodes[i][j-1].blocked)  neighbors.add(nodes[i][j-1]);
        if (i+1 < mapLayer.getWidth() && !nodes[i+1][j].blocked) neighbors.add(nodes[i+1][j]);
        if (j+1 < mapLayer.getHeight() && !nodes[i][j+1].blocked) neighbors.add(nodes[i][j+1]);

        return neighbors;
    }

    public void setGoal (Vector2 goalPosition) {
        goal = nodes
                [(int)(goalPosition.x / mapLayer.getTileWidth())]
                [(int)(goalPosition.y / mapLayer.getTileHeight())];
    }

    public void setStart (Vector2 startPosition) {
        start = nodes
                [(int)(startPosition.x / mapLayer.getTileWidth())]
                [(int)(startPosition.y / mapLayer.getTileHeight())];
    }

    public class Node {
        public int i;
        public int j;
        public boolean blocked;

        public Node(int i, int j, boolean blocked) {
            this.i = i;
            this.j = j;
            this.blocked = blocked;
        }

        public float getPositionX() {
            return i * mapLayer.getTileWidth() - mapLayer.getTileWidth() / 2 ;
        }
        public float getPositionY() {
            return j * mapLayer.getTileHeight() -  mapLayer.getTileHeight() / 2;
        }

    }

	boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}