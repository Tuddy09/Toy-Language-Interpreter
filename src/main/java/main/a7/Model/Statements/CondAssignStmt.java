package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.BoolType;
import main.a7.Model.Types.Type;

import java.io.IOException;

public class CondAssignStmt implements Stmt {
    String id;
    Exp exp1;
    Exp exp2;
    Exp exp3;

    public CondAssignStmt(String v, Exp valueExp1, Exp valueExp2, Exp valueExp3) {
        id = v;
        exp1 = valueExp1;
        exp2 = valueExp2;
        exp3 = valueExp3;
    }

    @Override
    public String toString() {
        return id + "=" + exp1.toString() + "?" + exp2.toString() + ":" + exp3.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        Stmt thenS = new AssignStmt(id, exp2);
        Stmt elseS = new AssignStmt(id, exp3);
        Stmt ifS = new IfStmt(exp1, thenS, elseS);
        state.getExeStack().push(ifS);
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp1 = exp1.typecheck(typeEnv);
        Type typeVar = typeEnv.get(id);
        if(typeExp1.equals(new BoolType())){
            Type typeExp2 = exp2.typecheck(typeEnv);
            Type typeExp3 = exp3.typecheck(typeEnv);
            if(typeExp2.equals(typeExp3)){
                if (typeVar.equals(typeExp2)) {
                    return typeEnv;
                } else
                    throw new MyException("Assigment: right hand side and left hand side have different types!");
            }
            else throw new MyException("The second and third expressions have different types!");
        }
        else throw new MyException("The first expression is not a boolean!");
    }
}
