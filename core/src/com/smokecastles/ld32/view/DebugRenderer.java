package com.smokecastles.ld32.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokecastles.ld32.World;

public class DebugRenderer {
    OrthographicCamera cam;
    SpriteBatch batch;
    World world;

    ShapeRenderer shapeRenderer;
    BitmapFont font;

    public static boolean DEBUG_ENABLED = false;

    private boolean drawGrid        = true;
    private boolean drawRectangles  = true;

    public DebugRenderer(OrthographicCamera cam, SpriteBatch batch, World world) {
        this.cam    = cam;
        this.batch  = batch;
        this.world  = world;

        shapeRenderer = new ShapeRenderer();

        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.02f);
    }

    public void render() {
        if (!DEBUG_ENABLED) return;

        shapeRenderer.setProjectionMatrix(cam.combined);

        drawDebugGrid();
        drawDebugRectangles();
        drawDebugTexts();
    }

    private void drawDebugGrid() {
        if (drawGrid) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.GREEN);

            shapeRenderer.line(0, 0, World.WORLD_WIDTH, 0);
            shapeRenderer.line(0, 0, 0, World.WORLD_HEIGHT);

            shapeRenderer.setColor(Color.RED);

            for (int i = 0; i < World.WORLD_WIDTH; i++)
                shapeRenderer.line(i, 0, i, World.WORLD_HEIGHT);

            for (int i = 0; i < World.WORLD_HEIGHT; i++)
                shapeRenderer.line(0, i, World.WORLD_WIDTH, i);

            shapeRenderer.end();
        }
    }

    private void drawDebugRectangles() {
        if (drawRectangles) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);

//            shapeRenderer.rect(world.dude.bounds.x, world.dude.bounds.y,
//                    world.dude.bounds.width, world.dude.bounds.height);

            shapeRenderer.end();
        }
    }


    private void drawDebugTexts() {
        batch.begin();

        if (drawGrid) {
            for (int i = 0; i < World.WORLD_HEIGHT; i++)
                font.draw(batch, ""+i, cam.position.x - cam.viewportWidth / 2 + 0, i + font.getLineHeight());

            for (int i = 0; i < World.WORLD_WIDTH; i++)
                font.draw(batch, "" + i, i, cam.position.y - cam.viewportHeight / 2 + font.getLineHeight());
        }

//        if (drawRectangles) {
//            font.draw(batch, "(" + String.format("%.3f", world.dude.bounds.x)  +
//                            ", " + String.format("%.3f", world.dude.bounds.y) + ")",
//                    world.dude.bounds.x, world.dude.bounds.y);
//        }

//        font.draw(batch, ""+ Gdx.graphics.getFramesPerSecond(),
//                cam.position.x + cam.viewportWidth / 2 - 2 * font.getBounds("  ").width,
//                cam.position.y + cam.viewportHeight / 2);

        batch.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
