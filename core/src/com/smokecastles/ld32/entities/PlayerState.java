package com.smokecastles.ld32.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.smokecastles.ld32.utils.Assets;

public interface PlayerState {
    void enterState(Player player);
    void moveLeft(Player player);
    void moveRight(Player player);
    void moveUp(Player player);
    void moveDown(Player player);
    void attack(Player player);
    void hitByEnemy(Player player);

    void update(Player player, float deltaTime);

    TextureRegion getKeyFrame();

    abstract class BaseState implements PlayerState {

        @Override
        public void hitByEnemy(Player player) {
            player.health--;
            player.state = player.justHitState;
            player.state.enterState(player);
            player.notifyObservers(new Event(Event.Type.HIT_BY_ENEMY));
        }
    }

    class NormalState extends BaseState {
        float stateTime;

        @Override
        public void enterState(Player player) {
            stateTime = 0;
        }

        @Override
        public void moveLeft(Player player) {
            player.orientation = DynamicGameEntity.Orientation.LEFT;
            player.velocity.x = -1 * player.WALK_ACCEL;
        }

        @Override
        public void moveRight(Player player) {
            player.orientation = DynamicGameEntity.Orientation.RIGHT;
            player.velocity.x = 1 * player.WALK_ACCEL;
        }

        @Override
        public void moveUp(Player player) {
            player.orientation = DynamicGameEntity.Orientation.UP;
            player.velocity.y = 1 * player.WALK_ACCEL;
        }

        @Override
        public void moveDown(Player player) {
            player.orientation = DynamicGameEntity.Orientation.DOWN;
            player.velocity.y = -1 * player.WALK_ACCEL;
        }

        @Override
        public void attack(Player player) {
            player.state = player.attackingState;
            player.state.enterState(player);
        }


        @Override
        public void update(Player player, float deltaTime) {
            if (player.weaponArea.radius > 0.1f) {
                player.weaponArea.radius -= 0.9 * stateTime;
            } else {
                player.weaponArea.radius = 0;
            }

            // Apply damping to the velocity on the x-axis so we don't
            // walk infinitely once a key is pressed
            player.velocity.x *= Player.WALK_DAMPING;
            player.velocity.y *= Player.WALK_DAMPING;

            if (Math.abs(player.velocity.x) < 1) {
                player.velocity.x = 0;
            }

            if (Math.abs(player.velocity.y) < 1) {
                player.velocity.y = 0;
            }

            stateTime += deltaTime;
        }

        @Override
        public TextureRegion getKeyFrame() {
            return Assets.playerNormal;
        }
    }

    /* Like normal state but can't be hit again */
    class JustHitState extends NormalState {
        private static final float INVULNERABLE_TIME_AFTER_HIT = 1f;

        @Override
        public void hitByEnemy(Player player) {
            // No damage
        }

        @Override
        public void attack(Player player) {
        }

        @Override
        public void update(Player player, float deltaTime) {
            super.update(player, deltaTime);

            if (stateTime > INVULNERABLE_TIME_AFTER_HIT) {
                player.state = player.normalState;
                player.state.enterState(player);
            }
        }

        @Override
        public TextureRegion getKeyFrame() {
            return Assets.playerHit;
        }
    }

    class AttackingState extends BaseState {
        float stateTime;
        float damageTimer;

        @Override
        public void enterState(Player player) {
            stateTime = 0;
            damageTimer = 0;
        }

        @Override
        public void moveLeft(Player player) {
        }

        @Override
        public void moveRight(Player player) {
        }

        @Override
        public void moveUp(Player player) {
        }

        @Override
        public void moveDown(Player player) {
        }

        @Override
        public void attack(Player player) {
        }

        @Override
        public void update(Player player, float deltaTime) {
            if (player.weaponArea.radius < Player.MAX_WEAPON_RADIUS) {
                player.weaponArea.radius += 0.1 * stateTime;
            }

            if (damageTimer > Player.WEAPON_TIME_BETWEEN_SELF_DAMAGE) {
                player.health--;
                damageTimer = 0;
                player.notifyObservers(new Event(Event.Type.LIFE_DRAINING));
            }

            player.velocity.x *= Player.WALK_DAMPING;
            player.velocity.y *= Player.WALK_DAMPING;

            if (Math.abs(player.velocity.x) < 1) {
                player.velocity.x = 0;
            }

            if (Math.abs(player.velocity.y) < 1) {
                player.velocity.y = 0;
            }

            stateTime += deltaTime;
            damageTimer += deltaTime;
        }

        @Override
        public TextureRegion getKeyFrame() {
            return Assets.playerNormal;
        }
    }
}
