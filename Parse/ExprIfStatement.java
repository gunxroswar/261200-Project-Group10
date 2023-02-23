import java.util.Map;

public class ExprIfStatement implements Expr{
    private Expr expression,stateTure,stateFalse;

    public ExprIfStatement(Expr expression,Expr stateTure,Expr stateFalse) {
        this.expression=expression;
        this.stateTure=stateTure;
        this.stateFalse=stateFalse;
    }

    @Override
    public int eval(Map<String, Integer> bindings) {
        return 0;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("if ( ");
        expression.prettyPrint(s);
        s.append(" ) then ");
        s.append(" { ");
        stateTure.prettyPrint(s);
        s.append(" } ");
        s.append(" else ");
        s.append(" { ");
        stateFalse.prettyPrint(s);
        s.append(" } ");

    }
}
