public class Space extends Jewel{
    private int[] coordinate;
    private String name = " ";

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
