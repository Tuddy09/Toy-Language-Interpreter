package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.Type;

public class NopStmt implements Stmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
