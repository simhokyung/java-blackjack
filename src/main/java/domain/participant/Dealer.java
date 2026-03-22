package domain.participant;

import domain.hand.Hand;

public class Dealer extends Participant {
    private static final int DRAW_THRESHOLD = 16;

    private Dealer(Hand hand) {
        super(hand);
    }

    public static Dealer from(Hand hand) {
        return new Dealer(hand);
    }

    public boolean shouldDrawCard() {
        return getHand().hasScoreLessThanOrEqual(DRAW_THRESHOLD);
    }
}
