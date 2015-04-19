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
    public static final float WALK_DAMPING = 0.89f;

    EnemyState.NormalState normalState = new EnemyState.NormalState();
    EnemyState.JustHitState justHitState = new EnemyState.JustHitState();

    EnemyController controller;

    EnemyState state;

    public Circle detectionArea;

    public boolean isDisappearing;
    public boolean isDead;
    public boolean playerInRange;
    public Vector2 playerPos;

    public int health;

    private TextureRegion keyFrame;

    public Enemy(float x, float y, EnemyType type) {
        super(x, y, type.width, type.height, new EnemyPhysics());

        accel = type.accel;
        detectionArea = new Circle(x, y, type.detectionAreaRadius);
        health = type.health;

        controller = type.controller;

        keyFrame = type.keyFrame;

        state = normalState;
        state.enterState(this);
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

    public void takeDamage(int damage) {
        state.takeDamage(this, damage);
    }

    @Override
    public TextureRegion getKeyFrame() {
        return state.getKeyFrame();
    }

    public static abstract class EnemyType {
        float width;
        float height;
        float detectionAreaRadius;
        float accel;
        int health;
        EnemyController controller;
        TextureRegion keyFrame;
    }

    public static class BigEnemy extends EnemyType {
        public BigEnemy() {
            width = 3f;
            height = 3f;
            detectionAreaRadius = 10f;
            accel = 4f;
            health = 3;
            controller = new EnemyController(EnemyController.MovementMode.FOLLOW_PLAYER);
            keyFrame = Assets.enemyBig;
        }
    }

    public static class MedEnemy extends EnemyType {
        public MedEnemy() {
            width = 2f;
            height = 2f;
            detectionAreaRadius = 5f;
            accel = 6f;
            health = 2;
            controller = new EnemyController(EnemyController.MovementMode.TOTALLY_RANDOM);
            keyFrame = Assets.enemyBig;
        }
    }

    public static class SmallEnemy extends EnemyType {
        public SmallEnemy() {
            width = 1f;
            height = 1f;
            detectionAreaRadius = 3f;
            accel = 12f;
            health = 1;
            controller = new EnemyController(EnemyController.MovementMode.ESCAPE_FROM_PLAYER);
            keyFrame = Assets.enemyBig;
        }
    }
}
