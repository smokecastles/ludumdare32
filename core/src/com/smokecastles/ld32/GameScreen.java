package com.smokecastles.ld32;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by juanma on 18/04/15.
 */
public class GameScreen implements Screen {
    static final int GAME_RUNNING = 0;
    static final int GAME_PAUSED = 1;
    static final int GAME_LEVEL_END = 2;
    static final int GAME_OVER = 3;

    LD32Game game;

    int state;
    World world;
    WorldRenderer worldRenderer;

    public GameScreen(LD32Game game) {
        this.game = game;
        world = new World();
        worldRenderer = new WorldRenderer(game.batch, world);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void pause() {
        state = GAME_PAUSED;
    }

    @Override
    public void resume() {
        state = GAME_RUNNING;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void update (float deltaTime) {
        if (deltaTime > 0.1f) deltaTime = 0.1f;

        switch (state) {
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                break;
            case GAME_LEVEL_END:
                break;
            case GAME_OVER:
                break;
        }
    }

    private void updateRunning(float deltaTime) {
        world.update(deltaTime);
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0.204f, 0.204f, 0.467f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
    }
}
