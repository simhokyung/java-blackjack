package domain;

import domain.participant.BettingMoney;
import domain.participant.Dealer;
import domain.participant.Player;
import domain.participant.Players;

public class ProfitCalculator {
    private static final double WIN_PROFIT_RATE = 1.0;
    private static final double LOSE_PROFIT_RATE = -1.0;
    private static final double BLACKJACK_PROFIT_RATE = 1.5;
    private static final double DRAW_PROFIT = 0.0;

    public static double calculatePlayerProfit(Player player, Dealer dealer) {
        BettingMoney bettingMoney = player.getBettingMoney();

        if (isDrawBlackjack(player, dealer)) {
            return DRAW_PROFIT;
        }
        if (player.isBlackjack()) {
            return calculateProfit(bettingMoney,BLACKJACK_PROFIT_RATE);
        }
        if (isLose(player, dealer)) {
            return calculateProfit(bettingMoney,LOSE_PROFIT_RATE);
        }
        if (isWin(player, dealer)) {
            return calculateProfit(bettingMoney,WIN_PROFIT_RATE);
        }
        return DRAW_PROFIT;
    }

    public static double calculateDealerProfit(Players players, Dealer dealer) {
        return -players.getPlayers().stream()
                .mapToDouble(player -> calculatePlayerProfit(player, dealer))
                .sum();
    }

    public static double calculateProfit(BettingMoney bettingMoney, double rate){
        return bettingMoney.getMoney()*rate;
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
