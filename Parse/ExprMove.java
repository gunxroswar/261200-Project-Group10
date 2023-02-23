import java.util.Map;

public class ExprMove implements Expr{

    private Expr direction;

    public ExprMove(Expr direction) {
        this.direction=direction;
    }

    @Override
    public int eval(Map<String, Integer> bindings) {
        return 0;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("move");
        direction.prettyPrint(s);

    }
}
