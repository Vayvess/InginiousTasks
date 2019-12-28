import java.util.List;

public class VisitorList extends Visitor {
    public VisitorList(Class cls) {
        super(cls);
    }

    @Override
    List<Object> getFiltered() {
        return filtered;
    }

    @Override
    void visit(Visitable visitable) {
        visitable.accept(this);
    }

    @Override
    void visit(Object o) {
        if ( toFilter.isInstance(o) ){
            filtered.add(o);
        }
    }

    public static void main(String[] args) {
        Visitor visitor = new VisitorList(Integer.class);
        Visitable visitable = new VisitableList(new Object[]{1, 2, 3, 3.1, 4, "HELLO"});

        visitor.visit(visitable);
        List<Object> lst = visitor.getFiltered(); // [1, 2, 3, 4]
        System.out.println(lst.toString());
    }
}