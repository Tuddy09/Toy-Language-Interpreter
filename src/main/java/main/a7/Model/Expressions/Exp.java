package main.a7.Model.Expressions;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.MyException;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.Value;

public interface Exp {
    Value eval(MyDictionary<String, Value> tbl, MyHeap<Integer, Value> heap) throws MyException;

    Type typecheck(MyDictionary<String, Type> typeEnv) throws MyException;
}
