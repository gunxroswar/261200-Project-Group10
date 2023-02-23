import java.util.Map;

public class InfoNearby implements Expr {

    private String nearby;
    private Expr Direction;

    public InfoNearby(String nearby, Expr Direction) {
        this.nearby = nearby;
        this.Direction = Direction;
    }

    public int eval(Map<String, Integer> bindings) {
        return -1;
    }

    public void prettyPrint(StringBuilder s) {
        s.append(nearby);
        Direction.prettyPrint(s);


    }
}
