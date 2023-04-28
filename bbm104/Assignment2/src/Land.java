public class Land extends Squares{
    private int id, cost;
    private String name;
    private int rent;
    private String className = "Land";

    public String getClassName() {
        return className;
    }

    @Override
    public int getRent() {
        return rent;
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public Land(int id, String name, int cost){
        this.id = id;
        this.name = name;
        this.cost = cost;
        if (getCost() <= 2000){
            rent = getCost() * 40 / 100;
        }
        else if (getCost() <= 3000){
            rent = getCost() * 30 / 100;
        }
        else if (getCost() <= 4000){
            rent = getCost() * 35 / 100;
        }
    }
}
