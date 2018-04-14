package com.mypjgdx.esg.utils;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.objects.characters.Enemy;
import com.mypjgdx.esg.game.objects.characters.EnemyState;
import com.mypjgdx.esg.game.objects.etcs.Etc;

import java.util.ArrayList;

public class ItemLink {

    private IndexedAStarPathFinder<Node> pathFinder;
    private GameMap gameMap;
    private DefaultStateMachine<Enemy, EnemyState> stateMachine;

    private Node startNode;
    private Node endNode;

    public ArrayList<Node> nodes = new ArrayList<Node>();

    private float startX,startY,goalX,goalY;

    public ItemLink(TiledMapTileLayer mapLayer, float startX, float startY, float goalX , float goalY , Etc link) {

        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;

        gameMap = new GameMap(mapLayer);
        pathFinder = new IndexedAStarPathFinder<Node>(gameMap);

        GraphPath<Node> pathOutput = new DefaultGraphPath<Node>();
        Heuristic<Node> heuristic = new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                return 1;
            }
        };

        gameMap.updateNeibors(); //TODO
        startNode = gameMap.getNode(startX, startY);
        endNode =gameMap.getNode(goalX, goalY);

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);
        pathOutput.reverse();

        for (int i = 0; i< pathOutput.getCount(); i++) {
            nodes.add(pathOutput.get(i));
            System.out.print(nodes.get(i));
        }
    }

}
