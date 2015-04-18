package com.smokecastles.ld32;

/**
 * Created by juanma on 18/04/15.
 */
public class Player {
    PlayerController controller;


    public Player(float x, float y) {
        controller = new PlayerController();
    }

    public void update(float deltaTime) {
        controller.update(this);
    }
}
