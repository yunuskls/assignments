public class SelectionSort {
    public SelectionSort(int[] array, int n){
        for(int i = 1; i < n; i++){
            int minIdx = i;
            for(int j = i+1; j <= n; j++){
                if(array[j] < array[minIdx]){
                    minIdx = j;
                }
            }
            if(minIdx != i){
                int temp = array[minIdx];
                array[minIdx] = array[i];
                array[i] = temp;
            }
        }
    }
}