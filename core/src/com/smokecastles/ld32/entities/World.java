package com.smokecastles.ld32.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.smokecastles.ld32.utils.ScreenShaker;
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

    public ScreenShaker screenShaker;
    public boolean needsShaking;

    public World(int level) {
        player = new Player(3, 3);

        enemies = new Array<Enemy>();
        initTiled();

        screenShaker = new ScreenShaker();

        populateWorld(level);

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

        screenShaker.tick(deltaTime);

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
                needsShaking = true;
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
            needsShaking = true;
            notifyObservers(new Event(Event.Type.BOOM));
            player.finishedCharging = false;
        }
    }

    private void checkGameOver() {
        if (player.health == 0) {
            state = WORLD_STATE_GAME_OVER;
            notifyObservers(new Event(Event.Type.GAME_OVER));
        }
    }

    private void checkNextLevel() {
        if (enemies.size == 0) {
            state = WORLD_STATE_NEXT_LEVEL;
            notifyObservers(new Event(Event.Type.WIN));
        }
    }

    private void populateWorld(int level) {
        int nSmall = 0;
        int nMed = 0;
        int nBig = 0;

        switch (level) {
            case 1:
                nSmall = 3;
                nMed = 2;
                nBig = 0;
                break;
            case 2:
                nSmall = 3;
                nMed = 2;
                nBig = 1;
                break;
            case 3:
                nSmall = 2;
                nMed = 2;
                nBig = 2;
                break;
            case 4:
                nSmall = 0;
                nMed = 2;
                nBig = 3;
                break;
        }

        float randomX;
        float randomY;

        for (int i = 0; i < nSmall; i++) {
            randomX = MathUtils.random(1, WORLD_WIDTH - 1);
            randomY = MathUtils.random(1, WORLD_HEIGHT - 1);
            Enemy e = new Enemy(randomX, randomY, new Enemy.SmallEnemy());
            enemies.add(e);
        }

        for (int i = 0; i < nMed; i++) {
            randomX = MathUtils.random(1, WORLD_WIDTH - 1);
            randomY = MathUtils.random(1, WORLD_HEIGHT - 1);
            Enemy e = new Enemy(randomX, randomY, new Enemy.MedEnemy());
            enemies.add(e);
        }

        for (int i = 0; i < nBig; i++) {
            randomX = MathUtils.random(1, WORLD_WIDTH - 1);
            randomY = MathUtils.random(1, WORLD_HEIGHT - 1);
            Enemy e = new Enemy(randomX, randomY, new Enemy.BigEnemy());
            enemies.add(e);
        }
    }
}
