import java.util.ArrayList;
import java.util.Random;        //import the needed files

public class Deck {
    private ArrayList<Cards> cardDeck;      //defining variable

    Deck(ArrayList<Cards> cardlist) {
        cardDeck = cardlist;
    }       //creating the constructor

    public Cards cardRemoved() {
        int a = new Random().nextInt(cardDeck.size());
        Cards draw = cardDeck.get(a);
        cardDeck.remove(a);
        return draw;
    }       //get a random card and remove the card from the deck

    public ArrayList<Cards> getCardDeck() {
        return cardDeck;
    }       //getter for the card deck
}
