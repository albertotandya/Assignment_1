import java.util.ArrayList;

public class Players {
    ArrayList<Cards> handCard;
    String playerName;

    Players(String name) {
        handCard = new ArrayList<Cards>();
        playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }
    public Cards getCards(int a) {
        return handCard.get(a);
    }
    public void drawCards (Cards card) {
        handCard.add(card);
    }
    public void playTurn() {

    }
}
