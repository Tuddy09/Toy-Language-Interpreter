package main.a7.Model.Statements;

import javafx.util.Pair;
import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.DataStructures.MySemaphoreTable;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.ProgramState.SymTable;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Values.Value;

import java.io.IOException;
import java.util.List;

public class acquireStmt implements Stmt {
    String var;

    public acquireStmt(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "acquire(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        SymTable symTable = state.getSymTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        MySemaphoreTable<Integer, javafx.util.Pair<Integer, java.util.List<Integer>>> semaphoreTable = state.getSemaphoreTable();
        if (symTable.isDefined(var)) {
            Value value = symTable.get(var);
            if (value.getType().equals(new IntType())) {
                IntValue intValue = (IntValue) value;
                int number = intValue.getVal();
                if (semaphoreTable.isDefined(number)) {
                    Pair<Integer, List<Integer>> pair = semaphoreTable.get(number);
                    int NL = pair.getValue().size();
                    if (pair.getKey() > NL) {
                        if (!pair.getValue().contains(Integer.valueOf(state.getId()))) {
                            pair.getValue().add(Integer.valueOf(state.getId()));
                        }
                    } else {
                        state.getExeStack().push(this);
                    }
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
            throw new MyException("The variable is not an IntType!");
    }
}
