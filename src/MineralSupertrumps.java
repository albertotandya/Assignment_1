import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class MineralSupertrumps {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of players: \n 3 \t 4 \t 5");
        int choice = input.nextInt();
        while (!(choice==3 || choice==4 || choice==5)) {
            System.out.println("Invalid choice");
            System.out.println("Enter number of players: \n 3 \t 4 \t 5");
            choice = input.nextInt();
        }
        ArrayList<Cards> cards = new ArrayList<Cards>();
        String[] words;
        String a;
        Path file = Paths.get("C:\\Users\\Hartono\\IdeaProjects\\Assignment_1\\src\\card.txt");
        try {
            InputStream fileInput = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInput));
            fileReader.readLine();
            while (( a = fileReader.readLine()) != null) {
                words = a.split(",");
                cards.add(new NormCards(words[0], Float.valueOf(words[1]), Float.valueOf(words[2]),words[3], words[4], words[5]));
            }
            cards.add(new TrumpCards("The Mineralogist"));
            cards.add(new TrumpCards("The Geologist"));
            cards.add(new TrumpCards("The Geophysicist"));
            cards.add(new TrumpCards("The Petrologist"));
            cards.add(new TrumpCards("The Miner"));
            cards.add(new TrumpCards("The Gemmologist"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Deck deck = new Deck(cards);
        GamePlay game = new GamePlay(choice, deck);

        int counter = 0;
        while (game.getPlayers().size() > 1) {
            int playernum = counter%game.getPlayers().size();
            if (game.getDeckCards().getCardDeck().size() == 0) {
                game.reFillCard();
            } else {
                game.getPlayers().get(playernum).playTurn(game);
                counter += 1;
            }
        }
        System.out.println("Game Over! " + game.getPlayers().get(0).getPlayerName() + " lost");
    }
}
