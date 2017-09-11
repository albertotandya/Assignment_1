import java.util.ArrayList;
import java.util.Scanner;       //importing the needed files

public class GamePlay {
    ArrayList<Cards> playedCards;
    Deck deckCards;
    ArrayList<Players> players;
    String category;
    String lastPlayer;
    Scanner input = new Scanner(System.in);     //defining the variables

    GamePlay(int numOfPlayers, Deck allCards) {     //creating the constructor
        category = "";
        playedCards = new ArrayList<Cards>();
        players = new ArrayList<Players>();
        deckCards = allCards;
        for (int a = 0; a < numOfPlayers; a++) {        //to get players' name
            System.out.println("Enter the player's name!");
            String playerName = input.nextLine();
            players.add(new Players(playerName));
        }
        for (int a = 0; a < 8; a++) {       //to give each player 8 cards
            for (Players player : players) {
                player.drawCards(allCards.cardRemoved());
            }
        }
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<Players> getPlayers() {
        return players;
    }

    public Deck getDeckCards() {
        return deckCards;
    }

    public String getLastPlayer() { return lastPlayer; }        //getter for the variables

    public Cards getLastCard() {
        return playedCards.get(playedCards.size()-1);
    }       //to know the last card played

    public void setCategory (String mode) { category = mode; }

    public void setLastPlayer(String lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public void setDeckCards(Deck deckCards) {
        this.deckCards = deckCards;
    }      //setter for the variables

    public void putCard(Cards cards) {
        playedCards.add(cards);
    }       //to add the played card to the variable

    public String categoryDesc() {      //to show the description of the category
        String desc = "";
        if (category.equals("H")) {
            desc = "Trump category: Hardness";
        } else if (category.equals("S")) {
            desc = "Trump category: Specific Gravity";
        } else if (category.equals("C")) {
            desc = "Trump category: Cleavage";
        } else if (category.equals("CA")) {
            desc = "Trump category: Crustal Abundance";
        } else if (category.equals("EV")) {
            desc = "Trump category: Economic Value";
        }
        return desc;
    }

    public void reFillCard() {      //to re-fill the played cards back to the deck, except the last played card
        ArrayList<Cards> refill = new ArrayList<Cards>();
        for (Cards card : playedCards) {
            refill.add(card);
        }
        setDeckCards(new Deck(refill));
        playedCards.clear();
        playedCards.add(refill.get(refill.size()-1));
    }

    public boolean cardPlayedBefore() {     //the check whether any cards have been played before or not
        boolean played = false;
        if (playedCards.size()>0) {
            played = true;
        }
        return played;
    }

    public boolean playCard(Cards card, Players player) {       //to check whether the played card can be played or not
        boolean higher = false;
        int compare = 0;
        if (playedCards.size() == 0 || this.playAgain(player)) {        //to check if it is a new game or this is a new round
            if (card instanceof TrumpCards) {       //to check if the first played card is a supertrump card
                setCategory(((TrumpCards) card).cardEffect());
            }
            higher = true;
        } else {
            if (card instanceof NormCards) {        //to check if the played card is a normal card
                if (getLastCard() instanceof NormCards) {       //to check whether the last played card is a normal card or a supertrump card
                    if (category.equals("H")) {
                        Float now = new Float(((NormCards) card).getHardness());
                        Float last = new Float(((NormCards) getLastCard()).getHardness());
                        compare = now.compareTo(last);
                    } else if (category.equals("S")) {
                        Float now = new Float(((NormCards) card).getSpecGravity());
                        Float last = new Float(((NormCards) getLastCard()).getSpecGravity());
                        compare = now.compareTo(last);
                    } else if (category.equals("C")) {
                        Float now = new Float(((NormCards) card).getCleavageValue());
                        Float last = new Float(((NormCards) getLastCard()).getCleavageValue());
                        compare = now.compareTo(last);
                    } else if (category.equals("CA")) {
                        Float now = new Float(((NormCards) card).getCrustalAbunVal());
                        Float last = new Float(((NormCards) getLastCard()).getCrustalAbunVal());
                        compare = now.compareTo(last);
                    } else if (category.equals("EV")) {
                        Float now = new Float(((NormCards) card).getEcoValueValue());
                        Float last = new Float(((NormCards) getLastCard()).getEcoValueValue());
                        compare = now.compareTo(last);

                    }
                    if (compare > 0) {
                        higher = true;
                    } else {
                        System.out.println("The selected card does not has higher value!");
                    }
                } else {        //or else, the last played card is a supertrump card
                    higher = true;
                }
            } else {        //or else, the played is a supertrump card
                setCategory(((TrumpCards) card).cardEffect());
                higher = true;
            }
        }
        return higher;
    }

    public boolean playAgain(Players players) {     //to check if all the other players have PASS
        boolean decide = false;
        if (getLastPlayer().equals(players.getPlayerName())) {
            decide = true;
        }
        return decide;
    }
}
