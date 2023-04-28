import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Player> playerList = new ArrayList<>();
        File leaderboard = new File("leaderboard.txt");     //reading leaderboard and creating player objects
        Scanner scan2 = new Scanner(leaderboard);
        while (scan2.hasNext()){
            String line = scan2.nextLine();
            String list[] = line.split(" ");
            playerList.add(new Player(list[0], Integer.parseInt(list[1])));
        }

        File monitoring = new File("monitoring.txt");
        File leaderboard1 = new File("leaderboard.txt");
        FileWriter fileWriter = new FileWriter(monitoring, false);
        FileWriter fileWriter1 = new FileWriter(leaderboard1, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);

        ArrayList<ArrayList<Jewel>> bigList = new ArrayList<>();
        File command = new File(args[0]);       //reading the game grid
        Scanner scan = new Scanner(command);
        while (scan.hasNext()) {
            ArrayList<Jewel> smallList = new ArrayList<>();
            String line = scan.nextLine();
            String[] list = line.split(" ");
            for ( String jewel: list){          //creating an object for each element at the gameGrid
                switch (jewel) {
                    case "D":
                        smallList.add(new Diamond());
                        break;
                    case "S":
                        smallList.add(new Square());
                        break;
                    case "T":
                        smallList.add(new Triangle());
                        break;
                    case "W":
                        smallList.add(new Wildcard());
                        break;
                    case "+":
                        smallList.add(new MathSymbol("+"));
                        break;
                    case "-":
                        smallList.add(new MathSymbol("-"));
                        break;
                    case "\\":
                        smallList.add(new MathSymbol("\\"));
                        break;
                    case "/":
                        smallList.add(new MathSymbol("/"));
                        break;
                    case "|":
                        smallList.add(new MathSymbol("|"));
                        break;
                }
            }
            bigList.add(smallList);
        }

        File command1 = new File(args[1]);          //reading commands
        Scanner scan1 = new Scanner(command1);
        Checker checker = new Checker();
        int totalScore = 0;
        boolean k = true;
        while (scan1.hasNext()) {
            String line1 = scan1.nextLine();
            String[] list1 = line1.split(" ");
            if (list1.length == 2) {
                int x = Integer.parseInt(list1[1]);
                int y = Integer.parseInt(list1[0]);
                printGrid(bigList, x, y, k, checker.getScore(), bufferedWriter, checker.isValid());
                k = false;
                totalScore = totalScore + checker.getScore();
                checker.setScore(0);
                checker.setValid(true);
                checker.Check(bigList.get(y).get(x), x, y, bigList);
                new Slider(bigList);
            }
            else if (list1[0].equals("E")){
                totalScore = totalScore + checker.getScore();
            }
            else {
                Player player = new Player(list1[0], totalScore);
                playerList.add(player);
                printEnd(bigList, checker.getScore(), totalScore, playerList, player, bufferedWriter);
                printLeaderboard(playerList, bufferedWriter1);

            }
        }
        bufferedWriter.close();
        bufferedWriter1.close();
    }





    private static void printGrid(ArrayList<ArrayList<Jewel>> bigList, int x, int y, boolean first, int score, BufferedWriter bufferedWriter, boolean valid) throws IOException {
        if (!valid){
            bufferedWriter.write("Please enter a valid coordinate\n\n");
            bufferedWriter.write("Select coordinate or enter E to end the game: " + y + " " + x + "\n\n");
        }
        else{
            if (first){
                bufferedWriter.write("Game grid:\n\n");  //prints the start of the game
                onlyGrid(bigList, bufferedWriter);
                bufferedWriter.write("\nSelect coordinate or enter E to end the game: " + y + " " + x + "\n\n");
            }
            else {
                onlyGrid(bigList, bufferedWriter);      //prints the parts after the start
                System.out.println();
                bufferedWriter.write("\nScore: " + score + " points" + "\n\n");
                bufferedWriter.write("Select coordinate or enter E to end the game: " + y + " " + x + "\n\n");
            }
        }

    }


    private static void printEnd(ArrayList<ArrayList<Jewel>> bigList, int score, int totalScore, ArrayList<Player> playerList, Player player, BufferedWriter bufferedWriter) throws IOException {     //prints the end of the game
        onlyGrid(bigList, bufferedWriter);
        bufferedWriter.write("\nScore: " + score + " points" + "\n\n");
        bufferedWriter.write("Select coordinate or enter E to end the game: E\n\n");
        bufferedWriter.write("Total score: " + totalScore + " points\n\nEnter name: " + playerList.get(playerList.size()-1).getName() + "\n\nYour rank is " + player.Sort(playerList) + "\n\nGood bye!");
    }


    private static void onlyGrid(ArrayList<ArrayList<Jewel>> bigList, BufferedWriter bufferedWriter) throws IOException {  //a method that only prints the grid
        for (ArrayList<Jewel> jewels : bigList) {
            ArrayList<String> printList = new ArrayList<>();
            for (Jewel i : jewels) {
                printList.add(i.getName());
            }
            bufferedWriter.write(printList.toString().replaceAll("[\\[\\]]", "").replaceAll(",", "") + "\n");
        }
    }

    private static void printLeaderboard(ArrayList<Player> playerList, BufferedWriter bufferedWriter1) throws IOException {
        for (Player i : playerList){
            bufferedWriter1.write(i.getName() + " " + i.getScore() + "\n");
        }
    }
}
