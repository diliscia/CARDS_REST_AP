package com.game.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Integer gameId;
    private List<Player> players = new ArrayList<>();
    private List<Deck> decks;

    public Game() {
        super();
    }

    public Integer getGameId() {
        return this.gameId;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setGameId(
        Integer aGameId) {
        this.gameId = aGameId;
    }

    public List<Deck> getDecks() {
        return this.decks;
    }

    public void setDecks(
        List<Deck> aDecks) {
        this.decks = aDecks;
    }
}
