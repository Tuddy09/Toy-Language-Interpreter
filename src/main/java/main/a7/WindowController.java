package main.a7;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
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
import main.a7.View.RunExample;


public class WindowController {
    @FXML
    public Label selectProgramLabel;
    @FXML
    public ListView<RunExample> programListView;

    public ListView<RunExample> getProgramListView() {
        return programListView;
    }


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

        PrgState prg1 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex1);
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
        PrgState prg2 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex2);
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
        PrgState prg3 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex3);
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
        PrgState prg4 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex4);
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
        PrgState prg5 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex5);
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
        PrgState prg6 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex6);
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
        PrgState prg7 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex7);
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
        PrgState prg8 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex8);
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
        PrgState prg9 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex9);
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
        PrgState prg10 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex10);
        Repository repository10 = new MemoryRepository(prg10, "log10.txt");
        Controller controller10 = new Controller(repository10);
        Stmt ex11 = new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("b", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v", new IntType()),
                                new CompStmt(
                                        new newStmt("a", new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new newStmt("b", new ValueExp(new IntValue(0))),
                                                new CompStmt(
                                                        new wHStmt("a", new ValueExp(new IntValue(1))),
                                                        new CompStmt(
                                                                new wHStmt("b", new ValueExp(new IntValue(2))),
                                                                new CompStmt(
                                                                        new CondAssignStmt("v",
                                                                                new RelationalExp("<", new rHExp(new VarExp("a")), new rHExp(new VarExp("b"))),
                                                                                new ValueExp(new IntValue(100)),
                                                                                new ValueExp(new IntValue(200))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new CompStmt(
                                                                                        new CondAssignStmt("v",
                                                                                                new RelationalExp(">",
                                                                                                        new ArithExp("-", new rHExp(new VarExp("b")), new ValueExp(new IntValue(2))),
                                                                                                        new rHExp(new VarExp("a"))),
                                                                                                new ValueExp(new IntValue(100)),
                                                                                                new ValueExp(new IntValue(200))),
                                                                                        new PrintStmt(new VarExp("v"))
                                                                                )
                                                                        )

                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            ex11.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex11");
        }
        PrgState prg11 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex11);
        Repository repository11 = new MemoryRepository(prg11, "log11.txt");
        Controller controller11 = new Controller(repository11);

        //write this example in code Ref int v1; int cnt;
        //new(v1,1);createSemaphore(cnt,rH(v1));
        //fork(acquire(cnt);wh(v1,rh(v1)*10));print(rh(v1));release(cnt));
        //fork(acquire(cnt);wh(v1,rh(v1)*10));wh(v1,rh(v1)*2));print(rh(v1));release(cnt
        //));
        //acquire(cnt);
        //print(rh(v1)-1);
        //release(cnt)
        Stmt ex12 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(
                                new newStmt("v1", new ValueExp(new IntValue(1))),
                                new CompStmt(
                                        new createSemaphore("cnt", new rHExp(new VarExp("v1"))),
                                        new CompStmt(
                                                new forkStmt(
                                                        new CompStmt(
                                                                new acquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new wHStmt("v1", new ArithExp("*", new rHExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new rHExp(new VarExp("v1"))),
                                                                                new releaseStmt("cnt")
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new forkStmt(
                                                                new CompStmt(
                                                                        new acquireStmt("cnt"),
                                                                        new CompStmt(
                                                                                new wHStmt("v1", new ArithExp("*", new rHExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                new CompStmt(
                                                                                        new wHStmt("v1", new ArithExp("*", new rHExp(new VarExp("v1")), new ValueExp(new IntValue(2)))),
                                                                                        new CompStmt(
                                                                                                new PrintStmt(new rHExp(new VarExp("v1"))),
                                                                                                new releaseStmt("cnt")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new acquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new PrintStmt(new ArithExp("-", new rHExp(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                                                                        new releaseStmt("cnt")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            ex12.typecheck(new FileTable<>());
        } catch (MyException msg) {
            System.err.println(msg + "\ntypecheck error in ex12");
        }
        PrgState prg12 = new PrgState(new ExecutionStack<>(), new SymTable(), new Out<>(), new FileTable<>(), new Heap<>(), new SemaphoreTable(), ex12);
        Repository repository12 = new MemoryRepository(prg12, "log12.txt");
        Controller controller12 = new Controller(repository12);
        programListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(RunExample runExample) {
                return runExample.toString();
            }

            @Override
            public RunExample fromString(String s) {
                return null;
            }
        }));
        programListView.getItems().add(new RunExample("1", ex1.toString(), controller1));
        programListView.getItems().add(new RunExample("2", ex2.toString(), controller2));
        programListView.getItems().add(new RunExample("3", ex3.toString(), controller3));
        programListView.getItems().add(new RunExample("4", ex4.toString(), controller4));
        programListView.getItems().add(new RunExample("5", ex5.toString(), controller5));
        programListView.getItems().add(new RunExample("6", ex6.toString(), controller6));
        programListView.getItems().add(new RunExample("7", ex7.toString(), controller7));
        programListView.getItems().add(new RunExample("8", ex8.toString(), controller8));
        programListView.getItems().add(new RunExample("9", ex9.toString(), controller9));
        programListView.getItems().add(new RunExample("10", ex10.toString(), controller10));
        programListView.getItems().add(new RunExample("11", ex11.toString(), controller11));
        programListView.getItems().add(new RunExample("12", ex12.toString(), controller12));

        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}