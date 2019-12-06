// Doesn't work on Inginious because it doesn't detect mergeSort as recursive :/

public class BestVersionOfMergeSort {
    public static FList<Integer> merge(FList<Integer> a, FList<Integer> b, boolean ascending) {
        FList<Integer> merged = FList.nil();
        while ( a.isNotEmpty() && b.isNotEmpty() ) {
            if (ascending ? a.head() >= b.head() : a.head() <= b.head()) {
                merged = merged.cons(a.head());
                a = a.tail();
            } else { merged = merged.cons(b.head()); b = b.tail(); } }

        FList<Integer> reminder = a.isNotEmpty() ? a: b;
        while ( reminder.isNotEmpty() ){
            merged = merged.cons(reminder.head());
            reminder = reminder.tail();
        }
        return merged;
    }

    public static FList<Integer> mergeSort(FList<Integer> fList, boolean ascending){
        if ( fList.length() == 1 ) return fList;

        int mid = fList.length() / 2; FList<Integer> newOne = FList.nil();
        while (mid-- != 0){
            newOne = newOne.cons(fList.head()); fList = fList.tail();
        }
        return merge( mergeSort(newOne, !ascending), mergeSort(fList, !ascending), ascending);
    }

    public static void main(String[] args) {
        FList<Integer> test = FList.nil();
        for (int i = 0; i < 25; i++) {
            test = test.cons((int) (Math.random() * 100));
        }
        System.out.println(mergeSort(test, true));
        System.out.println(mergeSort(test, false));
    }
}
