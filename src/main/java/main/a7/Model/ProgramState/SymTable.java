package main.a7.Model.ProgramState;

import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class SymTable implements MyDictionary<String, Value> {
    Map<String, Value> values_table;

    public SymTable() {
        values_table = new HashMap<>();
    }

    @Override
    public Value get(String key) {
        return values_table.getOrDefault(key, null);
    }

    @Override
    public boolean isDefined(String key) {
        return values_table.containsKey(key);
    }


    @Override
    public void put(String key, Value value) {
        values_table.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        for (String key : values_table.keySet()) {
            resultString.append(key);
            resultString.append(" --> ");
            resultString.append(values_table.get(key).toString());
            resultString.append('\n');
        }
        return resultString.toString();
    }

    @Override
    public void update(String key, Value value) {
        values_table.put(key, value);
    }

    @Override
    public void remove(String key) {
        values_table.remove(key);
    }

    @Override
    public Map<String, Value> getContent() {
        return values_table;
    }

    public SymTable deepCopy() {
        SymTable copy = new SymTable();
        for (Map.Entry<String, Value> entry : this.values_table.entrySet()) {
            copy.values_table.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return copy;
    }


}
