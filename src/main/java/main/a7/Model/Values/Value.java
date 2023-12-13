package main.a7.Model.Values;

import main.a7.Model.Types.Type;

public interface Value {
    Type getType();

    Value deepCopy();
}
