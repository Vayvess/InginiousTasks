import java.util.Arrays;

public class QuickSort {
    public static <E> void exchange(E[] array, int i, int j){
        E temporary = array[i]; array[i] = array[j]; array[j] = temporary;
    }

    // legal to call list[k].compareTo, because `T` is guaranteed to be `Comparable` due to <E extends Comparable<E>>
    public static <E extends Comparable<E>> int getPivot(E[] array, int lo, int hi){
        int mid = (lo + hi) / 2; int pivot = hi;
        if ( array[lo].compareTo(array[mid]) < 0){
            if ( array[mid].compareTo(array[hi]) < 0){
                pivot = mid;
            }
        }
        else if ( array[lo].compareTo(array[hi]) < 0){
            pivot = lo;
        }
        return pivot;
    }

    public static <E extends Comparable<E>> int partition(E[] array, int lo, int hi){
        int pivotIndex = getPivot(array, lo, hi);
        E pivotValue = array[pivotIndex];
        exchange(array, lo, pivotIndex);

        int border = lo;
        for (int i = lo; i <= hi; i++) {
            if ( array[i].compareTo(pivotValue) < 0){
                border++;
                exchange(array, i, border);
            }
        }
        exchange(array, lo, border);
        return border;
    }

    public static <E extends Comparable<E>> void quickSort(E[] array, int lo, int hi){
        if ( lo < hi){
            int p = partition(array, lo, hi);
            quickSort(array, lo, p - 1);
            quickSort(array, p + 1, hi);
        }
    }

    public static <E extends Comparable<E>> void quickSort(E[] array){
        quickSort(array, 0, array.length - 1);
    }

    public static void main(String[] args) {

        // Works for every objects that extends Comparable<T>
        Vector a = new Vector(1, 2, 3, 4, 8);
        Vector b = new Vector(1, 2, 3, 4, 5);
        Vector c = new Vector(1, 2);
        Vector d = new Vector(4, 8, 9);

        Vector[] vectors = {a, b, c, d};
        QuickSort.quickSort(vectors);
        System.out.println(Arrays.toString(vectors));


        Integer[] test = {5, 8, 4, 3, 2, 4, 8, 9, 7, 8, 1, 6, 4};
        QuickSort.quickSort(test);
        System.out.println(Arrays.toString(test));

        String[] testString = {"May", "The", "Force", "Be", "With", "You"};
        QuickSort.quickSort(testString);
        System.out.println(Arrays.toString(testString));
    }
}
