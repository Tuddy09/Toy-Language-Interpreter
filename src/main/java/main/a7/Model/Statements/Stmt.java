package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.Type;

import java.io.IOException;

public interface Stmt {
    PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException;

    MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException;

}
