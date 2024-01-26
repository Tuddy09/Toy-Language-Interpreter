package main.a7.Model.DataModel;

import javafx.beans.property.SimpleStringProperty;
import main.a7.Model.Values.Value;

public class LatchTableEntry {
    private final SimpleStringProperty latchLocation;
    private final SimpleStringProperty latchValue;

    public LatchTableEntry(Integer latchLocation, Integer latchValue) {
        this.latchLocation = new SimpleStringProperty(Integer.toString(latchLocation));
        this.latchValue = new SimpleStringProperty(Integer.toString(latchValue));
    }

    public SimpleStringProperty latchLocationProperty() {
        return this.latchLocation;
    }

    public SimpleStringProperty heapValueProperty() {
        return this.latchValue;
    }

    public String getLatchLocation() {
        return this.latchLocation.get();
    }

    public String getLatchValue() {
        return this.latchValue.get();
    }

    public void setLatchLocation(Integer newLatchLocation) {
        this.latchLocation.set(String.valueOf(latchLocation));
    }

    public void setLatchValue(Integer newLatchValue) {
        this.latchLocation.set(newLatchValue.toString());
    }
}
