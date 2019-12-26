import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockQueue {

    public final static int SIZE = 10;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public int head = 0;
    public int tail = 0;
    public final Integer [] cells = new Integer[SIZE];
    public int count = 0;

    public void enqueue(Integer i) {
        lock.lock();
        try {
            while (full()) {
                notFull.await();
            }
            cells[tail % SIZE] = i;
            ++tail;
            ++count;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public Integer dequeue() {
        lock.lock();
        try {
            while (empty()) {
                notEmpty.await();
            }
            Integer output = cells[head % SIZE];
            ++head;
            --count;
            notFull.signal();
            return output;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }


    public boolean full(){
        return this.count == SIZE;
    }

    public boolean empty(){
        return this.head == this.tail;
    }

    public int size() { return this.tail - this.head; }

    public static void main(String[] args) {

    }
}
