package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.ProgramState.SemaphoreTable;
import main.a7.Model.ProgramState.SymTable;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Values.Value;

import java.io.IOException;

public class createSemaphore implements Stmt {
    String var;
    Exp exp;

    public createSemaphore(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var + ", " + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        SymTable symTable = state.getSymTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        SemaphoreTable semaphoreTable = state.getSemaphoreTable();
        Value exp_value = exp.eval(symTable, heap);
        if (exp_value.getType().equals(new IntType())) {
            IntValue intValue = (IntValue) exp_value;
            int number = intValue.getVal();
            int newLocation = semaphoreTable.put(new javafx.util.Pair<>(number, new java.util.ArrayList<>()));
            if (symTable.isDefined(var)) {
                symTable.update(var, new IntValue(newLocation));
            } else {
                throw new MyException("The variable is not defined!");
            }
            return null;
        } else throw new MyException("The expression is not an IntType!");
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(var);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            if (typeVar.equals(new IntType())) {
                return typeEnv;
            } else throw new MyException("The variable is not an IntType!");
        } else
            throw new MyException("Assigment: right hand side and left hand side have different types!");
    }
}
