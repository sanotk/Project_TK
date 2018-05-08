package com.mypjgdx.esg.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mypjgdx.esg.MyPjGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Energy Saving Game";
		config.width = 1024;
		config.height = 576;
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new MyPjGdxGame(), config); //สร้าง application platform desktop
	}
}
