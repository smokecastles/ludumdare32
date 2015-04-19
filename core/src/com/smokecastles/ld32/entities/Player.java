package com.smokecastles.ld32.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.smokecastles.ld32.controller.PlayerController;

public class Player extends DynamicGameEntity {
    public static final float WIDTH = 2f;
    public static final float HEIGHT = 2f;

    public static final float WALK_ACCEL = 10f;
    public static final float WALK_DAMPING = 0.89f;

    public static final float MAX_WEAPON_RADIUS = WIDTH * 3;

    public static final int INITIAL_HEALTH = 12;

    PlayerState.NormalState normalState = new PlayerState.NormalState();
    PlayerState.AttackingState attackingState = new PlayerState.AttackingState();
    PlayerState.JustHitState justHitState = new PlayerState.JustHitState();

    PlayerController controller;

    PlayerState state; // current state

    public Circle weaponArea = new Circle();

    int health;

    boolean finishedCharging;

    public Player(float x, float y) {
        super(x, y, WIDTH, HEIGHT, new PlayerPhysics());

        controller = new PlayerController();

        health = INITIAL_HEALTH;

        state = normalState;
        state.enterState(this);

        finishedCharging = false;
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

    public void attack(boolean active) {
        if (active) {
            state.attack(this);
        } else {
            state = normalState;
            state.enterState(this);

            // To be read by World
            finishedCharging = true;
        }
    }

    public void hitByEnemy() {
        state.hitByEnemy(this);
    }

    public void update(float deltaTime) {
        controller.update(this);

        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;

        weaponArea.x = position.x;
        weaponArea.y = position.y;

        state.update(this, deltaTime);
    }

    @Override
    public TextureRegion getKeyFrame() {
        return state.getKeyFrame();
    }

    public int health(){
        return health;
    }
}
