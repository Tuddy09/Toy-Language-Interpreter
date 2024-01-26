package main.a7.Model.DataModel;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class SemaphoreTableEntry {
    private final SimpleStringProperty identifier;
    private final SimpleStringProperty value;
    private final SimpleStringProperty list;

    public SemaphoreTableEntry(Integer identifier, Integer value, List<Integer> list) {
        this.identifier = new SimpleStringProperty(Integer.toString(identifier));
        this.value = new SimpleStringProperty(Integer.toString(value));
        this.list = new SimpleStringProperty(list.toString());
    }

    public SimpleStringProperty identifierProperty() {
        return this.identifier;
    }

    public SimpleStringProperty valueProperty() {
        return this.value;
    }

    public SimpleStringProperty listProperty() {
        return this.list;
    }

    public String getIdentifier() {
        return this.identifier.get();
    }

    public String getValue() {
        return this.value.get();
    }

    public String getList() {
        return this.list.get();
    }

    public void setIdentifier(Integer newIdentifier) {
        this.identifier.set(String.valueOf(identifier));
    }

    public void setValue(Integer newValue) {
        this.value.set(String.valueOf(value));
    }

    public void setList(List<Integer> newList) {
        this.list.set(newList.toString());
    }

}
