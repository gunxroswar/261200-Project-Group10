import java.util.Map;

public class ExprAttack implements Expr{
    private Expr direction,expression;

    public ExprAttack(Expr direction,Expr expression) {
        this.direction = direction;
        this.expression=expression;
    }

    @Override
    public int eval(Map<String, Integer> bindings) {
        return 0;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("shoot ");
        direction.prettyPrint(s);
        s.append(" ");
        expression.prettyPrint(s);

    }
}
