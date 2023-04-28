public class Square extends Jewel{
    private int point = 15;
    private int[] coordinate;
    private String name = "S";


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
