public class ElementFactory extends Factory {

    // YOUR CODE HERE
    private ElementFactory(){}
    private static ElementFactory INSTANCE = new ElementFactory();

    public static ElementFactory getInstance() {
        // YOUR SINGLETON HERE
        return INSTANCE;
    }


    // Don't forget to note the accessor to be public otherwise we
    // downgrade the privileges the method had on the upper class (Factory) and it ain't no good also inginious crash :)
    @Override
    public LevelComponent getElement(char c) {
        switch (c){
            case 'D':
                return new Door();
            case 'K':
                return new Key();
            case '-':
                return new Floor();
            case '#':
                return new Wall();
            default:
                throw new IllegalArgumentException();
        }
    }
}