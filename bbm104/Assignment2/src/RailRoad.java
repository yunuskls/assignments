public class RailRoad extends Squares{
    private int id, cost;
    private String name;
    private int rent;
    private String className = "RailRoad";


    public String getClassName() {
        return className;
    }

    public int getRent(){
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

    public RailRoad(int id, String name, int cost){
        this.id = id;
        this.name = name;
        this.cost = cost;
        rent = 25;
    }
}
