package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.RefType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.RefValue;
import main.a7.Model.Values.Value;

import java.io.IOException;

public class wHStmt implements Stmt {
    String varName;
    Exp exp;

    public wHStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        if (symTable.isDefined(varName)) {
            Value value = symTable.get(varName);
            if (value.getType() instanceof RefType) {
                RefValue refValue = (RefValue) value;
                if (heap.isDefined(refValue.getAddress())) {
                    Value expValue = exp.eval(symTable, heap);
                    if (expValue.getType().equals(refValue.getLocationType())) {
                        heap.update(refValue.getAddress(), expValue);
                    } else throw new MyException("The location types do not match!");
                } else throw new MyException("Variable is not allocated in the heap!");
            } else throw new MyException("Value does not have type RefType!");
        } else throw new MyException("Variable is not defined!");
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(varName);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else throw new MyException("WH stmt: right hand side and left hand side have different types!");
    }
}
