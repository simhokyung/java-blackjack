package domain;

import domain.participant.Dealer;
import domain.participant.Player;
import domain.participant.Players;

public class ProfitCalculator {

    public static double calculatePlayerProfit(Player player, Dealer dealer){
        ProfitResult result = determineProfitResult(player,dealer);
        return result.apply(player.getBettingMoney().getMoney());
    }

    public static double calculateDealerProfit(Players players, Dealer dealer) {
        return -players.getPlayers().stream()
                .mapToDouble(player -> calculatePlayerProfit(player, dealer))
                .sum();
    }

    public static ProfitResult determineProfitResult(Player player, Dealer dealer) {
        if (isDrawBlackjack(player, dealer)) {return ProfitResult.DRAW;}

        if (player.isBlackjack()) {return ProfitResult.BLACKJACK;}

        if (isLose(player, dealer)) {return ProfitResult.LOSE;}

        if (isWin(player, dealer)) {return ProfitResult.WIN;}

        return ProfitResult.DRAW;
    }

    public static String formatProfit(double profit) {
        if (profit == (long) profit) {
            return String.valueOf((long) profit);
        }
        return String.valueOf(profit);
    }

    private static boolean isDrawBlackjack(Player player, Dealer dealer) {
        return player.isBlackjack() && dealer.isBlackjack();
    }

    private static boolean isLose(Player player, Dealer dealer) {
        return dealer.isBlackjack()
                || player.isBust()
                || isDealerHigher(player, dealer);
    }

    private static boolean isWin(Player player, Dealer dealer) {
        return dealer.isBust()
                || isPlayerHigher(player, dealer);
    }

    private static boolean isDealerHigher(Player player, Dealer dealer) {
        return player.getScore().isLessThan(dealer.getScore()) && !dealer.isBust();
    }

    private static boolean isPlayerHigher(Player player, Dealer dealer) {
        return player.getScore().isGreaterThan(dealer.getScore());
    }
}
