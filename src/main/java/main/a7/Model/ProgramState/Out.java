package main.a7.Model.ProgramState;

import main.a7.Model.DataStructures.MyList;

import java.util.ArrayList;

public class Out<E> implements MyList<E> {
    ArrayList<E> out;

    public Out() {
        out = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (E elem : out) {
            stringBuilder.append(elem.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void add(E elem) {
        out.add(elem);
    }

}
