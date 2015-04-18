package com.smokecastles.ld32;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smokecastles.ld32.utils.Assets;
import com.smokecastles.ld32.utils.Constants;
import com.smokecastles.ld32.view.WorldRenderer;

/**
 * Created by juanma on 18/04/15.
 */
public class GameScreen implements Screen {
    static final int GAME_RUNNING   = 0;
    static final int GAME_PAUSED    = 1;
    static final int GAME_LEVEL_END = 2;
    static final int GAME_OVER      = 3;

    LD32Game game;

    int state;
    World world;
    WorldRenderer worldRenderer;

    // HUD
    private Stage       hud;
    private SpriteBatch batch;
    private Table       rootTable, table;

    static Image[] array_full_life  = new Image[12];
    static Image[] array_empty_life = new Image[12];

    public GameScreen(LD32Game game_, SpriteBatch batch_) {
        game            = game_;
        batch           = batch_;
        world           = new World();
        worldRenderer   = new WorldRenderer(batch, world);

        Viewport viewport = new FitViewport(Constants.NATIVE_WIDTH, Constants.NATIVE_HEIGHT, new OrthographicCamera());
        hud               = new Stage( viewport, batch );

        for(int i=0; i<12;i++){
            array_full_life[i]  = new Image(Assets.player_life_unit);
            array_empty_life[i] = new Image(Assets.player_life_unit2 );
        }

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.pad(10);
//        rootTable.setDebug(true);

        table     = new Table();
//        table.setDebug(true);


        for (int i=0; i<6; i++){
            table.add(array_full_life[i]).pad(10);
        }
        for (int i=0; i<6; i++){
            table.add(array_empty_life[i]).pad(10);
        }

        rootTable.add(table);
        rootTable.top().left();
    }

    @Override
    public void show() {
        hud.addActor(rootTable);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
        // HUD
        hud.act(delta);
        hud.draw();
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
        world.update(deltaTime);
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(Constants.BGCOLOR_R, Constants.BGCOLOR_G, Constants.BGCOLOR_B, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
    }
}
