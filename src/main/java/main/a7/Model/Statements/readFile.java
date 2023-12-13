package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.IntValue;
import main.a7.Model.Values.StringValue;
import main.a7.Model.Types.IntType;
import main.a7.Model.Types.StringType;
import main.a7.Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements Stmt {
    String varName;
    Exp expression;

    public readFile(Exp exp, String var) {
        varName = var;
        expression = exp;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + varName + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyDictionary<String, Value> symTable = state.getSymTable();
        MyHeap<Integer, Value> heap = state.getHeap();
        MyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (symTable.isDefined(varName)) {
            Value value = symTable.get(varName);
            if (value.getType().equals(new IntType())) {
                Value expValue = expression.eval(symTable, heap);
                if (expValue.getType().equals(new StringType())) {
                    StringValue stringValue = (StringValue) expValue;
                    if (fileTable.isDefined(stringValue)) {
                        BufferedReader bufferedReader = fileTable.get(stringValue);
                        String lineRead = bufferedReader.readLine();
                        IntValue intValue;
                        if (lineRead == null) {
                            intValue = new IntValue(0);
                        } else {
                            intValue = new IntValue(Integer.parseInt(lineRead));
                        }
                        symTable.put(varName, intValue);
                        return null;
                    } else throw new MyException("The file is not opened in our program!");
                } else throw new MyException("The expression evaluation has to be a String!");
            } else throw new MyException("The variable value is not an Int");
        } else throw new MyException("The variable is not defined!");
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type.equals(new StringType())) {
            return typeEnv;
        } else throw new MyException("The expression is not a String type!");
    }
}
