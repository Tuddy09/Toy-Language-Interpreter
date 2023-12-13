package main.a7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.a7.Controller.Controller;
import main.a7.Model.Expressions.*;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.ProgramState.*;
import main.a7.Model.Statements.*;
import main.a7.Model.Types.BoolType;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.RefType;
import main.a7.Model.Types.StringType;
import main.a7.Model.Values.BoolValue;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Values.StringValue;
import main.a7.Repository.MemoryRepository;
import main.a7.Repository.Repository;

public class WindowController {
    @FXML
    public Label selectProgramLabel;
    @FXML
    public ListView<String> programListView;

    ObservableList<String> programs;

    @FXML
    void initialize() throws CloneNotSupportedException {
        Stmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        try {
            ex1.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex1");
        }

        PrgState prg1 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex1);
        Repository repository1 = new MemoryRepository(prg1, "log1.txt");
        Controller controller1 = new Controller(repository1);
        Stmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ArithExp("+",
                                                new ValueExp(new IntValue(2)),
                                                new ArithExp("*",
                                                        new ValueExp(new IntValue(3)),
                                                        new ValueExp(new IntValue(5))
                                                )
                                        )),
                                new CompStmt(
                                        new AssignStmt("b",
                                                new ArithExp("+",
                                                        new VarExp("a"),
                                                        new ValueExp(new IntValue(1))
                                                )),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("b")),
                                                new IfStmt(
                                                        new RelationalExp("<", new VarExp("a"), new VarExp("b")),
                                                        new PrintStmt(new ValueExp(new StringValue("a is smaller than b"))),
                                                        new PrintStmt(new ValueExp(new StringValue("b is smaller than a")))
                                                )
                                        )
                                )
                        )
                )

        );
        try {
            ex2.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex2");
        }
        PrgState prg2 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex2);
        Repository repository2 = new MemoryRepository(prg2, "log2.txt");
        Controller controller2 = new Controller(repository2);
        Stmt ex3 = new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
        try {
            ex3.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex3");
        }
        PrgState prg3 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex3);
        Repository repository3 = new MemoryRepository(prg3, "log3.txt");
        Controller controller3 = new Controller(repository3);
        Stmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new openRFile(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new readFile(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new readFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new closeRFile(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            ex4.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex4");
        }
        PrgState prg4 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex4);
        Repository repository4 = new MemoryRepository(prg4, "log4.txt");
        Controller controller4 = new Controller(repository4);

        Stmt ex5 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new newStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new rHExp(new VarExp("v"))),
                                new CompStmt(
                                        new wHStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp("+", new rHExp(new VarExp("v")), new ValueExp(new IntValue(5))))
                                )
                        )
                )
        );
        try {
            ex5.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex5");
        }
        PrgState prg5 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex5);
        Repository repository5 = new MemoryRepository(prg5, "log5.txt");
        Controller controller5 = new Controller(repository5);

        Stmt ex6 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new newStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new newStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new rHExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp("+", new rHExp(new rHExp(new VarExp("a"))), new ValueExp(new IntValue(5))))
                                        )
                                )
                        )
                )
        );
        try {
            ex6.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex6");
        }
        PrgState prg6 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex6);
        Repository repository6 = new MemoryRepository(prg6, "log6.txt");
        Controller controller6 = new Controller(repository6);

        Stmt ex7 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1))))
                                        )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        try {
            ex7.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex7");
        }
        PrgState prg7 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex7);
        Repository repository7 = new MemoryRepository(prg7, "log7.txt");
        Controller controller7 = new Controller(repository7);

        Stmt ex8 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new newStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new newStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new newStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new rHExp(new rHExp(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );
        try {
            ex8.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex8");
        }
        PrgState prg8 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex8);
        Repository repository8 = new MemoryRepository(prg8, "log8.txt");
        Controller controller8 = new Controller(repository8);
        Stmt ex9 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new newStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new forkStmt(
                                                        new CompStmt(
                                                                new wHStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new rHExp(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new rHExp(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            ex9.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex9");
        }
        PrgState prg9 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex9);
        Repository repository9 = new MemoryRepository(prg9, "log9.txt");
        Controller controller9 = new Controller(repository9);


        Stmt ex10 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new newStmt("v", new ValueExp(new IntValue(0))),
                        new CompStmt(
                                new VarDeclStmt("x", new IntType()),
                                new CompStmt(
                                        new AssignStmt("x", new ValueExp(new IntValue(10))),
                                        new CompStmt(
                                                new WhileStmt(
                                                        new RelationalExp("<", new VarExp("x"), new ValueExp(new IntValue(20))),
                                                        new CompStmt(
                                                                new newStmt("v", new ArithExp("+", new rHExp(new VarExp("v")), new ValueExp(new IntValue(1)))),
                                                                new AssignStmt("x", new ArithExp("+", new VarExp("x"), new ValueExp(new IntValue(1))))
                                                        )
                                                ),
                                                new PrintStmt(new rHExp(new VarExp("v")))
                                        )
                                )
                        )
                )
        );
        try {
            ex10.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex10");
        }
        PrgState prg10 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), ex10);
        Repository repository10 = new MemoryRepository(prg10, "log10.txt");
        Controller controller10 = new Controller(repository10);

        programs = FXCollections.observableArrayList();
        programs.add(ex1.toString());
        programs.add(ex2.toString());
        programs.add(ex3.toString());
        programs.add(ex4.toString());
        programs.add(ex5.toString());
        programs.add(ex6.toString());
        programs.add(ex7.toString());
        programs.add(ex8.toString());
        programs.add(ex9.toString());
        programs.add(ex10.toString());
        programListView.setItems(programs);

    }
}