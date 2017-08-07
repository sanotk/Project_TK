package com.mypjgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mypjgdx.esg.ashleytest.MyPjGdxGameAshley;

public class DesktopLauncherAshley {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config2 = new LwjglApplicationConfiguration();
        config2.title = "Ashley Test";
        config2.width = 1024;
        config2.height = 576;
        new LwjglApplication(new MyPjGdxGameAshley(), config2);
    }
}
