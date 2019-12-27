import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
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
        if (hi - lo < threshold){ sort(lo, hi); }
        else{
            int mid = (lo + hi) / 2;
            ParallelMergeSort<E> left = new ParallelMergeSort<>(array, lo, mid, aux, comp);
            ParallelMergeSort<E> right = new ParallelMergeSort<>(array, mid + 1, hi, aux, comp);
            invokeAll(left, right);
            merge(lo, mid, hi);
        }
    }

	//Sort array between lo and hi using merge sort
    private void sort(int lo, int hi){
        if (hi <= lo) return;
        int mid = (lo + hi) / 2;
        sort(lo, mid); sort(mid + 1, hi);
        merge(lo, mid, hi);
    }

    //merge two subarray and keep them sorted
    private void merge(int lo, int mid, int hi){
        for (int k = lo; k <= hi; k++) {
            aux[k] = array[k];
        }

        int i = lo, j = mid+1;
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

    private static boolean isArraySorted(Integer[] array){
        for(int i = 0 ; i < array.length -1; i++){
            if(array[i] > array[i+1]){
                System.out.println(i);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int size = 500; Random rng = new Random();
        Integer[] array = new Integer[size];
        for(int i = 0 ; i < size ; i++){
            array[i] = rng.nextInt(100000);
        }
        ParallelMergeSort<Integer> task = new ParallelMergeSort<>(array, 0, size-1, new Integer[size],
                Comparator.comparing(Integer::intValue));
        new ForkJoinPool().invoke(task);
        System.out.println(Arrays.toString(task.array));
        System.out.println(isArraySorted(task.array));
    }
}