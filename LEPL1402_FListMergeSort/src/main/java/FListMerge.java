public class FListMerge {
    public static FList<Integer> merge(FList<Integer> merged, FList<Integer> a, FList<Integer> b) {
        /* pre: merged: FList that will be construct to be thread safe
                a, b : FList of the original FList that has to be rebuild ordered */

        // each loop cuts the head of the smaller FList(a, b) and put it in a bag(FList merged)
        while ( a.isNotEmpty() && b.isNotEmpty() ) {
            if (a.head() <= b.head()) {
                merged = merged.cons(a.head());
                a = a.tail();
            } else {
                merged = merged.cons(b.head());
                b = b.tail();
            }
        }

        // Since one of them won't have heads anymore we just chop the other one
        FList<Integer> reminder = a.isNotEmpty() ? a: b;
        while ( reminder.isNotEmpty() ){
            merged = merged.cons(reminder.head());
            reminder = reminder.tail();
        }
        return merged;
    }


    /* This method receives an FList and returns the FList containing the same values but
     sorted with the smallest value to the highest one. */

    public static FList<Integer> mergeSort(FList<Integer> fList){
        // terminal condition
        if ( fList.length() == 1 ) return fList;

        // Split using sugar syntax due to the fact that FList implements Iterable
        FList<Integer> left = FList.nil(), right = FList.nil();
        int mid = fList.length() / 2; int n = 0;
        for (Integer i: fList) {
            if (n < mid){ left = left.cons(i); }
            else { right = right.cons(i); }
            n++;
        }

        // for FList of size n -> length: left = n/2, right = n/2 -> terminal condition -> length: left = 1, right = 1
        left = mergeSort(left); right = mergeSort(right);

        // Since a FList of size 1 is ordered and left and right are of size 1, we can merge the two;
        left = merge(FList.nil(), right, left);

        // Since the data structure is stack(FIFO) like: reverse before merge (Note: for simplicity I show the easy way)
        right = FList.nil();
        for (Integer i : left) {
            right = right.cons(i);
        }

        // right is ordered and in ascending order, right is the answer of an recursive call !
        return right;
    }
}