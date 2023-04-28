import java.util.ArrayList;

public class Checker {
    private int score = 0;
    private boolean valid = true;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void Check(Jewel JewelName, int x, int y, ArrayList<ArrayList<Jewel>> bigList) {
        switch (JewelName.getName()){
            case " ":
                this.valid = false;
                break;
            case "D":       //matches with diagonal directions (only D)
                if (x >= 2 && y >= 2) {
                    if (bigList.get(y - 1).get(x - 1).getName().equals("D") && bigList.get(y - 2).get(x - 2).getName().equals("D")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x - 1, new Space());
                        bigList.get(y - 2).set(x - 2, new Space());
                        score += 90;
                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3 && y <= bigList.size() - 3) {
                    if (bigList.get(y + 1).get(x + 1).getName().equals("D") && bigList.get(y + 2).get(x + 2).getName().equals("D")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x + 1, new Space());
                        bigList.get(y + 2).set(x + 2, new Space());
                        score += 90;
                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3 && y >= 2) {
                    if (bigList.get(y - 1).get(x + 1).getName().equals("D") && bigList.get(y - 2).get(x + 2).getName().equals("D")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x + 1, new Space());
                        bigList.get(y - 2).set(x + 2, new Space());
                        score += 90;
                        break;
                    }
                }
                if (x >= 2 && y <= bigList.size() - 3){
                    if (bigList.get(y + 1).get(x - 1).getName().equals("D") && bigList.get(y + 2).get(x - 2).getName().equals("D")){
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x - 1, new Space());
                        bigList.get(y + 2).set(x - 2, new Space());
                        score += 90;
                        break;
                    }
                }
            case "S":       //matches with left or right (only S)
                if (x >= 2) {
                    if (bigList.get(y).get(x - 1).getName().equals("D") && bigList.get(y).get(x - 2).getName().equals("S")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x - 1, new Space());
                        bigList.get(y).set(x - 2, new Space());
                        score += 45;
                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3) {
                    if (bigList.get(y).get(x + 1).getName().equals("D") && bigList.get(y).get(x + 2).getName().equals("S")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x + 1, new Space());
                        bigList.get(y).set(x + 2, new Space());
                        score += 45;
                        break;
                    }
                }
                break;
            case "T":       //matches with up or down (only T)
                if (y >= 2) {
                    if (bigList.get(y - 1).get(x).getName().equals("D") && bigList.get(y - 2).get(x).getName().equals("T")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x, new Space());
                        bigList.get(y - 2).set(x, new Space());
                        score += 45;
                        break;
                    }
                }
                if (y <= bigList.size() - 3) {
                    if (bigList.get(y + 1).get(x).getName().equals("D") && bigList.get(y + 2).get(x).getName().equals("T")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x, new Space());
                        bigList.get(y + 2).set(x, new Space());
                        score += 45;
                    }
                }
                break;
            case "W":       //matches with every directions (D, S, T, or W)
                if (y >= 2) {       //check upward
                    if (bigList.get(y - 1).get(x).getName().equals("W") || bigList.get(y - 2).get(x).getName().equals("W")) {
                        score += 10 + bigList.get(y - 1).get(x).getPoint() + bigList.get(y - 2).get(x).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x, new Space());
                        bigList.get(y - 2).set(x, new Space());
                        break;
                    }
                    else if (bigList.get(y - 1).get(x).getName().equals(bigList.get(y - 2).get(x).getName())){
                        score += 10 + bigList.get(y - 1).get(x).getPoint() + bigList.get(y - 2).get(x).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x, new Space());
                        bigList.get(y - 2).set(x, new Space());
                        break;
                    }
                }
                if (y <= bigList.size() - 3) {       //check down
                    if (bigList.get(y + 1).get(x).getName().equals("W") || bigList.get(y + 2).get(x).getName().equals("W")) {
                        score += 10 + bigList.get(y + 1).get(x).getPoint() + bigList.get(y + 2).get(x).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x, new Space());
                        bigList.get(y + 2).set(x, new Space());

                        break;
                    }
                    else if (bigList.get(y + 1).get(x).getName().equals(bigList.get(y + 2).get(x).getName())){
                        score += 10 + bigList.get(y + 1).get(x).getPoint() + bigList.get(y + 2).get(x).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x, new Space());
                        bigList.get(y + 2).set(x, new Space());

                        break;
                    }
                }
                if (x >= 2) {        //check left
                    if (bigList.get(y).get(x - 1).getName().equals("W") || bigList.get(y).get(x - 2).getName().equals("W")) {
                        score += 10 + bigList.get(y).get(x - 1).getPoint() + bigList.get(y).get(x - 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x - 1, new Space());
                        bigList.get(y).set(x - 2, new Space());

                        break;
                    }
                    else if (bigList.get(y).get(x - 1).getName().equals(bigList.get(y).get(x - 2).getName())) {
                        score += 10 + bigList.get(y).get(x - 1).getPoint() + bigList.get(y).get(x - 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x - 1, new Space());
                        bigList.get(y).set(x - 2, new Space());

                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3) {        //check right
                    if (bigList.get(y).get(x + 1).getName().equals("W") || bigList.get(y).get(x + 2).getName().equals("W")) {
                        score += 10 + bigList.get(y).get(x + 1).getPoint() + bigList.get(y).get(x + 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x + 1, new Space());
                        bigList.get(y).set(x + 2, new Space());
                        break;
                    }
                    else if (bigList.get(y).get(x + 1).getName().equals(bigList.get(y).get(x + 2).getName())) {
                        score += 10 + bigList.get(y).get(x + 1).getPoint() + bigList.get(y).get(x + 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x + 1, new Space());
                        bigList.get(y).set(x + 2, new Space());
                        break;
                    }
                }
                if (x >= 2 && y >= 2) {         //check left upward diagonal
                    if (bigList.get(y - 1).get(x - 1).getName().equals("W") || bigList.get(y - 2).get(x - 2).getName().equals("W")) {
                        score += 10 + bigList.get(y - 1).get(x - 1).getPoint() + bigList.get(y - 2).get(x - 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x - 1, new Space());
                        bigList.get(y - 2).set(x - 2, new Space());
                        break;
                    }
                    else if (bigList.get(y - 1).get(x - 1).getName().equals(bigList.get(y - 2).get(x - 2).getName())) {
                        score += 10 + bigList.get(y - 1).get(x - 1).getPoint() + bigList.get(y - 2).get(x - 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x - 1, new Space());
                        bigList.get(y - 2).set(x - 2, new Space());
                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3 && y <= bigList.size() - 3) {        //check right down diagonal
                    if (bigList.get(y + 1).get(x + 1).getName().equals("W") || bigList.get(y + 2).get(x + 2).getName().equals("W")) {
                        score += 10 + bigList.get(y + 1).get(x + 1).getPoint() + bigList.get(y + 2).get(x + 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x + 1, new Space());
                        bigList.get(y + 2).set(x + 2, new Space());
                        break;
                    }
                    else if (bigList.get(y + 1).get(x + 1).getName().equals(bigList.get(y + 2).get(x + 2).getName())) {
                        score += 10 + bigList.get(y - 1).get(x - 1).getPoint() + bigList.get(y - 2).get(x - 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x + 1, new Space());
                        bigList.get(y + 2).set(x + 2, new Space());
                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3 && y >= 2) {         //check right upward diagonal
                    if (bigList.get(y - 1).get(x + 1).getName().equals("W") || bigList.get(y - 2).get(x + 2).getName().equals("W")) {
                        score += 10 + bigList.get(y - 1).get(x + 1).getPoint() + bigList.get(y - 2).get(x + 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x + 1, new Space());
                        bigList.get(y - 2).set(x + 2, new Space());
                        break;
                    }
                    else if (bigList.get(y - 1).get(x + 1).getName().equals(bigList.get(y - 2).get(x + 2).getName())) {
                        score += 10 + bigList.get(y - 1).get(x + 1).getPoint() + bigList.get(y - 2).get(x + 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x + 1, new Space());
                        bigList.get(y - 2).set(x + 2, new Space());
                        break;
                    }
                }
                if (x >= 2 && y <= bigList.size() - 3){         //check left down diagonal
                    if (bigList.get(y + 1).get(x - 1).getName().equals("W") || bigList.get(y + 2).get(x - 2).getName().equals("W")){
                        score += 10 + bigList.get(y + 1).get(x - 1).getPoint() + bigList.get(y + 2).get(x - 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x - 1, new Space());
                        bigList.get(y + 2).set(x - 2, new Space());
                        break;
                    }
                    else if (bigList.get(y + 1).get(x - 1).getName().equals(bigList.get(y + 2).get(x - 2).getName())) {
                        score += 10 + bigList.get(y + 1).get(x - 1).getPoint() + bigList.get(y + 2).get(x - 2).getPoint();
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x - 1, new Space());
                        bigList.get(y + 2).set(x - 2, new Space());
                        break;
                    }
                }
            case "+":
                if (x >= 2) {
                    if (bigList.get(y).get(x - 1).getClassName().equals("mathSymbol") && bigList.get(y).get(x - 2).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x - 1, new Space());
                        bigList.get(y).set(x - 2, new Space());
                        score += 60;
                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3) {
                    if (bigList.get(y).get(x + 1).getClassName().equals("mathSymbol") && bigList.get(y).get(x + 2).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x + 1, new Space());
                        bigList.get(y).set(x + 2, new Space());
                        score += 60;
                        break;
                    }
                }
                if (y >= 2) {
                    if (bigList.get(y - 1).get(x).getClassName().equals("mathSymbol") && bigList.get(y - 2).get(x).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x, new Space());
                        bigList.get(y - 2).set(x, new Space());
                        score += 60;
                        break;
                    }
                }
                if (y <= bigList.size() - 3) {
                    if (bigList.get(y + 1).get(x).getClassName().equals("mathSymbol") && bigList.get(y + 2).get(x).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x, new Space());
                        bigList.get(y + 2).set(x, new Space());
                        score += 60;
                    }
                }


            case "-":
                if (x >= 2) {
                    if (bigList.get(y).get(x - 1).getClassName().equals("mathSymbol") && bigList.get(y).get(x - 2).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x - 1, new Space());
                        bigList.get(y).set(x - 2, new Space());
                        score += 60;
                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3) {
                    if (bigList.get(y).get(x + 1).getClassName().equals("mathSymbol") && bigList.get(y).get(x + 2).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y).set(x + 1, new Space());
                        bigList.get(y).set(x + 2, new Space());
                        score += 60;
                        break;
                    }
                }


            case "\\":
                if (x >= 2 && y >= 2) {
                    if (bigList.get(y - 1).get(x - 1).getClassName().equals("mathSymbol") && bigList.get(y - 2).get(x - 2).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x - 1, new Space());
                        bigList.get(y - 2).set(x - 2, new Space());
                        score += 60;
                        break;
                    }
                }
                if (x <= bigList.get(0).size() - 3 && y <= bigList.size() - 3) {
                    if (bigList.get(y + 1).get(x + 1).getClassName().equals("mathSymbol") && bigList.get(y + 2).get(x + 2).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x + 1, new Space());
                        bigList.get(y + 2).set(x + 2, new Space());
                        score += 60;
                        break;
                    }
                }

            case "/":
                if (x <= bigList.get(0).size() - 3 && y >= 2) {
                    if (bigList.get(y - 1).get(x + 1).getClassName().equals("mathSymbol") && bigList.get(y - 2).get(x + 2).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x + 1, new Space());
                        bigList.get(y - 2).set(x + 2, new Space());
                        score += 60;
                        break;
                    }
                }
                if (x >= 2 && y <= bigList.size() - 3){
                    if (bigList.get(y + 1).get(x - 1).getClassName().equals("mathSymbol") && bigList.get(y + 2).get(x - 2).getClassName().equals("mathSymbol")){
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x - 1, new Space());
                        bigList.get(y + 2).set(x - 2, new Space());
                        score += 60;
                    }
                }

            case "|":
                if (y >= 2) {
                    if (bigList.get(y - 1).get(x).getClassName().equals("mathSymbol") && bigList.get(y - 2).get(x).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y - 1).set(x, new Space());
                        bigList.get(y - 2).set(x, new Space());
                        score += 60;
                        break;
                    }
                }
                if (y <= bigList.size() - 3) {
                    if (bigList.get(y + 1).get(x).getClassName().equals("mathSymbol") && bigList.get(y + 2).get(x).getClassName().equals("mathSymbol")) {
                        bigList.get(y).set(x, new Space());
                        bigList.get(y + 1).set(x, new Space());
                        bigList.get(y + 2).set(x, new Space());
                        score += 60;
                    }
                }
        }
    }
}
