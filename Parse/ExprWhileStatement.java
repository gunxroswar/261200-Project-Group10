import java.util.Map;

public class ExprWhileStatement implements Expr{

    private Expr expression,statement;

    public ExprWhileStatement(Expr expression,Expr statement) {
        this.expression = expression;
        this.statement= statement;
    }

    @Override
    public int eval(Map<String, Integer> bindings) {
        return 0;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("while ( ");
        expression.prettyPrint(s);
        s.append(" ) "+'\n' +"{ ");
        statement.prettyPrint(s);
        s.append(" } ");


    }
}
