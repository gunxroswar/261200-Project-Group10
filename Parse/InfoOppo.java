import java.util.Map;

public class InfoOppo implements Expr {
    private String oppo;
    public InfoOppo(String oppo) {
        this.oppo = oppo;
    }
    public int eval(
            Map<String, Integer> bindings) {
        return -1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(oppo);
    }
}
