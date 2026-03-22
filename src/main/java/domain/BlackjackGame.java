package domain;

import domain.card.Card;
import domain.card.Deck;
import domain.hand.Hand;
import domain.participant.*;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private static final int INITIAL_CARD_COUNT = 2;

    private final Deck deck;
    private final GameParticipants participants;

    private BlackjackGame(Deck deck, GameParticipants participants) {
        this.deck = deck;
        this.participants = participants;
    }

    public static BlackjackGame start(List<PlayerInfo> playerInfos) {
        return start(playerInfos, Deck.createDeck());
    }

    public static BlackjackGame start(List<PlayerInfo> playerInfos, Deck deck) {
        Dealer dealer = createDealerWithInitialHand(deck);
        Players players = createPlayersWithInitialHand(playerInfos, deck);
        return new BlackjackGame(deck, GameParticipants.of(dealer, players));
    }

    private static Dealer createDealerWithInitialHand(Deck deck){
        return Dealer.from(new Hand(drawInitialCards(deck)));
    }

    private static Players createPlayersWithInitialHand(List<PlayerInfo> playerInfos, Deck deck){
        List<Player> players = new ArrayList<>();
        for(PlayerInfo playerInfo : playerInfos){
            players.add(createPlayerWithInitialHand(playerInfo,deck));
        }
        return Players.from(players);
    }

    private static Player createPlayerWithInitialHand(PlayerInfo playerInfo, Deck deck) {
        return Player.of(playerInfo, new Hand(drawInitialCards(deck)));
    }

    public void addPlayerCard(Player player) {
        player.addHandCard(deck.draw());
    }

    public boolean playDealerTurn() {
        if (!participants.canDealerDraw()) {
            return false;
        }
        drawDealerCards();
        return true;
    }

    public Dealer getDealer() {
        return participants.getDealer();
    }

    public Players getPlayers() {
        return participants.getPlayers();
    }

    private void drawDealerCards() {
        while (getDealer().shouldDrawCard()) {
            getDealer().addHandCard(deck.draw());
        }
    }


    private static List<Card> drawInitialCards(Deck deck) {
        List<Card> cards = new ArrayList<>();
        for (int count = 0; count < INITIAL_CARD_COUNT; count++) {
            cards.add(deck.draw());
        }
        return cards;
    }
}
