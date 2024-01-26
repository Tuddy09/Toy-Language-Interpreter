package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.Expressions.RelationalExp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.Type;

import java.io.IOException;

public class SwitchStmt implements Stmt {
    Exp exp;
    Exp exp1;
    Exp exp2;
    Stmt stmt1;
    Stmt stmt2;
    Stmt stmt3;

    @Override
    public String toString() {
        return "switch(" + exp.toString() + ") case(" + exp1.toString() + "): " + stmt1.toString() + " case(" + exp2.toString() + "): " + stmt2.toString() + " default: " + stmt3.toString();
    }

    public SwitchStmt(Exp e, Exp e1, Stmt s1, Exp e2, Stmt s2, Stmt s3) {
        exp = e;
        exp1 = e1;
        stmt1 = s1;
        exp2 = e2;
        stmt2 = s2;
        stmt3 = s3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        state.getExeStack().push(new IfStmt(new RelationalExp("==", exp1, exp), stmt1, new IfStmt(new RelationalExp("==", exp, exp2), stmt2, stmt3)));
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        Type typeExp1 = exp1.typecheck(typeEnv);
        Type typeExp2 = exp2.typecheck(typeEnv);
        if (typeExp.equals(typeExp1) && typeExp.equals(typeExp2)) {
            stmt1.typecheck(typeEnv);
            stmt2.typecheck(typeEnv);
            stmt3.typecheck(typeEnv);
            return typeEnv;
        } else throw new MyException("The condition of SWITCH has not the same type as the cases!");
    }
}