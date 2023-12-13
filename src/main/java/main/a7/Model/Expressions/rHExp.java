package main.a7.Model.Expressions;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.MyException;
import main.a7.Model.Types.RefType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.RefValue;
import main.a7.Model.Values.Value;

public class rHExp implements Exp {
    Exp exp;

    public rHExp(Exp exp1) {
        exp = exp1;
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }

    @Override
    public Value eval(MyDictionary<String, Value> tbl, MyHeap<Integer, Value> heap) throws MyException {
        Value v = exp.eval(tbl, heap);
        if (v instanceof RefValue) {
            int address = ((RefValue) v).getAddress();
            if (heap.isDefined(address)) {
                return heap.get(address);
            } else throw new MyException("Address is not defined!");
        } else throw new MyException("Value is not a RefValue!");
    }

    @Override
    public Type typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        if (type instanceof RefType refType) {
            return refType.getInner();
        } else throw new MyException("The rH argument is not a RefType");
    }
}
