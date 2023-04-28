public class Food {
    private String foodID;
    private String nameOfFood;
    private int calorieCount;

    public Food(String foodID, String nameOfFood, int calorieCount) {
        this.foodID = foodID;
        this.nameOfFood = nameOfFood;
        this.calorieCount = calorieCount;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getNameOfFood() {
        return nameOfFood;
    }

    public void setNameOfFood(String nameOfFood) {
        this.nameOfFood = nameOfFood;
    }

    public int getCalorieCount() {
        return calorieCount;
    }

    public void setCalorieCount(int calorieCount) {
        this.calorieCount = calorieCount;
    }
}
