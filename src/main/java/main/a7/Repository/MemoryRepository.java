package main.a7.Repository;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.DataStructures.MyList;
import main.a7.Model.DataStructures.MyStack;
import main.a7.Model.PrgState;
import main.a7.Model.Statements.Stmt;
import main.a7.Model.Values.StringValue;
import main.a7.Model.Values.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements Repository {
    List<PrgState> ListOfProgramStates;
    String logFilePath;

    public MemoryRepository(PrgState program_state, String fileName) {
        ListOfProgramStates = new ArrayList<>();
        ListOfProgramStates.add(program_state);
        this.logFilePath = fileName;
    }

    @Override
    public List<PrgState> getPrgList() {
        return ListOfProgramStates;
    }

    @Override
    public String toString() {
        return ListOfProgramStates.getLast().toString();
    }

    @Override
    public void setPrgList(List<PrgState> newPrgList) {
        ListOfProgramStates = newPrgList;
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws IOException {
        MyStack<Stmt> execStack = prgState.getExeStack();
        MyDictionary<String, Value> symTable = prgState.getSymTable();
        MyList<Value> out = prgState.getOut();
        MyDictionary<StringValue, BufferedReader> fileTable = prgState.getFileTable();
        MyHeap<Integer, Value> heap = prgState.getHeap();
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println("Id: " + prgState.getId());
        logFile.println("ExeStack:");
        logFile.println(execStack.toString());
        logFile.println("SymTable:");
        logFile.println(symTable.toString());
        logFile.println("Out:");
        logFile.println(out.toString());
        logFile.println("FileTable:");
        logFile.println(fileTable.toString());
        logFile.println("Heap:");
        logFile.println(heap.toString());
        logFile.println("---------------");
        logFile.close();
    }
}
