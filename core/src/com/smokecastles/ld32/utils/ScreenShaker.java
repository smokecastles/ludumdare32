package com.smokecastles.ld32.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

// Modified from https://carelesslabs.wordpress.com/2014/05/08/simple-screen-shake/
public class ScreenShaker {

    public float time;
    Random random;
    float x, y;
    float current_time;
    float power;
    float current_power;

    OrthographicCamera cam;
    Vector3 initialPos;

    boolean alreadyShaking;

    public ScreenShaker(){
        time = 0;
        current_time = 0;
        power = 0;
        current_power = 0;
        random = new Random();
        initialPos = new Vector3();
    }

    public void startShaking(float power, float time, OrthographicCamera cam, Vector3 initialPos) {
        if (alreadyShaking) return;

        this.power = power;
        this.time = time;
        this.current_time = 0;
        this.cam = cam;
        this.initialPos.set(initialPos);

        alreadyShaking = true;
    }

    public void tick(float delta) {
        if (cam == null) return; // not yet initialized

        if(current_time < time) {
            current_power = power * ((time - current_time) / time);
            // generate random new x and y values taking into account
            // how much force was passed in
            x = (random.nextFloat() - 0.5f) * 2 * current_power;
            y = (random.nextFloat() - 0.5f) * 2 * current_power;

            // Set the camera to this new x/y position
            cam.translate(-x, -y);
            current_time += delta;
        } else {
            // When the shaking is over move the camera back to the hero position
            cam.position.x = initialPos.x;
            cam.position.y = initialPos.y;

            alreadyShaking = false;
        }
    }
}
