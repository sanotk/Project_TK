package com.mypjgdx.esg.utils;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class Pathfinding {

    private TiledMapTileLayer mapLayer;
    private Node start;
    private Node goal;

    private Queue<Node> frontiers;
    private ObjectMap<Node, Node> cameFrom;
    private Array<Node> path;
    private Array<Node> neighbors;
    private ObjectMap<Node, Integer> costSoFar;

    private Node[][] nodes;

    public Pathfinding (TiledMapTileLayer mapLayer) {
        this.mapLayer = mapLayer;
        Comparator<Node> comparator = new Comparator<Node> () {
            @Override
            public int compare(Node n1, Node n2) {
                return (distanceToGoal(n1)+costSoFar.get(n1)) - (distanceToGoal(n2)+costSoFar.get(n2));
            }
        };
        frontiers = new PriorityQueue<Node>(100, comparator);
        cameFrom = new ObjectMap<Node, Node>();
        path = new Array<Node>();
        neighbors = new Array<Node>();
        costSoFar = new ObjectMap<Node, Integer>();
        nodes = new Node[mapLayer.getWidth()][mapLayer.getHeight()];

        createNode();
    }

    public void createNode() {
        for(int i = 0; i< mapLayer.getWidth() ;i++){
            for(int j = 0; j<mapLayer.getHeight(); j++) {
                nodes[i][j] = new Node(i, j, mapLayer.getCell(i, j).getTile().getProperties().containsKey("blocked"));
            }
        }
        for (int i = 0; i< mapLayer.getWidth() ;i++){
            for (int j = 0; j<mapLayer.getHeight(); j++) {
               if (nodeNearBlocked(nodes[i][j]))
                   nodes[i][j].cost = 99;
            }
        }
    }

    private void init() {
        frontiers.clear();
        costSoFar.clear();
        cameFrom.clear();
        path.clear();
    }


    public Array<Node> findPath (float startX, float startY, float goalX, float goalY) {
        init();

        setStart(startX, startY);
        setGoal(goalX, goalY);

        costSoFar.put(start, 0);
        frontiers.add(start);

        while (!frontiers.isEmpty()) {
            Node current = frontiers.poll();
            if (current.equals(goal))
                break;

            for (Node neighbor: getNeighbors(current)) {
                int newCost = costSoFar.get(current) + neighbor.cost;
                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    if (!neighbor.blocked) {
                        costSoFar.put(neighbor, newCost);
                        frontiers.add(neighbor);
                        cameFrom.put(neighbor, current);
                    }
                }
            }
        }
        Node current  = goal;
        path.add(current);

        while(!current.equals(start)) {
            current = cameFrom.get(current);
            path.add(current);
        }

        path.pop();
        path.reverse();
        return path;
    }

    public Array<Node> getNeighbors(Node node) {

        neighbors.clear();

        for (int i = node.i-1; i <= node.i+1; ++i) {
            for (int j = node.j-1; j <= node.j+1; ++j) {
                if (i == node.i && j == node.j) continue;
                if (i >= 0 && i < mapLayer.getWidth() && j>=0 && j< mapLayer.getHeight()) {
                    neighbors.add(nodes[i][j]);
                }
            }
        }

        return neighbors;
    }

    public boolean nodeNearBlocked(Node node) {
        for (Node neighbor : getNeighbors(node)) {
            if (neighbor.blocked)
                return true;
        }
        return false;
    }

    private void setGoal (float x, float y) {
        goal = nodes
                [(int)(x / mapLayer.getTileWidth())]
                [(int)(y / mapLayer.getTileHeight())];
    }

    private void setStart (float x, float y) {
        start = nodes
                [(int)(x / mapLayer.getTileWidth())]
                [(int)(y / mapLayer.getTileHeight())];
    }

    public class Node {
        public int i;
        public int j;
        public boolean blocked;
        public int cost;

        public Node(int i, int j, boolean blocked) {
            this.i = i;
            this.j = j;
            this.blocked = blocked;
            cost = 1;
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