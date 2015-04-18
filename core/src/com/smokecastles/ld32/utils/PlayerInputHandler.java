package com.smokecastles.ld32.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.smokecastles.ld32.view.DebugRenderer;
import com.smokecastles.ld32.entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanma on 5/02/15.
 */
public class PlayerInputHandler {
    Controller controller;
    boolean hasControllers;

    private boolean isSpaceDown = false;

    public interface Command {
        public abstract void execute(Player player);
    }

    private static Command moveLeftCommand  = new Command() {
        @Override
        public void execute(Player player) {
            player.moveLeft();
        }
    };

    private static Command moveRightCommand = new Command() {
        @Override
        public void execute(Player player) {
            player.moveRight();
        }
    };

    private static Command moveUpCommand = new Command() {
        @Override
        public void execute(Player player) {
            player.moveUp();
        }
    };

    private static Command moveDownCommand = new Command() {
        @Override
        public void execute(Player player) {
            player.moveDown();
        }
    };

    private static Command attackCommand = new Command() {
        @Override
        public void execute(Player player) {
            player.attack(true);
        }
    };

    private static Command cancelAttackCommand = new Command() {
        @Override
        public void execute(Player player) {
            player.attack(false);
        }
    };

    private static Command resetPosition    = new Command() {
        @Override
        public void execute(Player player) {
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
        Command keyUp       = moveUpCommand;
        Command keyDown     = moveDownCommand;
        Command keySpaceDown  = attackCommand;
        Command keySpaceUp  = cancelAttackCommand;

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

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ) {
            commands.add(keyDown);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) ) {
            isSpaceDown = true;
            // Start attack
            commands.add(keySpaceDown);
        } else if (isSpaceDown) {
            isSpaceDown = false;
            // Stop attack
            commands.add(keySpaceUp);
        }

        // XBOX 360 PAD
        if (controller != null){
            if(controller.getAxis(Xbox360Pad.AXIS_LEFT_Y) > 0.2f) {
                commands.add(keyRight);
            }

            if (controller.getAxis(Xbox360Pad.AXIS_LEFT_Y) < -0.2f) {
                commands.add(keyLeft);
            }

            if (controller.getAxis(Xbox360Pad.AXIS_LEFT_X) < -0.2f) {
                commands.add(keyUp);
            }

            if (controller.getAxis(Xbox360Pad.AXIS_LEFT_X) > -0.2f) {
                commands.add(keyDown);
            }

//            if (controller.getButton(Xbox360Pad.BUTTON_A)) {
//                commands.add(keySpace);
//            }

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
