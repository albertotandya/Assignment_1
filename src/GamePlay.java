import java.util.ArrayList;
import java.util.Scanner;

public class GamePlay {
    ArrayList<Cards> playedCards;
    Deck deckCards;
    ArrayList<Players> players;
    String category;
    String lastPlayer;
    Scanner input = new Scanner(System.in);

    GamePlay(int numOfPlayers, Deck allCards) {
        category = "";
        playedCards = new ArrayList<Cards>();
        players = new ArrayList<Players>();
        deckCards = allCards;
        for (int a = 0; a < numOfPlayers; a++) {
            System.out.println("Enter the player's name!");
            String playerName = input.nextLine();
            players.add(new Players(playerName));
        }
        for (int a = 0; a < 8; a++) {
            for (Players player : players) {
                player.drawCards(allCards.cardDrawn());
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

    public Cards getLastCard() {
        return playedCards.get(playedCards.size()-1);
    }

    public String getLastPlayer() {
        return lastPlayer;
    }

    public void setCategory (String mode) {
        category = mode;
    }

    public void setLastPlayer(String lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public void setDeckCards(Deck deckCards) {
        this.deckCards = deckCards;
    }

    public void putCard(Cards cards) {
        playedCards.add(cards);
    }
    public String categoryDesc() {
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

    public void reFillCard() {
        ArrayList<Cards> refill = new ArrayList<Cards>();
        for (Cards card : playedCards) {
            refill.add(card);
        }
        setDeckCards(new Deck(refill));
        playedCards.clear();
        playedCards.add(refill.get(refill.size()-1));
    }

    public boolean firstCardPlayed() {
        boolean played = false;
        if (playedCards.size()>0) {
            played = true;
        }
        return played;
    }

    public boolean playCard(Cards card, Players player) {
        boolean higher = false;
        int compare = 0;
        if (playedCards.size() == 0 || this.playAgain(player)) {
            if (card instanceof TrumpCards) {
                category = ((TrumpCards) card).cardEffect();
            }
            higher = true;
        }
        else {
            if (card instanceof NormCards) {
                if (getLastCard() instanceof NormCards) {
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
                        System.out.println("The selected card has lower value!");
                    }
                } else {
                    higher = true;
                }
            } else {
                setCategory(((TrumpCards) card).cardEffect());
                higher = true;
            }
        }
        return higher;
    }

    public boolean playAgain(Players players) {
        boolean decide = false;
        if (getLastPlayer().equals(players.getPlayerName())) {
            decide = true;
        }
        return decide;
    }
}
