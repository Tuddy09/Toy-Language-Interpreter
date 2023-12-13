package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.DataStructures.MyStack;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.BoolType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.BoolValue;
import main.a7.Model.Values.Value;

import java.io.IOException;

public class WhileStmt implements Stmt {
    Exp exp;
    Stmt stmt;

    public WhileStmt(Exp exp, Stmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "while (" + exp.toString() + ") " + stmt.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyStack<Stmt> execution_stack = state.getExeStack();
        MyHeap<Integer, Value> heap = state.getHeap();
        MyDictionary<String, Value> symTable = state.getSymTable();
        Value exp_value = exp.eval(symTable, heap);
        if (exp_value.getType() instanceof BoolType) {
            if (exp_value.equals(new BoolValue(false))) {
                return null;
            } else {
                execution_stack.push(new WhileStmt(exp, stmt));
                execution_stack.push(stmt);
            }
        } else throw new MyException("Condition expression is not a boolean!");
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            stmt.typecheck(typeEnv);
            return typeEnv;
        } else throw new MyException("The condition of IF has not the type Bool!");
    }
}
