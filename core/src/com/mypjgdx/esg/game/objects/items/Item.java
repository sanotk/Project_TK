package com.mypjgdx.esg.game.objects.items;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.collision.TiledCollisionProperty;
import com.mypjgdx.esg.game.objects.AnimatedObject;
import com.mypjgdx.esg.game.objects.Link;
import com.mypjgdx.esg.game.objects.items.Item.ItemAnimation;
import com.mypjgdx.esg.ui.LinkingWindow;
import com.mypjgdx.esg.utils.Direction;
import com.mypjgdx.esg.utils.GameMap;
import com.mypjgdx.esg.utils.Node;
import com.mypjgdx.esg.utils.Pathfinder;

public class Item extends AnimatedObject<ItemAnimation> {

    public enum ItemAnimation {
        ON,
        STARTING,
        OFF
    }

    public enum ItemState {
        ON,
        STARTING,
        OFF
    }

    private ItemState state;

    private LinkingWindow linkingWindow;
    private ObjectSet<Item> linkingItems = new ObjectSet<Item>();
    private ObjectSet<Item> correctLinkingItems = new ObjectSet<Item>();
    private ObjectMap<Item, Array<Link>> connectedLinks = new ObjectMap<Item, Array<Link>>();
    private Array<Link> links;
    private TiledMapTileLayer mapLayer;
    private GameMap gameMap;
    private boolean linkingWindowOpenable;

    private final String name;
    private TiledCollisionProperty collisionProperty;

    private boolean triggered;

    Item(String name, TextureAtlas atlas, float scale, boolean hasOnAnimation,
         TiledCollisionProperty collisionProperty, float frameTime) {
        super(atlas);
        this.scale.set(scale, scale);
        this.name = name;

        addLoopAnimation(ItemAnimation.OFF, frameTime, 0, 1);

        if (hasOnAnimation) {
            addNormalAnimation(ItemAnimation.STARTING, frameTime, 1, 3);
            addLoopAnimation(ItemAnimation.ON, frameTime, 1, 3);
        } else {
            addNormalAnimation(ItemAnimation.STARTING, frameTime, 1, 3);
            addNormalAnimation(ItemAnimation.ON, frameTime, 3, 1);
        }
        this.collisionProperty = collisionProperty;
    }

    public final void init(TiledMapTileLayer mapLayer, float x, float y) {
        this.mapLayer = mapLayer;
        gameMap = new GameMap(mapLayer);
        collisionCheck = new TiledCollisionCheck(bounds, mapLayer);
        state = ItemState.OFF;
        setCurrentAnimation(ItemAnimation.OFF);
        updateBounds();
        setPosition(x, y);
    }

    @Override
    protected void updateAnimation() {
        switch (state) {
            case ON:
                setCurrentAnimation(ItemAnimation.ON);
                break;
            case STARTING:
                setCurrentAnimation(ItemAnimation.STARTING);
                if (isAnimationFinished(ItemAnimation.STARTING)) {
                    state = ItemState.ON;
                }
                break;
            case OFF:
                setCurrentAnimation(ItemAnimation.OFF);
                break;
            default:
                break;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public TiledCollisionProperty getCollisionProperty() {
        return collisionProperty;
    }

    public void turnOn() {
        state = ItemState.STARTING;
        resetAnimation();
    }

    public void linkTo(Item item) {
        linkingItems.add(item);
        item.linkingItems.add(this);
        addLinkTo(item);
    }

    public void unlinkTo(Item item) {
        linkingItems.remove(item);
        item.linkingItems.remove(this);
        for (Link link : connectedLinks.get(item)) {
            links.removeValue(link, true);
        }
        connectedLinks.remove(item);
        item.connectedLinks.remove(this);
    }

    private void addLinkTo(Item item) {
        float startX = bounds.x + bounds.width / 2;
        float startY = bounds.y + bounds.height / 2;
        float endX = item.bounds.x + item.bounds.width / 2;
        float endY = item.bounds.y + item.bounds.height / 2;

        //TODO: need to refactor
        gameMap.updateNeighbors(this, item);
        Pathfinder.instance.setGameMap(gameMap);
        GraphPath<Node> outPath = Pathfinder.instance.findNodePath(startX, startY, endX, endY);

        Array<Link> linkArray = new Array<Link>();
        for (int i = 0; i < outPath.getCount() - 1; i++) {
            final Node node = outPath.get(i);
            final Node nextNode = outPath.get(i + 1);
            Direction nodeDirection;

            if (node.x > nextNode.x && node.y == nextNode.y)
                nodeDirection = Direction.LEFT;
            else if (node.x < nextNode.x && node.y == nextNode.y)
                nodeDirection = Direction.RIGHT;
            else if (node.x == nextNode.x && node.y < nextNode.y)
                nodeDirection = Direction.UP;
            else if (node.x == nextNode.x && node.y > nextNode.y)
                nodeDirection = Direction.DOWN;
            else
                throw new Error("Next node has same position as current node");

            Link link = new Link(mapLayer, node.getCenterPositionX(), node.getCenterPositionY(), nodeDirection);
            this.links.add(link);
            linkArray.add(link);
        }
        connectedLinks.put(item, linkArray);
        item.connectedLinks.put(this, linkArray);
    }

    public void setLinks(Array<Link> links) {
        this.links = links;
    }

    public boolean isLinkedTo(Item item) {
        return linkingItems.contains(item);
    }

    public boolean isCorrectLinked() {
        return linkingItems.equals(correctLinkingItems);
    }

    public void setCorrectLinkingItems(Item... correctLinkingItems) {
        this.correctLinkingItems.clear();
        this.correctLinkingItems.addAll(correctLinkingItems);
    }

    /**
     * @return true if linking windows is opened, otherwise false.
     */
    public boolean showLinkingWindow(Stage stage, LinkingWindow linkingWindow, Array<Item> items) {
        if (!linkingWindowOpenable) return false;
        this.linkingWindow = linkingWindow;
        Array<Item> otherItems = new Array<Item>(items);
        otherItems.removeValue(this, true);
        linkingWindow.setLinkingItem(this, otherItems);
        linkingWindow.show(stage);
        return true;
    }

    public void hideLinkingWindow() {
        linkingWindow.hide();
        linkingWindow = null;
    }

    public void setLinkingWindowOpenable(boolean linkingWindowOpenable) {
        this.linkingWindowOpenable = linkingWindowOpenable;
    }

    public void trigger() {
        triggered = true;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public ItemState getState() {
        return state;
    }
}
