package com.smokecastles.ld32;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by juanma on 18/04/15.
 */
public class World {
    public static final float WORLD_WIDTH = 43;
    public static final float WORLD_HEIGHT = 24;

    public static final int TILE_DIMENS = 1;

    // Tiled
    public TiledMap tiledMap;
    public TmxMapLoader tmxLoader;
    public static final int TILED_TILE_WIDTH_PIXELS = 70;

    public void update(float deltaTime) {
    }
}
