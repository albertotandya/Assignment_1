import java.util.ArrayList;
import java.util.Scanner;

public class Players {
    ArrayList<Cards> handCard;
    String playerName;
    Scanner input = new Scanner(System.in);

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
    public void playTurn(GamePlay game) {
        int cardNum;
        boolean finishTurn = false;
        String lastPlayedCard;
        if (game.firstCardPlayed() && !(game.playAgain(this))) {
            if (game.getLastCard() instanceof NormCards) {
                lastPlayedCard = "Card played -> " + game.getLastCard().getCardName() + ", " +
                        "Hardness: " + ((NormCards) game.getLastCard()).getHardness() + ", " +
                        "Specific Gravity: " + ((NormCards) game.getLastCard()).getSpecGravity() + ", " +
                        "Cleavage: " + ((NormCards) game.getLastCard()).getCleavage() + ", " +
                        "Crustal Abundance: " + ((NormCards) game.getLastCard()).getCrustalAbun() + ", " +
                        "Economic Value: " + ((NormCards) game.getLastCard()).getEcoValue() + "\n";
            } else {
                lastPlayedCard = "Card Played -> " + ((TrumpCards) game.getLastCard()).getCardName() + "\n";
            }
        } else if (game.firstCardPlayed() && game.playAgain(this)) {
            System.out.println("Choose the trump category");
            lastPlayedCard = "New round";
            newCategory(game);
        } else {
            lastPlayedCard = "Game start!";
            newCategory(game);
        } while (!finishTurn) {
            System.out.println(displayHandCards() + "\n" + game.categoryDesc() + "\n" + lastPlayedCard + "\n" + getPlayerName() + ", choose your card or enter 'P' to pass");
            System.out.println();
            String cardChoice = input.nextLine();
            if (cardChoice.toUpperCase().equals("P")) {
                drawCards(game.getDeckCards().cardDrawn());
                finishTurn = true;
            } else {
                try {
                    cardNum = Integer.parseInt(cardChoice) - 1;
                    Cards playedcard = getCards(cardNum);
                    boolean contPlay = game.playCard(playedcard, this);
                    if (game.getCategory().equals("ANY")) {
                        game.putCard(playedcard);
                        handCard.remove(cardNum);
                        newCategory(game);
                        game.setLastPlayer(this.getPlayerName());
                        finishTurn = true;
                    } else if (game.getCategory().equals("SS")) {
                        if (specialCond()) {
                            for (Cards card : handCard) {
                                game.putCard(card);
                                handCard.remove(card);
                            }
                            game.setLastPlayer(this.getPlayerName());
                            game.setCategory("S");
                            finishTurn = true;
                        } else {
                            game.setCategory("S");
                        }
                        if (contPlay) {
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
            }
        }
        if (handCard.size() == 0) {
            game.setLastPlayer(game.getPlayers().get((game.getPlayers().indexOf(this)+1)%game.getPlayers().size()).getPlayerName());
            leave(game);
        }
    }

    public String displayHandCards() {
        String allCards = "";
        int cardNum = 1;
        for (Cards card : handCard) {
            String cardInfo = "";
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

    public void newCategory(GamePlay newRnd) {
        System.out.println("\n" + displayHandCards() + "H for Hardness, S for Specific Gravity, C for Cleavage, CA for Crustal Abundance, EV for Economic Value\n" + getPlayerName() + ", choose the mode");
        String choice = input.nextLine();
        choice = choice.toUpperCase();
        while (!(choice.equals("H") || choice.equals("S") || choice.equals("C") || choice.equals("CA") || choice.equals("EV"))) {
            System.out.println("\nInvalid mode!\nH for Hardness, S for Specific Gravity, C for Cleavage, CA for Crustal Abundance, EV for Economic Value" + getPlayerName() + ", choose the mode");
            choice = input.nextLine();
            choice = choice.toUpperCase();
        }
        newRnd.setCategory(choice);
    }

    public boolean specialCond() {
        boolean winCond = false;
        for (Cards card : handCard) {
            if (card.getCardName().equals("Magnetit")) {
                winCond = true;
            }
        }
        return winCond;
    }

    public void leave(GamePlay game) {
        game.getPlayers().remove(this);
        System.out.println("Player " + this.getPlayerName() + " wins and left the game!");
    }
}
