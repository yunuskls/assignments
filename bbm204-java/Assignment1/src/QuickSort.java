public class QuickSort {
    int partition(int array[], int l, int h){
        int pivot = array[h];
        int i = l -1;
        for(int j = l; j <= h; j++){
            if(array[j] < pivot){
                i += 1;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i+1] = array[h];
        array[h] = temp;
        return i + 1;
    }
    public QuickSort(int array[], int l, int h){
        int[] stack = new int[h - l + 1];
        int top = -1;

        // push initial values of l and h to stack
        stack[++top] = l;
        stack[++top] = h;

        // Keep popping from stack while is not empty
        while (top >= 0) {
            // Pop h and l
            h = stack[top--];
            l = stack[top--];


            int pivot = partition(array, l, h);

            if (pivot - 1 > l) {
                stack[++top] = l;
                stack[++top] = pivot - 1;
            }

            if (pivot + 1 < h) {
                stack[++top] = pivot + 1;
                stack[++top] = h;
            }
        }
    }
}
