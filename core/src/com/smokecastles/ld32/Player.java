package com.smokecastles.ld32;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.smokecastles.ld32.framework.Assets;
import com.smokecastles.ld32.framework.DynamicGameEntity;

/**
 * Created by juanma on 18/04/15.
 */
public class Player extends DynamicGameEntity {
    public static final float WIDTH = 2f;
    public static final float HEIGHT = 2f;

    public static final float WALK_ACCEL = 5f;
    public static final float WALK_DAMPING = 0.89f;

    public PlayerState.NormalState standingState = new PlayerState.NormalState();

    PlayerController controller;

    PlayerState state; // current state

    public Player(float x, float y) {
        super(x, y, WIDTH, HEIGHT, new PlayerPhysics());

        controller = new PlayerController();
        state = standingState;
    }

    public void moveLeft() {
        state.moveLeft(this);
    }

    public void moveRight() {
        state.moveRight(this);
    }

    public void moveUp() {
        state.moveUp(this);
    }

    public void moveDown() {
        state.moveDown(this);
    }

    public void update(float deltaTime) {
        controller.update(this);

        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;

        state.update(this, deltaTime);
    }

    @Override
    public TextureRegion getKeyFrame() {
        return Assets.playerNormal;
    }
}
