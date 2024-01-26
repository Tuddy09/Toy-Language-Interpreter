package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.ProgramState.ExecutionStack;
import main.a7.Model.ProgramState.SymTable;
import main.a7.Model.Types.BoolType;
import main.a7.Model.Types.Type;

import java.io.IOException;

public class forkStmt implements Stmt {
    Stmt stmt;

    public forkStmt(Stmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException, CloneNotSupportedException {
        SymTable symTable = state.getSymTable();
        return new PrgState(new ExecutionStack<>(), symTable.deepCopy(), state.getOut(), state.getFileTable(), state.getHeap(), state.getLatchTable(), stmt);
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv);
        return typeEnv;
    }
}
