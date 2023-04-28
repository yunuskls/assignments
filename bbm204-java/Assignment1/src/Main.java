import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

public class Main {

    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(new File("TrafficFlowDataset.csv"));
        ArrayList<Integer> randomDataList = new ArrayList<>();
        sc.nextLine();
        while (sc.hasNext()){
            String[] array = sc.nextLine().split(",");
            randomDataList.add(Integer.parseInt(array[6]));
        }
        ArrayList<Integer> sortedDataList = new ArrayList<>(randomDataList);
        ArrayList<Integer> reverseSortedDataList = new ArrayList<>(randomDataList);
        sortedDataList.sort(Comparator.naturalOrder());
        reverseSortedDataList.sort(Comparator.reverseOrder());

        int[] sorted500 = sortedDataList.subList(0, 500).stream().mapToInt(o -> o).toArray();
        int[] sorted1000 = sortedDataList.subList(0, 1000).stream().mapToInt(o -> o).toArray();
        int[] sorted2000 = sortedDataList.subList(0, 2000).stream().mapToInt(o -> o).toArray();
        int[] sorted4000 = sortedDataList.subList(0, 4000).stream().mapToInt(o -> o).toArray();
        int[] sorted8000 = sortedDataList.subList(0, 8000).stream().mapToInt(o -> o).toArray();
        int[] sorted16000 = sortedDataList.subList(0, 16000).stream().mapToInt(o -> o).toArray();
        int[] sorted32000 = sortedDataList.subList(0, 32000).stream().mapToInt(o -> o).toArray();
        int[] sorted64000 = sortedDataList.subList(0, 64000).stream().mapToInt(o -> o).toArray();
        int[] sorted128000 = sortedDataList.subList(0, 128000).stream().mapToInt(o -> o).toArray();
        int[] sorted250000 = sortedDataList.subList(0, 250000).stream().mapToInt(o -> o).toArray();
        ArrayList<int[]> lists = new ArrayList<>(Arrays.asList(sorted500, sorted1000, sorted2000, sorted4000, sorted8000, sorted16000, sorted32000, sorted64000, sorted128000, sorted250000));
        ArrayList<Integer> insertionTimeList = new ArrayList<>();
        ArrayList<Integer> quickTimeList = new ArrayList<>();
        ArrayList<Integer> linearTimeList = new ArrayList<>();

        for(int[] i: lists){
            int time = 0;
            for(int j = 0; j <10; j++){
                double t1 = System.currentTimeMillis();
                new SelectionSort(i.clone(), i.length-1);
                double t2 = System.currentTimeMillis();
                time += t2-t1;
            }
            insertionTimeList.add(time/10);
        }
        System.out.println(insertionTimeList);

        for(int[] i: lists){
            int time = 0;
            for(int j = 0; j <10; j++){
                double t1 = System.currentTimeMillis();
                new QuickSort(i.clone(), 0,i.length-1);
                double t2 = System.currentTimeMillis();
                time += t2-t1;
            }
            quickTimeList.add(time/10);
        }
        System.out.println(quickTimeList);

        for(int[] i: lists){
            int time = 0;
            for(int j = 0; j <1000; j++){
                int randomNum = ThreadLocalRandom.current().nextInt(0, i.length);
                double t1 = System.nanoTime();
                LinearSearch.linearSearch(i.clone(), i[randomNum]);
                double t2 = System.nanoTime();
                time += t2-t1;
            }
            linearTimeList.add(time/1000);
        }
        System.out.println(linearTimeList);

        

        // X axis data
        int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        // Create sample data for linear runtime
        double[][] yAxis = new double[2][10];
        yAxis[0] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        yAxis[1] = new double[]{300, 800, 1800, 3000, 7000, 15000, 31000, 64000, 121000, 231000};

        // Save the char as .png and show it
        showAndSaveChart("Sample Test", inputAxis, yAxis);
    }
    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Sample Data 1", doubleX, yAxis[0]);
        chart.addSeries("Sample Data 2", doubleX, yAxis[1]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
