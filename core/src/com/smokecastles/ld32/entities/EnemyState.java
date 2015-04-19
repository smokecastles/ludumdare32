package com.smokecastles.ld32.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.smokecastles.ld32.utils.Assets;

/**
 * Created by juanma on 18/04/15.
 */
public interface EnemyState {
    void enterState(Enemy enemy);
    void moveLeft(Enemy enemy);
    void moveRight(Enemy enemy);
    void moveUp(Enemy enemy);
    void moveDown(Enemy enemy);
    void takeDamage(Enemy enemy, int damage);

    void update(Enemy enemy, float deltaTime);

    TextureRegion getKeyFrame();

    abstract class BaseState implements EnemyState {
    }

    class NormalState extends BaseState {
        float stateTime;

        @Override
        public void enterState(Enemy enemy) {
            stateTime = 0;
        }

        @Override
        public void moveLeft(Enemy enemy) {
            enemy.orientation = DynamicGameEntity.Orientation.LEFT;
            enemy.velocity.x = -1 * enemy.accel;
        }

        @Override
        public void moveRight(Enemy enemy) {
            enemy.orientation = DynamicGameEntity.Orientation.RIGHT;
            enemy.velocity.x = 1 * enemy.accel;
        }

        @Override
        public void moveUp(Enemy enemy) {
            enemy.orientation = DynamicGameEntity.Orientation.UP;
            enemy.velocity.y = 1 * enemy.accel;
        }

        @Override
        public void moveDown(Enemy enemy) {
            enemy.orientation = DynamicGameEntity.Orientation.DOWN;
            enemy.velocity.y = -1 * enemy.accel;
        }

        @Override
        public void takeDamage(Enemy enemy, int damage) {
            enemy.health -= damage;
            if (enemy.health > 0) {
                enemy.state = enemy.justHitState;
                enemy.state.enterState(enemy);
            } else {
                enemy.isDead = true;
            }
        }

        @Override
        public void update(Enemy enemy, float deltaTime) {
            // Apply damping to the velocity on the x-axis so we don't
            // walk infinitely once a key was pressed
            enemy.velocity.x *= Enemy.WALK_DAMPING;
            enemy.velocity.y *= Enemy.WALK_DAMPING;

            if (Math.abs(enemy.velocity.x) < 1) {
                enemy.velocity.x = 0;
            }

            if (Math.abs(enemy.velocity.y) < 1) {
                enemy.velocity.y = 0;
            }

            stateTime += deltaTime;
        }

        @Override
        public TextureRegion getKeyFrame() {
            return Assets.enemyBig;
        }
    }

    /* Like normal state but can't be hit again */
    class JustHitState extends NormalState {
        private static final float INVULNERABLE_TIME_AFTER_HIT = 1f;


        @Override
        public void update(Enemy enemy, float deltaTime) {
            super.update(enemy, deltaTime);

            if (stateTime > INVULNERABLE_TIME_AFTER_HIT) {
                enemy.state = enemy.normalState;
                enemy.state.enterState(enemy);
            }
        }

        @Override
        public TextureRegion getKeyFrame() {
            return Assets.enemyBigHit;
        }
    }
}
