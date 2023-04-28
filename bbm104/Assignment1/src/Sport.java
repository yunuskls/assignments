public class Sport {
    private String sportID;
    private String nameOfSport;
    private int calorieBurned;

    public Sport(String sportID, String nameOfSport, int calorieBurned) {
        this.sportID = sportID;
        this.nameOfSport = nameOfSport;
        this.calorieBurned = calorieBurned;
    }

    public String getSportID() {
        return sportID;
    }

    public void setSportID(String sportID) {
        this.sportID = sportID;
    }

    public String getNameOfSport() {
        return nameOfSport;
    }

    public void setNameOfSport(String nameOfSport) {
        this.nameOfSport = nameOfSport;
    }

    public int getCalorieBurned() {
        return calorieBurned;
    }

    public void setCalorieBurned(int calorieBurned) {
        this.calorieBurned = calorieBurned;
    }
}
