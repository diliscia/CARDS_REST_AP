package com.game.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Suit aSuit, Rank aRank) {
        super();
        this.suit = aSuit;
        this.rank = aRank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Rank getFaceValue() {
        return this.rank;
    }

    @Override
    public boolean equals(
        Object other) {
        if (!(other instanceof Card)) {
            return false;
        }
        Card castOther = (Card) other;
        return new EqualsBuilder().append(this.suit, castOther.suit).append(this.rank, castOther.rank).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.suit).append(this.rank).toHashCode();
    }
}
