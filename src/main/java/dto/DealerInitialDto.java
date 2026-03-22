package dto;

import domain.card.Card;
import domain.participant.Dealer;

import java.util.List;

public record DealerInitialDto(List<String> cards) {
    public static DealerInitialDto from(Dealer dealer){
        List<String> cardInfo = dealer.getHandCards().stream()
                .limit(1)
                .map(Card::getDisplayName)
                .toList();
        return new DealerInitialDto(cardInfo);
    }
}
