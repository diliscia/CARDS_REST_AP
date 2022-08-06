package com.game.exception;

public class PlayerNotFoundException extends Exception {

    private static String PLAYER_NOT_FOUND = "Player %d not found";

    public PlayerNotFoundException(Integer playerId) {
        super(String.format(PLAYER_NOT_FOUND, playerId));
    }
}
