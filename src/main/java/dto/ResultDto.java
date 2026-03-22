package dto;

import domain.card.Card;
import domain.participant.Dealer;
import domain.participant.Participant;

import java.util.List;

public record ResultDto(
        List<String> cards,
        int score
) {
    public static ResultDto from(Participant participant) {
        List<String> cardInfo = participant.getHandCards().stream()
                .map(Card::getDisplayName)
                .toList();

        return new ResultDto(cardInfo, participant.getScoreValue());
    }
}
