public class FListMerge {
    public static FList<Integer> merge(FList<Integer> a, FList<Integer> b, boolean ascending) {
        /* pre: a, b : FList of the original FList that has to be rebuild ordered */

        // each loop cuts the head of the smaller/greater FList(a, b) and put it in a bag(FList merged)
        FList<Integer> merged = FList.nil();
        while ( a.isNotEmpty() && b.isNotEmpty() ) {
            if (ascending ? a.head() >= b.head() : a.head() <= b.head()) {
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

    public static FList<Integer> split(FList<Integer> fList, boolean ascending){
        // terminal condition
        if ( fList.length() == 1 ) return fList;

        // Splits the original FList in two halves: newOne: 0 to mid, fList: mid to end
        int mid = fList.length() / 2; FList<Integer> newOne = FList.nil();
        while (mid-- != 0){ newOne = newOne.cons(fList.head()); fList = fList.tail(); }

        // the last call of split gives an ascending FList of size 1 so we can merge the two split.
        FList<Integer> left = split(newOne, !ascending), right = split(fList, !ascending);

        return merge(left, right, ascending);
    }


    public static FList<Integer> mergeSort(FList<Integer> fList){
        return split(fList, true);
    }

    public static void main(String[] args) {
        FList<Integer> test = FList.nil();
        for (int i = 0; i < 25; i++) {
            test = test.cons((int) (Math.random() * 100));
        }
        System.out.println(mergeSort(test));
    }
}
