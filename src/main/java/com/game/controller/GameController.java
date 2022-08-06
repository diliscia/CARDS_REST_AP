package com.game.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.game.model.Deck;
import com.game.model.Player;
import com.game.exception.GameAlreadyExistsException;
import com.game.exception.GameDoesNotHavePlayersException;
import com.game.exception.GameNotFoundException;
import com.game.exception.PlayerNotFoundException;
import com.game.service.GameService;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private GameService gameService;

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    public GameController() {
        super();
    }

    // POST Method to Create a game
    @PostMapping("/game")
    public ResponseEntity<String> createGame() {
        try {
            this.gameService.createGame();
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (GameAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    //DELETE Method to Delete a game
    @DeleteMapping("game/{gameId}")
    public ResponseEntity<String> deleteGame(
        @PathVariable Integer gameId) {
        try {
            this.gameService.deleteGame(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    //POST Method to Create and Add a deck to a game deck
    @PostMapping("/game/{gameId}/deck")
    public ResponseEntity<String> addDeck(
        @PathVariable Integer gameId) {

        try {
            this.gameService.addDeckToGameDeck(gameId, new Deck());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    //POST Method to Add a player to a game
    @PostMapping("/game/{gameId}/player")
    public ResponseEntity<?> addPlayer(
        @PathVariable Integer gameId) {
        try {
            Player player = new Player(this.atomicInteger.getAndIncrement());
            this.gameService.addPlayer(gameId, player);
            return ResponseEntity.status(HttpStatus.CREATED).body(player);

        } catch (GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    //DELETE Method to Delete a player from a game
    @DeleteMapping("/game/{gameId}/player/{playerId}")
    public ResponseEntity<String> deletePlayer(
        @PathVariable Integer gameId,
        @PathVariable Integer playerId) {
        try {
            this.gameService.deletePlayer(gameId, playerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    //PUT Method to Deal a card to a player in a game from the game deck
    @PutMapping("/game/{gameId}/player/{playerId}/card")
    public ResponseEntity<?> dealCards(
        @PathVariable Integer gameId,
        @PathVariable Integer playerId) {
        try {
            this.gameService.dealCardToAPlayer(gameId, playerId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    //GET Method to Get the list of cards for a player
    @GetMapping("/game/{gameId}/player/{playerId}/cards")
    public ResponseEntity<?> getPlayerCards(
        @PathVariable Integer gameId,
        @PathVariable Integer playerId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getPlayerCards(gameId, playerId));
        } catch (GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

//    //GET Method to Get a player
//    @GetMapping("/game/{gameId}/player/{playerId}/cards")
//    public ResponseEntity<?> getPlayer(
//        @PathVariable Integer gameId,
//        @PathVariable Integer playerId) {
//        try {
//            Player player = this.gameService.getPlayer(gameId);
//            return ResponseEntity.status(HttpStatus.OK).body(player);
//        } catch (GameNotFoundException | PlayerNotFoundException exception) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
//        }
//
//    }

    //GET Method to Get the list of players in a game along with the total added value of all the cards each
    //player holds
    @GetMapping("/game/{gameId}/players")
    public ResponseEntity<?> getPlayers(
        @PathVariable Integer gameId) {
        try {
            List<Player> players = this.gameService.getSortedPlayers(gameId);
            return ResponseEntity.status(HttpStatus.OK).body(players);
        } catch (GameDoesNotHavePlayersException | GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    //GET Method to Get the count of how many cards per suit are left undealt in the game deck
    @GetMapping("/game/{gameId}/undealt")
    public ResponseEntity<?> getUndealtCards(
        @PathVariable Integer gameId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getUndealtCards(gameId));
        } catch (GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    //GET Method to Get the count of each card (suit and value) remaining in the game deck
    @GetMapping("/game/{gameId}/cards")
    public ResponseEntity<?> getSortedRemainingUndealtCards(
        @PathVariable Integer gameId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getSortedRemainingUndealtCards(gameId));
        } catch (GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    //POST Method to Shuffle the game deck (shoe)
    @PostMapping("/game/{gameId}/shuffle")
    public ResponseEntity<?> shuffle(
        @PathVariable Integer gameId) {
        try {
            this.gameService.shuffle(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

}
