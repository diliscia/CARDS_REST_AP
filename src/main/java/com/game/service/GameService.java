package com.game.service;

import java.util.List;
import java.util.Map;

import com.game.exception.GameAlreadyExistsException;
import com.game.exception.GameDoesNotHavePlayersException;
import com.game.exception.GameNotFoundException;
import com.game.exception.PlayerNotFoundException;
import com.game.model.Suit;
import com.game.model.Card;
import com.game.model.Deck;
import com.game.model.Rank;
import com.game.model.Player;

public interface GameService {

    // Method to Create a Game
    void createGame()
            throws GameAlreadyExistsException;

    // Method to Delete a Game
    void deleteGame(
        Integer gameId)
        throws GameNotFoundException;

    // Method to Add a Deck to the Game
    void addDeckToGameDeck(
        Integer gameId,
        Deck deck)
        throws GameNotFoundException;

    // Method to Deal a Card to a Player
    void dealCardToAPlayer(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
            PlayerNotFoundException;

    // Method to Add a Player
    void addPlayer(
        Integer gameId,
        Player player)
        throws GameNotFoundException;

    // Method to Remove a Player
    void deletePlayer(
        Integer gameId,
        Integer playerId)
        throws PlayerNotFoundException,
        GameNotFoundException;

    // Method to Get Sorted Players
    List<Player> getSortedPlayers(
        Integer gameId)
        throws GameNotFoundException,
        GameDoesNotHavePlayersException;

    // Method to Get a PLayer
    Player getPlayer(
        Integer gameId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    // Method to Get Player Cards
    List<Card> getPlayerCards(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    // Method to Get the Undealt  Cards
    Map<Suit, Long> getUndealtCards(
        Integer gameId)
        throws GameNotFoundException;

    // Method to Get the Sorted Undealt Cards
    Map<Suit, Map<Rank, List<Card>>> getSortedRemainingUndealtCards(
        Integer gameId)
        throws GameNotFoundException;

    // Method to Shuffle the Cards
    void shuffle(
        Integer gameId)
        throws GameNotFoundException;

}
