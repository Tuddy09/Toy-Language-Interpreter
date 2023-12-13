package main.a7.Model.Types;

import main.a7.Model.Values.StringValue;
import main.a7.Model.Values.Value;

public class StringType implements Type {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
