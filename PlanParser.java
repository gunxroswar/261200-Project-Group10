public class PlanParser implements Parser{

    private Tokenizer tkz;
    public PlanParser(Tokenizer tkz) {
        this.tkz = tkz;
    }

    // Plan → Statement
    private Expr parsePlan() throws SyntaxError{
        Expr e = parseStatement();
        return e;
    }
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
        }
        return s;
    }
    // Command → AssignmentStatement | ActionCommand
    private Expr parseCommand() throws SyntaxError{

        return null;
    }
        // AssignmentStatement → <identifier> = Expression
        private Expr parseAssignStatement() throws SyntaxError{
            return null;
        }
        // ActionCommand → done | relocate | MoveCommand | RegionCommand | AttackCommand
        private Expr parseActionCommand() throws SyntaxError{

            return null;
        }
            // MoveCommand → move Direction
            private Expr parseMoveCommand() throws SyntaxError{
                return null;
            }
                // Direction → up | down | upleft | upright | downleft | downright
                private Expr parseDirection() throws SyntaxError{
                    return null;
                }
            // RegionCommand → invest Expression | collect Expression
            private Expr parseRegionCommand() throws SyntaxError{

                return null;
            }
            // AttackCommand → shoot Direction Expression
            private Expr parseAttackCommand() throws SyntaxError{
                return null;
            }
    // BlockStatement → { Statement* }
    private Expr parseBlockStatement() throws SyntaxError{
        return null;
    }
    // IfStatement → if ( Expression ) then Statement else Statement
    private Expr parseIfStatement() throws SyntaxError{
        return null;
    }
    // WhileStatement → while ( Expression ) Statement
    private Expr parseWhileStatement() throws SyntaxError{
        Expr w = null;
        if(tkz.peek("while")) {
            tkz.comsume("{");
            parseExpression();
        }
        parseStatement();
    }
    //Expression → Expression + Term | Expression - Term | Term
    private Expr parseExpression() throws SyntaxError{
        Expr e = parseTerm();
        if(tkz.peek("+") || tkz.peek("-")) {
            if(tkz.peek("+")) {
                tkz.comsume();
                e =
            }
        }

        return null;
    }
    // Term → Term * Factor | Term / Factor | Term % Factor | Factor
    private Expr parseTerm() throws SyntaxError{
        return null;
    }
    // Factor → Power ^ Factor | Power
    private Expr parseFactor() throws SyntaxError{
        return null;
    }
    // Power → <number> | <identifier> | ( Expression ) | InfoExpression
    private Expr parsePower() throws SyntaxError{
        return null;
    }
    // InfoExpression → opponent | nearby Direction
    private Expr parseInfoExpression() throws SyntaxError{
        return null;
    }



    Expr parse() throws SyntaxError {
        return null;
    }


}
