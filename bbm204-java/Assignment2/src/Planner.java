import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;
    public double  maximum = 0;
    public int maxIndex = 0;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];
        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
        Arrays.fill(compatibility, -1);  // ben ekledim
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        int low = 0;
        int high = index;
        int compatibleInt = -1;
        while(high - low > 0){
            int mid = (low + high)/2;
            if(compatibleInt == mid){
                break;
            }
            if(taskArray[mid].getFinishTime().compareTo(taskArray[index].start) <= 0){  //if compatible
                compatibleInt = mid;
                low = mid;
            }
            else if (taskArray[mid].getFinishTime().compareTo(taskArray[index].start) > 0){  //not compatible
                high = mid;
            }
        }
        return compatibleInt;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        for(int i = 0; i < taskArray.length; i++){
            compatibility[i] = binarySearch(i);
        }
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        calculateCompatibility();
        System.out.println("Calculating max array\n" + "---------------------");
        calculateMaxWeight(taskArray.length-1);
        System.out.println("\n" + "Calculating the dynamic solution\n" + "--------------------------------");
        solveDynamic(taskArray.length-1);
        System.out.println("\n" + "Dynamic Schedule\n" + "----------------");
        for(int i = 0; i < planDynamic.size(); i++){
            System.out.println("At " + planDynamic.get(i).getStartTime() + ", " + planDynamic.get(i).getName() + ".");
        }
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        if(i < 0){
            return;
        }
        System.out.println("Called solveDynamic(" + i + ")");
        if(i==0 || maxWeight[i] > maxWeight[i-1]){
            solveDynamic(compatibility[i]);
            planDynamic.add(taskArray[i]);
        }
        else{
            solveDynamic(i-1);
        }
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        System.out.println("Called calculateMaxWeight(" + i + ")");
        if(i < 0){
            return 0.0;
        }
        if(maxWeight[i] != null){
            return maxWeight[i];
        }
        else{
            maxWeight[i] = Math.max(taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]), calculateMaxWeight(i-1));
        }
        return maxWeight[i];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        planGreedy.add(taskArray[0]);
        int lastIndex = 0;
        for(int i = 0; i < taskArray.length; i++){
            if (taskArray[i].getStartTime().compareTo(taskArray[lastIndex].getFinishTime()) >= 0){
                planGreedy.add(taskArray[i]);
                lastIndex = i;
            }
        }
        System.out.println("Greedy Schedule");
        System.out.println("---------------");
        for(int i = 0; i < planGreedy.size(); i++){
            System.out.println("At " + planGreedy.get(i).getStartTime() + ", " + planGreedy.get(i).getName() + ".");
        }
        return planGreedy;
    }
}
