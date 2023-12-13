package main.a7.Model.Expressions;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.MyException;
import main.a7.Model.Types.BoolType;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.BoolValue;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Types.IntType;
import main.a7.Model.Values.Value;

import java.util.Objects;

public class RelationalExp implements Exp {
    Exp exp1;
    Exp exp2;
    int op;

    public RelationalExp(String operand, Exp e1, Exp e2) {
        if (Objects.equals(operand, "<")) op = 1;
        if (Objects.equals(operand, "<=")) op = 2;
        if (Objects.equals(operand, "==")) op = 3;
        if (Objects.equals(operand, "!=")) op = 4;
        if (Objects.equals(operand, ">")) op = 5;
        if (Objects.equals(operand, ">=")) op = 6;
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public String toString() {
        if (op == 1) return exp1.toString() + "<" + exp2.toString();
        if (op == 2) return exp1.toString() + "<=" + exp2.toString();
        if (op == 3) return exp1.toString() + "==" + exp2.toString();
        if (op == 4) return exp1.toString() + "!=" + exp2.toString();
        if (op == 5) return exp1.toString() + ">" + exp2.toString();
        return exp1.toString() + ">=" + exp2.toString();
    }

    @Override
    public Value eval(MyDictionary<String, Value> tbl, MyHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = exp1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = exp2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue intValue1 = (IntValue) v1;
                IntValue intValue2 = (IntValue) v2;
                int nr1, nr2;
                nr1 = intValue1.getVal();
                nr2 = intValue2.getVal();
                if (op == 1) return new BoolValue(nr1 < nr2);
                if (op == 2) return new BoolValue(nr1 <= nr2);
                if (op == 3) return new BoolValue(nr1 == nr2);
                if (op == 4) return new BoolValue(nr1 != nr2);
                if (op == 5) return new BoolValue(nr1 > nr2);
                if (op == 6) return new BoolValue(nr1 >= nr2);
            } else throw new MyException("Second operand is not an integer!");
        }
        throw new MyException("First operand is not an integer!");
    }

    @Override
    public Type typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = exp1.typecheck(typeEnv);
        type2 = exp2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else throw new MyException("Second operand is not an integer!");
        } else
            throw new MyException("First operand is not an integer!");
    }
}
