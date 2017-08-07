package com.mypjgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mypjgdx.esg.AshleyTest;

public class DesktopLauncher {
    public static void main (String[] arg) {
//        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.title = "Energy Saving Game";
//        config.width = 1024;
//        config.height = 576;
//        config.addIcon("icon.png", Files.FileType.Internal);
//        new LwjglApplication(new MyPjGdxGame(), config);

        LwjglApplicationConfiguration config2 = new LwjglApplicationConfiguration();
        config2.title = "Ashley Test";
        config2.width = 1024;
        config2.height = 576;
        new LwjglApplication(new AshleyTest(), config2);
    }
}
