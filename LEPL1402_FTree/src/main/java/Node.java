public class Node<A> extends FTree<A> {

    private final A value;
    private final FTree<A> left, right;

    public Node(final A value, FTree<A> left,  FTree<A> right){
        this.value = value;
        this.left = left; this.right = right;
    }

    @Override
    public A value() { return value; }

    @Override
    public FTree<A> left() { return left; }

    @Override
    public FTree<A> right() { return right; }
}
