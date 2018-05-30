package com.mypjgdx.esg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.mypjgdx.esg.game.Assets;
import com.mypjgdx.esg.game.WorldController;

public class StatusWindow extends Window {

    private Label[] labels = new Label[8];

    public StatusWindow(final WorldController worldController) {
        super("ข้อมูลการใช้พลังงานไฟฟ้า", new WindowStyle(Assets.instance.newFont, Color.WHITE, new NinePatchDrawable(Assets.instance.window)));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.newFont;
        labelStyle.fontColor = Color.WHITE;

        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label("", labelStyle);
        }

        Button closeButton = new CloseButton();
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                worldController.level.player.statusEnergyWindow = false;
                worldController.level.player.timeStop = false;
            }
        });

        setModal(true);
        padTop(45);
        padLeft(40);
        padRight(40);
        padBottom(20);
        getTitleLabel().setAlignment(Align.center);
        row().padBottom(10).padTop(10);
        for (Label label : labels) {
            row().padTop(10);
            add(label);
        }
        row().padTop(10);
        add(closeButton).colspan(3);
        pack();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            if (EnergyProducedBar.instance.energyProduced == 0) {
                String textString1 = ("ยังไม่เริ่มการผลิตพลังงาน");
                labels[0].setText(textString1);
            } else {
                String textString1 = ("กำลังไฟฟ้าผลิต : " + String.valueOf((EnergyProducedBar.instance.energyProduced) + " วัตต์"));
                String textString2 = ("กำลังไฟฟ้าใช้งานรวม : " + String.valueOf(EnergyUsedBar.instance.energyUse) + " วัตต์");
                if (EnergyProducedBar.instance.energyProduced < EnergyUsedBar.instance.energyUse) {
                    String textString3 = ("อีก : " + String.valueOf((int) (BatteryBar.instance.getBatteryStorage() / (((EnergyProducedBar.instance.energyProduced
                            * SunBar.instance.accelerateTime) - (EnergyUsedBar.instance.energyUse * SunBar.instance.accelerateTime)))) + " วินาทีพลังงานจะหมดลง"));
                    labels[2].setText(textString3);
                } else {
                    String textString3 = ("อีก : " + String.valueOf((int) (BatteryBar.instance.getBatteryStorageBlank() / (((EnergyProducedBar.instance.energyProduced
                            * SunBar.instance.accelerateTime) - (EnergyUsedBar.instance.energyUse * SunBar.instance.accelerateTime)))) + " วินาทีพลังงานจะเต็มแบตเตอรี่"));
                    labels[2].setText(textString3);
                }
                String textString4 = ("กำลังไฟฟ้าที่ผลิตได้หลังจากหักลบแล้ว : " + String.valueOf((EnergyProducedBar.instance.energyProduced - EnergyUsedBar.instance.energyUse)) + " วัตต์");
                labels[0].setText(textString1);
                labels[1].setText(textString2);
                labels[3].setText(textString4);
            }
            pack();
            setPosition(
                    getStage().getWidth() / 2 - getWidth() / 2,
                    getStage().getHeight() / 2 - getHeight() / 2);
        }
    }

    public void show(WorldController worldController) {
        worldController.level.player.timeStop = true;
        addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }
}
