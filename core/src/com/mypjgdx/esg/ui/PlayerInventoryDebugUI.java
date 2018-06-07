package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.game.objects.items.drop.DroppedItemType;

public class PlayerInventoryDebugUI extends Table {

    private Label[] labels = new Label[3];

    public PlayerInventoryDebugUI(final Player player) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Assets.instance.newFont;
        style.fontColor = Color.BLACK;

        setFillParent(true);
        left().bottom();

        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label("", style);
            row().left();
            add(labels[i]);
        }

        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                labels[0].setText("สายไฟ: " + player.findDroppedItemCount(DroppedItemType.LINK));
                labels[1].setText("กังหัน: " + player.findDroppedItemCount(DroppedItemType.TURBINE));
                labels[2].setText("เเครื่องกำเนิดไฟฟ้า: " + player.findDroppedItemCount(DroppedItemType.GENERATOR));
                return false;
            }
        });
    }
}
