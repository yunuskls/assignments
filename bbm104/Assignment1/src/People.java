public class People {
    private String personID;
    private String name;
    private String gender;
    private double weight;
    private double height;
    private int dateOfBirth;
    private double dailyCalorieNeeds;
    private int age;
    private int calorie;
    private int calTaken;
    private int calBurned;




    public People(String personID, String name, String gender, double weight, double height, int dateOfBirth) {
        this.personID = personID;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.age = 2022 - this.dateOfBirth;
        if(this.gender.equals("male")){
            this.dailyCalorieNeeds = Math.round(66 + (13.75 * this.weight) + (5 * this.height) - (6.8 * this.age));
        }
        else if(this.gender.equals("female")){
            this.dailyCalorieNeeds = Math.round(665 + (9.6 * this.weight) + (1.7 * this.height) - (4.7 * this.age));
        }
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getDailyCalorieNeeds() {
        return dailyCalorieNeeds;
    }

    public void setDailyCalorieNeeds(int dailyCalorieNeeds) {
        this.dailyCalorieNeeds = dailyCalorieNeeds;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie += calorie;
    }
    public int getCalTaken() {
        return calTaken;
    }

    public void setCalTaken(int calTaken) {
        this.calTaken += calTaken;
    }

    public int getCalBurned() {
        return calBurned;
    }

    public void setCalBurned(int calBurned) {
        this.calBurned += calBurned;
    }
}
