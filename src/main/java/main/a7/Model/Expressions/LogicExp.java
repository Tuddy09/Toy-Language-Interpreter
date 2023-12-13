package main.a7.Model.Expressions;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.MyException;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.BoolValue;
import main.a7.Model.Types.BoolType;
import main.a7.Model.Values.Value;

import java.util.Objects;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    int op;

    LogicExp(String o, Exp expr1, Exp expr2) {
        if (Objects.equals(o, "or")) op = 1;
        if (Objects.equals(o, "and")) op = 2;
        e1 = expr1;
        e2 = expr2;
    }

    @Override
    public String toString() {
        if (op == 1) return e1.toString() + "and" + e2.toString();
        else
            return e1.toString() + "or" + e2.toString();
    }

    @Override
    public Value eval(MyDictionary<String, Value> tbl, MyHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = b1.getVal();
                n2 = b2.getVal();
                if (op == 1) return new BoolValue(n1 && n2);
                if (op == 2) return new BoolValue(n1 || n2);
            } else throw new MyException("Second operand is not an boolean");
        }
        throw new MyException("First operand is not a boolean");
    }

    @Override
    public Type typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else throw new MyException("Second operand is not an boolean!");
        } else
            throw new MyException("First operand is not an boolean!");
    }
}
