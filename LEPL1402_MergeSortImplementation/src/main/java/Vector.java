import java.util.Arrays;

public class Vector implements Comparable<Vector> {

    public int[] args;
    public Vector(int... args){
        this.args = args;
    }

    @Override
    public int compareTo(Vector vector) {
        int length = args.length - vector.args.length;
        if (length == 0){
            int min = Math.min(args.length, vector.args.length);
            for (int i = 0; i < min; i++) {
                int difference = args[i] - vector.args[i];
                if ( difference != 0 ){
                    return difference;
                }
            }
        }
        return length;
    }

    @Override
    public String toString() {
        return Arrays.toString(args);
    }
}
