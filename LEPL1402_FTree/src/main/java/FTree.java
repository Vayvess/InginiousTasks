import java.util.function.Function;

public abstract class FTree<A> {

    public final int depth() {
        return left() == null ? 0 : 1 + left().depth();
    }

    public abstract A value();
    public abstract FTree<A> left();
    public abstract FTree<A> right();

    public final <B> FTree<B> map(Function<A,B> f) {
        B app = f.apply(value());
        if (left() == null) {
            if (right() == null) return new Leaf<B>(app);
            return new Node<B>(app, null, right().map(f));
        }
        else if (right() == null) return new Node<B>(app, left().map(f), null);

        return new Node<B>(app, left().map(f), right().map(f));
    }
}
