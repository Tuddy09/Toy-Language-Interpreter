package main.a7.Model.Values;

import main.a7.Model.Types.RefType;
import main.a7.Model.Types.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RefValue value)) {
            return false;
        }
        return value.address == address;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }

    public Type getLocationType() {
        return locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType);
    }
}
