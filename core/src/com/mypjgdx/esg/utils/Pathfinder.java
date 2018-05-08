package com.mypjgdx.esg.utils;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;

public class Pathfinder {

    public static final Pathfinder instance = new Pathfinder();

    private IndexedAStarPathFinder<Node> finder;
    private GameMap gameMap;
    private GraphPath<Node> outNodePath = new DefaultGraphPath<Node>();

    private final Heuristic<Node> heuristic = new Heuristic<Node>() {
        @Override
        public float estimate(Node node, Node endNode) {
            return (float) (Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.x));
        }
    };

    private Pathfinder() {
    }

    public GraphPath<Node> findNodePath(float startX, float startY, float endX, float endY) {
        final Node startNode = gameMap.getNode(startX, startY);
        final Node endNode = gameMap.getNode(endX, endY);

        outNodePath.clear();
        finder.searchNodePath(startNode, endNode, heuristic, outNodePath);
        return outNodePath;
    }

    public void setGameMap(GameMap gameMap) {
        finder = new IndexedAStarPathFinder<Node>(gameMap);
        this.gameMap = gameMap;
    }
}
