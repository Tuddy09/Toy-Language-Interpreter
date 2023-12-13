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

public class newStmt implements Stmt {
    String name;
    Exp exp;

    public newStmt(String varName, Exp expression) {
        name = varName;
        exp = expression;
    }

    @Override
    public String toString() {
        return "new(" + name + ", " + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        if (symTable.isDefined(name)) {
            Type var_type = symTable.get(name).getType();
            Value val = exp.eval(symTable, heap);
            if (var_type.equals(new RefType(val.getType()))) {
                int allocation = heap.put(val);
                symTable.update(name, new RefValue(allocation, val.getType()));
            } else throw new MyException("Type is not a ref type with the same location type!");
        } else throw new MyException("Variable is not declared!");
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(name);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types!");
    }
}
