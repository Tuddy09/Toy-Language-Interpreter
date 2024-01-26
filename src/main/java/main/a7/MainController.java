package main.a7;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import main.a7.Model.DataModel.HeapEntry;
import main.a7.Model.DataModel.SemaphoreTableEntry;
import main.a7.Model.DataModel.SymbolTableEntry;
import main.a7.Model.DataStructures.MyDictionary;
import main.a7.Model.DataStructures.MyHeap;
import main.a7.Model.DataStructures.MyList;
import main.a7.Model.DataStructures.MyStack;
import main.a7.Model.PrgState;
import main.a7.Model.ProgramState.SemaphoreTable;
import main.a7.Model.Statements.Stmt;
import main.a7.Model.Values.StringValue;
import main.a7.Model.Values.Value;
import main.a7.View.RunExample;

import java.io.BufferedReader;
import java.util.List;

public class MainController {
    private WindowController windowController;
    private RunExample selectedExample;
    private PrgState selectedPrg;

    @FXML
    public TableView<HeapEntry> heapTableView;
    @FXML
    public TextField numberOfPrgStatesTextField;
    @FXML
    public ListView<PrgState> prgStatesIdsListView;
    @FXML
    public ListView<Value> outListView;
    @FXML
    public Button runOneStepButton;
    @FXML
    public TableView<SymbolTableEntry> symbolTableView;
    @FXML
    public ListView<StringValue> fileTableListView;
    @FXML
    public ListView<Stmt> executionStackListView;
    @FXML
    public TableColumn<SymbolTableEntry, String> varNameColumn;
    @FXML
    public TableColumn<SymbolTableEntry, String> symbolTableValueColumn;
    @FXML
    public TableColumn<HeapEntry, String> addressColumn;
    @FXML
    public TableColumn<HeapEntry, String> valueHeapColumn;
    @FXML
    public TableView<SemaphoreTableEntry> semaphoreTableView;
    @FXML
    public TableColumn<SemaphoreTableEntry, String> semaphoreVarNameColumn;
    @FXML
    public TableColumn<SemaphoreTableEntry, String> semaphoreValueColumn;
    @FXML
    public TableColumn<SemaphoreTableEntry, List<String>> semaphoreListColumn;

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
        this.windowController.getProgramListView().getSelectionModel().selectedItemProperty().addListener((a, b, ex) -> this.showDataForSelectedExample(ex));
    }


    @FXML
    private void initialize() {
        this.numberOfPrgStatesTextField.setEditable(false);

        this.addressColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("heapAddress"));
        this.valueHeapColumn.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("heapValue"));

        this.varNameColumn.setCellValueFactory(new PropertyValueFactory<SymbolTableEntry, String>("variableName"));
        this.symbolTableValueColumn.setCellValueFactory(new PropertyValueFactory<SymbolTableEntry, String>("value"));

        this.semaphoreVarNameColumn.setCellValueFactory(new PropertyValueFactory<SemaphoreTableEntry, String>("identifier"));
        this.semaphoreValueColumn.setCellValueFactory(new PropertyValueFactory<SemaphoreTableEntry, String>("value"));
        this.semaphoreListColumn.setCellValueFactory(new PropertyValueFactory<SemaphoreTableEntry, List<String>>("list"));

        this.outListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<Value>() {
            @Override
            public String toString(Value valueInterface) {
                return valueInterface.toString();
            }

            @Override
            public Value fromString(String s) {
                return null;
            }
        }));

        this.fileTableListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<StringValue>() {
            @Override
            public String toString(StringValue stringValue) {
                return stringValue.toString();
            }

            @Override
            public StringValue fromString(String s) {
                return null;
            }
        }));

        this.prgStatesIdsListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<PrgState>() {
            @Override
            public String toString(PrgState programState) {
                return programState.getId();
            }

            @Override
            public PrgState fromString(String s) {
                return null;
            }
        }));

        this.executionStackListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<Stmt>() {
            @Override
            public String toString(Stmt statementInterface) {
                return statementInterface.toString();
            }

            @Override
            public Stmt fromString(String s) {
                return null;
            }
        }));


        // Like that we are able to select only one item from the list
        this.prgStatesIdsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // We create a listener on the programStatesListView, which in our case will look if there are changes regarding
        // the current program state, more exactly, if there is a current program state selected, and it is changed,
        // the necessary changes in the tables/lists will be done as well
        this.prgStatesIdsListView.getSelectionModel().selectedItemProperty().addListener((a, b, state) -> {
            if (state != null)
                showDataForSelectedProgramState(state);

        });

        // We activate the button and connect it to the runOneStep function
        this.runOneStepButton.setOnAction(actionEvent -> runOneStep(this.windowController.getProgramListView().getSelectionModel().getSelectedItems().getFirst()));

    }

    private void showDataForSelectedExample(RunExample example) {
        // We clear all the tables and lists and prepare them for new data
        this.heapTableView.getItems().clear();
        this.outListView.getItems().clear();
        this.fileTableListView.getItems().clear();
        this.prgStatesIdsListView.getItems().clear();
        this.symbolTableView.getItems().clear();
        this.executionStackListView.getItems().clear();
        this.semaphoreTableView.getItems().clear();

        // We get the list with the existing programStates
        List<PrgState> programStates = example.getController().getRepo().getPrgList();

        // If the size is still different from 0 we can update the current programState
        if (!programStates.isEmpty())
            this.selectedPrg = programStates.getFirst();

        // We get the heap, fileTable and output based on the programState 0 because they are shared and have the same
        // values for each program state
        MyHeap<Integer, Value> sharedHeap = this.selectedPrg.getHeap();
        MyDictionary<StringValue, BufferedReader> fileTable = this.selectedPrg.getFileTable();
        MyList<Value> output = this.selectedPrg.getOut();
        SemaphoreTable semaphoreTable = this.selectedPrg.getSemaphoreTable();

        // We update their content with the new content
        sharedHeap.getContent().forEach((address, value) -> this.heapTableView.getItems().add(new HeapEntry(address, value)));
        fileTable.getContent().forEach((fileName, filePath) -> this.fileTableListView.getItems().add(fileName));
        output.getContent().forEach((value) -> this.outListView.getItems().add(value));
        semaphoreTable.getContent().forEach((var, pair) -> this.semaphoreTableView.getItems().add(new SemaphoreTableEntry(var, pair.getKey(), pair.getValue())));
        programStates.forEach((programState) -> this.prgStatesIdsListView.getItems().add(programState));

        // We update the number of program states
        this.numberOfPrgStatesTextField.setText(Integer.toString(programStates.size()));

    }

    private void showDataForSelectedProgramState(PrgState program) {
        //We clear the execution stack and the symbol stack
        this.symbolTableView.getItems().clear();
        this.executionStackListView.getItems().clear();

        MyStack<Stmt> executionStack = program.getExeStack();
        MyDictionary<String, Value> symbolTable = program.getSymTable();

        //We are going to repopulate them back with the "new values" if it's the case
        executionStack.getContent().forEach((statement) -> this.executionStackListView.getItems().add(statement));
        symbolTable.getContent().forEach((name, value) -> this.symbolTableView.getItems().add(new SymbolTableEntry(name, value)));
    }

    private void runOneStep(RunExample ex) {
        try {
            ex.getController().oneStepExecution();
        } catch (Exception ignored) {

        }
        showDataForSelectedExample(ex);
    }
}