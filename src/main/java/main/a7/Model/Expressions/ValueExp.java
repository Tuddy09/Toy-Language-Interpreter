package main.a7.Model.Expressions;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.MyException;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.Value;

public class ValueExp implements Exp {
    Value e;

    public ValueExp(Value v) {
        e = v;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Value eval(MyDictionary<String, Value> tbl, MyHeap<Integer, Value> heap) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
