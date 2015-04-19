package com.smokecastles.ld32;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smokecastles.ld32.entities.World;
import com.smokecastles.ld32.utils.Constants;
import com.smokecastles.ld32.view.HUD;
import com.smokecastles.ld32.view.WorldRenderer;

public class GameScreen implements Screen {
    static final int GAME_RUNNING   = 0;
    static final int GAME_PAUSED    = 1;
    static final int GAME_LEVEL_END = 2;
    static final int GAME_OVER      = 3;

    LD32Game game;

    int state;
    World world;
    WorldRenderer worldRenderer;

    private SpriteBatch batch;
    private HUD hud;

    public GameScreen(LD32Game game_, SpriteBatch batch_) {
        game            = game_;
        batch           = batch_;
        world           = new World();
        worldRenderer   = new WorldRenderer(batch, world);
        hud             = new HUD(batch);

        world.player.addObserver(hud);
    }

    @Override
    public void show() {
        hud.show();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
        hud.render(delta);

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
        hud.dispose();
    }

    public void update(float deltaTime) {
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
        switch (world.state) {
            case World.WORLD_STATE_RUNNING:
                world.update(deltaTime);
                break;
            case World.WORLD_STATE_NEXT_LEVEL:
                //world.update(deltaTime);
                break;
            case World.WORLD_STATE_GAME_OVER:
                //world.update(deltaTime);
                break;
        }
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(Constants.BGCOLOR_R, Constants.BGCOLOR_G, Constants.BGCOLOR_B, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
    }
}
