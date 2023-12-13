package main.a7.Model.Expressions;

import main.a7.Model.*;
import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Types.IntType;
import main.a7.Model.Values.Value;

import java.util.Objects;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int op;

    public ArithExp(String o, Exp expr1, Exp expr2) {
        if (Objects.equals(o, "+")) op = 1;
        if (Objects.equals(o, "-")) op = 2;
        if (Objects.equals(o, "*")) op = 3;
        if (Objects.equals(o, "/")) op = 4;
        e1 = expr1;
        e2 = expr2;
    }

    @Override
    public String toString() {
        if (op == 1) return e1.toString() + "+" + e2.toString();
        if (op == 2) return e1.toString() + "-" + e2.toString();
        if (op == 3) return e1.toString() + "*" + e2.toString();
        return e1.toString() + "/" + e2.toString();
    }

    @Override
    public Value eval(MyDictionary<String, Value> tbl, MyHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new IntValue(n1 + n2);
                if (op == 2) return new IntValue(n1 - n2);
                if (op == 3) return new IntValue(n1 * n2);
                if (op == 4) if (n2 == 0) throw new MyException("division by zero");
                else return new IntValue(n1 / n2);
            } else throw new MyException("second operand is not an integer");
        }
        throw new MyException("first operand is not an integer");
    }

    @Override
    public Type typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else throw new MyException("Second operand is not an integer!");
        } else
            throw new MyException("First operand is not an integer!");
    }
}