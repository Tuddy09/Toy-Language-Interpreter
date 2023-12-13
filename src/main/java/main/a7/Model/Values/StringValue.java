package main.a7.Model.Values;

import main.a7.Model.Types.StringType;
import main.a7.Model.Types.Type;

import java.util.Objects;

public class StringValue implements Value {
    String val;

    public StringValue(String v) {
        val = v;
    }

    public String getVal() {
        return val;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StringValue value)) {
            return false;
        }
        return Objects.equals(val, value.getVal());
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }
}
