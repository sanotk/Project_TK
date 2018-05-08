package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.objects.items.Item;

public class LinkingWindow extends CustomWindow {

    private TextButton[] linkingButton = new TextButton[4];

    private TextButton.TextButtonStyle linkButtonStyle = new TextButton.TextButtonStyle();
    private TextButton.TextButtonStyle cancelButtonStyle = new TextButton.TextButtonStyle();

    public LinkingWindow() {
        super("Choice", style());

        Button.ButtonStyle closeButtonStyle = new Button.ButtonStyle();
        TextureRegionDrawable buttonRegion = new TextureRegionDrawable(Assets.instance.uiBlue.findRegion("button_cross"));
        closeButtonStyle.up = buttonRegion;
        closeButtonStyle.down = buttonRegion.tint(Color.LIGHT_GRAY);

        Button closeButton = new Button(closeButtonStyle);

        linkButtonStyle.up = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_04"));
        linkButtonStyle.down = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("button_03"));
        linkButtonStyle.font = Assets.instance.font;

        cancelButtonStyle.up = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_04"));
        cancelButtonStyle.down = new NinePatchDrawable(Assets.instance.uiRed.createPatch("button_03"));
        cancelButtonStyle.font = Assets.instance.font;

        for (int i = 0; i < 4; i++) {
            linkingButton[i] = new TextButton("", linkButtonStyle);
            linkingButton[i].getLabel().setEllipsis(true);
        }

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        setModal(true);
        padTop(40);
        padLeft(40);
        padRight(40);
        padBottom(20);
        getTitleLabel().setAlignment(Align.center);
        row().padBottom(10).padTop(20);
        add(linkingButton[0]).width(180);
        add(linkingButton[1]).width(180).padLeft(20);
        row().padTop(10);
        add(linkingButton[2]).width(180);
        add(linkingButton[3]).width(180).padLeft(20);
        row().padTop(20);
        add(closeButton).colspan(2);
        pack();
    }

    private static WindowStyle style() {
        WindowStyle style = new WindowStyle();
        style.background = new NinePatchDrawable(Assets.instance.uiBlue.createPatch("window_01"));
        style.titleFont = Assets.instance.font;

        return style;
    }

    public void setLinkingItem(Item item, Array<Item> itemsToLink) {
        for (int i = 0; i < 4; i++) {
            updateButton(linkingButton[i], item, itemsToLink.get(i));
        }
    }

    private void updateButton(final TextButton button, final Item item, final Item itemToLink) {
        final boolean isLinked = item.isLinkedTo(itemToLink);
        String text = (!isLinked ? "Link To " : "Cancel Link To ") + itemToLink.getName();
        TextButton.TextButtonStyle style = !isLinked ? linkButtonStyle : cancelButtonStyle;

        button.clearListeners();
        button.setText(text);
        button.getLabelCell().width(button.getWidth());
        button.setStyle(style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isLinked) {
                    item.unlinkTo(itemToLink);
                } else {
                    item.linkTo(itemToLink);
                }
                updateButton(button, item, itemToLink);
                hide();
            }
        });
    }
}
