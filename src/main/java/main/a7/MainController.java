package main.a7;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML
    public TableView heapTableView;
    @FXML
    public TextField numberOfPrgStatesTextField;
    @FXML
    public ListView prgStatesIdsListView;
    @FXML
    public ListView outListView;
    @FXML
    public Button runOneStepButton;
    @FXML
    public TableView symbolTableView;
    @FXML
    public ListView fileTableListView;
    @FXML
    public ListView executionStackListView;
    @FXML
    public TableColumn varNameColumn;
    @FXML
    public TableColumn symbolTableValueColumn;
    @FXML
    public TableColumn addressColumn;
    @FXML
    public TableColumn valueHeapColumn;
}