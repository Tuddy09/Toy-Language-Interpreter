package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.DataStructures.MyList;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.Value;

public class PrintStmt implements Stmt {
    Exp expression;

    public PrintStmt(Exp exp) {
        expression = exp;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyList<Value> out = state.getOut();
        MyDictionary<String, Value> symTable = state.getSymTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        out.add(expression.eval(symTable, heap));
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }
}
