package main.a7.Model.Statements;

import main.a7.Model.*;
import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.DataStructures.MyStack;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.BoolValue;
import main.a7.Model.Types.BoolType;
import main.a7.Model.Values.Value;

public class IfStmt implements Stmt {
    Exp exp;
    Stmt thenS;
    Stmt elseS;

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ")THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    public IfStmt(Exp expression, Stmt thenStatement, Stmt elseStatement) {
        exp = expression;
        thenS = thenStatement;
        elseS = elseStatement;

    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyStack<Stmt> execution_stack = state.getExeStack();
        MyHeap<Integer, Value> heap = state.getHeap();
        MyDictionary<String, Value> symTable = state.getSymTable();
        Value exp_value = exp.eval(symTable, heap);
        if (exp_value.getType().equals(new BoolType())) {
            if (exp_value.equals(new BoolValue(true))) {
                execution_stack.push(thenS);
            } else {
                execution_stack.push(elseS);
            }
        } else throw new MyException("Conditional expression is not a boolean!");
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenS.typecheck(typeEnv);
            elseS.typecheck(typeEnv);
            return typeEnv;
        } else throw new MyException("The condition of IF has not the type Bool!");
    }
}
