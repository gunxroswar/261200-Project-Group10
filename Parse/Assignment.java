import java.util.Map;

public class Assignment implements Expr{

    private String assign,op;
    private Expr Expression;

    public Assignment(String assign,String op,Expr Expression){
        this.assign = assign;
        this.op = op;
        this.Expression = Expression;

    }


    @Override
    public int eval(Map<String, Integer> bindings) {
        return 0;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(assign);
        s.append(op);
        Expression.prettyPrint(s);

    }
}
