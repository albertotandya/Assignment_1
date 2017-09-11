import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MineralSupertrumps {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of players: \n3   4   5");
        int choice = input.nextInt();
        while (!(choice==3 || choice==4 || choice==5)) {
            System.out.println("Invalid choice");
            System.out.println("Enter number of players: \n3   4   5");
            choice = input.nextInt();
        }
        ArrayList<Cards> array = new ArrayList<Cards>();
        String[] string;
        String a;
        Path file = Paths.get("C:\\Users\\Hartono\\IdeaProjects\\Assignment_1\\src\\card.txt");
        try {
            InputStream fileInput = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInput));
            fileReader.readLine();
            while (( a = fileReader.readLine()) != null) {
                string = a.split(",");
                array.add(new NormCards(string[0], Float.valueOf(string[1]), Float.valueOf(string[2]),string[3], string[4], string[5]));
            }
            array.add(new TrumpCards("The Mineralogist"));
            array.add(new TrumpCards("The Geologist"));
            array.add(new TrumpCards("The Geophysicist"));
            array.add(new TrumpCards("The Petrologist"));
            array.add(new TrumpCards("The Miner"));
            array.add(new TrumpCards("The Gemmologist"));
            fileReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Deck deck = new Deck(array);
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
