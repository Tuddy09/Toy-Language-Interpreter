package main.a7.Model.Statements;

import main.a7.Model.*;
import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.Value;

public class AssignStmt implements Stmt {
    String id;
    Exp exp;

    public AssignStmt(String v, Exp valueExp) {
        id = v;
        exp = valueExp;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        if (symTable.isDefined(id)) {
            Value val = exp.eval(symTable, heap);
            Type typeId = (symTable.get(id)).getType();
            if (val.getType().equals(typeId)) {
                symTable.update(id, val);
            } else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
        } else throw new MyException("the used variable " + id + " was never used");
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(id);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        } else
            throw new MyException("Assigment: right hand side and left hand side have different types!");
    }

}
