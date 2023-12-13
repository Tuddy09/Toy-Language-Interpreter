package main.a7.Model.Values;

import main.a7.Model.Types.BoolType;
import main.a7.Model.Types.Type;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BoolValue value)) {
            return false;
        }
        return val == value.getVal();
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
}
