public class Triangle extends Jewel{
    private int point = 15;
    private int[] coordinate;
    private String name = "T";


    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void setPoint(int point) {
        this.point = 15;
    }

    @Override
    public int[] getCoordinate() {
        return coordinate;
    }

    @Override
    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
