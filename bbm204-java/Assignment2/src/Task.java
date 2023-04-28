import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task implements Comparable<Task> {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        LocalTime time = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime newTime = time.plusHours(duration);
        String newTimeStr = newTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        return newTimeStr;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        if (urgent){
            return importance * 2000 / duration;
        }
        else {
            return importance / duration;
        }

    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Task o) {
        LocalTime time1 = LocalTime.parse(this.getFinishTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime time2 = LocalTime.parse(o.getFinishTime(), DateTimeFormatter.ofPattern("HH:mm"));
        return time1.compareTo(time2);
    }
}
