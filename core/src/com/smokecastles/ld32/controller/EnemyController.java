package com.smokecastles.ld32.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.smokecastles.ld32.entities.Enemy;

/**
 * Created by juanma on 18/04/15.
 */
public class EnemyController {
    private static final float TIMER_VALUE_CHECK_POSITION = 0.5f;

    float timer = -1f;
    float timerCheckPosition = TIMER_VALUE_CHECK_POSITION;

    int dirX = 0;
    int dirY = 0;

    Vector2 previousPosition = new Vector2(0, 0);

    public void update(Enemy enemy, float deltaTime) {
        if (timer < 0) {
            dirX = MathUtils.random(-1, 1);
            dirY = MathUtils.random(-1, 1);

            timer = MathUtils.random(1, 2);
        } else if (timerCheckPosition < 0) {
            boolean didCorrectDirection = false;

            // Check if we haven't changed position in a while, in case we have to turn around.

            boolean didMoveInXAxis = true;
            if (Math.abs(enemy.position.x - previousPosition.x) < 0.01f) {
                didMoveInXAxis = false;
            }

            boolean didMoveInYAxis = true;
            if (Math.abs(enemy.position.y - previousPosition.y) < 0.01f) {
                didMoveInYAxis = false;
            }

            if (!didMoveInXAxis && !didMoveInYAxis) {
                if (dirX > 0) {
                    enemy.moveLeft();
                    dirX = -1;
                } else {
                    enemy.moveRight();
                    dirX = 1;
                }

                if (dirY > 0) {
                    enemy.moveUp();
                    dirY = -1;
                } else {
                    enemy.moveDown();
                    dirY = 1;
                }

                didCorrectDirection = true;
            }

            previousPosition = new Vector2(enemy.position.x, enemy.position.y);
            timerCheckPosition = TIMER_VALUE_CHECK_POSITION;

            if (didCorrectDirection) {
                timer = MathUtils.random(1, 2);
                return;
            }
        }

        if (dirX > 0) {
            enemy.moveRight();
        } else if (dirX < 0) {
            enemy.moveLeft();
        }

        if (dirY > 0) {
            enemy.moveUp();
        } else if (dirY < 0) {
            enemy.moveDown();
        }

        timer -= deltaTime;
        timerCheckPosition -= deltaTime;
    }
}
