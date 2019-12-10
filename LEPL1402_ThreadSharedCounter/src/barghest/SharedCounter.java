package barghest;

public class SharedCounter {

    private int counter;

    public synchronized void inc(){ counter++; notify(); }

    public synchronized void dec(){
        while ( counter == 0 ){
            try{ wait(); } catch (InterruptedException e) { throw new RuntimeException("Unexpected interrupt", e); }
        } counter--;
    }

    public int get(){ return counter; }
}
