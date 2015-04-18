package com.smokecastles.ld32.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.smokecastles.ld32.World;

public abstract class DynamicGameEntity {
    public enum Orientation {
        LEFT, RIGHT, UP, DOWN
    }
    
    PhysicsComponent physics;

    public final Vector2 position;
    public final Rectangle bounds;

    public final Vector2 velocity;
    public final Vector2 accel;

    public Orientation orientation;
    boolean grounded = false;
    boolean atEdge = false;

    public DynamicGameEntity(float x, float y, float width, float height, PhysicsComponent physics) {
        this.position   = new Vector2(x, y);
        this.bounds     = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.velocity   = new Vector2();
        this.accel      = new Vector2();

        this.physics    = physics;
    }

    public abstract void update(float deltaTime);

    public void updatePhysics(World world) {
        physics.update(this, world);
    }

    public abstract TextureRegion getKeyFrame();
}
