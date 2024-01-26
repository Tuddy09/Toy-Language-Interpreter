package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.DataStructures.MyLatchTable;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Values.Value;

import java.io.IOException;

public class newLatch implements Stmt {
    private final String var;
    private final Exp exp;

    public newLatch(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " + exp + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        MyLatchTable<Integer, Integer> latchTable = state.getLatchTable();
        if (symTable.isDefined(var) && symTable.get(var).getType().equals(new IntType())) {
            Value val = exp.eval(symTable, heap);
            if (val.getType().equals(new IntType())) {
                int number = ((IntValue) val).getVal();
                int freeLocation = latchTable.put(number);
                symTable.update(var, new IntValue(freeLocation));
            } else
                throw new MyException("Expression is not an integer");
        } else throw new MyException("Variable is not defined or is not an integer");
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(var);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new IntType())) {
            if (typeExp.equals(new IntType())) {
                return typeEnv;
            } else throw new MyException("Expression is not an integer");
        } else throw new MyException("Variable is not an integer");
    }
}
