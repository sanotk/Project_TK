package com.mypjgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mypjgdx.esg.MyPjGdxGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Energy Saving Game";
        config.useGL30 = true;
        config.width = 1280;
        config.height = 720;
        config.vSyncEnabled = true;
        config.resizable = false;
        //config.addIcon("assets/globe.png", Files.FileType.Internal);
        new LwjglApplication(new MyPjGdxGame(), config);
    }
}
