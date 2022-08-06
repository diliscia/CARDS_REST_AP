package com.game.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Integer playerId;

    private List<Card> cards = new ArrayList<>();

    public Player(Integer aPlayerId) {
        this.playerId = aPlayerId;
    }

    public Integer getPlayerId() {
        return this.playerId;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(
        List<Card> aCards) {
        this.cards = aCards;
    }

}
