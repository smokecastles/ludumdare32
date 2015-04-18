package com.smokecastles.ld32.utils;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.smokecastles.ld32.entities.Player;
import com.smokecastles.ld32.World;

/**
 * Created by juanma on 18/04/15.
 */
public abstract class WorldPhysics {

    private static Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };

    private static Array<Rectangle> tiles = new Array<Rectangle>();

    public static void checkTiledMapCollisions(DynamicGameEntity entity, World world, boolean detectEdges) {
        entity.grounded = false;
        entity.atEdge = false;

        int startX, startY, endX, endY;

        Rectangle intersection = rectPool.obtain();

        // perform collision detection & response, on each axis, separately
        // if the entity is moving right, check the tiles to the right of it's
        // right bounding box edge, otherwise check the ones to the left
        if (entity.velocity.x > 0) {
            startX = endX = (int) (entity.bounds.x + Player.WIDTH);
        } else {
            startX = endX = (int) entity.bounds.x;
        }

        startY = (int) entity.bounds.y;
        endY = (int) (entity.bounds.y + Player.HEIGHT);

        getTiles(startX, startY, endX, endY, world, tiles, "solid_tiles");

        for (Rectangle tile : tiles) {

            // First check the intersection to find out if we need to resolve the collision
            // on the X axis or delegate it to the Y axis.
            // If the width is greater than the height, usually (for high movement speed may fail)
            // we need to correct the entity position from the vertical collision results.
            // http://www.raywenderlich.com/15230/how-to-make-a-platform-game-like-super-mario-brothers-part-1
            // http://gamedev.stackexchange.com/questions/17502/how-to-deal-with-corner-collisions-in-2d?rq=1

            // Check if we're still intersecting, it's possible that we're no longer colliding from the
            // resolution to the previous iteration
            if (!Intersector.intersectRectangles(entity.bounds, tile, intersection)) continue;

            if (intersection.width > intersection.height) {
                continue;
            } else if (intersection.height - intersection.width < 0.01f) {
                // TODO?: fix so that this HACK is not needed
                // For "corner against corner" collisions it's possible that the previous width > height
                // check is not accurate. See once again previous stackexchange link.
                continue;
            }

            if (entity.velocity.x > 0) {
                entity.bounds.x = tile.x - Player.WIDTH;
                entity.position.x = entity.bounds.x + entity.bounds.width / 2;
            } else {
                entity.bounds.x = tile.x + World.TILE_DIMENS;
                entity.position.x = entity.bounds.x + entity.bounds.width / 2;
            }

            entity.velocity.x = 0;

            break;
        }

        // if the entity is moving upwards, check the tiles to the top of it's
        // top bounding box edge, otherwise check the ones to the bottom
        if (entity.velocity.y >= 0) {
            startY = endY = (int) (entity.bounds.y + Player.HEIGHT);
        } else {
            startY = endY = (int) entity.bounds.y;
        }

        startX = (int) entity.bounds.x;
        endX = (int) (entity.bounds.x + Player.WIDTH);

        getTiles(startX, startY, endX, endY, world, tiles, "solid_tiles");

        // We need the first and the last X positions from the retrieved tiles
        // for the edge detection
        int lastTileX = -1;
        int firstTileX = -1;

        for (Rectangle tile : tiles) {
            if (firstTileX == -1) firstTileX = (int) tile.x;

            if (true) {
                // Now we check for overlap, needed in case the collision detection in X axis
                // has moved the entity bounds
                if (tile.overlaps(entity.bounds)) {
                    // we actually reset the entity y-position here
                    // so it is just below/above the tile we collided with
                    // this removes bouncing :)
                    if (entity.velocity.y > 0) {
                        entity.bounds.y = tile.y - Player.HEIGHT;
                        entity.position.y = entity.bounds.y + entity.bounds.height / 2;
                    } else {
                        entity.bounds.y = tile.y + World.TILE_DIMENS;
                        entity.position.y = entity.bounds.y + entity.bounds.height / 2;
                    }

                    entity.velocity.y = 0;
                    entity.grounded = true;
                }
            }

            lastTileX = (int) tile.x;

            // If we're not interested in edges (e.g. for the playable character)
            // break to avoid further iterations
            if (!detectEdges) break;
        }

        // If we're moving right and the X from the last tile doesn't match
        // the end X position of the entity (x + width)
        // OR
        // if we're moving left and the X from the first tile doesn't match
        // the X position of the entity
        // THEN
        // the ground tile ahead of the entity is empty, mark as atEdge for the
        // controller to react if needed
        if (detectEdges && entity.grounded) {
            if ( (entity.velocity.x > 0 && lastTileX != endX)
                    || (entity.velocity.x < 0 && firstTileX != startX) ) {
                entity.atEdge = true;
            }
        }

        rectPool.free(intersection);
    }

    private static void getTiles(int startX, int startY, int endX, int endY, World world, Array<Rectangle> tiles, String layerName) {
        TiledMapTileLayer layer = (TiledMapTileLayer) world.tiledMap.getLayers().get(layerName);
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
    }
}
