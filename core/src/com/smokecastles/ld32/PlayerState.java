package com.smokecastles.ld32;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.smokecastles.ld32.framework.Assets;
import com.smokecastles.ld32.framework.DynamicGameEntity;

/**
 * Created by juanma on 18/04/15.
 */
public interface PlayerState {
    void enterState(Player player);
    void moveLeft(Player player);
    void moveRight(Player player);
    void moveUp(Player player);
    void moveDown(Player player);
    void attack(Player player);
    void hitEnemy(Player player);

    void update(Player player, float deltaTime);

    TextureRegion getKeyFrame();

    abstract class BaseState implements PlayerState {
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
//            dude.state = dude.walkingState;
//            dude.orientation = -1;
//            dude.state.enterState(dude);
        }

        @Override
        public void moveRight(Player player) {
            player.orientation = DynamicGameEntity.Orientation.RIGHT;
            player.velocity.x = 1 * player.WALK_ACCEL;
//            dude.state = dude.walkingState;
//            dude.orientation = 1;
//            dude.state.enterState(dude);
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
        public void hitEnemy(Player player) {
//            dude.state = dude.jumpingState;
//            dude.state.enterState(dude);
        }

        @Override
        public void update(Player player, float deltaTime) {
            if (player.weaponArea.radius > 0.1f) {
                player.weaponArea.radius -= 0.7 * stateTime;
            } else {
                player.weaponArea.radius = 0;
            }

            // Apply damping to the velocity on the x-axis so we don't
            // walk infinitely once a key was pressed
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

    class AttackingState extends BaseState {
        float stateTime;

        @Override
        public void enterState(Player player) {
            stateTime = 0;
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
        public void hitEnemy(Player player) {
        }

        @Override
        public void update(Player player, float deltaTime) {
            if (player.weaponArea.radius < Player.MAX_WEAPON_RADIUS) {
                player.weaponArea.radius += 0.1 * stateTime;
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
        }

        @Override
        public TextureRegion getKeyFrame() {
            return Assets.playerNormal;
        }
    }
}
