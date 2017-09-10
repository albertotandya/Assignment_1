import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    ArrayList<Cards> cardDeck;

    Deck(ArrayList<Cards> cardlist) {
        cardDeck = cardlist;
    }

    public Cards drawCard() {
        int a = new Random().nextInt(cardDeck.size());
        Cards draw = cardDeck.get(a);
        cardDeck.remove(a);
        return draw;
    }

    public ArrayList<Cards> getCardDeck() {
        return cardDeck;
    }
}
