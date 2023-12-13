package main.a7.Model.Values;

import main.a7.Model.Types.IntType;
import main.a7.Model.Types.Type;

public class IntValue implements Value {
    int val;

    public IntValue(int v) {
        val = v;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntValue value)) {
            return false;
        }
        return val == value.getVal();
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    public int getVal() {
        return val;
    }


    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }
}
