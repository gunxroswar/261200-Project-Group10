public class PlanParser implements Parser{

    private  Tokenizer tkz;
    public PlanParser(Tokenizer tkz) {
        this.tkz = tkz;
    }

    public Expr parsePlan() throws SyntaxError {
        // begin parsing at start symbol
        Expr sum = parseStatement();
        // reject if there is remaining token
        if (tkz.hasNextToken()) throw new SyntaxError("leftover token");
        else return sum;
    }
    // Plan → Statement

    // Statement → Command | BlockStatement | IfStatement | WhileStatement
    private Expr parseStatement() throws SyntaxError{
        Expr s = null;
        if(tkz.peek("while")){
            s = parseWhileStatement();
        }else if(tkz.peek("if")){
            s = parseIfStatement();
        }else if(tkz.peek("{")){
            s = parseBlockStatement();
        }else
            s = parseCommand();

        return s;
    }
    // Command → AssignmentStatement | ActionCommand
    private Expr parseCommand() throws SyntaxError{
        Expr c =null;
        if(isActionCommand(tkz.peek())){
            c = parseActionCommand();
        }else{
            c = parseAssignStatement();
        }

        return c;
    }
        // AssignmentStatement → <identifier> = Expression
        private Expr parseAssignStatement() throws SyntaxError{
            Expr ass = null;
            if(isIdentify(tkz.peek())){
                String assign = tkz.consume();
                if(tkz.peek("=")){
                    tkz.comsume("=");
                    ass = new Assignment(assign,"=",parseExpression());
                }
            }
            return ass;
        }
        // ActionCommand → done | relocate | MoveCommand | RegionCommand | AttackCommand
        private Expr parseActionCommand() throws SyntaxError{
            Expr a = null;
            if(isAttackCommand(tkz.peek())){
               a = parseAttackCommand();
            }else if(isMoveCommand(tkz.peek())){
                a = parseMoveCommand();
            }else if(isRegionCommand(tkz.peek())){
                a = parseRegionCommand();
            }else if(isCharacter(tkz.peek())){
                if(tkz.peek("done")||tkz.peek("relocate")) {
                    return a = new ExprAction(tkz.consume()); /*new "Something" tkz.consume();*/
                }
            }
            return a;
        }
            // MoveCommand → move Direction
            private Expr parseMoveCommand() throws SyntaxError{
                Expr m =null;
                if(isCharacter(tkz.peek()))
                {
                    if(tkz.peek("move"))
                    {
                        tkz.comsume("move");
                        m=new ExprMove(parseDirection()); /*new "Something" tkz.consume(); parseDirection()*/

                    }
                }
                return m;
            }
                // Direction → up | down | upleft | upright | downleft | downright
                private Expr parseDirection() throws SyntaxError{
                Expr d=null;
                if(isDirection(tkz.peek()))
                {
                    d = new ExprDirection(tkz.consume()); /*new "Something" tkz.consume(); */
                }
                    return d;
                }
            // RegionCommand → invest Expression | collect Expression
            private Expr parseRegionCommand() throws SyntaxError{
            Expr r= null;

            if(isCharacter(tkz.peek()))
            {
                if(tkz.peek("invest")||tkz.peek("collect"))
                {

                    r=new ExprRegion(tkz.consume(),parseExpression()); /*new "Something" tkz.consume();with parseExpression()*/
                }

            }
                return r;
            }
            // AttackCommand → shoot Direction Expression
            private Expr parseAttackCommand() throws SyntaxError{
        Expr att=null;
        if(isAttackCommand(tkz.peek()))
        {
            tkz.comsume("shoot");

            att=new ExprAttack(parseDirection(),parseExpression());
        }
                return att;
            }
    // BlockStatement → { Statement* }
    private Expr parseBlockStatement() throws SyntaxError{
        Expr b=null;
        if(tkz.peek("{"))
        {
            tkz.comsume("{");
            while (!tkz.peek("}")){
                b=parseStatement();/* ยังไม่มั้นใจความถูกต้อง*/
            }

        }
        return b;
    }
    // IfStatement → if ( Expression ) then Statement else Statement
    private Expr parseIfStatement() throws SyntaxError{
        Expr e =null;
        Expr t=null;
        Expr f=null;
        tkz.comsume("if");
        tkz.comsume("(");
        e=parseExpression();
        tkz.comsume(")");
        tkz.comsume("then");
        t=parseStatement();
        tkz.comsume("else");
        f=parseStatement();/*new "Something" tkz.consume();with parseExpression() and Two parseStatement() */
        return new ExprIfStatement(e,t,f);
    }
    // WhileStatement → while ( Expression ) Statement
    private Expr parseWhileStatement() throws SyntaxError {
        Expr e=null;
        Expr w = null;
        if(tkz.peek("while")) {
            tkz.comsume("(");
            e=parseExpression();
            tkz.comsume(")");
        }
        w= parseStatement();/*new "Something" tkz.consume();with parseExpression()  and parseStatement()*/
        return new ExprWhileStatement(e,w);
    }
    //Expression → Expression + Term | Expression - Term | Term
    private Expr parseExpression() throws SyntaxError{
        Expr e = parseTerm();
        while(tkz.peek("+") || tkz.peek("-")) {
            if(tkz.peek("+")) {
                tkz.consume();
                e = new BinaryArithExpr(e,"+",parseTerm());
            }else if(tkz.peek("-")){
                tkz.consume();
                e = new BinaryArithExpr(e,"-",parseTerm());
            }
        }
        return e;
    }
    // Term → Term * Factor | Term / Factor | Term % Factor | Factor
    private Expr parseTerm() throws SyntaxError{
        Expr t = parseFactor();
        while(tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            if (tkz.peek("*")) {
                tkz.consume();
                t = new BinaryArithExpr(t, "*", parseFactor());
            } else if (tkz.peek("/")) {
                tkz.consume();
                t = new BinaryArithExpr(t, "/", parseFactor());
            } else if (tkz.peek("%")) {
                tkz.consume();
                t = new BinaryArithExpr(t, "%", parseFactor());
            }
        }
        return t;
    }
    // Factor → Power ^ Factor | Power
    private Expr parseFactor() throws SyntaxError{
        Expr f = parsePower();
        while(tkz.peek("^")){
            if(tkz.peek("^")){
                tkz.consume();
                f =new BinaryArithExpr(f,"^",parsePower());
            }
        }
        return null;
    }
    // Power → <number> | <identifier> | ( Expression ) | InfoExpression
    private Expr parsePower() throws SyntaxError{
        if(isNumeric(tkz.peek())){
            return new IntLit(Integer.parseInt(tkz.consume()));
        }else if(isIdentify(tkz.peek())){
            return new Identifier(tkz.consume());
        }else if(tkz.peek("(")){
            tkz.comsume("(");
            Expr p = parseExpression();
            tkz.comsume(")");
            return p;
        }else{
            return parseInfoExpression();
            }
    }
    // InfoExpression → opponent | nearby Direction
    private Expr parseInfoExpression() throws SyntaxError{
    Expr i=null;
        if(tkz.peek("opponent")){
            i= new InfoOppo(tkz.consume());//ยังไม่เสร็จ แต่จะอนู่ในรูป new "Class"ตามด้วยopponent
        }else{
            i=  new InfoNearby(tkz.consume(),parseDirection());
        }
        return i;
    }

    private boolean isCharacter(String peek) {
        if (peek == null) {
            return false;
        }
        try {
            if(peek.matches("^[a-zA-Z]*$")) {
                return true;
            }else throw new NumberFormatException();
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean isIdentify(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*up | down | upleft | upright | downleft | downright*/
            if(!(peek.equals("up") || peek.equals("down") || peek.equals("upleft") || peek.equals("upright") || peek.equals("downleft") || peek.equals("downright")
                    || peek.equals("done")|| peek.equals("relocate")|| peek.equals("move")|| peek.equals("invest")|| peek.equals("collect")|| peek.equals("shoot")
                    || peek.equals("if")|| peek.equals("then")|| peek.equals("else")|| peek.equals("while")|| peek.equals("oppenent")|| peek.equals("nearby"))){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isDirection(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*up | down | upleft | upright | downleft | downright*/
            if(peek.equals("up") || peek.equals("down") || peek.equals("upleft") || peek.equals("upright") || peek.equals("downleft") || peek.equals("downright")){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isIfฺStatement(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*if then else*/
            if(peek.equals("if") || peek.equals("then") || peek.equals("else")){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isWhileฺStatement(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*if then else*/
            if(peek.equals("while")){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isActionCommand(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*if then else*/
            if(peek.equals("done") || peek.equals("relocate") || isMoveCommand(peek) || isRegionCommand(peek) || isAttackCommand(peek)){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isMoveCommand(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*if then else*/
            if(peek.equals("move")){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isRegionCommand(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*if then else*/
            if(peek.equals("invest") || peek.equals("collect")){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isAttackCommand(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*if then else*/
            if(peek.equals("shoot")){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isInfoExpression(String peek) {
        if (peek == null) {
            return false;
        }
        if(!isCharacter(peek)) {
            return false;
        }
        try {
            /*if then else*/
            if(peek.equals("opponent") || peek.equals("nearby")){
                return true;
            }else throw new NumberFormatException(); //Exception รอแก้
        } catch (NumberFormatException nfe) {
            return false;
        }
    }



    Expr parse() throws SyntaxError {
        return null;
    }


}
