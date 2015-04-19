package com.smokecastles.ld32.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.smokecastles.ld32.utils.WorldPhysics;

import java.util.Iterator;

public class World extends Entity{
    public static final float WORLD_WIDTH = 43;
    public static final float WORLD_HEIGHT = 24;

    public static final int TILE_DIMENS = 1;

    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;

    public int state;

    // Tiled
    public TiledMap tiledMap;
    public TmxMapLoader tmxLoader;
    public static final int TILED_TILE_WIDTH_PIXELS = 30;

    public final Player player;
    
    // Entities
    public Array<Enemy> enemies;

    public World() {
        player = new Player(3, 3);

        enemies = new Array<Enemy>();

        Enemy enemy = new Enemy(6, 6, new Enemy.BigEnemy());
        enemies.add(enemy);
        Enemy enemy2 = new Enemy(12, 12, new Enemy.MedEnemy());
        enemies.add(enemy2);
        Enemy enemy3 = new Enemy(12, 12, new Enemy.SmallEnemy());
        enemies.add(enemy3);

        initTiled();

        state = WORLD_STATE_RUNNING;
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

        checkGameOver();
        checkNextLevel();
    }

    public void updatePlayer(float deltaTime) {
        player.update(deltaTime);
    }

    private void updateEnemies(float deltaTime) {
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }
    }

    private void checkCollisions() {
        player.updatePhysics(this);

        for (Enemy enemy : enemies) {
            if (WorldPhysics.checkCircleAndRectangleOverlap(enemy.detectionArea, player.bounds)) {
                enemy.startFollowingPlayer(player.position);
            } else if (enemy.playerInRange) {
                enemy.stopFollowingPlayer();
            }

            if (enemy.bounds.overlaps(player.bounds)) {
                player.hitByEnemy();
                // TODO: send notification
            }

            enemy.updatePhysics(this);
        }

        if (player.finishedCharging) {
            // We need removal, use iterator
            for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
                Enemy enemy = iterator.next();
                if (WorldPhysics.checkCircleAndRectangleOverlap(player.weaponArea, enemy.bounds)) {
                    enemy.takeDamage(1);
                    if (enemy.isDead) {
                        iterator.remove();
                    }
                }
            }
            player.finishedCharging = false;
        }
    }

    private void checkGameOver() {
        if (player.health == 0) {
            state = WORLD_STATE_GAME_OVER;
            System.out.println("GAME OVER!!");
        }
    }

    private void checkNextLevel() {
        if (enemies.size == 0) {
            state = WORLD_STATE_NEXT_LEVEL;
            System.out.println("NEXT LEVEL!!");
        }
    }
}
