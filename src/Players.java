import java.util.ArrayList;
import java.util.Scanner;       //importing the needed files

public class Players {
    private ArrayList<Cards> handCard;
    private String playerName;
    private boolean passed;
    private Scanner input = new Scanner(System.in);     //defining the variables

    public Players(String name) {
        handCard = new ArrayList<Cards>();
        playerName = name;
    }       //creating the constructor

    public String getPlayerName() {
        return playerName;
    }       //getter for the current player name
    public Cards getCards(int a) {
        return handCard.get(a);
    }       //to get the selected card from the hand card
    public void drawCards (Cards card) {
        handCard.add(card);
    }       //to draw card and add it to the player's hand

    public void playTurn(GamePlay game) {       //player play his turn
        boolean turnEnd = false;
        String cardChoice;
        String lastCard = displayLastCardDesc(game);
        while (!turnEnd) {
            if (!passed){       //to check if the player has not passed yet
                System.out.println(displayHandCards() + "\n" + game.categoryDesc() + "\n" + lastCard + "\n" + getPlayerName() + ", choose your card or enter 'P' to pass");
                cardChoice = input.nextLine();
                if (cardChoice.toUpperCase().equals("P")) {     //to see if the player choose to pass
                    passStatus(game);
                    turnEnd = true;
                } else {
                    turnEnd = cardChoiceOption(cardChoice, game);
                }
            } else {        //or else, the player already passed
                System.out.println(getPlayerName()+", you already passed");
                turnEnd = true;
            }
        }
        if (handCard.size() == 0) {     //to check if the player has no more card
            game.setLastPlayer(game.getPlayers().get((game.getPlayers().indexOf(this)+1)%game.getPlayers().size()).getPlayerName());
            leave(game);
        }
    }

    private boolean cardChoiceOption(String cardChoice, GamePlay game) {     //to decide if the player's turn is done or not
        boolean finishTurn = false;
        int cardNum;
            try {
                cardNum = Integer.parseInt(cardChoice) - 1;     //change the input from String to integer
                Cards playedcard = getCards(cardNum);
                boolean contPlay = game.playCard(playedcard, this);
                if (game.getCategory().equals("ANY")) {     //to check if the player played 'The Geologist' card
                    game.putCard(playedcard);
                    handCard.remove(cardNum);
                    newRound(game);
                    game.setLastPlayer(this.getPlayerName());
                    finishTurn = true;
                } else if (game.getCategory().equals("SG")) {       //to check if the player played the 'The Geophysicist' card
                    if (specialCond()) {        //to check if the player has the 'Magnetit" card
                        for (Cards card : handCard) {
                            game.putCard(card);
                            handCard.remove(card);
                        }
                        game.setLastPlayer(this.getPlayerName());
                        game.setCategory("S");      //and set the category to Specific Gravity
                        finishTurn = true;
                    } else {        //or else, only set the category to Specific Gravity
                        game.setCategory("S");
                    }
                    if (contPlay) {     //play the card after
                        game.putCard(playedcard);
                        handCard.remove(cardNum);
                        game.setLastPlayer(this.getPlayerName());
                        finishTurn = true;
                    }
                } else {
                    if (contPlay) {
                        game.putCard(playedcard);
                        handCard.remove(cardNum);
                        game.setLastPlayer(this.getPlayerName());
                        finishTurn = true;
                    }
                }
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        return finishTurn;
    }

    private String displayHandCards() {      //to show the player's hand card
        String allCards = "";
        int cardNum = 1;
        for (Cards card : handCard) {
            String cardInfo;
            if (card instanceof NormCards) {
                cardInfo = "No: "+ cardNum + "   "+
                        "Name: " + card.getCardName() + "   " +
                        "Hardness: " + ((NormCards) card).getHardness() + "   " +
                        "Specific Gravity: " + ((NormCards) card).getSpecGravity() + "   " +
                        "Cleavage: " + ((NormCards) card).getCleavage() + "   " +
                        "Crystal Abundance: " + ((NormCards) card).getCrustalAbun() + "   " +
                        "Economic Value: " + ((NormCards) card).getEcoValue()+"\n";
            } else {
                cardInfo = "No: "+ cardNum+ "   "+ "Name: " + card.getCardName()+ "   Description: "+
                        ((TrumpCards) card).cardEffectDesc()+"\n";
            }
            cardNum += 1;
            allCards += cardInfo;
        }
        return allCards;
    }

    private String displayLastCardDesc(GamePlay game) {
        String lastPlayedCard;
        if (game.cardPlayedBefore() && !(game.playAgain(this))) {        //to check if it is not the first card played in the game
            if (game.getLastCard() instanceof NormCards) {      //to create the description of the last played card
                lastPlayedCard = "Card played -> " + game.getLastCard().getCardName() + ", " +
                        "Hardness: " + ((NormCards) game.getLastCard()).getHardness() + ", " +
                        "Specific Gravity: " + ((NormCards) game.getLastCard()).getSpecGravity() + ", " +
                        "Cleavage: " + ((NormCards) game.getLastCard()).getCleavage() + ", " +
                        "Crustal Abundance: " + ((NormCards) game.getLastCard()).getCrustalAbun() + ", " +
                        "Economic Value: " + ((NormCards) game.getLastCard()).getEcoValue() + "\n";
            } else {
                lastPlayedCard = "Card Played -> " + ((TrumpCards) game.getLastCard()).getCardName() + "\n";
            }
        } else if (game.cardPlayedBefore() && game.playAgain(this)) {        //to check if all the player already pass and the game is already started(not the first card)
            System.out.println("Choose the trump category");
            lastPlayedCard = "New round";
            newRound(game);
        } else {        //or else, this is the first card played
            lastPlayedCard = "Game start!";
            newRound(game);
        }
        return lastPlayedCard;
    }

    public void newRound(GamePlay newRnd) {     //to set a new round
        System.out.println("\n" + displayHandCards() + "\nH for Hardness, S for Specific Gravity, C for Cleavage, CA for Crustal Abundance, EV for Economic Value\n" + getPlayerName() + ", choose the mode");
        String choice = input.nextLine();
        choice = choice.toUpperCase();
        while (!(choice.equals("H") || choice.equals("S") || choice.equals("C") || choice.equals("CA") || choice.equals("EV"))) {
            System.out.println("\nInvalid mode!\nH for Hardness, S for Specific Gravity, C for Cleavage, CA for Crustal Abundance, EV for Economic Value" + getPlayerName() + ", choose the mode");
            choice = input.nextLine();
            choice = choice.toUpperCase();
        }
        newRnd.setCategory(choice);
    }



    public boolean specialCond() {      //to check whether the player that plays 'The Geophysicist' has the card 'Magnetit' or not
        boolean winCond = false;
        for (Cards card : handCard) {
            if (card.getCardName().equals("Magnetit")) {
                winCond = true;
            }
        }
        return winCond;
    }

    public void passStatus(GamePlay game) {
        passed = true;
        drawCards(game.getDeckCards().cardRemoved());
    }

    public void passReset() {
        passed = false;
    }

    public void leave(GamePlay game) {      //player leave the game
        game.getPlayers().remove(this);
        System.out.println("Player " + this.getPlayerName() + " wins and leave the game!");
    }
}
