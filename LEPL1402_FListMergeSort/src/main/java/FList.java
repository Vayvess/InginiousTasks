import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class FList<A> implements Iterable<A>{

    public final boolean isNotEmpty() {
        return this instanceof Cons;
    }

    public final boolean isEmpty() {
        return this instanceof Nil;
    }

    private int length = 0;
    public final int length() {
        length = tail().length + 1;
        return length;
    }

    public abstract A head();

    public abstract FList<A> tail();

    public static <A> FList<A> nil() {
        return (Nil<A>) Nil.INSTANCE;
    } // Not my fault: i know it's frustrating

    public final FList<A> cons(final A a) { return new Cons(a, this); } // Same :)

    public Iterator<A> iterator() {
        FList<A> Current = this;
        return new Iterator<A>() {
            FList<A> Curr = Current;

            @Override
            public boolean hasNext() {
                return Curr.isNotEmpty();
            }

            @Override
            public A next() {
                if ( hasNext() ){
                    A Output = Curr.head();
                    Curr = Curr.tail();
                    return Output;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static final class Nil<A> extends FList<A> {
        private static  final Nil<Object> INSTANCE = new Nil(); // Yes i know :)
        @Override
        public A head() { return null; }

        @Override
        public FList<A> tail() { return FList.nil(); }
    }

    public static final class Cons<A> extends FList<A> {
        private A head;
        private int length;
        private FList<A> tail;

        public Cons(A head, FList<A> tail){
            this.head = head;
            this.tail = tail;
            length = length();
        }

        @Override
        public A head() {
            if ( isEmpty() ) throw new NoSuchElementException();
            return head;
        }

        @Override
        public FList<A> tail() {
            if ( isEmpty() ) throw new NoSuchElementException();
            return tail;
        }
    }

    // Note i've implemented it myself ;) -> System.out.println(anyFList)
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append('[');
        for (A item: this) {
            output.append(item.toString()).append(", ");
        }
        output.append(']');
        return output.toString();
    }

}

