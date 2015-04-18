package com.smokecastles.ld32;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.smokecastles.ld32.entities.Enemy;
import com.smokecastles.ld32.entities.Player;

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
    public static final int TILED_TILE_WIDTH_PIXELS = 30;

    public final Player player;
    // Entities
    public Array<Enemy> enemies = new Array<Enemy>();

    public World() {
        player = new Player(3, 3);

        Enemy enemy = new Enemy(6,6);
        enemies.add(enemy);

        initTiled();
    }

    private void initTiled() {
        tmxLoader = new TmxMapLoader();
        TmxMapLoader.Parameters tmlParams = new TmxMapLoader.Parameters();
        tmlParams.textureMagFilter = Texture.TextureFilter.Linear;
        tmlParams.textureMinFilter = Texture.TextureFilter.Linear;
        tiledMap = tmxLoader.load("map1.tmx", tmlParams);
    }

    public void update(float deltaTime) {
        updatePlayer(deltaTime);
        updateEnemies(deltaTime);

        checkCollisions();
    }

    public void updatePlayer(float deltaTime) {
        player.update(deltaTime);
    }

    private void updateEnemies(float deltaTime) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive) enemy.update(deltaTime);
        }
    }

    private void checkCollisions() {
        player.updatePhysics(this);

        for (Enemy enemy : enemies) {
            if (enemy.isAlive) enemy.updatePhysics(this);
        }
    }
}
