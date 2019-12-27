import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort<E> extends RecursiveAction {

    private volatile E[] array, aux;
    private int lo, hi;
    private Comparator<? super E> comp;
    
    private static final int threshold = 128;

    public ParallelMergeSort(E[] a, int lo, int hi, E[] aux, Comparator<? super E> comp) {
        array = a; this.lo = lo; this.hi = hi; this.aux = aux; this.comp = comp;
    }

    /*
     * Run a normal sort when the difference between hi and lo is under the threshold
     * Otherwise : Split the sub array in two and start the sort on each part of the array simultaneously
     */
    @Override
    protected void compute() {
        if ( hi - lo < threshold ){ sort(lo, hi); }
        else{
            int mid = array.length / 2;
            E[] left = (E[]) new Object[mid];
            for (int i = 0; i < mid; i++) { left[i] = array[i]; }

            E[] right = (E[]) new Object[array.length - mid];
            for (int i = mid; i < array.length; i++) { right[array.length - 1 - i] = array[i]; }

            ParallelMergeSort<E> leftFork = new ParallelMergeSort<>(left, 0, left.length - 1, aux, comp);
            ParallelMergeSort<E> rightFork = new ParallelMergeSort<>(right, 0, right.length - 1, aux, comp);
            leftFork.fork(); rightFork.fork();
            leftFork.join(); rightFork.join();

            // Merge
            int i = 0; int j = 0; int step = 0;
            while (i < leftFork.array.length && j < rightFork.array.length){
                if (comp.compare(leftFork.array[i], rightFork.array[j]) < 0){
                    array[step++] = leftFork.array[i++];
                }
                else{
                    array[step++] = rightFork.array[j++];
                }
            }
            while (i < leftFork.array.length){
                array[step++] = leftFork.array[i++];
            }
            while (j < rightFork.array.length){
                array[step++] = rightFork.array[j++];
            }
        }
    }

	//Sort array between lo and hi using merge sort
    private void sort(int lo, int hi){
        // Sort
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(lo, mid); sort(mid + 1, hi);
        merge(lo, mid, hi);
    }

    //merge two subarray and keep them sorted
    private void merge(int lo, int mid, int hi){
        for (int k = lo; k <= hi; k++){ aux[k] = array[k]; }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if(i > mid) {
                array[k] = aux[j++];
            }else if (j > hi){
                array[k] = aux[i++];
            }else if (comp.compare(aux[j], aux[i]) < 0){
                array[k] = aux[j++];
            }else{
                array[k] = aux[i++];
            }
        }
    }

    public static void main(String[] args) {
        int size = 10000; Random rng = new Random();
        Integer[] array = new Integer[size];
        for(int i = 0 ; i < size ; i++){
            array[i] = rng.nextInt(100000);
        }
        ParallelMergeSort<Integer> task = new ParallelMergeSort<>(array, 0, size - 1, new Integer[size],
                Comparator.comparing(Integer::intValue));
        new ForkJoinPool().invoke(task);
        System.out.println(Arrays.toString(task.array) );
    }
}