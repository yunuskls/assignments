public class Wildcard extends Jewel{
    private int point = 10;
    private int[] coordinate;
    private String name = "W";


    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void setPoint(int point) {
        this.point = 10;
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
