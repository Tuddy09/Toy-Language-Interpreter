package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyLatchTable;
import main.a7.Model.DataStructures.MyList;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.StringValue;
import main.a7.Model.Values.Value;

import java.io.IOException;

public class countDown implements Stmt {
    private final String var;

    public countDown(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "countDown(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        MyLatchTable<Integer, Integer> latchTable = state.getLatchTable();
        MyList<Value> out = state.getOut();
        if (symTable.isDefined(var)) {
            Value val = symTable.get(var);
            if (val.getType().equals(new IntType())) {
                int index = ((main.a7.Model.Values.IntValue) val).getVal();
                if (latchTable.isDefined(index)) {
                    if (latchTable.get(index) > 0) {
                        latchTable.update(index, latchTable.get(index) - 1);
                        out.add(new StringValue(state.getId()));
                    } else {
                        out.add(new StringValue(state.getId()));
                    }
                } else throw new MyException("Index not defined in LatchTable!");
            } else throw new MyException("Value is not an integer!");
        } else throw new MyException("Variable not defined in SymTable!");
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.get(var).equals(new IntType())) {
            return typeEnv;
        } else throw new MyException("Variable is not an integer!");
    }
}
