package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyStack;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.Type;

public class CompStmt implements Stmt {
    Stmt first;
    Stmt second;

    public CompStmt(Stmt s1, Stmt s2) {
        first = s1;
        second = s2;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) {
        MyStack<Stmt> execution_stack = state.getExeStack();
        execution_stack.push(second);
        execution_stack.push(first);
        return null;
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

}
