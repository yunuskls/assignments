public class MathSymbol extends Jewel{
    private int point = 20;
    private int[] coordinate;
    private String name;
    private String className = "mathSymbol";

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public MathSymbol(String name){
        this.name = name;
    }

}
