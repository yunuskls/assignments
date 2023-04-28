import java.util.ArrayList;

public class Player {
    private int money;
    private int location;
    private String name;
    private int jailCount;
    private int freePark;
    private boolean bankrupt = false;

    public ArrayList<String> propList = new ArrayList<>();

    public Player() {
        this.money = 15000;
        this.location = 1;
    }

    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public int getFreePark() {
        return freePark;
    }

    public void setFreePark(int freePark) {
        this.freePark = freePark;
    }

    public int getJailCount() {
        return jailCount;
    }

    public void setJailCount(int jailCount) {
        this.jailCount = jailCount;
        if (this.jailCount == 4){
            this.jailCount = 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if (money >= 0){
            this.money = money;
        }
        else {
            this.setBankrupt(true);
        }
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
        if (this.location > 40) {
            this.location -= 40;
            this.money += 200;
            Banker.money -= 200;
        }
    }
}









