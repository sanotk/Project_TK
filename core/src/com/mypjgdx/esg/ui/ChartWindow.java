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
import com.mypjgdx.esg.game.objects.characters.Player;
import com.mypjgdx.esg.screens.GameScreen;

public class ChartWindow extends Window {

    private Label[] labels = new Label[7];

    public ChartWindow(final GameScreen gameScreen, final WorldController worldController) {
        super("ยินดีด้วย คุณได้รับชัยชนะ", new WindowStyle(Assets.instance.newFont, Color.WHITE,
                new NinePatchDrawable(Assets.instance.window)));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.instance.newFont;
        labelStyle.fontColor = Color.WHITE;

        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label("", labelStyle);
        }
        labels[0].setText("สถิติ");

        Button closeButton = new CloseButton();
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.visible(false)));
                gameScreen.stageFourClear = true;
                worldController.level.player.timeStop = true;
                String text = "\"ยินดีต้อนรับสู่่สถานที่หลบภัย\" \n\" (กรุณากด Enter เพื่อไปยังด่านถัดไป หรือกด ESC เพื่อบันทึกและกลับไปหน้าเมนู)\"";
                gameScreen.dialog.show();
                gameScreen.dialog.clearPages();
                gameScreen.dialog.addWaitingPage(text);
            }
        });

        setModal(true);
        padTop(45);
        padLeft(40);
        padRight(40);
        padBottom(20);
        getTitleLabel().setAlignment(Align.center);
        for (Label label : labels) {
            row().padTop(10);
            add(label);
        }
        row().padTop(10);
        add(closeButton).colspan(3);
        pack();
    }

    public void show(WorldController worldController, int enemyKilled) {
        Player player = worldController.level.player;
        player.timeStop = true;
        player.timeClear = true;
        String text1 = "เวลาที่ใช้ : " + (player.getIntitalTime() - player.timeCount) + " วินาที";
        String text2 = "มอนสเตอร์ที่ถูกกำจัด : " + enemyKilled + " ตัว";
        String text3 = "กำลังไฟฟ้าผลิต : " + EnergyProducedBar.instance.energyProduced + " วัตต์";
        String text4 = "กำลังไฟฟ้าใช้งานรวม : " + EnergyUsedBar.instance.energyUse + " วัตต์";
        String text5 = "พลังงานไฟฟ้าที่ได้รับจากมอนสเตอร์ : " + enemyKilled * 1000 + " จูล";
        String text6 = "ความพอใจของประชาชน : " + LikingBar.instance.liking;
        labels[1].setText(text1);
        labels[2].setText(text2);
        labels[3].setText(text3);
        labels[4].setText(text4);
        labels[5].setText(text5);
        labels[6].setText(text6);
        labels[1].setText(text1);
        pack();
        setPosition(
                getStage().getWidth() / 2 - getWidth() / 2,
                getStage().getHeight() / 2 - getHeight() / 2);
        addAction(Actions.sequence(Actions.visible(true), Actions.fadeIn(0.2f)));
    }
}
