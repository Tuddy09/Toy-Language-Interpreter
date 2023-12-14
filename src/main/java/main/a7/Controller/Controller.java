package main.a7.Controller;

import main.a7.Model.MyException;
import main.a7.Model.PrgState;
import main.a7.Model.Values.RefValue;
import main.a7.Model.Values.Value;
import main.a7.Repository.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    Repository repository;
    ExecutorService executor;

    public Controller(Repository repo) {
        repository = repo;
    }

    Map<Integer, Value> enhancedGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<Value> heap) {
        return heap.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .toList();
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        if (e.getCause() == null)
                            throw new RuntimeException();
                        else {
                            try {
                                throw new MyException(e.getCause().toString());
                            } catch (MyException ex) {
                                System.err.println(ex.toString());
                                throw new RuntimeException();
                            }
                        }
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        repository.setPrgList(prgList);
    }

    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while (!prgList.isEmpty()) {
            List<Integer> symTableAddr = new ArrayList<>();
            prgList.forEach(prg -> symTableAddr.addAll(getAddrFromSymTable(prg.getSymTable().getContent().values())));
            prgList.getLast().getHeap().setContent(enhancedGarbageCollector(symTableAddr, getAddrFromHeap(prgList.getLast().getHeap().getContent().values()),
                    prgList.getLast().getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    @Override
    public String toString() {
        return repository.toString();
    }

    public Repository getRepo() {
        return this.repository;
    }

    public void oneStepExecution() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        if (!prgList.isEmpty()) {
            List<Integer> symTableAddr = new ArrayList<>();
            prgList.forEach(prg -> symTableAddr.addAll(getAddrFromSymTable(prg.getSymTable().getContent().values())));
            prgList.getLast().getHeap().setContent(enhancedGarbageCollector(symTableAddr, getAddrFromHeap(prgList.getLast().getHeap().getContent().values()),
                    prgList.getLast().getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }
}
