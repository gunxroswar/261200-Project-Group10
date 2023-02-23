import java.util.Map;

public class ExprRegion implements  Expr{
    private Expr expression;
    private String act;

    public ExprRegion(String act,Expr expression) {
        this.expression = expression;
        this.act=act;
    }

    @Override
    public int eval(Map<String, Integer> bindings) {
        return switch (act) {
            case "invest" -> 1;
            case "collect" -> 2;

            default -> throw new EvalError("Wrong input" + act);
        };
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(act);
        s.append(" ");
        expression.prettyPrint(s);
    }
}
