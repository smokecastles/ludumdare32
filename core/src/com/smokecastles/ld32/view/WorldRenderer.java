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
import com.smokecastles.ld32.entities.Enemy;
import com.smokecastles.ld32.utils.Constants;
import com.smokecastles.ld32.entities.Player;
import com.smokecastles.ld32.entities.World;

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

        this.cam.position.set(World.WORLD_WIDTH / 2, World.WORLD_HEIGHT / 2, 0);

        tiledRenderer = new OrthogonalTiledMapRenderer(world.tiledMap, 1f / World.TILED_TILE_WIDTH_PIXELS, batch);

        debugRenderer = new DebugRenderer(cam, batch, world);
        shapeRenderer = new ShapeRenderer();

    }

    public void render() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);

        renderTiledMap();

        renderEntities();
        
        shakeIfNeeded();

        debugRenderer.render();
    }

    private void shakeIfNeeded() {
        if (world.needsShaking) {
            world.screenShaker.startShaking(0.4f, 0.2f, cam, cam.position);
            world.needsShaking = false;
        }
    }

    private void renderEntities() {
        // Render weapon circle!
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.455f, 0.792f, 0.455f, 0.4f);
        shapeRenderer.circle(world.player.weaponArea.x, world.player.weaponArea.y, world.player.weaponArea.radius, 30);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.enableBlending();
        batch.begin();
        renderPlayer();
        renderEnemies();
        batch.end();
    }

    private void renderPlayer() {
       TextureRegion keyFrame = world.player.getKeyFrame();

        float renderHeight = Player.HEIGHT;
        float renderWidth = renderHeight * keyFrame.getRegionWidth() / keyFrame.getRegionHeight();

        batch.draw(keyFrame, world.player.position.x + renderWidth / 2, world.player.position.y - renderHeight / 2, -renderWidth, renderHeight);
    }

    private void renderEnemies() {
        for (Enemy enemy : world.enemies) {
            TextureRegion keyFrame = enemy.getKeyFrame();

            float renderHeight = enemy.bounds.height;
            float renderWidth = enemy.bounds.width;

            batch.draw(keyFrame, enemy.position.x + renderWidth / 2, enemy.position.y - renderHeight / 2, -renderWidth, renderHeight);
        }
    }

    private void renderTiledMap() {
        batch.enableBlending();
        tiledRenderer.setView(cam);
        tiledRenderer.render();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
