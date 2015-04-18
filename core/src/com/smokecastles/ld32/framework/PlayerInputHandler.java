package com.smokecastles.ld32.framework;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.smokecastles.ld32.DebugRenderer;
import com.smokecastles.ld32.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanma on 5/02/15.
 */
public class PlayerInputHandler {
    Controller controller;
    boolean hasControllers;

    public interface Command {
        public abstract void execute(Player player);
    }

    private static Command moveLeftCommand  = new Command() {
        @Override
        public void execute(Player dude) {
            /*dude.moveLeft();*/
        }
    };

    private static Command moveRightCommand = new Command() {
        @Override
        public void execute(Player dude) {
            /*dude.moveRight();*/
        }
    };

    private static Command jumpCommand      = new Command() {
        @Override
        public void execute(Player player) {
            //dude.jump();
        }
    };

    private static Command resetPosition    = new Command() {
        @Override
        public void execute(Player dude) {
            //dude.position.x = 2;
            //dude.position.y = 12;
        }
    };

    public PlayerInputHandler() {
        if(Controllers.getControllers().size == 0)
            hasControllers = false;
        else
            controller = Controllers.getControllers().first();
    }

    public List<Command> getInput() {
        List<Command> commands = new ArrayList<Command>();

        Command keyLeft     = moveLeftCommand;
        Command keyRight    = moveRightCommand;
        Command keyUp       = jumpCommand;
        Command keySpace    = jumpCommand;

        // TODO: distinguish platform
        Application.ApplicationType appType = Gdx.app.getType();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            commands.add(keyLeft);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            commands.add(keyRight);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) ) {
            commands.add(keyUp);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) ) {
            commands.add(keySpace);
        }

        // XBOX 360 PAD
        if (controller != null){
            if(controller.getAxis(Xbox360Pad.AXIS_LEFT_Y) > 0.2f) {
                commands.add(keyRight);
            }

            if (controller.getAxis(Xbox360Pad.AXIS_LEFT_Y) < -0.2f) {
                commands.add(keyLeft);
            }

            if (controller.getButton(Xbox360Pad.BUTTON_A)) {
                commands.add(keySpace);
            }

            if (controller.getButton(Xbox360Pad.BUTTON_BACK)) {
                commands.add(resetPosition);
            }
        }

        // DEBUG MODE
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) ) {
            DebugRenderer.DEBUG_ENABLED = !DebugRenderer.DEBUG_ENABLED;
            Gdx.app.log("InputHandler", "Toggle debug");
        }

        // DEBUG ACTIONS
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) ) {
            commands.add(resetPosition);
        }

        return commands;
    }
}
