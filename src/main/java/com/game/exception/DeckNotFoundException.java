package com.game.exception;

public class DeckNotFoundException extends Exception {

    private static String DECK_NOT_FOUND = "Deck id:[%d] not found";

    public DeckNotFoundException(Integer deckId) {
        super(String.format(DECK_NOT_FOUND, deckId));
    }
}