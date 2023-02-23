import java.util.NoSuchElementException;

public class PlanTokenizer implements Tokenizer{

    private String src, next;  private int pos;
    public PlanTokenizer(String src) {
        this.src = src;  pos = 0;
        computeNext();
    }
    public boolean hasNextToken() {
        return next != null;
    }
    public String peek() {
        if (!hasNextToken()) {
            throw new NoSuchElementException("no more tokens");
        }
        return next;
    }

    public String consume() {
        if (!hasNextToken()){
            throw new NoSuchElementException("no more tokens");
        }
        String result = next;
        computeNext();
        return result;
    }
    private void computeNext(){
        StringBuilder s = new StringBuilder();

        while (pos < src.length() && Character.isWhitespace(src.charAt(pos))) pos++;  // ignore whitespace
        if (pos == src.length()) {
            next = null;
            return;
        }  // no more tokens
        char c = src.charAt(pos);
        if (Character.isDigit(c)) {  // start of number
            s.append(c);
            for (pos++; pos < src.length() && Character.isDigit(src.charAt(pos)); pos++)
                s.append(src.charAt(pos));
        } else if (c == '+' || c == '(' || c == ')' || c == '-' || c == '*' || c == '/' || c == '%' || c=='^') {
            s.append(c);  pos++;
        }else if(Character.isLetter(c)){
            s.append(c);
            for (pos++; pos < src.length() && Character.isLetter(src.charAt(pos)); pos++)
                s.append(src.charAt(pos));
        }else if(c == '#'){
            boolean findln = true;

            while(pos < src.length() && findln){
                if(src.charAt(pos) == '\n') findln = false;
                pos++;
            }

        }else {
            throw new SyntaxError("unknown character: " + c);
        }
        next = s.toString();
    }

    /** Returns true if
     *  the next token (if any) is s. */
    public boolean peek(String s) {
        if (!hasNextToken()) return false;
        return peek().equals(s);
    }

    @Override
    public void comsume(String s)
        throws SyntaxError {
            if (peek(s))
                this.consume();
            else
                throw new SyntaxError(s + " expected");
        }


    /* Consumes the next token if it is s.
     *  Throws SyntaxError otherwise.
     *  effects: removes the next token
     *           from input stream if it is s
     */


}
