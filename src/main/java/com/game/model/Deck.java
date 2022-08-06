package com.game.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>(52);

    public Deck() {
        super();
        initCards();
    }

    private void initCards() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.add(new Card(suit, rank));
            }
        }
    }

    public List<Card> getCards() {
        return this.cards;
    }
}
