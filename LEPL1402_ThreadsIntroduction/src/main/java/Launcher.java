import java.util.Arrays;

public class Launcher {

    /*
     * Instantiate and start each thread from "t" with its OWN Counter object,
     * then wait for all threads to finish and return the set of Counter objects
     * the threads ran on. Each thread must be named according to its index in the
     * "t" array.
     */
    public static Counter[] init(Thread[] t){
        Counter[] counters = new Counter[t.length];
        for (int i = 0; i < t.length; i++) {
            Counter curr = new Counter();
            t[i] = new Thread(curr);
            t[i].setName(Integer.toString(i)); t[i].start();
            counters[i] = curr;
        }
        for (Thread thread : t) {
            try{ thread.join(); }
            catch (InterruptedException e) { throw new RuntimeException("Unexpected interrupt", e); }
        }
        return counters;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[15];
        Counter[] counters = init(threads);
        System.out.println(Arrays.toString(counters));
    }
}
