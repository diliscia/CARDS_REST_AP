# A Basic Deck of Cards Game
## The Goal
Your assignment is to code, in Java, a set of classes and a REST API that represent a deck of poker-style playing cards along with the services for a very basic game between multiple players holding cards. A deck is defined as follows: Fifty-two playing cards in four suits: hearts, spades, clubs, and diamonds, with face values of Ace, 2-10, Jack, Queen, and King.

The game API is a very basic game in which one or more decks are added to create a ‘game deck’, commonly referred to as a shoe, along with a group of players getting cards from the game deck.

You must provide the following operations:
* Create and delete a game
* Create a deck
* Add a deck to a game deck
	 * Please note that once a deck has been added to a game deck it cannot be removed.
* Add and remove players from a game
* Deal cards to a player in a game from the game deck
	 * Specifically, for a game deck containing only one deck of cards, a call to shuffle followed by 52 calls to dealCards(1) for the same player should result in the caller being provided all 52 cards of the deck in a random order. If the caller then makes a 53rd call to dealCard(1), no card is dealt. This approach is to be followed if the game deck contains more than one deck.
* Get the list of cards for a player
* Get the list of players in a game along with the total added value of all the cards each
player holds; use face values of cards only. Then sort the list in descending order, from the player with the highest value hand to the player with the lowest value hand:
	 * For instance if player ‘A’ holds a 10 + King then her total value is 23 and player ‘B’ holds a 7 + Queen then his total value is 19, so player ‘A’ will be listed first followed by player ‘B’.
* Get the count of how many cards per suit are left undealt in the game deck (example: 5 hearts, 3 spades, etc.)
* Get the count of each card (suit and value) remaining in the game deck sorted by suit ( hearts, spades, clubs, and diamonds) and face value from high value to low value (King, Queen, Jack, 10....2, Ace with value of 1)
* Shuffle the game deck (shoe)
	 * Shuffle returns no value, but results in the cards in the game deck being
randomly permuted. Please do not use library-provided “shuffle” operations to implement this function. You may use library- provided random number generators in your solution.
	 * Shuffle can be called at any time

The structure of the REST API is up to you but please consider the combination of resources and actions represented by this problem and make appropriate tradeoffs in compliance to strict REST doctrine.

We have intentionally left many details of this assignment vague. You should follow the principle of least surprise in making reasonable decisions regarding the implementation.

While this is a trivial assignment, pretend that this code will become a foundational part of a new product. Take whatever measures you feel are required for your code to meet this bar within the scope of the allotted time and be prepared to discuss the tradeoffs you made.

# Strategy

First I did a research to get familiarized with the terminology of a card game. For example that the drawings in the cards are called suits (for hearts, spades, clubs, and diamonds) and rank is used for the values/numbers on the cards, among other terminology. Then, I looked in the web for examples of card games and how the decks were repestented, how the shuffle was done, etc. 
There were different approches to create the deck of cards. Some examples used  graphic interfaces, other images, others used arrays, others lists and others enumeration. I thought that enumeration is the most convenient choice for the ranks and the suits. The solution is separated  in separated in 4: GameController, Exceptions, Models and Service. The Model objects are: Card, Deck, Game and Player.
A Deck contains a list of Cards, a Player contains a List of Cards, a Game contains a List of Players and a List of Decks.
All the methods, except for those in the object classes, are located in Game Service.

Among the different posibilities I thought that using InteliJ IDE and Postman API Platform was the right choice. I thought that could be nice to be able run the API from a web page but I couldn't finish on time. Below I include a list of the operation/calls the API can perform.   

The Postman Project File CARDS_REST_API.postman_collection.json is in the main folder of this repository.  

## Operations (Calls)

#### Create Game

POST http://localhost:8080/api/game

#### Delete Game

DELETE http://localhost:8080/api/game/{gameId}

#### Add Deck to a Game Deck

POST http://localhost:8080/api/game/{gameId}/deck

#### Add a Player 

POST http://localhost:8080/api/game/{gameId}/player

#### Delete a Player

DELETE http://localhost:8080/api/game/{gameId}/player/{playerId}

#### Deal a Card to a Player

PUT http://localhost:8080/api/game/{gameId}/player/{playerId}/card

#### List of Cards for a Player

GET http://localhost:8080/api/game/{gameId}/player/{playerId}/cards

#### List of Players in a Gme Along with the Cards

GET http://localhost:8080/api/game/{gameId}/players

#### Get the Count of How Many Cards per Suit are Left Undealt

GET http://localhost:8080/api/game/{gameId}/undealt

#### Get the Count of Each Card (Suit and Value) Remaining in the Game Deck

GET http://localhost:8080/api/game/{gameId}/cards

#### Shuffle the Cards from a Game 

POST http://localhost:8080/api/game/{gameId}/shuffle
