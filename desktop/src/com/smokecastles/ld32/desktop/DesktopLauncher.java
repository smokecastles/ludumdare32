package com.smokecastles.ld32.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smokecastles.ld32.utils.Constants;
import com.smokecastles.ld32.LD32Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("icons/icon256.png", Files.FileType.Internal);
		config.addIcon("icons/icon64.png", Files.FileType.Internal);
		config.addIcon("icons/icon32.png", Files.FileType.Internal);
		config.width 	= Constants.WINDOW_WIDTH;
		config.height 	= Constants.WINDOW_HEIGHT;
		config.title 	= Constants.GAME_TITLE;
		new LwjglApplication(new LD32Game(), config);
	}
}
