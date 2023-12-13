package main.a7.Model.Types;

import main.a7.Model.Values.BoolValue;
import main.a7.Model.Values.Value;

public class BoolType implements Type {
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
