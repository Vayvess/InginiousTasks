import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MaxFinder {

    private final int nThreads,length,width,depth;
    private final int[][][] data;
    private final CyclicBarrier barrier;
    private int[] sums;
    private int max;

    /*
     * Worker constructor takes only one parameter int r, which is his associated row number
     * A worker is responsible of the calculation of the sum of each 2D-Array with row == r + nThread * round; with round >= 0
     *
     * Run should compute the sum of a 2D-array and store the result in sums[] then wait for the cyclic barrier to get the result
     * And restart computing nThreads further
     */
    class Worker implements Runnable {
        int myRow;
        Worker(int row){ this.myRow = row; }

        @Override
        public void run() {
            while (myRow < length){
                int sigma = 0;
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < depth; j++) {
                        sigma += data[myRow][i][j];
                    }
                }
                sums[myRow % nThreads] = sigma;
                myRow += nThreads;
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException ignored) { return; }
            }

            // if there's too much threads, some need to await to form a group of nThreads and don't bock the main
            if (length / nThreads == myRow / nThreads){
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException ignored) { }
            }
        }
    }
	
    
    /*
     *
     * Initialize all the instance variable and start the right amount of Threads
     *
     */
    private MaxFinder(int[][][] matrix, int nThreads) throws InterruptedException{
        data = matrix; this.nThreads = nThreads;
        length = matrix.length; width = matrix[0].length; depth = matrix[0][0].length;

        sums = new int[nThreads];
        barrier = new CyclicBarrier(nThreads, () -> {
            max = Math.max(max, sums[0]);
            for (int i = 1; i < nThreads; i++) {
                max = Math.max(max, sums[i]);
            }
            sums = new int[nThreads];
        });
        Thread[] threads = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(new Worker(i));
            threads[i].start();
        }

        // wait until done // Inginious pass without or with the joins ;) // Join is obligatory in any other cas
        for (Thread thread : threads)
            thread.join();
    }

    public static int getMaxSum(int[][][] matrix, int nThreads){
        try{
            MaxFinder mf = new MaxFinder(matrix, nThreads);
            return mf.max;
        }catch(InterruptedException e){
            return -1;
        }

    }

    public static int[][][] rand3DMatrix(int x, int y, int z){
        Random gen = new Random(); int[][][] output = new int[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    output[i][j][k] = gen.nextInt(9);
                }
            }
        }
        return output;
    }

    public static void main(String[] args) {
        int[][][] test = rand3DMatrix(4, 2, 2);
        System.out.println(Arrays.deepToString(test));
        System.out.println(getMaxSum(test, 2));
    }
}
