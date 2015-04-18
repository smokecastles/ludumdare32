package com.smokecastles.ld32.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smokecastles.ld32.utils.Constants;
import com.smokecastles.ld32.entities.Player;
import com.smokecastles.ld32.World;

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
    private ShapeRenderer shapeRenderer;

    public WorldRenderer(SpriteBatch batch, World world) {
        this.batch      = batch;
        this.world      = world;
        this.cam        = new OrthographicCamera();
        this.viewport   = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, cam);

        viewport.apply();

        this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);

        tiledRenderer = new OrthogonalTiledMapRenderer(world.tiledMap, 1f / world.TILED_TILE_WIDTH_PIXELS, batch);

        debugRenderer = new DebugRenderer(cam, batch, world);
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);

        renderEntities();

        debugRenderer.render();
    }

    private void renderEntities() {
        batch.enableBlending();
        batch.begin();
        renderPlayer();
        batch.end();

        // Render weapon circle!
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 0.3f);
        shapeRenderer.circle(world.player.weaponArea.x, world.player.weaponArea.y, world.player.weaponArea.radius, 50);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void renderPlayer() {
       TextureRegion keyFrame = world.player.getKeyFrame();

        float renderHeight = Player.HEIGHT;
        float renderWidth = renderHeight * keyFrame.getRegionWidth() / keyFrame.getRegionHeight();

        batch.draw(keyFrame, world.player.position.x + renderWidth / 2, world.player.position.y - renderHeight / 2, -renderWidth, renderHeight);
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
