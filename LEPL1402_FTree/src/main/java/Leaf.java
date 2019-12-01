public class Leaf<A> extends FTree<A> {

    private final A value;

    public Leaf(A value){
        this.value = value;
    }

    @Override
    public A value() { return value; }

    @Override
    public FTree<A> left() { return null; }

    @Override
    public FTree<A> right() { return null; }
}
