package main.a7;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import main.a7.Model.DataModel.HeapEntry;
import main.a7.Model.DataModel.LatchTableEntry;
import main.a7.Model.DataModel.SymbolTableEntry;
import main.a7.Model.DataStructures.*;
import main.a7.Model.PrgState;
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
    public TableView<LatchTableEntry> latchTableView;
    @FXML
    public TableColumn<LatchTableEntry, String> latchLocationColumn;
    @FXML
    public TableColumn<LatchTableEntry, String> latchValueColumn;

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
        this.windowController.getProgramListView().getSelectionModel().selectedItemProperty().addListener((a, b, ex) -> this.showDataForSelectedExample(ex));
    }


    @FXML
    private void initialize() {
        this.numberOfPrgStatesTextField.setEditable(false);

        this.addressColumn.setCellValueFactory(new PropertyValueFactory<>("heapAddress"));
        this.valueHeapColumn.setCellValueFactory(new PropertyValueFactory<>("heapValue"));

        this.varNameColumn.setCellValueFactory(new PropertyValueFactory<>("variableName"));
        this.symbolTableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        this.latchLocationColumn.setCellValueFactory(new PropertyValueFactory<>("latchLocation"));
        this.latchValueColumn.setCellValueFactory(new PropertyValueFactory<>("latchValue"));

        this.outListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(Value valueInterface) {
                return valueInterface.toString();
            }

            @Override
            public Value fromString(String s) {
                return null;
            }
        }));

        this.fileTableListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(StringValue stringValue) {
                return stringValue.toString();
            }

            @Override
            public StringValue fromString(String s) {
                return null;
            }
        }));

        this.prgStatesIdsListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(PrgState programState) {
                return programState.getId();
            }

            @Override
            public PrgState fromString(String s) {
                return null;
            }
        }));

        this.executionStackListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
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
        this.latchTableView.getItems().clear();

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
        MyLatchTable<Integer, Integer> latchTable = this.selectedPrg.getLatchTable();

        // We update their content with the new content
        sharedHeap.getContent().forEach((address, value) -> this.heapTableView.getItems().add(new HeapEntry(address, value)));
        fileTable.getContent().forEach((fileName, filePath) -> this.fileTableListView.getItems().add(fileName));
        output.getContent().forEach((value) -> this.outListView.getItems().add(value));
        latchTable.getContent().forEach((location, value) -> this.latchTableView.getItems().add(new LatchTableEntry(location, value)));

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