import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<People> peopleList = new ArrayList<>();
        ArrayList<Food> foodList = new ArrayList<>();
        ArrayList<Sport> sportList = new ArrayList<>();
        File people = new File("people.txt");
        File food = new File("food.txt");
        File sport = new File("sport.txt");
        File command = new File(args[0]);
        File monitoring = new File("monitoring.txt");
        Scanner scan1 = new Scanner(people);
        Scanner scan2 = new Scanner(food);
        Scanner scan3 = new Scanner(sport);
        Scanner scanCommand = new Scanner(command);
        FileWriter fileWriter = new FileWriter(monitoring, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String line;
        String[] list;
        ArrayList<String> warnList = new ArrayList<>();
        ArrayList<String> printList = new ArrayList<>();
        while (scan1.hasNext()) {
            line = scan1.nextLine();
            list = line.split("\t");
            peopleList.add(new People(list[0], list[1], list[2], Integer.parseInt(list[3]), Integer.parseInt(list[4]),
                    Integer.parseInt(list[5])));
        }
        while (scan2.hasNext()) {
            line = scan2.nextLine();
            list = line.split("\t");
            foodList.add(new Food(list[0], list[1], Integer.parseInt(list[2])));
        }
        while (scan3.hasNext()) {
            line = scan3.nextLine();
            list = line.split("\t");
            sportList.add(new Sport(list[0], list[1], Integer.parseInt(list[2])));
        }
        while (scanCommand.hasNext()) {
            line = scanCommand.nextLine();
            list = line.split("\t");
            if (list.length == 3) {
                People peopleObj = null;
                Food foodObj = null;
                Sport sportObj = null;
                for (People i : peopleList) {
                    if (i.getPersonID().equals(list[0])) {
                        peopleObj = i;
                    }
                }
                for (Food i : foodList) {
                    if (i.getFoodID().equals(list[1])) {
                        foodObj = i;
                    }
                }
                for (Sport i : sportList) {
                    if (i.getSportID().equals(list[1])) {
                        sportObj = i;
                    }
                }
                if (Integer.parseInt(list[1]) < 2000) {
                    int calGain = foodObj.getCalorieCount() * Integer.parseInt(list[2]);
                    peopleObj.setCalorie(calGain);
                    peopleObj.setCalTaken(calGain);
                    bufferedWriter.write(peopleObj.getPersonID() + "\t" + "has" + "\t" +"taken" + "\t" + calGain + "kcal" + "\t" + "from" + "\t" + foodObj.getNameOfFood() + "\n");
                } else {
                    double calBurn = sportObj.getCalorieBurned() * (Double.parseDouble(list[2]) / 60);
                    peopleObj.setCalorie((int) -calBurn);
                    peopleObj.setCalBurned((int) calBurn);
                    bufferedWriter.write(peopleObj.getPersonID() + "\t" + "has" + "\t" + "burned" + "\t" + Math.round(calBurn) + "kcal" + "\t" + "thanks to" + "\t" + sportObj.getNameOfSport() + "\n");
                }
                if (scanCommand.hasNext()){
                    bufferedWriter.write("***************" + "\n");
                }
                printList.add(peopleObj.getPersonID());
                Set<String> listSet = new LinkedHashSet<>();
                listSet.addAll(printList);
                printList.clear();
                printList.addAll(listSet);
            }
            else if (list[0].equals("printWarn")) {
                for (String s : printList) {
                    for (People i : peopleList) {
                        if (i.getPersonID().equals(s)) {
                            if (i.getDailyCalorieNeeds() - i.getCalorie() < 0) {
                                warnList.add(i.getPersonID());
                                bufferedWriter.write(i.getName() + "\t" + i.getAge() + "\t" + Math.round(i.getDailyCalorieNeeds()) + "kcal" + "\t" + i.getCalTaken() + "kcal" + "\t" + (i.getCalBurned()) + "kcal" + "\t" + "+" + Math.round(-1 * (i.getDailyCalorieNeeds() - i.getCalorie())) + "kcal" + "\n");
                            }
                        }
                    }
                }
                if (warnList.size() == 0) {
                    bufferedWriter.write("there" + "\t" + "is" + "\t" + "no" + "\t" + "such" + "\t" + "person" + "\n");
                }
                if (scanCommand.hasNext()){
                    bufferedWriter.write("***************" + "\n");
                }
                warnList.clear();
            }
            else if (list[0].equals("printList")) {
                for (String s : printList) {
                    for (People i : peopleList) {
                        if (i.getPersonID().equals(s)) {
                            if (i.getDailyCalorieNeeds() - i.getCalorie() <= 0) {
                                bufferedWriter.write(i.getName() + "\t" + i.getAge() + "\t" + Math.round(i.getDailyCalorieNeeds()) + "kcal" + "\t" + i.getCalTaken() + "kcal" + "\t" + (i.getCalBurned()) + "kcal" + "\t" + "+" + Math.round(-1 * (i.getDailyCalorieNeeds() - i.getCalorie())) + "kcal" + "\n");

                            } else {
                                bufferedWriter.write(i.getName() + "\t" + i.getAge() + "\t" + Math.round(i.getDailyCalorieNeeds()) + "kcal" + "\t" + i.getCalTaken() + "kcal" + "\t" + (i.getCalBurned()) + "kcal" + "\t" + Math.round(-1 * (i.getDailyCalorieNeeds() - i.getCalorie())) + "kcal" + "\n");
                            }
                        }
                    }
                }
                if (scanCommand.hasNext()){
                    bufferedWriter.write("***************" + "\n");
                }
            }
            else {
                String bor = list[0];
                String bora = bor.substring(6, 11);
                for (People i : peopleList) {
                    if (i.getPersonID().equals(bora)) {
                        if (i.getDailyCalorieNeeds() - i.getCalorie() <= 0){
                            bufferedWriter.write( i.getName() + "\t" + i.getAge() + "\t" + Math.round(i.getDailyCalorieNeeds()) +"kcal" + "\t" + i.getCalTaken() +"kcal" + "\t" + (i.getCalBurned()) +"kcal" + "\t" + "+" + Math.round(-1 * (i.getDailyCalorieNeeds() - i.getCalorie())) +"kcal" + "\n");

                        }
                        else {
                            bufferedWriter.write( i.getName() + "\t" + i.getAge() + "\t" + Math.round(i.getDailyCalorieNeeds()) +"kcal" + "\t" + i.getCalTaken() +"kcal" + "\t" + (i.getCalBurned()) +"kcal" + "\t" + Math.round(-1 * (i.getDailyCalorieNeeds() - i.getCalorie())) +"kcal" + "\n");
                        }
                        if (scanCommand.hasNext()){
                            bufferedWriter.write("***************" + "\n");
                        }
                    }
                }
            }
        }
        bufferedWriter.close();
        RandomAccessFile monitor = new RandomAccessFile(new File("monitoring.txt"), "rw");
        monitor.setLength(monitor.length() - 1);
        monitor.close();
    }
}
