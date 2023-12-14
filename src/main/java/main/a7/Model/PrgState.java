package main.a7.Model;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.DataStructures.MyList;
import main.a7.Model.DataStructures.MyStack;
import main.a7.Model.ProgramState.SymTable;
import main.a7.Model.Statements.Stmt;
import main.a7.Model.Values.StringValue;
import main.a7.Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {
    MyStack<Stmt> exeStack;
    SymTable symTable;
    MyList<Value> out;

    MyDictionary<StringValue, BufferedReader> fileTable;

    MyHeap<Integer, Value> heap;
    Stmt originalProgram;

    private static final AtomicInteger uniqueId = new AtomicInteger();
    public int id;

    public PrgState(MyStack<Stmt> stk, SymTable symtbl, MyList<Value> ot, MyDictionary<StringValue, BufferedReader> fileTable, MyHeap<Integer, Value> heap, Stmt prg) throws CloneNotSupportedException {
        id = uniqueId.getAndIncrement();
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        originalProgram = deepCopy(prg);
        stk.push(prg);
    }

    public String getId() {
        return String.valueOf(id);
    }

    private Stmt deepCopy(Stmt prg) {
        return prg;
    }

    public MyStack<Stmt> getExeStack() {
        return exeStack;
    }

    public SymTable getSymTable() {
        return symTable;
    }

    public MyList<Value> getOut() {
        return out;
    }

    public Boolean isNotCompleted() {
        return !(exeStack.isEmpty());
    }

    public PrgState oneStep() throws MyException, IOException, CloneNotSupportedException {
        if (exeStack.isEmpty()) throw new MyException("Execution stack is empty!");
        Stmt currentStmt = exeStack.pop();
        return currentStmt.execute(this);
    }


    public MyDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyHeap<Integer, Value> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += "\n------------------\n";
        representation += "Program ID: " + id + "\n";
        representation += "Execution Stack: \n";
        representation += this.exeStack.toString();
        representation += "\nSymbol Table:\n";
        representation += this.symTable.toString();
        representation += "\nOutput Table:\n";
        representation += this.out.toString();
        representation += "\nFile Table:\n";
        representation += this.fileTable.toString();
        representation += "\nHeap: \n";
        representation += this.heap.toString();

        return representation;
    }
}
