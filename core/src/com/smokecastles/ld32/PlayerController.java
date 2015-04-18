package com.smokecastles.ld32;

import com.smokecastles.ld32.framework.PlayerInputHandler;

import java.util.List;

/**
 * Created by juanma on 18/04/15.
 */
public class PlayerController {
    PlayerInputHandler inputHandler;

    public PlayerController() {
        inputHandler = new PlayerInputHandler();
    }

    public void update(Player player) {
        List<PlayerInputHandler.Command> commands = inputHandler.getInput();

        for (PlayerInputHandler.Command command : commands) {
            command.execute(player);
        }
    }
}
