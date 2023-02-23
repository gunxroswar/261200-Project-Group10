import java.util.Map;

public class ExprAction implements Expr{

    private String x;

    public ExprAction(String x){
        this.x = x;

    }


    @Override
    public int eval(Map<String, Integer> bindings) {

        return switch (x) {
            case "done" -> 1;
            case "relocate" -> 2;

            default -> throw new EvalError("Wrong input" + x);
        };
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(x);

    }
}
