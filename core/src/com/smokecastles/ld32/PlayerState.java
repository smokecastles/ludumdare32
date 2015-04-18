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

    void hitEnemy(Player player);

    void update(Player player, float deltaTime);

    TextureRegion getKeyFrame();

    abstract class BaseState implements PlayerState {
    }

    class NormalState extends BaseState {

        @Override
        public void enterState(Player player) {}

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
        public void hitEnemy(Player player) {
//            dude.state = dude.jumpingState;
//            dude.state.enterState(dude);
        }

        @Override
        public void update(Player player, float deltaTime) {
            // Apply damping to the velocity on the x-axis so we don't
            // walk infinitely once a key was pressed
            player.velocity.x *= Player.WALK_DAMPING;
            player.velocity.y *= Player.WALK_DAMPING;

            if (Math.abs(player.velocity.x) < 1) {
                player.velocity.x = 0;
            }

            if (player.velocity.x == 0) {
//                stateTime = 0;
//                dude.state = dude.standingState;
            }
        }

        @Override
        public TextureRegion getKeyFrame() {
            return Assets.playerNormal;
        }
    }
}
