import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Banker banker = new Banker();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setName("Player 1");
        player2.setName("Player 2");
        ArrayList<Player> player_list = new ArrayList<>();
        player_list.add(player1);
        player_list.add(player2);

        PropertyJsonReader propReader = new PropertyJsonReader();
        ListJsonReader listReader = new ListJsonReader();

        FileWriter fileWriter = new FileWriter("monitoring.txt");

        File command = new File(args[0]);
        Scanner scan = new Scanner(command);
        String line;
        String[] list;

        while (scan.hasNext()){
            line = scan.nextLine();
            list = line.split(";");
            if (list[0].equals("Player 1")){
                jailParkCheck(banker, player1, player2, player_list, propReader, listReader, list, fileWriter);
            }
            else if (list[0].equals("Player 2")){
                jailParkCheck(banker, player2, player1, player_list, propReader, listReader, list, fileWriter);
            }
            else {
                show(banker, player1, player2, fileWriter);


            }
        }
        show(banker, player1, player2, fileWriter);
        fileWriter.close();

    }

    private static void show(Banker banker, Player player1, Player player2, FileWriter fileWriter) throws IOException {
        fileWriter.write("-------------------------------------------------------------------------------------------------------------------------" + "\n");
        fileWriter.write("Player 1" + "\t" + player1.getMoney() + "\t" + "have: " + player1.propList.toString().replace("[", "").replace("]", "") + "\n");
        fileWriter.write("Player 2" + "\t" + player2.getMoney() + "\t" + "have: " + player2.propList.toString().replace("[", "").replace("]", "") + "\n");
        fileWriter.write("Banker" + "\t" + banker.getMoney() + "\n");
        if (player1.getMoney() > player2.getMoney()){
            fileWriter.write("Winner\tPlayer 1" + "\n");
        }
        else{
            fileWriter.write("Winner\tPlayer 2" + "\n");
        }
        fileWriter.write("-------------------------------------------------------------------------------------------------------------------------" + "\n");

    }

    //checks if the player is in jail or free park
    private static void jailParkCheck(Banker banker, Player player1, Player player2, ArrayList<Player> player_list, PropertyJsonReader propReader, ListJsonReader listReader, String[] list, FileWriter fileWriter) throws IOException {
        if (player1.getJailCount() > 0){
            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " is in jail (count =" + player1.getJailCount() + ")" + "\n");
            player1.setJailCount(player1.getJailCount() + 1);
        }
        else if (player1.getFreePark() == 1){
            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " in free park (count =" + player1.getFreePark() + ")" + "\n");
            player1.setFreePark(0);
        }
        else {
            play(player1, player2, banker, propReader, listReader, list, player_list, fileWriter);
        }
    }

    private static void play(Player player1, Player player2, Banker banker, PropertyJsonReader propReader, ListJsonReader listReader, String[] list, ArrayList<Player> player_list, FileWriter fileWriter) throws IOException {
        player1.setLocation(player1.getLocation() + Integer.parseInt(list[1]));
        checkSquare(player1, player2, banker, propReader, list, player_list, fileWriter);
        if (player1.getLocation() == 3 || player1.getLocation() == 18 || player1.getLocation() == 34) {  //community chest squares
            if (listReader.communityCards.get(listReader.x).equals("Advance to Go (Collect $200)")) {
                player1.setLocation(1);
                player1.setMoney(player1.getMoney() + 200);
                banker.setMoney(banker.getMoney() - 200);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest Advance to Go (Collect $200)" + "\n");

            } else if (listReader.communityCards.get(listReader.x).equals("Bank error in your favor - collect $75")) {
                player1.setMoney(player1.getMoney() + 75);
                banker.setMoney(banker.getMoney() - 75);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest Bank error in your favor - collect $75" + "\n");

            } else if (listReader.communityCards.get(listReader.x).equals("Doctor's fees - Pay $50")) {
                player1.setMoney(player1.getMoney() - 50);
                banker.setMoney(banker.getMoney() + 50);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest Doctor's fees - Pay $50" + "\n");

            } else if (listReader.communityCards.get(listReader.x).equals("It is your birthday Collect $10 from each player")) {
                player1.setMoney(player1.getMoney() + 10);
                player2.setMoney(player2.getMoney() - 10);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest It is your birthday Collect $10 from each player");

            } else if (listReader.communityCards.get(listReader.x).equals("Grand Opera Night - collect $50 from every player for opening night seats")) {
                player1.setMoney(player1.getMoney() + 50);
                player2.setMoney(player2.getMoney() - 50);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest Grand Opera Night - collect $50 from every player for opening night seats");

            } else if (listReader.communityCards.get(listReader.x).equals("Income Tax refund - collect $20")) {
                player1.setMoney(player1.getMoney() + 20);
                banker.setMoney(banker.getMoney() - 20);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest Income Tax refund - collect $20" + "\n");

            } else if (listReader.communityCards.get(listReader.x).equals("Life Insurance Matures - collect $100")) {
                player1.setMoney(player1.getMoney() + 100);
                banker.setMoney(banker.getMoney() - 100);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest Life Insurance Matures - collect $100" + "\n");

            } else if (listReader.communityCards.get(listReader.x).equals("Pay Hospital Fees of $100")) {
                player1.setMoney(player1.getMoney() - 100);
                banker.setMoney(banker.getMoney() + 100);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest Pay Hospital Fees of $100" + "\n");


            } else if (listReader.communityCards.get(listReader.x).equals("Pay School Fees of $50")) {
                player1.setMoney(player1.getMoney() - 50);
                banker.setMoney(banker.getMoney() + 50);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest Pay School Fees of $50" + "\n");


            } else if (listReader.communityCards.get(listReader.x).equals("You inherit $100")) {
                player1.setMoney(player1.getMoney() + 100);
                banker.setMoney(banker.getMoney() - 100);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest You inherit $100" + "\n");


            } else if (listReader.communityCards.get(listReader.x).equals("From sale of stock you get $50")) {
                player1.setMoney(player1.getMoney() + 50);
                banker.setMoney(banker.getMoney() - 50);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw community chest From sale of stock you get $50" + "\n");

            }
            listReader.setX(listReader.getX() + 1);
        }
        else if (player1.getLocation() == 8 || player1.getLocation() == 23 || player1.getLocation() == 37) { //chance squares
            if (listReader.chanceCards.get(listReader.a).equals("Advance to Go (Collect $200)")) {
                player1.setLocation(1);
                player1.setMoney(player1.getMoney() + 200);
                banker.setMoney(banker.getMoney() - 200);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw Advance to Go (Collect $200)" + "\n");
            }
            else if (listReader.chanceCards.get(listReader.a).equals("Advance to Leicester Square")) {
                if (player1.getLocation() > 27){
                    player1.setMoney(player1.getMoney() + 200);
                    banker.setMoney(banker.getMoney() - 200);
                }
                player1.setLocation(27);
                for (Squares i : propReader.squares) {
                    if (i.getId() == 27)
                        if (player2.propList.contains(i.getName())) {
                            player1.setMoney(player1.getMoney() - i.getRent());
                            player2.setMoney(player2.getMoney() + i.getRent());
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw Advance to Leicester Square " + player1.getName() + " paid rent for Leicester Square" + "\n");
                        } else if (player1.propList.contains(i.getName())) {
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw Advance to Leicester Square " + player1.getName() + " has Leicester Square" + "\n");
                        } else {
                            player1.setMoney(player1.getMoney() - i.getCost());
                            banker.setMoney(banker.getMoney() + i.getCost());
                            player1.propList.add(i.getName());
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw Advance to Leicester Square " + player1.getName() + " bought Leicester Square" + "\n");
                        }
                }
            }
            else if (listReader.chanceCards.get(listReader.a).equals("Go back 3 spaces")) {
                player1.setLocation(player1.getLocation() - 3);
                for (Squares i : propReader.squares) {
                    if (i.getId() == player1.getLocation())
                        if (player2.propList.contains(i.getName())) {
                            player1.setMoney(player1.getMoney() - i.getRent());
                            player2.setMoney(player2.getMoney() + i.getRent());
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw Go back 3 spaces " + player1.getName() + " paid rent for " + i.getName() + "\n");
                        } else if (player1.propList.contains(i.getName())) {
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw Go back 3 spaces " + player1.getName() + " has " + i.getName() + "\n");
                        } else {
                            player1.setMoney(player1.getMoney() - i.getCost());
                            banker.setMoney(banker.getMoney() + i.getCost());
                            player1.propList.add(i.getName());
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw Go back 3 spaces" + player1.getName() + " bought " + i.getName() + "\n");
                        }
                }
                if (player1.getLocation() == 5){
                    player1.setMoney(player1.getMoney() - 100);
                    banker.setMoney(banker.getMoney() + 100);
                    fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                            + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw Go back 3 spaces " + player1.getName() + " paid Tax " + "\n");
                }
            }
            else if (listReader.chanceCards.get(listReader.a).equals("Pay poor tax of $15")) {
                player1.setMoney(player1.getMoney() - 15);
                banker.setMoney(banker.getMoney() + 15);

                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw draw Pay poor tax of $15" + "\n");
            }
            else if (listReader.chanceCards.get(listReader.a).equals("Your building loan matures - collect $150")) {
                player1.setMoney(player1.getMoney() + 150);
                banker.setMoney(banker.getMoney() - 150);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw draw Your building loan matures - collect $150" + "\n");
            }
            else if (listReader.chanceCards.get(listReader.a).equals("You have won a crossword competition - collect $100")) {
                player1.setMoney(player1.getMoney() + 100);
                banker.setMoney(banker.getMoney() - 100);
                fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                        + player_list.get(1).getMoney() + "\t" + player1.getName() + " draw You have won a crossword competition - collect $100" + "\n");
            }
            listReader.setA(listReader.getA() + 1);
        }
        else if (player1.getLocation() == 5 || player1.getLocation() == 39){  //Tax squares
            player1.setMoney(player1.getMoney() - 100);
            banker.setMoney(banker.getMoney() + 100);
            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " paid Tax" + "\n");
        }
        else if (player1.getLocation() == 31 || player1.getLocation() == 11){ //jail square
            player1.setLocation(11);
            player1.setJailCount(1);
            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " went to jail" + "\n");
        }
        else if (player1.getLocation() == 21){  //free parking square
            player1.setFreePark(1);
            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " is in free parking" + "\n");
        }
        else if (player1.getLocation() == 1){  //GO square
            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " is in GO square" + "\n");
        }
    }

    private static void checkSquare(Player player1, Player player2, Banker banker, PropertyJsonReader propReader, String[] list, ArrayList<Player> player_list, FileWriter fileWriter) throws IOException {
        for (Squares i : propReader.squares) {
            if (i.getId() == player1.getLocation()) {
                if (player2.propList.contains(i.getName())) {   //if the square is owned by the other player
                    if (i.getClassName().equals("Land")){   //rent for land
                        player1.setMoney(player1.getMoney() - i.getRent());
                        if (player1.isBankrupt()){
                            bankrupt(player1, player2, banker, propReader, list, player_list, fileWriter);
                        }
                        else{
                            player2.setMoney(player2.getMoney() + i.getRent());
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " paid rent for " + i.getName() + "\n");
                        }
                    }
                    else if (i.getClassName().equals("RailRoad")){  //rent for railroad
                        int x = 0;
                        for (String r : player2.propList){
                            if (r.equals("Kings Cross Station") || r.equals("Marylebone Station") || r.equals("Fenchurch St Station") || r.equals("Liverpool Street Station")){
                                x += 1;
                            }
                        }
                        player1.setMoney(player1.getMoney() - (x * i.getRent()));
                        if (player1.isBankrupt()){
                            bankrupt(player1, player2, banker, propReader, list, player_list, fileWriter);
                        }
                        else{
                            player2.setMoney(player2.getMoney() + (x * i.getRent()));
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " paid rent for " + i.getName() + "\n");
                        }
                    }
                    else{   //rent for company
                        player1.setMoney(player1.getMoney() - (4 * Integer.parseInt(list[1])));
                        if (player1.isBankrupt()){
                            bankrupt(player1, player2, banker, propReader, list, player_list, fileWriter);
                        }
                        else{
                            player2.setMoney(player2.getMoney() + (4 * Integer.parseInt(list[1])));
                            fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                    + player_list.get(1).getMoney() + "\t" + player1.getName() + " paid rent for " + i.getName() + "\n");
                        }
                    }
                } else if (player1.propList.contains(i.getName())) {  //if the player owns the square
                    fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                            + player_list.get(1).getMoney() + "\t" + player1.getName() + " has " + i.getName() + "\n");

                } else {   //if the square is owned by no one
                    player1.setMoney(player1.getMoney() - i.getCost());
                    player1.propList.add(i.getName());
                    banker.setMoney(banker.getMoney() + i.getCost());

                    if (player1.isBankrupt()){
                        banker.setMoney(banker.getMoney() - i.getCost());
                        player1.propList.remove(player1.propList.size() - 1);
                        bankrupt(player1, player2, banker, propReader, list, player_list, fileWriter);
                    }
                    else{
                        fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                                + player_list.get(1).getMoney() + "\t" + player1.getName() + " bought " + i.getName() + "\n");
                    }

                }
            }
        }
    }
    //ends the game if a player is bankrupt
    private static void bankrupt(Player player1, Player player2, Banker banker, PropertyJsonReader propReader, String[] list, ArrayList<Player> player_list, FileWriter fileWriter) throws IOException {
        fileWriter.write(player1.getName() + "\t" + Integer.parseInt(list[1]) + "\t" + player1.getLocation() + "\t" + player_list.get(0).getMoney() + "\t"
                + player_list.get(1).getMoney() + "\t" + player1.getName() + " goes bankrupt" + "\n");
        fileWriter.write("-------------------------------------------------------------------------------------------------------------------------" + "\n");
        fileWriter.write("Player 1" + "\t" + player_list.get(0).getMoney() + "\t" + "have: " + player_list.get(0).propList.toString().replace("[", "").replace("]", "") + "\n");
        fileWriter.write("Player 2" + "\t" + player_list.get(1).getMoney() + "\t" + "have: " + player_list.get(1).propList.toString().replace("[", "").replace("]", "") + "\n");
        fileWriter.write("Banker" + "\t" + banker.getMoney() + "\n");
        if (player_list.get(0).getMoney() > player_list.get(1).getMoney()){
            fileWriter.write("Winner\tPlayer 1" + "\n");
        }
        else{
            fileWriter.write("Winner\tPlayer 2" + "\n");
        }
        fileWriter.write("-------------------------------------------------------------------------------------------------------------------------");
        fileWriter.close();
        System.exit(0);
    }

}
