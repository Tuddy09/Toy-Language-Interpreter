module main.a7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.a7 to javafx.fxml;
    exports main.a7;
}