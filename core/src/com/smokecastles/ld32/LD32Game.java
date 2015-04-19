package com.smokecastles.ld32;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smokecastles.ld32.utils.Assets;
import com.smokecastles.ld32.view.MainMenu;

public class LD32Game extends Game {
	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.load();
		setScreen(new MainMenu(this));
	}
}
