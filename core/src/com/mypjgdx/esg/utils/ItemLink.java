package com.mypjgdx.esg.utils;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mypjgdx.esg.game.objects.etcs.Link;
import com.mypjgdx.esg.game.objects.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemLink {

    private IndexedAStarPathFinder<Node> pathFinder;
    private GameMap gameMap;

    private Node startNode;
    private Node endNode;

    public List<Link> linkList;

    public ArrayList<Node> nodes = new ArrayList<Node>();

    public float startX,startY,goalX,goalY;

    private SolarState solarState = null;

    private Direction direction;

    public ItemLink(TiledMapTileLayer mapLayer, Item item1, Item item2, List<Link> linkList, SolarState solarState) {

        this.startX = item1.bounds.x + item1.bounds.width/2;
        this.startY = item1.bounds.y + item1.bounds.height/2;
        this.goalX = item2.bounds.x + item2.bounds.width/2;
        this.goalY = item2.bounds.y + item2.bounds.height/2;
        this.linkList = linkList;

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

        gameMap.updateNeighbors(item1, item2); //TODO

        startNode = gameMap.getNode(this.startX, this.startY);
        endNode =gameMap.getNode(this.goalX, this.goalY);

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);

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
            this.linkList.add(new Link(mapLayer, nodes.get(i).getPositionX()+22,nodes.get(i).getPositionY(),direction,this.solarState));
        }
    }

    public ItemLink(TiledMapTileLayer mapLayer, Item item1, Item item2, List<Link> linkList) {

        this.startX = item1.bounds.x + item1.bounds.width/2;
        this.startY = item1.bounds.y + item1.bounds.height/2;
        this.goalX = item2.bounds.x + item2.bounds.width/2;
        this.goalY = item2.bounds.y + item2.bounds.height/2;
        this.linkList = linkList;

        gameMap = new GameMap(mapLayer);
        pathFinder = new IndexedAStarPathFinder<Node>(gameMap);

        GraphPath<Node> pathOutput = new DefaultGraphPath<Node>();
        Heuristic<Node> heuristic = new Heuristic<Node>() {
            @Override
            public float estimate(Node node, Node endNode) {
                return 1;
            }
        };

        gameMap.updateNeighbors(item1, item2); //TODO

        startNode = gameMap.getNode(this.startX, this.startY);
        endNode =gameMap.getNode(this.goalX, this.goalY);

        pathFinder.searchNodePath(startNode, endNode, heuristic, pathOutput);

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
            this.linkList.add(new Link(mapLayer, nodes.get(i).getPositionX()+22,nodes.get(i).getPositionY(),direction,this.solarState));
        }
    }

}
