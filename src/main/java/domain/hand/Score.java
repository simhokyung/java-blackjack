package domain.hand;

public record Score(int value) {
    private static final int BUST_LIMIT_SCORE = 21;

    public boolean isBust() {
        return value > BUST_LIMIT_SCORE;
    }

    public boolean isLessThanOrEqual(int target) {
        return value <= target;
    }

    public boolean isGreaterThan(Score other) {
        return value > other.value;
    }

    public boolean isLessThan(Score other) {
        return value < other.value;
    }
}
