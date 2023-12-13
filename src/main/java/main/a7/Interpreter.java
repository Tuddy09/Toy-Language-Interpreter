package main.a7;

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
import main.a7.View.ExitCommand;
import main.a7.View.RunExample;
import main.a7.View.TextMenu;

public class Interpreter {


//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1", ex1.toString(), controller1));
//        menu.addCommand(new RunExample("2", ex2.toString(), controller2));
//        menu.addCommand(new RunExample("3", ex3.toString(), controller3));
//        menu.addCommand(new RunExample("4", ex4.toString(), controller4));
//        menu.addCommand(new RunExample("5", ex5.toString(), controller5));
//        menu.addCommand(new RunExample("6", ex6.toString(), controller6));
//        menu.addCommand(new RunExample("7", ex7.toString(), controller7));
//        menu.addCommand(new RunExample("8", ex8.toString(), controller8));
//        menu.addCommand(new RunExample("9", ex9.toString(), controller9));
//        menu.addCommand(new RunExample("10", ex10.toString(), controller10));
//        menu.show();
//    }
}
