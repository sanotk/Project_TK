package com.mypjgdx.esg.utils;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.objects.etcs.Link;
import com.mypjgdx.esg.game.objects.items.Item;

import java.util.ArrayList;

public class ItemLink {

    private ItemLink() {
    }

    public static void linkItem(WorldController worldController, Item item1, Item item2) {
        linkItem(worldController, item1, item2, null);
    }

    public static void linkItem(WorldController worldController, Item item1, Item item2, SolarState solarState) {
        float startX = item1.bounds.x + item1.bounds.width / 2;
        float startY = item1.bounds.y + item1.bounds.height / 2;
        float goalX = item2.bounds.x + item2.bounds.width / 2;
        float goalY = item2.bounds.y + item2.bounds.height / 2;

        GameMap gameMap = new GameMap(worldController.level.mapLayer);
        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<Node>(gameMap);

        GraphPath<Node> pathOutput = new DefaultGraphPath<Node>();
        Heuristic<Node> heuristic = new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                return 1;
            }
        };

        gameMap.updateNeighbors(item1, item2);

        Node startNode = gameMap.getNode(startX, startY);
        Node endNode = gameMap.getNode(goalX, goalY);

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);

        ArrayList<Node> nodes = new ArrayList<Node>();
        Direction direction;

        for (int i = 0; i < pathOutput.getCount() - 1; i++) {
            Node currentNode = pathOutput.get(i);
            Node nextNode = pathOutput.get(i + 1);
            nodes.add(currentNode);

            if ((currentNode.getPositionY() < nextNode.getPositionY())
                    && (currentNode.getPositionX() <= nextNode.getPositionX()))
                direction = Direction.RIGHT;
            else if ((currentNode.getPositionY() > nextNode.getPositionY())
                    && (currentNode.getPositionX() >= nextNode.getPositionX()))
                direction = Direction.LEFT;
            else if ((currentNode.getPositionX() < nextNode.getPositionX())
                    && (currentNode.getPositionY() <= nextNode.getPositionY()))
                direction = Direction.UP;
            else if ((currentNode.getPositionX() > nextNode.getPositionX())
                    && (currentNode.getPositionY() >= nextNode.getPositionY()))
                direction = Direction.DOWN;
            else
                throw new Error("No link direction!");

            worldController.level.links.add(
                    new Link(worldController.level.mapLayer,
                    nodes.get(i).getPositionX() + 22,
                            nodes.get(i).getPositionY(),
                            direction,
                            solarState));
        }
    }
}
