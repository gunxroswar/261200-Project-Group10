import java.util.Map;

public class Identifier implements Expr{
    private final String name;
    public Identifier(String name) {
        this.name = name;
    }
    public int eval(Map<String, Integer> bindings) {
        if (bindings.containsKey(name))
            return bindings.get(name);
        throw new EvalError(
                "undefined variable: " + name);
    }
    public void prettyPrint(
            StringBuilder s) {
        s.append(name);
    }

}
