import java.util.Arrays;

public class Level extends AbstractLevel {

    private String levelString;
    public Level(String input){
        int n = input.indexOf('\n'); size = n * n; levelString = input;
        ElementFactory elementFactory = ElementFactory.getInstance();
        components = new LevelComponent[n][n]; int componentsPutPointer = 0;

        LevelComponent[] temporary = new LevelComponent[n]; int temporaryPutPointer = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ( c == '\n'){
                components[componentsPutPointer++] = temporary;
                temporary = new LevelComponent[n]; temporaryPutPointer = 0;
            }
            else { temporary[temporaryPutPointer++] = elementFactory.getElement(c); }
        }
    }

    /* Example of level building with this String : String s = "#-K\n-D-\n#-K\n"
     * Gives : LevelComponent[][] componentsObjects = {
     *                                        {new Wall(), new Floor(), new Key()},
     *                                        {new Floor(), new Door(), new Floor()},
     *                                        {new Wall(), new Floor(), new Key()}}
     */


    // YOUR CODE HERE

    @Override
    public String toString() {
        return levelString;
    }

    @Override
    LevelComponent[][] getComponents() {
        return components;
    }

    @Override
    int getSize() {
        return size;
    }

    public static void main(String[] args) {
        String s = "#-K\n-D-\n#-K\n";
        Level level = new Level(s);
        System.out.println(Arrays.deepToString(level.components));
    }
}