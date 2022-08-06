package com.game.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.game.model.Card;
import com.game.model.Deck;
import com.game.model.Rank;
import com.game.model.Game;
import com.game.model.Player;
import com.game.model.Suit;
import com.game.exception.GameDoesNotHavePlayersException;
import com.game.exception.GameNotFoundException;
import com.game.exception.PlayerNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl
    implements GameService {
    private List<Game> games = new ArrayList<>();
    private static Random RANDOM = new Random();
    private static int MAX_CARDS = 52;

    private AtomicInteger gameIdsHolder = new AtomicInteger(1);

    @Override
    public void createGame() {
        var game = new Game();
        var deck = new Deck();
        List<Deck> decks = new ArrayList<>();
        decks.add(deck);
        game.setDecks(decks);
        game.setGameId(this.gameIdsHolder.getAndIncrement());
        this.games.add(game);
    }

    @Override
    public void deleteGame(Integer gameId)
        throws GameNotFoundException {
        Game game = findGameById(gameId);
        this.games.remove(game);
    }

    @Override
    public void addPlayer(Integer gameId, Player player)
        throws GameNotFoundException {
        Game game = findGameById(gameId);
        game.getPlayers().add(player);
    }

    @Override
    public void deletePlayer(Integer gameId, Integer playerId)
        throws PlayerNotFoundException,
        GameNotFoundException {
        Game game = findGameById(gameId);
        List<Player> players = game.getPlayers();
        Player player = getPlayer(players, playerId);
        players.remove(player);

    }

    @Override
    public Player getPlayer(Integer gameId)
        throws GameNotFoundException,
        PlayerNotFoundException {
        Game game = findGameById(gameId);
        return getPlayer(game.getPlayers(), gameId);
    }

    @Override
    public List<Player> getSortedPlayers(Integer gameId)
        throws GameNotFoundException,
        GameDoesNotHavePlayersException {
        Map<Player, Integer> playersWithTotalCardsValue = new HashMap<>();
        Game game = findGameById(gameId);
        List<Player> players = game.getPlayers();
        if (players.isEmpty()) {
            throw new GameDoesNotHavePlayersException(gameId);
        }
        for (Player player : players) {
            List<Card> cards = player.getCards();
            Integer value = 0;
            for (Card card : cards) {
                value += card.getFaceValue().getValue(); // adds the values of the cards
                playersWithTotalCardsValue.put(player, value);
//                System.out.println (player + " " + card + " " + value);
            }
        }
        Map<Player, Integer> sortedMap = getSortedMap(playersWithTotalCardsValue);
        return new ArrayList<>(sortedMap.keySet());
    }

    @Override
    public void addDeckToGameDeck(Integer gameId, Deck deck)
        throws GameNotFoundException {
        Game game = findGameById(gameId);
        game.getDecks().add(deck);
    }

    @Override
    public void dealCardToAPlayer(Integer gameId, Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException {
        Game game = findGameById(gameId);
        Player player = getPlayer(game.getPlayers(), playerId);
        List<Card> cards = player.getCards();
        if (cards.size() < MAX_CARDS) {
            List<Deck> decks = game.getDecks();
            for (Deck deck : decks) {
                List<Card> deckCards = deck.getCards();
                if (!deckCards.isEmpty()) {
                    cards.add(deckCards.get(0));        // this gives the card to the player
                    deckCards.remove(deckCards.get(0)); // this removes the card from deck
                }
            }
            player.setCards(cards);
        }
    }

    @Override
    public List<Card> getPlayerCards(Integer gameId, Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException {
        Game game = findGameById(gameId);
        Player player = getPlayer(game.getPlayers(), playerId);
        return player.getCards();
    }

    @Override
    public Map<Suit, Long> getUndealtCards(Integer gameId)
        throws GameNotFoundException {
        List<Card> cards = new ArrayList<>();
        Game game = findGameById(gameId);
        List<Deck> decks = game.getDecks();
        for (Deck deck : decks) {
            cards.addAll(deck.getCards());
        }
        return cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    @Override
    public Map<Suit, Map<Rank, List<Card>>> getSortedRemainingUndealtCards(Integer gameId)
        throws GameNotFoundException {
        List<Card> cards = new ArrayList<>();
        Game game = findGameById(gameId);
        List<Deck> decks = game.getDecks();
        for (Deck deck : decks) {
            cards.addAll(deck.getCards());
        }
        return cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.groupingBy(Card::getFaceValue)));
    }

    @Override
    public void shuffle(Integer gameId)
        throws GameNotFoundException {
        Game game = findGameById(gameId);
        List<Deck> decks = game.getDecks();
        for (Deck deck : decks) {
            List<Card> cards = deck.getCards();
            if (cards != null && !cards.isEmpty()) {
                int size = cards.size();
                for (int i = 0; i < size; i++) {
                    int newIndex = i + RANDOM.nextInt(size - i);
                    swap(cards, i, newIndex);
                }
            }
        }
    }

    private <T> void swap(List<T> list, int index, int value) {
        T objectContent = list.get(index);
        list.set(index, list.get(value));
        list.set(value, objectContent);
    }

    private Player getPlayer(List<Player> players, Integer playerId)
        throws PlayerNotFoundException {
        return players.stream().filter(player -> player.getPlayerId().equals(playerId)).findFirst()
            .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }

    public Game findGameById(Integer gameId)
        throws GameNotFoundException {
        return this.games.stream().filter(game -> game.getGameId().equals(gameId)).findFirst()
            .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    private Map<Player, Integer> getSortedMap(Map<Player, Integer> unsortedMap) {
        Map<Player, Integer> sortedMap = new HashMap<>();
        unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }
}
