package com.game.exception;

public class GameDoesNotHavePlayersException extends Exception {

    private static String GAME_DOES_NOT_HAVE_PLAYERS = "Game %d does not have players";

    public GameDoesNotHavePlayersException(Integer gameId) {
        super(String.format(GAME_DOES_NOT_HAVE_PLAYERS, gameId));
    }
}
