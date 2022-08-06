package com.game.exception;

public class GameNotFoundException extends Exception {

    private static String GAME_NOT_FOUND = "Game %d not found";

    public GameNotFoundException(Integer gameId) {
        super(String.format(GAME_NOT_FOUND, gameId));
    }
}
