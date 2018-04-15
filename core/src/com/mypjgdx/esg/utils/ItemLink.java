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
import com.mypjgdx.esg.game.objects.etcs.Link;

import java.util.ArrayList;
import java.util.List;

public class ItemLink {

    private IndexedAStarPathFinder<Node> pathFinder;
    private GameMap gameMap;
    private DefaultStateMachine<Enemy, EnemyState> stateMachine;

    private Node startNode;
    private Node endNode;

    public ArrayList<Node> nodes = new ArrayList<Node>();

    public float startX,startY,goalX,goalY;

    public SolarState solarState = null;

    private Direction direction;

    public ItemLink(TiledMapTileLayer mapLayer, float startX, float startY, float boundStartX, float boundStartY, float goalX, float goalY, float boundGoalX, float boundGoalY, List<Etc> etcList, SolarState solarState) {

        this.startX = startX + boundStartX/2;
        this.startY = startY + boundStartY/2;
        this.goalX = goalX + boundGoalX/2;
        this.goalY = goalY + boundGoalY/2;

        System.out.print(boundStartX);
        System.out.print(boundStartY);


        this.solarState = solarState;

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
        //pathOutput.reverse();

        for (int i = 0; i< pathOutput.getCount(); i++) {
            nodes.add(pathOutput.get(i));
            System.out.print(nodes.get(i));
            if((nodes.get(i).getPositionY() < endNode.getPositionY())) direction = Direction.RIGHT;
            else if(nodes.get(i).getPositionY() > endNode.getPositionY()) direction = Direction.LEFT;
            else if(nodes.get(i).getPositionX() < endNode.getPositionX()) direction = Direction.UP;
            else if(nodes.get(i).getPositionX() > endNode.getPositionX()) direction = Direction.DOWN;
            /*System.out.print(nodes.get(i).getPositionX());
            System.out.print(nodes.get(i).getPositionY());
            System.out.print(startNode.getPositionX());
            System.out.print(startNode.getPositionY());
            System.out.print(endNode.getPositionX());
            System.out.print(endNode.getPositionY());
            System.out.print(direction);
            */
            etcList.add(new Link(mapLayer, nodes.get(i).getPositionX(),nodes.get(i).getPositionY(),direction));
        }
    }

}
