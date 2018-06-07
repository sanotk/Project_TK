package com.mypjgdx.esg.game.objects.items.drop;

import com.mypjgdx.esg.game.Assets;

public enum DroppedItemType {
    LINK,
    TURBINE,
    GENERATOR;

    public DroppedItem spawn() {
        switch (this) {
            case LINK:
                return new DroppedItem(this, Assets.instance.droppedLink);
            case TURBINE:
                return new DroppedItem(this, Assets.instance.droppedTurbine);
            case GENERATOR:
                return new DroppedItem(this, Assets.instance.droppedGenerator);
            default:
                throw new IllegalStateException("Dropped Item Spawner Error!");
        }
    }


}
