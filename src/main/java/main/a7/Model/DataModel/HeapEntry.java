package main.a7.Model.DataModel;

import javafx.beans.property.SimpleStringProperty;
import main.a7.Model.Values.Value;

public class HeapEntry {
    private final SimpleStringProperty heapAddress;
    private final SimpleStringProperty heapValue;

    public HeapEntry(Integer heapAddress, Value heapValue){
        this.heapAddress = new SimpleStringProperty(Integer.toString(heapAddress));
        this.heapValue = new SimpleStringProperty(heapValue.toString());
    }

    public SimpleStringProperty heapAddressProperty() {
        return this.heapAddress;
    }
    public SimpleStringProperty heapValueProperty(){
        return this.heapValue;
    }

    public String getHeapAddress(){
        return this.heapAddress.get();
    }

    public String getHeapValue(){
        return this.heapValue.get();
    }

    public void setHeapAddress(Integer newHeapAddress){
        this.heapAddress.set(String.valueOf(newHeapAddress));
    }

    public void setHeapValue(Value newHeapValue){
        this.heapValue.set(newHeapValue.toString());
    }

}
