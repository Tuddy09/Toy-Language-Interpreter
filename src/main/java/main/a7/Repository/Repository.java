package main.a7.Repository;

import main.a7.Model.PrgState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Repository {

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> newPrgList);

    void logPrgStateExec(PrgState prgState) throws IOException;
}
