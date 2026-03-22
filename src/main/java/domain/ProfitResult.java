package domain;

public enum ProfitResult {
    DRAW(0.0),
    WIN(1.0),
    LOSE(-1.0),
    BLACKJACK(1.5);

    private final double rate;

    ProfitResult(double rate) {
        this.rate = rate;
    }

    public double apply(int money){
        return money*rate;
    }
}
