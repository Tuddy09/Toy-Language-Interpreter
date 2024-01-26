package main.a7.Model.Statements;

import javafx.util.Pair;
import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MySemaphoreTable;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Values.Value;

import java.io.IOException;
import java.util.List;

public class releaseStmt implements Stmt {
    String var;

    public releaseStmt(String var) {
        this.var = var;
    }
    @Override
    public String toString() {
        return "release(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        MySemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();
        if (symTable.isDefined(var)) {
            Value value = symTable.get(var);
            if (value.getType().equals(new IntType())) {
                int number = ((IntValue) value).getVal();
                if (semaphoreTable.isDefined(number)) {
                    Pair<Integer, List<Integer>> pair = semaphoreTable.get(number);
                    List<Integer> list = pair.getValue();
                    list.remove(Integer.valueOf(state.getId()));
                } else {
                    throw new MyException("The semaphore is not defined!");
                }
                return null;
            } else {
                throw new MyException("The variable is not an IntType!");
            }
        } else {
            throw new MyException("The variable is not defined!");
        }
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(var);
        if (typeVar.equals(new IntType())) {
            return typeEnv;
        } else
            throw new MyException("Variable is not an IntType!");
    }
}
