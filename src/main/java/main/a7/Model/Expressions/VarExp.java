package main.a7.Model.Expressions;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.MyException;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String varName) {
        id = varName;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Value eval(MyDictionary<String, Value> tbl, MyHeap<Integer, Value> heap) throws MyException {
        Value v = tbl.get(id);
        if (v == null) {
            throw new MyException("Value not assigned.");
        } else {
            return v;
        }
    }

    @Override
    public Type typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.get(id);
    }
}
