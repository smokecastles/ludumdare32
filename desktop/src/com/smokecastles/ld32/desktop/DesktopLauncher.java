package com.smokecastles.ld32.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smokecastles.ld32.utils.Constants;
import com.smokecastles.ld32.LD32Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width 	= Constants.WINDOW_WIDTH;
		config.height 	= Constants.WINDOW_HEIGHT;
		config.title 	= Constants.GAME_TITLE;
		new LwjglApplication(new LD32Game(), config);
	}
}
