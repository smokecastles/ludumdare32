package com.smokecastles.ld32;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by juanma on 18/04/15.
 */
public class WorldRenderer {
    static final float FRUSTUM_HEIGHT   = 24;
    static final float FRUSTUM_WIDTH    = 24 * (float) Constants.NATIVE_WIDTH / Constants.NATIVE_HEIGHT;

    World world;
    OrthographicCamera cam;
    Viewport viewport;
    SpriteBatch batch;

    private OrthogonalTiledMapRenderer tiledRenderer;

    private DebugRenderer debugRenderer;

    public WorldRenderer(SpriteBatch batch, World world) {
        this.batch      = batch;
        this.world      = world;
        this.cam        = new OrthographicCamera();
        this.viewport   = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, cam);

        viewport.apply();

        this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);

        tiledRenderer = new OrthogonalTiledMapRenderer(world.tiledMap, 1f / world.TILED_TILE_WIDTH_PIXELS, batch);

        debugRenderer = new DebugRenderer(cam, batch, world);
    }

    public void render() {

    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
