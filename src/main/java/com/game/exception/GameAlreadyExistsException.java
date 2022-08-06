package com.game.exception;

public class GameAlreadyExistsException extends Exception {

    private static String GAME_ALREADY_EXISTS = "Game %d already exists";

    public GameAlreadyExistsException(Integer gameId) {
        super(String.format(GAME_ALREADY_EXISTS, gameId));
    }
}
