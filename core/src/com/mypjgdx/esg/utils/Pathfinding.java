package com.mypjgdx.esg.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Pathfinding {

    private TiledMapTileLayer mapLayer;
    private Node start;
    private Node goal;

    private Queue<Node> frontiers;
    private List<Node> oldFrontiers;
    private Map<Node, Node> cameFrom;
    private List<Node> path;
    private List<Node> neighbors;

    private Node[][] nodes;

    public Pathfinding (TiledMapTileLayer mapLayer) {
        this.mapLayer = mapLayer;
        Comparator<Node> comparator = new Comparator<Node> () {
            @Override
            public int compare(Node n1, Node n2) {
                return distanceToGoal(n1)-distanceToGoal(n2);
            }
        };
        frontiers = new PriorityQueue<Node>(10, comparator);

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

    private void init() {
        frontiers.clear();
        oldFrontiers.clear();
        cameFrom.clear();
        path.clear();
    }


    public List<Node> findPath() {
        init();

        frontiers.add(start);
        oldFrontiers.add(start);

        while (!frontiers.isEmpty()) {
            Node current = frontiers.poll();
            if (current.equals(goal))
                break;

            for (Node neighbor: getNeighbors(current)) {
                if(!oldFrontiers.contains(neighbor) && !neighbor.blocked) {
                    frontiers.add(neighbor);
                    oldFrontiers.add(neighbor);
                    cameFrom.put(neighbor, current);
                    Gdx.app.debug("A* Logging", String.format("Create Frontier at: %d, %d", neighbor.i, neighbor.j));
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

    public Node[] getNeighbors(Node node) {

        neighbors.clear();

        for (int i = node.i-1; i <= node.i+1; ++i) {
            for (int j = node.j-1; j <= node.j+1; ++j) {
                if (i == node.i && j == node.j) continue;
                if (i >= 0 && i < mapLayer.getWidth() && j>=0 && j< mapLayer.getHeight()) {
                    neighbors.add(nodes[i][j]);
                }
            }
        }

        return neighbors.toArray(new Node[neighbors.size()]);
    }

    public boolean nodeNearBlocked(Node node) {

        for (Node neighbor : getNeighbors(node)) {
            if(neighbor.blocked)
                return true;
        }

        return false;
    }


    public void setGoal (float x, float y) {
        goal = nodes
                [(int)(x / mapLayer.getTileWidth())]
                [(int)(y / mapLayer.getTileHeight())];
    }

    public void setStart (float x, float y) {
        start = nodes
                [(int)(x / mapLayer.getTileWidth())]
                [(int)(y / mapLayer.getTileHeight())];
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
            return i * mapLayer.getTileWidth() + mapLayer.getTileWidth() / 2 ;
        }
        public float getPositionY() {
            return j * mapLayer.getTileHeight() +  mapLayer.getTileHeight() / 2;
        }

    }

    public int distanceToGoal(Node frontier) {
        return Math.abs(frontier.i -goal.i) + Math.abs(frontier.j - goal.j);
    }
}