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

    public List<Etc> etcList;

    public ArrayList<Node> nodes = new ArrayList<Node>();

    public float startX,startY,goalX,goalY;

    public SolarState solarState = null;

    private Direction direction;

    public ItemLink(TiledMapTileLayer mapLayer, float startX, float startY, float startWidth, float startHeight, float goalX, float goalY, float goalWidth, float goalHeight, List<Etc> etcList, SolarState solarState) {

        this.startX = startX + startWidth/2;
        this.startY = startY + startHeight/2;
        this.goalX = goalX + goalWidth/2;
        this.goalY = goalY + goalHeight/2;
        this.etcList = etcList;

        System.out.print(goalHeight);
        System.out.print(goalWidth);
        System.out.print(startHeight);
        System.out.print(startWidth);

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
        startNode = gameMap.getNode(this.startX, this.startY);
        endNode =gameMap.getNode(this.goalX, this.goalY);

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);
        //pathOutput.reverse();

        for (int i = 0; i< pathOutput.getCount()-1; i++) {
            nodes.add(pathOutput.get(i));
            System.out.print(nodes.get(i));
            if((nodes.get(i).getPositionY() < pathOutput.get(i+1).getPositionY())
                    &&(nodes.get(i).getPositionX() <= pathOutput.get(i+1).getPositionX())) direction = Direction.RIGHT;
            else if((nodes.get(i).getPositionY() > pathOutput.get(i+1).getPositionY())
                    &&(nodes.get(i).getPositionX() >= pathOutput.get(i+1).getPositionX())) direction = Direction.LEFT;
            else if((nodes.get(i).getPositionX() < pathOutput.get(i+1).getPositionX())
                    &&(nodes.get(i).getPositionY() <= pathOutput.get(i+1).getPositionY())) direction = Direction.UP;
            else if((nodes.get(i).getPositionX() > pathOutput.get(i+1).getPositionX())
                    &&(nodes.get(i).getPositionY() >= pathOutput.get(i+1).getPositionY())) direction = Direction.DOWN;
            this.etcList.add(new Link(mapLayer, nodes.get(i).getPositionX()+22,nodes.get(i).getPositionY(),direction,this.solarState));
        }
    }

}
