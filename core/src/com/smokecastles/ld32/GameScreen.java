package com.smokecastles.ld32;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smokecastles.ld32.entities.Event;
import com.smokecastles.ld32.entities.World;
import com.smokecastles.ld32.utils.Constants;
import com.smokecastles.ld32.view.HUD;
import com.smokecastles.ld32.view.MainMenu;
import com.smokecastles.ld32.view.SoundBox;
import com.smokecastles.ld32.view.WorldRenderer;

public class GameScreen implements Screen {
    static final int GAME_GET_READY   = 0;
    static final int GAME_RUNNING   = 1;
    static final int GAME_PAUSED    = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER      = 4;
    static final int GAME_FINISHED  = 5;

    private static final int N_LEVELS = 4;

    LD32Game game;

    int state;
    World world;
    WorldRenderer worldRenderer;

    private SpriteBatch batch;
    private HUD hud;
    private SoundBox soundBox;

    int currentLevel = 1;

    public GameScreen(LD32Game game_, int level) {
        game            = game_;
        batch           = game.batch;
        hud             = new HUD(batch, level);
        soundBox        = new SoundBox();
        currentLevel    = level;

        initWorld(currentLevel);
    }

    public void initWorld(int level) {
        world           = new World(level);
        worldRenderer   = new WorldRenderer(batch, world);

        // adding observers to observable entities
        world.player.addObserver(hud);
        world.player.addObserver(soundBox);
        world.addObserver(hud);
        world.addObserver(soundBox);

        hud.showMessage(true);
        state = GAME_GET_READY;
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
        if (state == GAME_RUNNING) {
            state = GAME_PAUSED;
            soundBox.pause();
        }
    }

    @Override
    public void resume() {
        if (state == GAME_PAUSED) {
            state = GAME_RUNNING;
            soundBox.play();
        }
    }

    @Override
    public void hide() {
        soundBox.stop();
    }

    @Override
    public void dispose() {
        hud.dispose();
        soundBox.dispose();
    }

    public void update(float deltaTime) {
        if (deltaTime > 0.1f) deltaTime = 0.1f;

        switch (state) {
            case GAME_GET_READY:
                if (Gdx.input.isKeyJustPressed(-1)) {
                    hud.showMessage(false);
                    soundBox.play();
                    state = GAME_RUNNING;
                }
                break;

            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;

            case GAME_PAUSED:
                break;

            case GAME_LEVEL_END:
                soundBox.stop();
                if (Gdx.input.isKeyJustPressed(-1)) {
                    game.setScreen(new GameScreen(game, ++currentLevel));
                }
                break;

            case GAME_OVER:
                soundBox.stop();

                if (Gdx.input.isKeyJustPressed(-1)) {
                    // Go to main menu if gameover
                    game.setScreen(new MainMenu(game));
                }
                break;

            case GAME_FINISHED:
                soundBox.stop();
                hud.onNotify(null, new Event(Event.Type.GAME_FINISHED));
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    game.setScreen(new MainMenu(game));
                }
                break;
        }
    }

    private void updateRunning(float deltaTime) {
        switch (world.state) {
            case World.WORLD_STATE_RUNNING:
                world.update(deltaTime);
                break;

            case World.WORLD_STATE_NEXT_LEVEL:
                if (currentLevel == N_LEVELS) {
                    state = GAME_FINISHED;
                } else {
                    state = GAME_LEVEL_END;
                }
                break;

            case World.WORLD_STATE_GAME_OVER:
                state = GAME_OVER;
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
