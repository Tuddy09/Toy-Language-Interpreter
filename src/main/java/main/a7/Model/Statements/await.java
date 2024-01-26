package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Values.Value;

import java.io.IOException;

public class await implements Stmt {
    private final String var;

    public await(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(var)) {
            Value val = symTable.get(var);
            if (val.getType().equals(new IntType())) {
                int index = ((IntValue) val).getVal();
                if(state.getLatchTable().isDefined(index)) {
                    if (state.getLatchTable().get(index) != 0) {
                        state.getExeStack().push(this);
                    }
                } else throw new MyException("Index not defined in LatchTable");
            }
        } else throw new MyException("Variable not defined in SymTable");
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.get(var).equals(new IntType())) {
            return typeEnv;
        } else throw new MyException("Variable is not an integer");
    }
}
