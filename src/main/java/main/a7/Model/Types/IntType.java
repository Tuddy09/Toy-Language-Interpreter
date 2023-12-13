package main.a7.Model.Types;

import main.a7.Model.Values.IntValue;
import main.a7.Model.Values.Value;

public class IntType implements Type {
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
