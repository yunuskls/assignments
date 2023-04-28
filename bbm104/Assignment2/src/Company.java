public class Company extends Squares{
    private int id, cost;
    private String name;
    private int rent;
    private String className = "Company";

    public String getClassName() {
        return className;
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

    public Company(int id, String name, int cost){
        this.id = id;
        this.name = name;
        this.cost = cost;

    }
}
