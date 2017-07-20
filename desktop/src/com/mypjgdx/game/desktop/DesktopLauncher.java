package com.mypjgdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mypjgdx.esg.MyPjGdxGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Energy Saving Game";
        config.width = 480;
        config.height = 800;
        config.addIcon("icon.png", Files.FileType.Internal);
        new LwjglApplication(new MyPjGdxGame(), config);
    }
}
