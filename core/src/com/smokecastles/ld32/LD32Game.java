package com.smokecastles.ld32;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smokecastles.ld32.framework.Assets;

public class LD32Game extends Game {
	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.load();
		setScreen(new GameScreen(this));
	}
}
