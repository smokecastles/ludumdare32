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

    PlayerController controller;

    public Player(float x, float y) {
        super(x, y, WIDTH, HEIGHT, new PlayerPhysics());

        controller = new PlayerController();
    }

    public void update(float deltaTime) {
        controller.update(this);
    }

    @Override
    public TextureRegion getKeyFrame() {
        return Assets.playerNormal;
    }
}
