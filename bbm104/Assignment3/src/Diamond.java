public class Diamond extends Jewel {
    private int point = 30;
    private int[] coordinate;
    private String name = "D";

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void setPoint(int point) {
        this.point = point;
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
