package main.a7.Model.Types;

import main.a7.Model.Values.RefValue;
import main.a7.Model.Values.Value;

public class RefType implements Type {

    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType)
            return inner.equals(((RefType) obj).getInner());
        else
            return false;
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }
}
