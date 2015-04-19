package com.smokecastles.ld32.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.smokecastles.ld32.controller.EnemyController;
import com.smokecastles.ld32.utils.Assets;

/**
 * Created by juanma on 18/04/15.
 */
public class Enemy extends DynamicGameEntity {
    public static final float WIDTH = 3f;
    public static final float HEIGHT = 3f;

    public static final float WALK_ACCEL = 6f;
    public static final float WALK_DAMPING = 0.89f;

    public static final float DETECTION_RADIUS = 6f;

    EnemyState.NormalState normalState = new EnemyState.NormalState();

    EnemyController controller;

    EnemyState state;

    public Circle detectionArea;

    public boolean isDisappearing;
    public boolean isAlive;
    public boolean playerInRange;
    public Vector2 playerPos;

    public Enemy(float x, float y) {
        super(x, y, WIDTH, HEIGHT, new EnemyPhysics());

        controller = new EnemyController();
        detectionArea = new Circle(x, y, DETECTION_RADIUS);

        state = normalState;
        state.enterState(this);

        isAlive = true;
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
        controller.update(this, deltaTime);

        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;

        detectionArea.x = position.x;
        detectionArea.y = position.y;

        state.update(this, deltaTime);
    }

    public void startFollowingPlayer(Vector2 playerPos) {
        this.playerPos = playerPos;
        playerInRange = true;
    }

    public void stopFollowingPlayer() {
        playerInRange = false;
    }

    @Override
    public TextureRegion getKeyFrame() {
        return Assets.enemyBig;
    }
}
