import java.util.ArrayList;

public class Player {
    String name;
    int score;



    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String Sort(ArrayList<Player> playerList){
        int k = 1;
        for (Player i: playerList){
            int x = i.getScore();
            if (x > playerList.get(playerList.size()-1).getScore()){
                k += 1;
            }
        }
        return k + "/" + playerList.size();
    }
}
