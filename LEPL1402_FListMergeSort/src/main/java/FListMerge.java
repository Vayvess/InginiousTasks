public class FListMerge {

    public static FList<Integer> merge(FList<Integer> merged, FList<Integer> a, FList<Integer> b) {
        while ( a.isNotEmpty() && b.isNotEmpty() ) {
            if (a.head() <= b.head()) {
                merged = merged.cons(a.head());
                a = a.tail();
            } else {
                merged = merged.cons(b.head());
                b = b.tail();
            }
        }
        FList<Integer> reminder = a.isNotEmpty() ? a: b;
        while ( reminder.isNotEmpty() ){
            merged = merged.cons(reminder.head());
            reminder = reminder.tail();
        }
        return merged;
    }
    /*
     * This method receives an FList and returns the FList containing the same values but sorted with the smallest value to the highest one.
     *
     */
    public static FList<Integer> mergeSort(FList<Integer> fList){
        if ( fList.length() == 1 ) return fList;
        FList<Integer> left = FList.nil(), right = FList.nil();
        int mid = fList.length() / 2; int n = 0;
        for (Integer i: fList) {
            if (n < mid){ left = left.cons(i); }
            else { right = right.cons(i); }
            n++;
        }
        left = mergeSort(left); right = mergeSort(right);
        left = merge(FList.nil(), right, left); right = FList.nil();
        for (Integer i : left) {
            right = right.cons(i);
        }
        return right;
    }
}