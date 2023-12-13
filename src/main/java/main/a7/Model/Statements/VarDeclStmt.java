package main.a7.Model.Statements;

import main.a7.Model.*;
import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.Value;

public class VarDeclStmt implements Stmt {
    String name;
    Type varType;

    @Override
    public String toString() {
        return varType.toString() + " " + name;
    }

    public VarDeclStmt(String varName, Type variableType) {
        name = varName;
        varType = variableType;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) throw new MyException("Variable is already declared!");
        else {
            symTable.update(name, varType.defaultValue());
        }
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, varType);
        return typeEnv;
    }
}
