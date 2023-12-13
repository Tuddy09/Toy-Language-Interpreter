package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.StringValue;
import main.a7.Model.Types.StringType;
import main.a7.Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFile implements Stmt {
    Exp expression;

    public openRFile(Exp exp) {
        expression = exp;
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException {
        MyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        MyDictionary<String, Value> symTable = state.getSymTable();
        Value exp_value = expression.eval(symTable, heap);
        if (exp_value.getType().equals(new StringType())) {
            StringValue stringValue = (StringValue) exp_value;
            if (fileTable.isDefined(stringValue)) {
                throw new MyException("The file is already defined in the file table!");
            } else {
                String computedStringValue = stringValue.getVal();
                BufferedReader fileD = new BufferedReader(new FileReader(computedStringValue));
                fileTable.put(stringValue, fileD);
                return null;
            }
        } else throw new MyException("The expression is not a StringValue");
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type.equals(new StringType())) {
            return typeEnv;
        } else throw new MyException("The expression is not a String type!");
    }
}
