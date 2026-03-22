package domain.hand;

import domain.card.Card;
import domain.card.CardNumber;

import java.util.List;

public class Hand {
    private static final int ACE_BONUS = 10;
    private static final int BUST_LIMIT_SCORE = 21;

    private final List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Score getScore() {
        return new Score(calculateScore());
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    public boolean hasScoreLessThanOrEqual(int value) {
        return getScore().isLessThanOrEqual(value);
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getScore().value() == 21;
    }

    private int calculateScore() {
        int baseSum = calculateBaseSum();
        int aceCount = countAce();
        return applyAceLogic(baseSum, aceCount);
    }

    private int calculateBaseSum() {
        int sum = 0;
        for (Card card : cards) {
            sum += card.getCardNumber().getValue();
        }
        return sum;
    }

    private int countAce() {
        return (int) cards.stream()
                .map(Card::getCardNumber)
                .filter(cardNumber -> cardNumber == CardNumber.ACE)
                .count();
    }

    private int applyAceLogic(int baseSum, int aceCount) {
        int score = baseSum;
        for (int i = 0; i < aceCount; i++) {
            if (score + ACE_BONUS <= BUST_LIMIT_SCORE) {
                score = score + ACE_BONUS;
            }
        }
        return score;
    }
}
