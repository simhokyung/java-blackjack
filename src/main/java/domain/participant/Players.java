package domain.participant;

import java.util.List;

public class Players {
    private final List<Player> players;

    private Players(List<Player> players) {
        this.players = List.copyOf(players);
    }

    public static Players from(List<Player> players) {
        return new Players(players);
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public Player firstPlayers(){
        if(players.isEmpty()){
            throw new IllegalArgumentException("플레이어가 없습니다.");
        }
        return players.getFirst();
    }

    public boolean isAllBust() {
        return players.stream().allMatch(Player::isBust);
    }
}
