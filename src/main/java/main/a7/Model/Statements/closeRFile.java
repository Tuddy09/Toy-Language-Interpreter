package main.a7.Model.Statements;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.Expressions.Exp;
import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Types.Type;
import main.a7.Model.Values.StringValue;
import main.a7.Model.Types.StringType;
import main.a7.Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements Stmt {
    Exp expression;

    public closeRFile(Exp exp) {
        expression = exp;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new StringType())) {
            StringValue stringValue = (StringValue) value;
            if (fileTable.isDefined(stringValue)) {
                BufferedReader fileD = fileTable.get(stringValue);
                fileD.close();
                fileTable.remove(stringValue);
                return null;
            } else throw new MyException("The file is not an entry in the fileTable");
        } else throw new MyException("The expression does not return a StringValue");
    }

    @Override
    public MyDictionary<String, Type> typecheck(MyDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type.equals(new StringType())) {
            return typeEnv;
        } else throw new MyException("The expression is not a String type!");
    }
}
